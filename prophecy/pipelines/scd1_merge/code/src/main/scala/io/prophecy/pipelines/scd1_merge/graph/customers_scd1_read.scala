package io.prophecy.pipelines.scd1_merge.graph

import io.prophecy.libs._
import io.prophecy.pipelines.scd1_merge.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object customers_scd1_read {

  def apply(spark: SparkSession): DataFrame =
    spark.read.format("delta").load("/data/tmp/tpch-examples/scd1_customers")

}
