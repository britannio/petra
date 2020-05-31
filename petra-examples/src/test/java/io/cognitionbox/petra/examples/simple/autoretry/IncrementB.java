/**
 * Copyright (C) 2016-2020 Aran Hakki.
 *
 * This file is part of Petra.
 *
 * Petra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Petra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Petra.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.cognitionbox.petra.examples.simple.autoretry;

import io.cognitionbox.petra.core.IRollback;
import io.cognitionbox.petra.examples.simple.common.B;
import io.cognitionbox.petra.lang.PEdge;
import io.cognitionbox.petra.lang.annotations.Feedback;
import io.cognitionbox.petra.util.Petra;

import static io.cognitionbox.petra.util.Petra.rw;
import static io.cognitionbox.petra.util.Petra.rt;

@Feedback
public class IncrementB extends PEdge<B,B> implements IRollback<B> {
    {
       pre(rw(B.class, a->a.value<10));
       func(b->{
            if (Math.random()>=0.5){
                throw new IllegalStateException();
            }
            b.value++;
            System.out.println("B="+b.value);
            return b;
        });
        post(Petra.rt(B.class, b->b.value==10));
    }

    @Override
    public void capture(B b) {
        b.setCaptured(b.value);
    }
    @Override
    public void rollback(B b) {
        b.value = b.getCaptured();
    }
}