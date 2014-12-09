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
package com.jhkt.playgroundArena.shared.tasks;

/**
 * Running Hadoop in Windows:
 * 1)   Get Cygwin or VMWare
 * 2)   Install ssh, if the package doesn't exist
 * 3)   Create a RSA key pair:  ssh-keygen -t rsa   [/home/hadoop-user/.ssh/id_rsa]
 * 4)   Copy the public key to every slave node as well as the master node
 * 5)   Manually log in to the target node and set the master key as an authorized key (or append to the 
 *      list of authorized keys if you have others defined)
 * 6)   Verify that it's correctly defined by attempting to log in to the target node from the master
 * 7)   Set JAVA_HOME and HADOOP_HOME environment variables
 * 8)   Within $HADOOP_HOME/conf directory create masters and slaves file
 * 9)   Confirm that one can ssh back to oneself: ssh localhost; otherwise execute
 *      a)  ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
 *      b)  cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys 
 * 10)  bin/hadoop namenode -format
 * 11)  bin/start-all.sh; bin/stop-all.sh
 * 
 * Common commands for Hadoop:
 * $    hadoop fs -ls
 * $    hadoop fs -put example.txt .
 * $    hadoop fs -get example.txt .
 * $    hadoop fs -cat example.txt
 * $    hadoop fs -tail example.txt
 * $    hadoop fs -rm example.txt
 * $    hadoop fs -help ls
 * $    hadoop jar playground/MyJob.jar MyJob input/cite75_99.txt output
 * 
 * Random notes:
 * 1)   Hadoop's command line utilities include a getmerge command for merging a number of HDFS files before copying them 
 *      onto the local machine.
 * 2)   Hadoop's input data usually resides in large files, typically tens or hundreds of gigabytes or even more. One of the 
 *      fundamental principles of MapReduce's processing power is the splitting of the input data into chunks. You can 
 *      process these chunks in parallel using multiple machines. In Hadoop terminology these chunks are called input splits.
 * 3)   Hadoop by default considers each line in the input file to be a record and the key/value pair is the byte offset (key) 
 *      and content of the line (value), respectively...Hadoop supports a few other data formats and allows you to define your 
 *      own.
 *      a)  TextInputFormat         -   Each line in the text files is a record. Key is the byte offset of the line and value 
 *                                      is the content of the line.
 *      b)  SequenceFileInputFormat -   An InputFormat for reading in sequence files. Key and value are user defined. Sequence 
 *                                      file is a Hadoop-specific compressed binary file format. It's optimized for passing data 
 *                                      between the output of one MapReduce job to the input of some other MapReduce job.
 *      c)  and etceteras
 * 4)   We can use any executable script that processes a line-orientated data stream from STDIN and outputs to STDOUT with Hadoop
 *      Streaming. Hadoop includes a library package called Aggregate that simplifies obtaining aggregate statistics of a data set. 
 * 5)   Hadoop solves these bottlenecks by extending the MapReduce framework with a combiner step in between the mapper and
 *      reducer. You can think of the combiner as a helper for the reducer. It's supposed to whittle down the output of the 
 *      mapper to lessen the load on the network and on the reducer. If we specify a combiner, the MapReduce framework may apply 
 *      it zero, one, or more times to the intermediate data. 
 * 6)   Hadoop has a contrib package called datajoin that works as a generic framework for data joining in Hadoop. Its jar file is
 *      at contrib/datajoin/hadoop-*-datajoin.jar. To distinguish it from other joining techniques, it's called the reduce-side join, 
 *      as we do most of the processing on the reduce side. It's also known as the repartitioned join (or the repartitioned sort-merge join), 
 *      as it's the same as the database technique of the same name. Although it's not the most efficient joining technique, it's 
 *      the most general and forms the basis of some more advanced techniques (such as the semijoin). Reduce-side join introduces some 
 *      new terminologies and concepts, namely, data source, tag, and group key.s
 * 7)   
 * 
 * 
 * @author Ji Hoon Kim
 */
public final class HadoopTaskRunner implements ITaskRunner {
    
    public HadoopTaskRunner() {
        super();
    }
    
    @Override
    public void init() {
        
    }
    
    @Override
    public void destroy() {
        
    }
    
}
