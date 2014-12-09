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

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.jhkt.playgroundArena.hadoop.tasks.jobs.writable.AverageWritable;
import com.jhkt.playgroundArena.hadoop.util.JPAHadoopConstants;

/**
 * @author Ji Hoon Kim
 */
public final class AverageMapper extends Mapper<Text, Text, Text, AverageWritable> {
    
    private AverageWritable _aWriter = new AverageWritable();
    private Text _key = new Text();
    
    protected void map(Text key, Text value, Mapper<Text, Text, Text, AverageWritable>.Context context) throws IOException, InterruptedException {
        
        String[] valueSplit = value.toString().split(JPAHadoopConstants.DEFAULT_DELIM_REG_EXP);
        
        _key.set(valueSplit[0]);
        Double sum = Double.parseDouble(valueSplit[1]);
        _aWriter.setSum(sum);
        
        context.write(_key, _aWriter);
    };
    
}
