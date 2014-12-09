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
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.bloom.BloomFilter;

import com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper.BloomFilterMapper;
import com.jhkt.playgroundArena.hadoop.tasks.jobs.reducer.BloomFilterReducer;

/**
 * @author Ji Hoon Kim
 */
public final class BloomFilterJob extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        Configuration conf = getConf();
        Job job = new Job(conf, BloomFilterJob.class.getSimpleName());
        job.setJarByClass(BloomFilterJob.class);
        
        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);
        
        job.setJobName("Sample BloomFilter Job");
        job.setMapperClass(BloomFilterMapper.class);
        job.setReducerClass(BloomFilterReducer.class);
        job.setNumReduceTasks(1);
        
        job.setInputFormatClass(TextInputFormat.class);
        
        /*
         * We want our reducer to output the final BloomFilter as a binary file. I think 
         * Hadoop doesn't have this format [check later], so using NullOutpuFormat.class.
         * 
         * In general life gets a little more dangerous when you deviate from MapReduce's input/output 
         * framework and start working with your own files. Your tasks are no longer guaranteed to be idempotent 
         * and you'll need to understand how various failure scenarios can affect your tasks. For example, your files 
         * may only be partially written when some tasks are restarted. Our example here is safe(r) because all the file 
         * operations take place together only once in the close() method and in only one reducer. A more 
         * careful/paranoid implementation would check each individual file operation more closely.
         */
        job.setOutputFormatClass(NullOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BloomFilter.class);
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        System.exit( ToolRunner.run(new Configuration(), new BloomFilterJob(), args) );
    }
    
}
