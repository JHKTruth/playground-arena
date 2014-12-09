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
package com.jhkt.playgroundArena.hadoop.tasks.jobs.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * @author Ji Hoon Kim
 */
public final class AverageWritable implements WritableComparable<AverageWritable> {
    
    private double _sum;
    private int _count;
    
    public AverageWritable() {
        super();
        
        _count = 1;
    }
    
    public double getSum() {
        return _sum;
    }
    public void setSum(double sum) {
        _sum = sum;
    }
    public int getCount() {
        return _count;
    }
    public void setCount(int count) {
        _count = count;
    }
    
    @Override
    public void readFields(DataInput input) throws IOException {
        _sum = input.readDouble();
        _count = input.readInt();
    }
    
    @Override
    public void write(DataOutput output) throws IOException {
        output.writeDouble(_sum);
        output.writeInt(_count);
    }
    
    @Override
    public int compareTo(AverageWritable instance) {
        return instance._count - _count;
    }
    
}
