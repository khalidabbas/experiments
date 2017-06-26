package com.spark.dataAnalysis.footballResults;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//        int[] arr = {1,2,3,4,5};
//       for(int i = 0; i < arr.length; i++) {
//    	   if(arr[i] == 3) {
//    		   System.out.println("YES");
//    	   }
//       }
    	 int l = 2;
         int r = 16;
         List<Integer> list = new ArrayList<>();
         int size = 0;
         while(l <= r) {        
             if( l % 2 > 0) {
                 list.add(l);
                 size++;
             }
             l++;
         }
         int[] primitives = new int[size];

         for(int i = 0; i < size; i++) {
             primitives[i] = list.get(i);
         }
         
         for(int i = 0; i < primitives.length; i++) {
            System.out.println(primitives[i]);
         }
         
    	
    	assertTrue( true );
    }
    
    
    public void readFile(String filename){
    	
    	  String requestName;
          String line;
    	Map<String, Integer> requestMap = new HashMap();
        try{
        BufferedReader buffReader = new BufferedReader(new FileReader(filename));
           line = buffReader.readLine();
            while(line != null) {
                
                requestName = line.split(" ")[0];       
                if(requestMap.containsKey(requestName) )
                requestMap.put(requestName, 1);

            }
 
            
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("records_" + filename)); 
            for(Map.Entry<String, Integer>  entry : requestMap.entrySet()) {
                buffWriter.write(entry.getKey() + "  " + entry.getValue());
            }    
            
        } catch(Exception ioex) {
            
        }    
    	
    	
    }
    	
}
