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
package com.jhkt.playgroundArena.hadoop.tasks.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper.AverageMapper;
import com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper.ReverseMapper;
import com.jhkt.playgroundArena.hadoop.tasks.jobs.reducer.AverageReducer;
import com.jhkt.playgroundArena.hadoop.tasks.jobs.writable.AverageWritable;

/**
 * 
 * I assume below has been deprecated, but nevertheless interesting from where the initial thought was:
 * The argument byValue will need a little explanation. In the standard Mapper model, the output key/value pairs are serialized and written 
 * to disk, prepared to be shuffled to a  reducer that may be at a completely different node. Formally this is considered to be passed by value, as 
 * a copy of the key/value pair is sent over. IN the current case where we can chain one Mapper to another, we can execute the two in the same 
 * JVM thread. Therefore, it's possible for the key/value pairs to be passed by reference, where the output of the initial Mapper stays in place in 
 * memory location. When Map1 calls OutputCollector.collect(K k, V v), the objects k and v pass directly to Map2's map() method. This improves 
 * performance by not having to clone a potentially large volume of data between the mappers. But doing this can violate one of the more subtle 
 * "contracts" in Hadoop's MapReduce API. The call to OutputCollector.collect(K k, V v) is guaranteed to not alter the content of k and v. Map1 
 * can call OutputCollector.collect(K k, V v) and then use the objects k and v afterward, fully expecting their values to stay the same. But if 
 * we pass those objects by reference to Map2, then Map2 may alter them and violate the API's guarantee.
 * 
 * Simple ChainJob where only distinction between AverageJob is that it will perform a ReverseMapper prior to AverageMapper
 * 
 * @author Ji Hoon Kim
 */
public final class ChainJob extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        Configuration conf = getConf();
        Job job = new Job(conf, ChainJob.class.getSimpleName());
        job.setJobName("Sample Chain Job");
        job.setJarByClass(ChainJob.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);
        
        ChainMapper.addMapper(job, ReverseMapper.class, Text.class, Text.class, Text.class, Text.class, new Configuration(false));
        ChainMapper.addMapper(job, AverageMapper.class, Text.class, Text.class, Text.class, AverageWritable.class, new Configuration(false));
        ChainReducer.setReducer(job, AverageReducer.class, Text.class, AverageWritable.class, Text.class, DoubleWritable.class, new Configuration(false));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        System.exit( ToolRunner.run(new Configuration(), new ChainJob(), args) );
    }
    
}
