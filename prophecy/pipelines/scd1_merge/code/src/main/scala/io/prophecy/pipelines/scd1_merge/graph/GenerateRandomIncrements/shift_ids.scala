package io.prophecy.pipelines.scd1_merge.graph.GenerateRandomIncrements

import io.prophecy.libs._
import io.prophecy.pipelines.scd1_merge.config.ConfigStore._
import io.prophecy.pipelines.scd1_merge.udfs.UDFs._
import io.prophecy.pipelines.scd1_merge.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object shift_ids {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("customer_id",
                  col("customer_id") + (lit(10000) * rand()).cast(IntegerType)
    )

}
