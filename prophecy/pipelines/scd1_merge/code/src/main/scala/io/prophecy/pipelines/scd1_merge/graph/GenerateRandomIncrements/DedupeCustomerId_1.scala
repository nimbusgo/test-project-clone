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

object DedupeCustomerId_1 {

  def apply(spark: SparkSession, in: DataFrame): DataFrame = {
    import org.apache.spark.sql.expressions.Window
    in.withColumn("row_number",
                  row_number().over(
                    Window
                      .partitionBy("customer_id")
                      .orderBy(lit(1))
                      .rowsBetween(Window.unboundedPreceding, Window.currentRow)
                  )
      )
      .filter(col("row_number") === lit(1))
      .drop("row_number")
  }

}
