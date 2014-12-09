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
package com.jhkt.playgroundArena.hadoop.tasks.jobs.reducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.bloom.BloomFilter;

/**
 * @author Ji Hoon Kim
 */
public class BloomFilterReducer extends Reducer<Text, BloomFilter, Text, Text> {
    
    private BloomFilter _bFilter = new BloomFilter();
    
    protected void reduce(Text key, Iterable<BloomFilter> values, Reducer<Text, BloomFilter, Text, Text>.Context context) throws IOException, InterruptedException {
        
        for(BloomFilter curr : values) {
            _bFilter.or(curr);
        }
        
    };
    
    protected void cleanup(Reducer<Text, BloomFilter, Text, Text>.Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        Path file = new Path(conf.get("mapred.output.dir") + "/bloomFilter");
        FSDataOutputStream out = file.getFileSystem(conf).create(file);
        _bFilter.write(out);
        out.close();
    };
    
}
