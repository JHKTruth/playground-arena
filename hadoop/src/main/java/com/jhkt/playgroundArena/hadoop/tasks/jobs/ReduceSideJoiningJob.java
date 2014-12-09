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

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;

/**
 * The reduce-side joining technique discussed in the last section is flexible, but it can also 
 * be quite inefficient. Joining doesn't take place until the reduce phase. We shuffle all data across 
 * the network first, and in many situtations we drop the majority of this data during the joining process. 
 * It would be more efficient if we eliminate the unnecessary data right in the map phase. Even better would 
 * be to perform the entire joining operation in the map phase.
 * 
 * The main obstacle to performing joins in the map phase is that a record being processed by a mapper may be 
 * joined with a record not easily accessible (or even located) by that mapper. If we can guarantee the accessibility 
 * of all the necessary data when joining a record, joining on the map side can work. For example, if we know that 
 * the two sources of data are partitioned into the same number of partitions and the partitions are all sorted on the 
 * key and the key is the desired join key, then each mapper (with the proper InputFormat and RecordReader) can 
 * be deterministically locate and retrieve all the data necessary to perform joining. In fact, Hadoop's org.apache.hadoop.mapred.join 
 * package contains helper classes to facilitate this mapside join. Unfortunately, situations where we can naturally apply 
 * this are limited, and running extra MapReduce jobs to repartition the data sources to be usable by this package seems 
 * to defeat the efficiency gain. Therefore, we'll not pursue this package further.
 * 
 * All hope is not lost though => DistributedCacheJob
 * 
 * @author Ji Hoon Kim
 */
public final class ReduceSideJoiningJob extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        
    }
    
}
