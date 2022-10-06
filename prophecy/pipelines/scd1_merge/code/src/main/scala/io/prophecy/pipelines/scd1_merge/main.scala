package io.prophecy.pipelines.scd1_merge

import io.prophecy.libs._
import io.prophecy.pipelines.scd1_merge.config.ConfigStore._
import io.prophecy.pipelines.scd1_merge.config._
import io.prophecy.pipelines.scd1_merge.udfs.UDFs._
import io.prophecy.pipelines.scd1_merge.udfs._
import io.prophecy.pipelines.scd1_merge.graph._
import io.prophecy.pipelines.scd1_merge.graph.GenerateRandomIncrements
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_GenerateRandomIncrements = GenerateRandomIncrements.apply(spark)
    customers_scd1_write(spark, df_GenerateRandomIncrements)
    val df_customers_scd1_read = customers_scd1_read(spark)
    val df_Preview             = Preview(spark, df_customers_scd1_read)
  }

  def main(args: Array[String]): Unit = {
    ConfigStore.Config = ConfigurationFactoryImpl.fromCLI(args)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Prophecy Pipeline")
      .config("spark.default.parallelism",             "4")
      .config("spark.sql.legacy.allowUntypedScalaUDF", "true")
      .enableHiveSupport()
      .getOrCreate()
      .newSession()
    spark.conf.set("prophecy.metadata.pipeline.uri", "pipelines/scd1_merge")
    MetricsCollector.start(
      spark,
      spark.conf.get("prophecy.project.id") + "/" + "pipelines/scd1_merge"
    )
    apply(spark)
    MetricsCollector.end(spark)
  }

}
