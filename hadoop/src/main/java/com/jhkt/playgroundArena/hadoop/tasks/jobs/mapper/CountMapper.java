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

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.jhkt.playgroundArena.hadoop.util.JPAHadoopConstants;

/**
 * The reason for defining currentValue and ONE in the class rather than inside the method is purely one of efficiency. The map() method 
 * will be called as many times as there are records (in a split, for each JVM). Reducing the number of objects created inside the 
 * map() method can increase performance and reduce garbage collection.
 * 
 * @author JihoonKim
 */
public final class CountMapper extends Mapper<Text, Text, Text, IntWritable> {
    
    private static final IntWritable ONE = new IntWritable(1);
    
    private Text _currentValue = new Text();
    
    protected void map(Text key, Text value, Mapper<Text, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        String[] valueSplit = value.toString().split(JPAHadoopConstants.DEFAULT_DELIM_REG_EXP);
        
        _currentValue.set(valueSplit[1]);
        context.write(_currentValue, ONE);
    };
    
}
