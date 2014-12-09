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

import com.jhkt.playgroundArena.hadoop.tasks.jobs.mapper.DistributedCacheMapper;

/**
 * There's another data pattern that occurs quite frequently that we can take advantage of. 
 * When joining big data, often only one of the sources is big; the second source may be orders of 
 * magnitude smaller...When the smaller source can fit in memory of a mapper, we can achieve a tremendous 
 * gain in efficiency by copying the smaller source to all mappers and performing joining in the map 
 * phase. This is called replicated join in the database literature as one of the data tables is 
 * replicated across all nodes in the clusters. (The next section will cover the case when the smaller 
 * source doesn't fit in memory).
 * 
 * Hadoop has a mechanism called distributed cache that's designed to distribute files to all nodes in 
 * a cluster. It's normally used for distributing files containing "background" data needed by all mappers. 
 * For example, if you're using Hadoop to classify documents, you may have a list of keywords for each class. 
 * (Or better yet, a probabilistic model for each class, but we digress...) You would use distributed cache 
 * to ensure all mappers have access to the lists of keywords, the "background" data. For executing replicated 
 * joins, we consider the smaller data source as background data.
 * 
 * One of the limitations in using replicated join is that one of the join tables has to be small enough to fit in 
 * memory. Even with the usual asymmetry of size in the input sources, the smaller one may still not be small enough. 
 * You can solve this problem by rearranging the processing steps to make them more efficient. For example, if you're 
 * looking for the order history of all customers in the 415 are code, it's correct but inefficient to join the 
 * Orders and the Customers tables first before filtering out records where the customer is in the 415 area code. 
 * Both the Orders and Customers tables may be too big for replicated join and you'll have to resort to the inefficient 
 * reduce-side join. A better approach is to first filter out customers living in the 415 area code. We store this in a 
 * temporary file called Customer415. We can arrive at the same end result by joining Orders with Customers415, but now 
 * Customers415 is small enough that a replicated join is feasible. There is some overhead in creating and distributing 
 * the Customers415 file, but it's often compensated by the overall gain in efficiency.
 * 
 * Sometimes you may have a lot of data to analyze. You can't use replicated join no matter how you rearrange your 
 * processing steps. Don't worry. We still have ways to make reduce-side joining more efficient. Recall that the main 
 * problem with reduce-side joining is that the mapper only tags the data, all of which is shuffled across the network but 
 * most of which is ignored in the reducer. The inefficiency is ameliorated if the mapper has an extra prefiltering function 
 * to eliminate most or even all the unnecessary data before it is shuffled across the network. We need to build this filtering 
 * mechanism.
 * 
 * Continuing our example of joining Customers415 with Orders, the join key is Customer ID and we would like our mappers to filter out 
 * any customer not from the 415 area code rather than send those records to reducers. We create a data set CustomerID415 to 
 * store all the Customer IDs of customers in the 415 area code. CustomerID415 is smaller than Customers415 because it only has one 
 * data field. Assuming CustomerID415 can now fit in memory, we can improve reduce-side join by using distributed cache to disseminate 
 * CustomerID415 across all the mappers. When processing records from Customers and Orders, the mapper will drop any record whose keys 
 * is not in the set CustomerID415. This is sometimes called a semijoin, taking the terminology from the database world.
 * 
 * Last but not least, what if the file CustomerID415 is still too big to fit in memory? Or maybe CustomerID415 does fit in memory 
 * but it's size makes replicating it across all the mappers inefficient. This situation calls for a data structure called a Bloom filter => BloomFilterJob. 
 * 
 * 
 * @author Ji Hoon Kim
 */
public class DistributedCacheJob extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        Configuration conf = getConf();
        Job job = new Job(conf, DistributedCacheJob.class.getSimpleName());
        job.setJarByClass(DistributedCacheJob.class);
        
        /*
         * The following will disseminate the file to all the nodes and the file defaults to HDFS.
         * The second and third arguments denote the input and output paths of the standard Hadoop 
         * job. Note that we've limited the number of data sources to two. This is not an inherent 
         * limitation of the technique, but a simplification that makes our code easier to follow.
         */
        //job.addCacheFile(new Path(args[0]).toUri());
        
        Path in = new Path(args[1]);
        Path out = new Path(args[2]);
        
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);
        
        job.setJobName("Sample DistributedCache Job");
        job.setMapperClass(DistributedCacheMapper.class);
        
        /*
         * Took out the Reduce class as the plan is performing the joining in the map phase and will 
         * configure the job to have no reduce.
         */
        job.setNumReduceTasks(0);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        System.exit( ToolRunner.run(new Configuration(), new DistributedCacheJob(), args) );
    }
    
}
