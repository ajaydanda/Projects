//Ajay Kishorkumar Danda
val datafile = sc.textFile("FileStore/tables/soc-LiveJournal1Adj.txt")   
val friendpairs = file.map(l=>l.split("\\t"))
  .filter(l => (l.size == 2))
  .map(l=>(l(0),l(1).split(",").toList))
var tempmutual = friendpairs.flatMap(a=>a._2.map(b=>
                           if(a._1.toInt < b.toInt)
                                        {((a._1,b),a._2)}
                           else
                                          {((b,a._1),a._2)}
                          ))  
val mutualfriends = tempmutual.reduceByKey((a,b) => a.intersect(b))
val output = mutualfriends.map(l => l._1._1+"\t"+l._1._2+"\t"+l._2.mkString(","))
output.coalesce(1).saveAsTextFile("/FileStore/tables/output1.txt")