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
package io.cognitionbox.petra.examples.simple.extraction;


import io.cognitionbox.petra.examples.simple.common.B;
import io.cognitionbox.petra.lang.PEdge;
import io.cognitionbox.petra.lang.Void;

import static io.cognitionbox.petra.lang.Void.vd;
import static io.cognitionbox.petra.util.Petra.readConsume;


public class PrintB extends PEdge<B, Void> {
    {
       pre(readConsume(B.class,x->true));
       func(a->{
           System.out.println(a.getClass().getSimpleName());
           return vd;
        });
       postVoid();
    }
}
