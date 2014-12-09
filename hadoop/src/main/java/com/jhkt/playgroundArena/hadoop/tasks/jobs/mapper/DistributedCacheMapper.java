/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.jhkt.playgroundArena.hadoop.util.JPAHadoopConstants;

/**
 * @author Ji Hoon Kim
 */
public final class DistributedCacheMapper  extends Mapper<Text, Text, Text, Text> {

    private ConcurrentMap<String, String> joinData = new ConcurrentHashMap<String, String>();
    
    private Text _currentValue = new Text();
    
    /* 
     * Called for the first time when Mapper class is instantiated.
     */
    protected void setup(Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        
        /*
         * Replace DistributedCache with a non-deprecated Class method invocation [issue possibly due to jar mixing]
         */
        for(Path cacheFile : DistributedCache.getLocalCacheFiles(context.getConfiguration())) {
            
            BufferedReader joinReader = null;
            
            try{
                joinReader = new BufferedReader(new FileReader(cacheFile.toString()));
                String line;
                String[] tokens;
                
                /*
                 * Now the content of the local cache file will be pushed to joinData for the map method
                 */
                while((line = joinReader.readLine()) != null) {
                    tokens = line.split(JPAHadoopConstants.DEFAULT_DELIM_REG_EXP, 2);
                    joinData.put(tokens[0], tokens[1]);
                }
            }catch(IOException ioE) {
                
            }finally {
                if(joinReader != null) {
                    try{
                        joinReader.close();
                    }catch(IOException ioE2) {
                        
                    }
                }
            }
        }
        
    };
    
    /* 
     * Called when the mapper finishes processing its split
     */
    protected void cleanup(Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        
    };
    
    protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        String joinValue = joinData.get(key);
        if(joinValue != null) {
            _currentValue.set(value.toString() + JPAHadoopConstants.DEFAULT_SPLITTER + joinValue);
            
            /*
             * Output the result direclty into HDFS as we don't have any reducer for further processing
             */
            context.write(key, _currentValue);
        }
    };
    
}
