/*
 * Copyright 2014 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.aenygmatic.spring.osgi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields in a Spring component annotated with this will be injected with the matching OSGI service.
 * <p>
 * @author Balazs Berkes
 * @deprecated This annotation is only planed to be implemented.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface OsgiAutowired {

    /**
     * The name of the OSGI service. This name will be used for look up in the OSGI container. If no service name is
     * given the service will be searched by type.
     * <p>
     * @return name of the OSGI service
     */
    String name() default "";
}
