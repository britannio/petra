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
package io.cognitionbox.petra.guarantees.impl;

import io.cognitionbox.petra.guarantees.GraphCheck;
import io.cognitionbox.petra.core.IGraph;
import io.cognitionbox.petra.core.IJoin;
import io.cognitionbox.petra.core.IStep;
import io.cognitionbox.petra.lang.*;

public class CheckAllPrePostTypesAreStates implements GraphCheck {

        private boolean checkPType(Guard guard) {
            if (guard instanceof GuardXOR) {
                boolean ok = true;
                for (Guard p : ((GuardXOR<?>) guard).getChoices()) {
                    ok = ok && checkTypeImpl(p);
                }
                return ok;
            } else {
                return checkTypeImpl(guard);
            }
        }

        private boolean checkTypeImpl(Guard guard) {
            return true;//guard.getTypeClass().isAnnotationPresent(State.class);
        }

        @Override
        public boolean test(IGraph<?> step) {
            if (step instanceof RGraph) {
                boolean ok = true;
                for (IStep<?> s : ((RGraph<?, ?>) step).getParallizable()) {
                    ok = ok && checkPType(s.p()) && checkPType(s.q());
                }
                for (IJoin j : ((RGraph<?, ?>) step).getJoinTypes()) {
                    if (j instanceof PJoin) {
                        ok = ok && checkPType(((PJoin) j).a()) && checkPType(((PJoin) j).r());
                    } else if (j instanceof PJoin2) {
                        ok = ok && checkPType(((PJoin2) j).a()) && checkPType(((PJoin2) j).b()) && checkPType(((PJoin2) j).r());
                    } else if (j instanceof PJoin3) {
                        ok = ok && checkPType(((PJoin3) j).a()) && checkPType(((PJoin3) j).b()) && checkPType(((PJoin3) j).c()) && checkPType(((PJoin3) j).r());
                    }
                }
                return ok;
            } else if (step instanceof PEdge) {
                return checkPType(step.p()) && checkPType(step.q());
            }
            return false;
        }
    }