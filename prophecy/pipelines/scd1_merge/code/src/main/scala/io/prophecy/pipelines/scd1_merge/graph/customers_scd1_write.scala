package io.prophecy.pipelines.scd1_merge.graph

import io.prophecy.libs._
import io.prophecy.pipelines.scd1_merge.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object customers_scd1_write {

  def apply(spark: SparkSession, in: DataFrame): Unit = {
    import _root_.io.delta.tables._
    if (
      DeltaTable.isDeltaTable(spark, "/data/tmp/tpch-examples/scd1_customers")
    )
      DeltaTable
        .forPath(spark, "/data/tmp/tpch-examples/scd1_customers")
        .as("target")
        .merge(in.as("source"),
               col("source.customer_id") === col("target.customer_id")
        )
        .whenMatched()
        .updateAll()
        .whenNotMatched()
        .insertAll()
        .execute()
    else
      in.write
        .format("delta")
        .mode("overwrite")
        .save("/data/tmp/tpch-examples/scd1_customers")
  }

}
