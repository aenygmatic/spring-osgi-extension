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
package com.github.aenygmatic.spring.osgi.registration;

/**
 * This exception is thrown when the Spring component annotated with
 * {@link com.github.aenygmatic.spring.osgi.OsgiService} does not fulfill the requirements of the annotation.
 * <p>
 * @author Balazs Berkes
 */
public class NotRegistrableServiceException extends RuntimeException {

    public NotRegistrableServiceException(String message) {
        super(message);
    }
}
