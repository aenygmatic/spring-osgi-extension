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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.github.aenygmatic.spring.osgi.OsgiService;
import com.github.aenygmatic.spring.osgi.OsgiService.ByInterfaceRegistration;

/**
 * Searches for components annotated with {@link OsgiService} in the Spring application Context;
 * <p>
 * @author Balazs Berkes
 */
public class OsgiSpringComponentCollector {

    public List<SpringOsgiComponent> findOsgiComponents(ApplicationContext springContext) {
        List<SpringOsgiComponent> components = new ArrayList<>();

        for (Map.Entry<String, Object> entry : springContext.getBeansWithAnnotation(OsgiService.class).entrySet()) {
            Object bean = entry.getValue();
            components.add(new SpringOsgiComponent(registration(bean), bean, null));
        }

        return components;
    }

    private Class<?> registration(Object bean) {
        Class<?> registration = bean.getClass().getAnnotation(OsgiService.class).registration();
        if (ByInterfaceRegistration.class.equals(registration)) {
            registration = findImpementedInterface(bean.getClass());
        }
        return registration;
    }

    private Class<?> findImpementedInterface(Class<? extends Object> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces != null & interfaces.length > 0) {
            return interfaces[0];
        } else {
            throw new NotRegistrableServiceException("Component annotated with @OsgiService must have a registration interface given or at least the class should implement an interface!");
        }
    }
}
