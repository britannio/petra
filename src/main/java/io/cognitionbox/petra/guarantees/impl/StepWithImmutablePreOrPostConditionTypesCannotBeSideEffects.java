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

import io.cognitionbox.petra.guarantees.StepCheck;
import io.cognitionbox.petra.lang.annotations.Immutable;
import io.cognitionbox.petra.core.IStep;

public class StepWithImmutablePreOrPostConditionTypesCannotBeSideEffects implements StepCheck {

        private boolean isImmutableType(Class<?> clazz) {
            if (clazz.getAnnotationsByType(Immutable.class).length > 0 ||
                    Boolean.class.isAssignableFrom(clazz) ||
                    Number.class.isAssignableFrom(clazz) ||
                    String.class.isAssignableFrom(clazz)) {
                return true;
            }
            return false;
        }

        private boolean isSideEffect(IStep<?, ?> step) {
            return step.getEffectType().isPresent();
        }

        @Override
        public boolean test(IStep<?, ?> step) {
            if (isSideEffect(step) && (isImmutableType(step.p().getTypeClass()) ||
                    isImmutableType(step.q().getTypeClass()))) {
                return false;
            }
            return true;
        }
    }