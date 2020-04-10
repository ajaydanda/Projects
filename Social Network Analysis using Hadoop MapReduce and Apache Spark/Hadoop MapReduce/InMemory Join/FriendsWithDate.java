import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
import org.apache.hadoop.util.StringUtils;


public class FriendsWithDate {

	public static class FriendMapper extends Mapper<LongWritable, Text, Text, Text>{
		HashMap<String,String> map = new HashMap<String, String>();
		
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
							}
							else{
									str3 = str2;
							}
							
							if(str3.charAt(str3.length()-1) == ','){
									str3 = str3.substring(0, str3.length()-1);									
							}
							}
							else{
								str3 = outputvalues[1].toString();
							}
							String[] temp = str3.split(",");
							
							String namedate = "";
							String tempname = "";
							for(String singlefriend2 : temp){
								
								if(map.containsKey(singlefriend2.trim())){
										tempname = map.get(singlefriend2.trim());
										
										if(namedate != ""){
											namedate += "," + tempname;
										}else{
											namedate = tempname;
										}
								}
							}
							
							
							friends.set(namedate); 
							Integer currentuser = Integer.parseInt(outputvalues[0].toString().trim());
							Integer prevuser = Integer.parseInt(singlefriend.toString());
							
							Text output = new Text();
							
							if (prevuser - currentuser > 0){
									output.set(currentuser + "," + prevuser);
							}else{
									output.set(prevuser + "," + currentuser);
							}
							int friend1 = context.getConfiguration().getInt("friend1", 0);
							int friend2 = context.getConfiguration().getInt("friend2", 0);
							boolean flag = checkfunc(output, friend1,friend2);
							
							if(flag){
						
								if(friends.toString().trim().length()>0){
										context.write(output, friends);
								}
							}

					}
				}
			}
		}
		

		private boolean checkfunc(Text output,
				int friend1, int friend2) {
			
			String[] friend = output.toString().split(",");
				
			if((friend1 == Integer.parseInt(friend[0].trim())) && (friend2 == Integer.parseInt(friend[1].trim()))){
					return true;
			}
			return false;
		}



		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
			
			Configuration conf = context.getConfiguration();
			System.out.println("Context Configuration : " + context.getConfiguration());
			System.out.println("USERDATA  context.getConfiguration().get(USERDATA) : " + context.getConfiguration().get("USERDATA"));
			Path part = new Path(context.getConfiguration().get("USERDATA"));
			
			FileSystem fs = FileSystem.get(conf);
			FileStatus[] fsStatus = fs.listStatus(part);
			
			for(FileStatus status : fsStatus){
					Path pt = status.getPath();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
					String oneline;
					oneline = br.readLine();
					
					while(oneline != null){
							String[] attributes = oneline.split(",");
							
							String tempname = attributes[0]+"-"+attributes[1]+":"+attributes[9];
							map.put(attributes[0],tempname);
							oneline = br.readLine();
					}
			}
		}
}	
	

	public static class FriendReducer extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
				
			String[] friends = new String[2];
			int index=0;
			
			for(Text value: values){
					friends[index] = value.toString().trim();
					++index;
			}
			
			if(index == 2){
			
					String[] str1 = friends[0].split(",");
					String[] str2 = friends[1].split(",");
					
					ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(str1));
					ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(str2));
					
					if(list1.size() > list2.size()){
							list1.retainAll(list2);
							list2.retainAll(list1);
					}else{
							list2.retainAll(list1);
							list1.retainAll(list2);
					}

					String friendship = key.toString().trim();
					String[] friends2 = friendship.split(",");
					
					int friend1 = context.getConfiguration().getInt("friend1", 0);
					int friend2 = context.getConfiguration().getInt("friend2", 0);
					
					if(friend1 == Integer.parseInt(friends2[0].toString()) && (friend2 == Integer.parseInt(friends2[1].toString()))){
						new Text(key.toString());
						
						key.set(key.toString() + " ");
						Text newkey = new Text(key.toString());
						
						ArrayList<String> templist1 = new ArrayList<String>();
						String[] str;
						for(String singlevalue : list1){
								str = singlevalue.split("-");
								singlevalue = str[1];
								templist1.add(singlevalue);
						}
					     
						
						if(list1.size() >= 1){
							context.write(newkey, new Text( "[" +StringUtils.join(", ", templist1)+ "]"));
						}else{
								context.write(newkey, new Text("NO COMMON FRIENDS FOUND"));
						}					
					}
			}

		}
				
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String[] terminalargs = new GenericOptionsParser(conf, args).getRemainingArgs();
		String ip = terminalargs[0];
		String dp = terminalargs[1];
		String op = terminalargs[2];
		
		
		if(terminalargs.length != 5){
			System.err.println("Error occured!!!!");
			System.exit(2);
		}
			
		
		conf.set("USERDATA",dp);
		
		if(terminalargs[4] != null && terminalargs[3]!=null){
			int friend1 = Integer.parseInt(terminalargs[3].toString());
			int friend2 = Integer.parseInt(terminalargs[4].toString());
			
			if(friend1 > friend2){
					int temp = friend1;
					friend1= friend2;
					friend2 = temp;
			}
			conf.setInt("friend1", friend1);
			conf.setInt("friend2",friend2);
		}
		
		{
			
			Job job = Job.getInstance(conf,"FriendsWithDateJob");
			job.setJarByClass(FriendsWithDate.class);
			job.setMapperClass(FriendsWithDate.FriendMapper.class);
			job.setReducerClass(FriendsWithDate.FriendReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			FileInputFormat.addInputPath(job, new Path(ip));
			
			FileOutputFormat.setOutputPath(job, new Path(op));
		
			if(!job.waitForCompletion(true))
			{

					System.exit(1);
			}
		}

	}

}

