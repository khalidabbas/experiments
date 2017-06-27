package com.spark.dataAnalysis.footballResults;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import scala.Tuple2;




public class ResultsPredictor implements Serializable {

	
	 private static final Pattern SPACE = Pattern.compile(" ");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResultsPredictor rp = new ResultsPredictor();
		//rp.wordCounter();
		rp.createDataFrame();
		//rp.trialCode();

	}
	  
	
	public void createDataFrame() {
				
//		Div = League Division
//				Date = Match Date (dd/mm/yy)
//				HomeTeam = Home Team
//				AwayTeam = Away Team
//				FTHG = Full Time Home Team Goals
//				FTAG = Full Time Away Team Goals
//				FTR = Full Time Result (H=Home Win, D=Draw, A=Away Win)
//				HTHG = Half Time Home Team Goals
//				HTAG = Half Time Away Team Goals
//				HTR = Half Time Result (H=Home Win, D=Draw, A=Away Win)
//
//				Match Statistics (where available)
//				Attendance = Crowd Attendance
//				Referee = Match Referee
//				HS = Home Team Shots
//				AS = Away Team Shots
//				HST = Home Team Shots on Target
//				AST = Away Team Shots on Target
//				HHW = Home Team Hit Woodwork
//				AHW = Away Team Hit Woodwork
//				HC = Home Team Corners
//				AC = Away Team Corners
//				HF = Home Team Fouls Committed
//				AF = Away Team Fouls Committed
//				HO = Home Team Offsides
//				AO = Away Team Offsides
//				HY = Home Team Yellow Cards
//				AY = Away Team Yellow Cards
//				HR = Home Team Red Cards
//				AR = Away Team Red Cards
//				HBP = Home Team Bookings Points (10 = yellow, 25 = red)
//				ABP = Away Team Bookings Points (10 = yellow, 25 = red)
		
		
		System.setProperty("hadoop.home.dir", "C:\\Users\\user\\Khalid_Projects\\hadoop-2.8.0");
		System.setProperty(
			    "spark.sql.warehouse.dir", 
			    "file///Users/user/Khalid_Projects/sparkProjects/footballResults/spark-warehouse"
			);
		SparkConf conf = new SparkConf().setMaster("local").setAppName("ResultsPredictor");
		SparkContext context = new SparkContext(conf);
		SparkSession sparkSession = new SparkSession(context);
		StructType customSchema = new StructType(new StructField[] {
		    new StructField("Div", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("Date", DataTypes.StringType, true, Metadata.empty()),
		    new StructField("HomeTeam", DataTypes.StringType, true, Metadata.empty()),
		    new StructField("AwayTeam", DataTypes.StringType, true, Metadata.empty()),
		    new StructField("FTHG", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("FTAG", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("FTR", DataTypes.StringType, true, Metadata.empty()),
		    new StructField("HTHG", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HTAG", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HTR", DataTypes.StringType, true, Metadata.empty()),
		    new StructField("HS", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AS", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HST", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AST", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HHW", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AHW", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HC", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AC", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HF", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AF", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HO", DataTypes.IntegerType, true, Metadata.empty()),		    
		    new StructField("AO", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HY", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AY", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HR", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("AR", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("HBP", DataTypes.IntegerType, true, Metadata.empty()),
		    new StructField("ABP", DataTypes.IntegerType, true, Metadata.empty())
		});

		Dataset<Row> df = sparkSession.read().
				format("com.databricks.spark.csv").
				option("header", true).
				schema(customSchema).
				load("../Football_data_Files/E1.csv");
		
		df.createOrReplaceTempView("premierLeague2016");
		Dataset<Row> awayWinsByMoreThanThree = sparkSession.sql("select HomeTeam,AwayTeam,Date,FTHG,FTAG from premierLeague2016 where FTAG >= 3 ORDER BY Date");
		Dataset<String> namesDS = awayWinsByMoreThanThree.map(new MapFunction<Row, String>() {
			 
			private static final long serialVersionUID = 1L;

			@Override
			  public String call(Row row) throws Exception {
			    return row.getString(0) + " " + row.getString(1) + " " + row.getString(2) + " " + row.getInt(3) + "-" + row.getInt(4);
			  }
			}, Encoders.STRING());
			
		
		List<String> games = namesDS.collectAsList();
		games.stream().forEach(a -> {System.out.println(a);});
		
		sparkSession.stop();
		
	}
	
	public void wordCounter()  {
		 
		SparkConf conf = new SparkConf().setMaster("local").setAppName("ResultsPredictor");
		SparkContext context = new SparkContext(conf);
		SparkSession spark = new SparkSession(context);
		
	

	    JavaRDD<String> lines = spark.read().textFile("C:\\Users\\user\\Khalid_Projects\\sparkProjects\\Football_data_Files\\Baby_Names__Beginning_2007 - test1.txt").javaRDD();

	    JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

	    JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

	    JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

	    List<Tuple2<String, Integer>> output = counts.collect();
	    for (Tuple2<?,?> tuple : output) {
	      System.out.println(tuple._1() + ": " + tuple._2());
	    }
	    spark.stop();
	  }
	
	
	public void trialCode() {
		System.setProperty("hadoop.home.dir", "C:\\Users\\user\\Khalid_Projects\\hadoop-2.8.0");
		System.setProperty(
			    "spark.sql.warehouse.dir", 
			    "file///Users/user/Khalid_Projects/sparkProjects/footballResults/spark-warehouse"
			);
		
		SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local");
		// create Spark Context
		SparkContext context = new SparkContext(conf);
		// create spark Session
		SparkSession sparkSession = new SparkSession(context);

		Dataset<Row> df = sparkSession.read().format("com.databricks.spark.csv").option("header", true).option("inferSchema", true).load("../Football_data_Files/Baby_Names__Beginning_2007.csv");

		        //("hdfs://localhost:9000/usr/local/hadoop_data/loan_100.csv");
		System.out.println("========== Print Schema ============");
		df.printSchema();
		System.out.println("========== Print Data ==============");
		df.show();
		System.out.println("========== Print title ==============");
		df.select("County").show();
		
	}
	}
	
	


