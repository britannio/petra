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
package io.cognitionbox.petra.core.engine;

import io.cognitionbox.petra.lang.RGraphComputer;
import io.cognitionbox.petra.core.IRingbuffer;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

public class StepWorker implements Callable<Boolean>, Serializable {
    private static final long serialVersionUID = 3L;
    public volatile boolean done = false;

    @Override
    public Boolean call() throws Exception {
        final AtomicLong seq = new AtomicLong(0);
        ((ThreadPoolExecutor) RGraphComputer.getWorkerExecutor())
                .setRejectedExecutionHandler(new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        seq.decrementAndGet();
                    }
                });
        IRingbuffer<Serializable> tasks = RGraphComputer.getTaskQueue();
        int count = 0;
        while(true){//count<10){
            if (seq.get()<=tasks.tailSequence()){
                Serializable read = tasks.readOne(seq.get());
                if (read instanceof StepCallable){
                    if (read!=null && !((StepCallable) read).isDone()){
                        try {
                            RGraphComputer.getWorkerExecutor().submit(((StepCallable) read)); // .get() to see errors
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                seq.incrementAndGet();
                if (seq.get()>=tasks.capacity()){
                    seq.set(0);
                }
            } else {
                Thread.sleep(20);
            }
            count++;
        }
        //return true;
    }
}
