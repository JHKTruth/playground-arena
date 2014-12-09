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

/**
 * Hadoop requires the Mapper and the Reducer to be their own static classes.
 * Various nodes with different JVMs clone and run the Mapper and the Reducer during job execution, 
 * whereas the rest of the job class is executed only at the client machine.
 *      Of course I am taking a detour from their approach hoping all will be good as I wish to use the mapper 
 *      and reducer in various jobs [of course one can still do it while they are static inner classes, but I 
 *      personally do not like that coding practice {what do I know lol}]
 * 
 * It's highly recommended that data that are passed between Hadoop jobs use the Hadoop-specific sequence file format.
 * Sequence file is a compressable binary file format for storing key/value pairs. It is designed to support compression 
 * while remaining splittable. Recall that one of the parallelisms of Hadoop is its ability to split an input file for reading 
 * and processing by multiple map tasks. If the input file is in a compressed format, Hadoop will have to be able to split the file 
 * such that each split can be decompressed by the map tasks independently. Otherwise parallelism is destroyed if Hadoop has to 
 * decompress the file as a whole first. Not all compressed file formats are designed for splitting and decompressing in chunks. 
 * Sequence files were specially developed to support this feature. The file format provides sync markers to Hadoop to denote 
 * splittable boundaries. 
 * 
 * We want to emphasize again that reading and writing to databases from within Hadoop is only appropriate for data sets that are 
 * relatively small by Hadoop standards. Unless your database setup is as parallel as Hadoop (which can be the case if your Hadoop 
 * cluster is relatively small while you have many shards in your database system), your DB will be the performance bottleneck, and you 
 * may not gain any scalability advantage from your Hadoop cluster.
 * 
 * @author Ji Hoon Kim
 */

package com.jhkt.playgroundArena.hadoop.tasks.jobs;