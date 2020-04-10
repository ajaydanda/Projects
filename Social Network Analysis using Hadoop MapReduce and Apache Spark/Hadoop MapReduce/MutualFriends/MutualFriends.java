import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;


public class MutualFriends {
	
	public static class Mapper1 extends Mapper<LongWritable, Text, Text, Text>{
		private Text friends = new Text();
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			
			String[] textvalues = value.toString().split(" ");
			
			String[] outputvalues = textvalues[0].toString().split("\t");
			
			if(outputvalues.length >1){
				
				String[] inputLOF = outputvalues[1].toString().split(",");
				if(inputLOF[0].toString().trim().length() > 0){
					String str3;
					for(String singlefriend : inputLOF){
							if(outputvalues[1].toString().contains(",")){
							String str = outputvalues[1].toString().replace(","+singlefriend+",", ",");
							String str2 = str.toString().replace(",,","," );
						
							
							if(str2.charAt(0) == ','){
									str3 = str2.toString().substring(1);
							}else{
									str3 = str2;
							}
							
							if(str3.charAt(str3.length()-1) == ','){
									str3 = str3.substring(0, str3.length()-1);									
							}
							}else{
								str3 = outputvalues[1].toString();
							}
							
							friends.set(str3);
							Integer currentuser = Integer.parseInt(outputvalues[0].toString().trim());
							Integer prevuser = Integer.parseInt(singlefriend.toString());
							
							Text outputkey = new Text();
							
							if (prevuser - currentuser > 0){
									outputkey.set(currentuser + "," + prevuser);
							}else{
									outputkey.set(prevuser + "," + currentuser);
							}
							
							if(friends.toString().trim().length()>0){
									context.write(outputkey, friends);
							}
					}
				}
			}
		}
	}
	
	public static class Reducer1 extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
				
			String[] friends_list = new String[2];
			int i=0;
			
			for(Text value: values){
					friends_list[i] = value.toString().trim();
					++i;
			}
			
			if(i == 2){
					String[] str1 = friends_list[0].split(",");
					String[] str2 = friends_list[1].split(",");
					Text tempkey = new Text(key.toString());
					Long[] long1 = new Long[str1.length];
					Long[] long2 = new Long[str2.length];
					
					for(int j=0; j < str1.length ; j++){
							str1[j] = str1[j].toString().trim();
							long1[j] = Long.parseLong(str1[j].toString());
					}
					
					for(int k=0; k < str2.length ; k++){
							str2[k] = str2[k].toString().trim();
							long2[k] = Long.parseLong(str2[k].toString());
					}
					
					ArrayList<Long> list1 = new ArrayList<Long>(Arrays.asList(long1));
					ArrayList<Long> list2 = new ArrayList<Long>(Arrays.asList(long2));
					
					if(list1.size() > list2.size()){
							list1.retainAll(list2);
							list2.retainAll(list1);
					}else{
							list2.retainAll(list1);
							list1.retainAll(list2);
					}
						
					key.set(key.toString() + " ");
					Text newkey = new Text(key.toString());
					
					
					if(tempkey.toString().equals(new String("0,1")) || tempkey.toString().equals(new String("20,28193")) || tempkey.toString().equals(new String("1,29826")) 
							|| tempkey.toString().equals(new String("6222,19272")) || tempkey.toString().equals(new String("28041,28056")) ){
							
							if(list1.size() >= 1)
							context.write(newkey, new Text(StringUtils.join(", ", list1)));
					}
			}
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String[] terminalargs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (terminalargs.length != 2) {
			System.err.println("Error!!");
			System.exit(1); 	
		}
		Job job = Job.getInstance(conf,"MutualFriends");
		job.setJarByClass(MutualFriends.class);
		job.setMapperClass(Mapper1.class);
		job.setReducerClass(Reducer1.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(terminalargs[0]));
		
		FileOutputFormat.setOutputPath(job, new Path(terminalargs[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}

