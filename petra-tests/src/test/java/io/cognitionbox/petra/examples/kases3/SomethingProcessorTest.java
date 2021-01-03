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
package io.cognitionbox.petra.examples.kases3;

import io.cognitionbox.petra.config.ExecMode;
import io.cognitionbox.petra.examples.kases3.objects.Something;
import io.cognitionbox.petra.examples.kases3.steps.SomethingProcessor;
import io.cognitionbox.petra.lang.*;
import io.cognitionbox.petra.util.Petra;
import org.javatuples.Pair;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class SomethingProcessorTest extends StepTest<Something> {
    public SomethingProcessorTest(ExecMode execMode) {
        super(execMode);
    }

    protected Supplier<AbstractStep<Something>> stepSupplier(){
        return ()->new SomethingProcessor();
    }

    @Test
    public void test1() {
        setInput(new Something(1));
        setExpectation(x->true);
    }

    @Test
    public void test2() {
        setInput(new Something(6));
        setExpectation(x->true);
    }

//    @Test
//    public void test3() {
//        setInput(new Something(11));
//        setExpectation(i->true);
//    }
}
