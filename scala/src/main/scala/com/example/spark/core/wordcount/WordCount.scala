package com.example.spark.core.wordcount

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 方式一
 *
 * @author JC
 * @date 2021/4/7 16:22
 */
object WordCount {

  def main(args: Array[String]): Unit = {
    // 建立和spark的连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("wc")
    val sc = new SparkContext(sparkConf)

    // 1、读取文件，获取一行行数据
    // hello world
    // 获取编译后生成的${项目名}/target/classes/路径
    val resource: URL = this.getClass.getClassLoader.getResource("")
    println("resource => " + resource)
    val lines: RDD[String] = sc.textFile(s"$resource/wc")

    // 2、将一行数据进行拆分，形成一个个单词（分词）
    // 扁平化：将整体拆分成个体的操作
    // "hello world" => hello, world, hello, world
    val words: RDD[String] = lines.flatMap(_.split(" "))

    // 3、将数据根据单词进行分组，便于统计
    // (hello, hello, hello), (world, world)
    val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

    // 4、对分组后的数据进行转换
    // (hello, hello, hello), (world, world)
    // (hello, 3), (world, 2)
    val word2count: RDD[(String, Int)] = wordGroup.map {
      case (str, value) => {
        (str, value.size)
      }
    }

    // 5、将转换结果采集，打印
    val array: Array[(String, Int)] = word2count.collect()
    array.foreach(println)

    // 关闭spark连接
    sc.stop()
  }

}
