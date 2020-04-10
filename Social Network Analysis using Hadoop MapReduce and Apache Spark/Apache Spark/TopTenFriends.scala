//Ajay Kishorkumar Danda
val datafile = sc.textFile("FileStore/tables/soc-LiveJournal1Adj.txt")   
val friendpairs = datafile.map(l=>l.split("\\t"))
  .filter(l => l.size == 2)
  .map(l =>(l(0),l(1).split(",").toList))

var tempmutual = friendpairs.flatMap(a=>a._2.map(b=>
                           if(a._1.toInt < b.toInt)
                                        {((a._1,b),a._2)}
                           else
                                          {((b,a._1),a._2)}
                          ))  
tempmutual.collect()
val mutualfriends = tempmutual.reduceByKey((a,b) => a.intersect(b))
val mfsize = mutualfriends.map(a => (a._1,a._2.size))
val top10 = mfsize.sortBy(_._2, false).take(10)
val userdata = sc.textFile("FileStore/tables/userdata.txt")
val user_split = userdata.map(l => l.split(","))
val column_result = user_split.map(l => (l(0), List(l(1),l(2),l(3))))
val result = sc.parallelize(top10.map(l=>(l._2+"\t"+column_result.lookup(l._1._1)(0).mkString("\t")+"\t"+column_result.lookup(l._1._2)(0).mkString("\t"))));
result.coalesce(1).saveAsTextFile("/FileStore/tables/output2.1.txt")