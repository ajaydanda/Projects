import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.util.*;
import java.io.IOException;

public class TopTenMutual
{

    public static class Mapper1 extends Mapper<LongWritable,Text,Text,Text>
    {
        private Text outputvalues =new Text();
        public void map(LongWritable key,Text value,Context context) throws InterruptedException,IOException
        {
            String[] inputvalues = value.toString().split("\t");
            String tempuser = inputvalues[0];
            if (inputvalues.length == 2) 
            {
                String[] division = inputvalues[1].split(",");
                List<String> friends = Arrays.asList(division);
                for (String singlefriend : friends)
                {
                    int userid = Integer.parseInt(tempuser);
                    int friendid = Integer.parseInt(singlefriend);
                    if (userid < friendid)
                    {
                        outputvalues.set(tempuser+","+singlefriend);
                    }
                    else
                    {
                        outputvalues.set(singlefriend+","+tempuser);
                    }
                    context.write(outputvalues,new Text(inputvalues[1]));
                }
            }
        }
    }

    public static class Reducer1 extends Reducer<Text,Text,Text,IntWritable>
    {
        public void reduce (Text key, Iterable<Text> values, Context context) throws InterruptedException,IOException
        {
            int count =0;
            HashMap<String,Integer> hashmap = new HashMap<String, Integer>();
            for(Text value : values)
            {
                List<String> friends = Arrays.asList(value.toString().split(","));
                for (String friend : friends)
                {
                    if (hashmap.containsKey(friend))
                    {
                        count+=1;
                    }
                    else
                    {
                        hashmap.put(friend,1);
                    }
                }
            }
            context.write(key,new IntWritable(count));

        }
    }


    public static class Mapper2 extends Mapper<LongWritable, Text, IntWritable, Text>
    {
        private final static IntWritable one = new IntWritable(1);
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(one, value);
        }
    }

    private static class Reducer2 extends Reducer<IntWritable,Text,Text,IntWritable>
    {
        public void reduce (IntWritable key, Iterable<Text> values, Context context) throws InterruptedException,IOException
        {
            HashMap<String, Integer> hashmap = new HashMap<String,Integer>();
            for (Text singlevalue : values)
            {
                String[] data = singlevalue.toString().split("\t");
                String pairnames = data[0];
                Integer count = Integer.parseInt(data[1]);
                hashmap.put(pairnames,count);
            }
            ValueComparator vc = new ValueComparator(hashmap);
            TreeMap<String, Integer> done = new TreeMap<String,Integer>(vc);
            done.putAll(hashmap);
            int printcount =10;
            for (Map.Entry<String,Integer> tuple : done.entrySet())
            {
                context.write(new Text(tuple.getKey()), new IntWritable(tuple.getValue()));
                printcount--;
                if (printcount ==0)
                {
                    break;
                }

            }
        }
    }

    static class ValueComparator implements Comparator<Object>
    {
        HashMap<String, Integer> hashmap;
        public ValueComparator(HashMap<String, Integer> tempmap)
        {
            this.hashmap = tempmap;
        }
        @Override
        public int compare(Object k1, Object k2)
        {
            Integer a = (Integer) hashmap.get(k1);
            Integer b = (Integer) hashmap.get(k2);
            if(b >= a)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        Configuration config = new Configuration();
        String[] terminalargs = new GenericOptionsParser(config,args).getRemainingArgs();
        if (terminalargs.length !=3)
        {
            System.err.println("Error!!!");
            System.exit(2);
        }
        Job job1 = Job.getInstance(config,"mutualfriends");
        job1.setJarByClass(TopTenMutual.class);
        job1.setMapperClass(Mapper1.class);
        job1.setReducerClass(Reducer1.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job1,new Path(terminalargs[0]));
        FileOutputFormat.setOutputPath(job1, new Path(terminalargs[1]));

        if (job1.waitForCompletion(true))
        {
            Configuration config2 = new Configuration();
            Job job2 = Job.getInstance(config2, "Q2Top10");
            job2.setJarByClass(TopTenMutual.class);
            job2.setMapperClass(Mapper2.class);
            job2.setReducerClass(Reducer2.class);
            job2.setMapOutputKeyClass(IntWritable.class);
            job2.setMapOutputValueClass(Text.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job2, new Path(args[1]));
            FileOutputFormat.setOutputPath(job2, new Path(args[2]));
            System.exit(job2.waitForCompletion(true) ? 0:1);
        }

    }

}
