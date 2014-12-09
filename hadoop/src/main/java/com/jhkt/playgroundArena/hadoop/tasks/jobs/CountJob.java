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
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper.CountMapper;
import com.jhkt.playgroundArena.hadoop.tasks.jobs.reducer.CountReducer;

/**
 * @author Ji Hoon Kim
 */
public final class CountJob extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        Configuration conf = getConf();
        Job job = new Job(conf, CountJob.class.getSimpleName());
        job.setJarByClass(CountJob.class);
        
        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);
        
        job.setJobName("Sample Count Job");
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        System.exit( ToolRunner.run(new Configuration(), new CountJob(), args) );
    }
    
}
