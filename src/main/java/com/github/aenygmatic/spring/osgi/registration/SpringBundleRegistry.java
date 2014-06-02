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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.context.ApplicationContext;

/**
 * Registers the desired Spring components to the OSGI container.
 * <p>
 * @author Balazs Berkes
 */
public class SpringBundleRegistry {

    private final OsgiSpringComponentCollector collector;
    private final List<ServiceRegistration> registrations;
    private final ApplicationContext springContext;
    private final BundleContext bundleContext;

    public SpringBundleRegistry(ApplicationContext springContext, BundleContext bundleContext) {
        this(new OsgiSpringComponentCollector(), new ArrayList<ServiceRegistration>(), springContext, bundleContext);
    }

    SpringBundleRegistry(OsgiSpringComponentCollector collector, List<ServiceRegistration> registrations, ApplicationContext springContext, BundleContext bundleContext) {
        this.collector = collector;
        this.registrations = registrations;
        this.springContext = springContext;
        this.bundleContext = bundleContext;
    }

    public void register() {
        for (SpringOsgiComponent component : collector.findOsgiComponents(springContext)) {
            registrations.add(registerService(component));
        }
    }

    private ServiceRegistration registerService(SpringOsgiComponent component) {
        return bundleContext.registerService(component.getRegistrationsAsString(), component.getBean(), component.getProperties());
    }

    public void unregister() {
        for (ServiceRegistration registration : registrations) {
            registration.unregister();
        }
        registrations.clear();
    }
}
