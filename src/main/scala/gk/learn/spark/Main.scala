package gk.learn.spark

import org.apache.spark.{SparkConf, SparkContext}
class Main {

}
object Main
{


  val levels=Set("INFO","WARN")
//  zookeeper启动日志日期统计


    def main(args:Array[String]):Unit={
      println("Hello,Scala")

      val conf = new SparkConf().setAppName("zookeeper日志测试").setMaster("spark://master:7077")
      val sc = new SparkContext(conf)

      var zoo = sc.textFile("/sparktest/zookeeper.out")
//      scala> zoo
      //res2: org.apache.spark.rdd.RDD[String] = /sparktest/* MapPartitionsRDD[1] at textFile at <console>:24

//      统计各个等级日志的行数,INFO,WARN

      print("过滤前的行数:"+zoo.count())

      val result = zoo.map(x=>{

// 2019-06-02 11:12:42,890 [myid:2] - INFO  [main:DatadirCleanupManager@79] - autopurge.purgeInterval set to 0
          val fields = x.split(" ")
          if(fields.length>=5 &&  levels.contains(fields(4)))(fields(4),1)
          else
            ("未知",1)
        }).reduceByKey(_+_)
      result.saveAsTextFile("/sparktest_out")
//







    }

}