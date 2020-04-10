//Ajay Kishorkumar Danda
var business = spark.read.format("csv").option("sep",":").load("FileStore/tables/business.csv")
var review = spark.read.format("csv").option("delimiter",":").load("FileStore/tables/review.csv")
val business_index = Seq("business_id","_c1","full_address","_c3","categories");
val review_index = Seq("review_id","_c1","user_id","_c3","business_id","_c5","stars");
val business_dataframe = business.toDF(business_index:_*);
val review_dataframe = review.toDF(review_index:_*);

val id_addr_cate = business_dataframe.select("business_id" ,"full_address","categories");
val star_integer = review_dataframe.selectExpr("review_id","_c1","user_id","_c3","business_id","_c5","cast(stars as int) stars");
val id_bid_star = star_integer.select("user_id","business_id","stars");

val join_op = id_addr_cate.join(id_bid_star , "business_id");

val result = join_op.select("business_id","full_address","categories","stars").groupBy("business_id","full_address","categories").agg(avg("stars").as("avg_stars")).sort(desc("avg_stars")).take(10);