//Ajay Kishorkumar Danda
var business = spark.read.format("csv").option("sep",":").load("FileStore/tables/business.csv")
var review = spark.read.format("csv").option("delimiter",":").load("FileStore/tables/review.csv")
val business_index = Seq("business_id","_c1","full_address","_c3","categories");
val review_index = Seq("review_id","_c1","user_id","_c3","business_id","_c5","stars");
val business_dataframe = business.toDF(business_index:_*);
val review_dataframe = review.toDF(review_index:_*);

val id_addr = business_dataframe.select("business_id" ,"full_address");

val join_op = id_addr.join(review_dataframe , "business_id");

val result = join_op.filter($"full_address".like("%Stanford%")).select("user_id","stars").distinct
result.rdd.coalesce(1).saveAsTextFile("/FileStore/tables/output1.txt")