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

import java.util.Dictionary;

/**
 * Contains properties of the OSGI Spring component which is required for the OSGI bundle.
 * <p>
 * @author Balazs Berkes
 */
public class SpringOsgiComponent {

    private final Class<?> registration;
    private final Dictionary<String, ?> properties;
    private final Object bean;

    public SpringOsgiComponent(Class<?> registration, Object bean, Dictionary<String, ?> properties) {
        this.registration = registration;
        this.bean = bean;
        this.properties = properties;
    }

    public String getRegistrationsAsString() {
        return registration.getName();
    }

    public Class<?> getRegistration() {
        return registration;
    }

    public Object getBean() {
        return bean;
    }

    public Dictionary<String, ?> getProperties() {
        return properties;
    }
}
