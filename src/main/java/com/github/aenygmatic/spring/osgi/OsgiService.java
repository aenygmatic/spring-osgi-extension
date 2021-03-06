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

import org.springframework.stereotype.Service;

/**
 * Classes annotated with {@code @OsgiService} will be subject of Spring component scan and will also be published to
 * the OSGI container.
 * <p>
 * @author Balazs Berkes
 */
@Service
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OsgiService {

    /**
     * The name which the service will be registered to the OSGI container.
     * <p>
     * @return name of the service
     */
    String name() default "";

    /**
     * The interface type as the service will be registered to the OSGI container. The annotated class must implement
     * the given interface.
     * <p>
     * @return interface as the service will be registered
     */
    Class<?> registration() default ByInterfaceRegistration.class;

    public static final class ByInterfaceRegistration {

        private ByInterfaceRegistration() {
        }
    }
}
