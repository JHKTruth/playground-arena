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
import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;

import com.jhkt.playgroundArena.hadoop.util.JPAHadoopConstants;

/**
 * Recall that our strategy for the mapper is to build a single Bloom filter on the entire split and output 
 * it at the end of the split to the reducer. Given that the map() method of the Mapper class has no state 
 * information about which record in the split it's processing, we should output the BloomFilter in the close() method 
 * to ensure that all the records in the split have been read.
 * 
 * Note that it is not easy to separate Hadoop's BloomFilter from the Hadoop framework and use it as a standalone class.
 * 
 * @author Ji Hoon Kim
 */
public final class BloomFilterMapper extends Mapper<Text, Text, Text, BloomFilter> {
    
    private BloomFilter _bFilter = new BloomFilter();
    
    protected void map(Text key, Text value, Mapper<Text, Text, Text, BloomFilter>.Context context) throws IOException, InterruptedException {
        String[] valueSplit = value.toString().split(JPAHadoopConstants.DEFAULT_DELIM_REG_EXP);
        _bFilter.add(new Key(valueSplit[0].getBytes()));
    };
    
    protected void cleanup(Mapper<Text, Text, Text, BloomFilter>.Context context) throws IOException, InterruptedException {
        context.write(new Text("bloomFilterSample"), _bFilter);
    };
    
}
