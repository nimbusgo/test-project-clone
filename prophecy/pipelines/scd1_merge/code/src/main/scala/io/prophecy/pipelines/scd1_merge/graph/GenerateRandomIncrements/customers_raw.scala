package io.prophecy.pipelines.scd1_merge.graph.GenerateRandomIncrements

import io.prophecy.libs._
import io.prophecy.pipelines.scd1_merge.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object customers_raw {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("csv")
      .option("header",      true)
      .option("inferSchema", true)
      .option("sep",         ",")
      .schema(
        StructType(
          Array(
            StructField("customer_id",     IntegerType, true),
            StructField("tax_id",          DoubleType,  true),
            StructField("tax_code",        StringType,  true),
            StructField("customer_name",   StringType,  true),
            StructField("state",           StringType,  true),
            StructField("city",            StringType,  true),
            StructField("postcode",        StringType,  true),
            StructField("street",          StringType,  true),
            StructField("number",          StringType,  true),
            StructField("unit",            StringType,  true),
            StructField("region",          StringType,  true),
            StructField("district",        StringType,  true),
            StructField("lon",             DoubleType,  true),
            StructField("lat",             DoubleType,  true),
            StructField("ship_to_address", StringType,  true),
            StructField("valid_from",      IntegerType, true),
            StructField("valid_to",        DoubleType,  true),
            StructField("units_purchased", DoubleType,  true),
            StructField("loyalty_segment", IntegerType, true)
          )
        )
      )
      .load("dbfs:/databricks-datasets/retail-org/customers/customers.csv")

}
