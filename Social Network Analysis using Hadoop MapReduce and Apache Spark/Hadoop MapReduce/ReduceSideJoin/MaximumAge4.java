import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MaximumAge4 {
	
	public static class Mapper1 extends Mapper<LongWritable, Text, Text, Text> {

        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] inputvalues = value.toString().split("\\t");
            if (inputvalues.length == 2) {
                String userid = inputvalues[0];
                String friends = inputvalues[1];
                context.write(new Text(userid), new Text(friends));
            }
        }
    }

    public static class Reducer1 extends Reducer<Text, Text, Text, LongWritable> {
        private Map<String, Integer> agemap = new HashMap<>();

        private int computeage(String birthdate) {

            SimpleDateFormat dateformat = new SimpleDateFormat("MM/DD/YYYY");
            Calendar birthtime = Calendar.getInstance();
            Date birthday;
            try {
                birthday = dateformat.parse(birthdate);
                birthtime.setTime(birthday);
            } catch (ParseException e) {
                e.getMessage();
            }
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR)
                    - birthtime.get(Calendar.YEAR);

            if (today.get(Calendar.MONTH) < birthtime.get(Calendar.MONTH)) {
                age--;
            } else {
                if (today.get(Calendar.MONTH) == birthtime.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthtime.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
            }
            return age;
        }

        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            String user_data_path = conf.get("user_data");

            Path user_path = new Path(user_data_path);
            FileSystem fs = FileSystem.get(conf);
            FileStatus[] fss = fs.listStatus(user_path);
            for (FileStatus f : fss) {
                Path path = f.getPath();
                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path)));

                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] user_data = line.split(",");
                    int age = computeage(user_data[9]);
                    agemap.put(user_data[0], age);
                }
            }
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String[] friends = values.iterator().next().toString().split(",");
            int tempage = Integer.MIN_VALUE;
            for (String singlefriend : friends) {
            	if(tempage<agemap.get(singlefriend)) {
            		tempage=agemap.get(singlefriend);
            	} 
            }
            context.write(key, new LongWritable(tempage));
        }
    }

    public static class Mapper2 extends Mapper<LongWritable, Text, LongWritable, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] user_data = value.toString().split("\\t");
            if (user_data.length == 2) {
                String userid = user_data[0];
                String maximumage = user_data[1];
                context.write(new LongWritable(Long.parseLong(maximumage)), new Text(userid));
            }
        }
    }

    public static class Reducer2 extends Reducer<LongWritable, Text, Text, Text> {
        private Map<String, String> addressmap = new HashMap<>();

        private int counter = 1;

        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration conf2 = context.getConfiguration();
            String user_data_path = conf2.get("user_data");

            Path user_path = new Path(user_data_path);
            FileSystem fs = FileSystem.get(conf2);
            FileStatus[] fss = fs.listStatus(user_path);
            for (FileStatus fileStatus : fss) {
                Path path = fileStatus.getPath();
                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path)));
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] user_details = line.split(",");
                    addressmap.put(user_details[0], user_details[3] + "," + user_details[4] + "," + user_details[5]);
                }
            }
        }

        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                if (counter <= 10) {
                    counter++;
                    context.write(value, new Text(addressmap.get(value.toString()) + "," + key.toString()));
                }
            }
        }
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        String[] terminalargs = new GenericOptionsParser(configuration, args).getRemainingArgs();

        if (terminalargs.length != 4) {
            System.err.println("Error!!!");
            System.exit(0);
        }

        String ip = terminalargs[0];
        String dp = terminalargs[1];
        String tp = terminalargs[2];
        String op = terminalargs[3];

        configuration.set("user_data", dp);
        Job job1 = new Job(configuration, "MaximumAge");
        job1.setJarByClass(MaximumAge4.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(LongWritable.class);
        job1.setMapperClass(Mapper1.class);
        job1.setReducerClass(Reducer1.class);
        FileInputFormat.addInputPath(job1, new Path(ip));
        FileOutputFormat.setOutputPath(job1, new Path(tp));

        if (!job1.waitForCompletion(true)) {
            System.exit(0);
        }

        configuration.set("user_data", dp);
        Job job2 = new Job(configuration, "MaximumAgeJob2");
        job2.setJarByClass(MaximumAge4.class);

        job2.setMapperClass(Mapper2.class);
        job2.setReducerClass(Reducer2.class);

        job2.setMapOutputKeyClass(LongWritable.class);
        job2.setMapOutputValueClass(Text.class);

        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);

        job2.setSortComparatorClass(LongWritable.DecreasingComparator.class);

        FileInputFormat.addInputPath(job2, new Path(tp));
        FileOutputFormat.setOutputPath(job2, new Path(op));

        if (!job2.waitForCompletion(true)) {
            System.exit(0);
        }
        System.exit(1);
    }
}
