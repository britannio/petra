/**
 * Copyright 2016-2020 Aran Hakki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cognitionbox.petra.core.impl;

import io.cognitionbox.petra.core.IRingbuffer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MapBasedRingBuffer<T> implements IRingbuffer<T> {
    public MapBasedRingBuffer(String description, long capacity) {
        this.capacity = capacity;
        buffer = new ConcurrentHashMap<>();
        sequence = new AtomicLong(0);
    }

    private AtomicLong sequence;
    private Map<Long,T> buffer;
    private long capacity;

    public long add(T object){
        long seq = sequence.incrementAndGet();
        if(seq>=capacity){
            seq = 0;
            sequence.set(seq);
        }
        buffer.put(seq,object);
        return seq;
    }

    public T readOne(long seq){
        return buffer.get(seq);
    }

    @Override
    public long capacity() {
        return capacity;
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long tailSequence() {
        return sequence.get();
    }

    @Override
    public long headSequence() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long remainingCapacity() {
        throw new UnsupportedOperationException();
    }
}
