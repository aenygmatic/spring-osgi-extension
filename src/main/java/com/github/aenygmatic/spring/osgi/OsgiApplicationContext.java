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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.springframework.context.ConfigurableApplicationContext;

import com.github.aenygmatic.spring.osgi.registration.SpringBundleRegistry;

/**
 * Spring application context which also an OSGI bundle activator. To use this class for connect the Spring context to
 * the OSGI container, you should extend this class and pass your spring application context in the constructor. After
 * doing that you can set that class as your bundle activator.
 * <p>
 * @author Balazs Berkes
 */
public class OsgiApplicationContext implements BundleActivator {

    private ConfigurableApplicationContext springContext;
    private SpringBundleRegistry bundleRegistry;

    public OsgiApplicationContext(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleRegistry = new SpringBundleRegistry(springContext, bundleContext);
        bundleRegistry.register();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        bundleRegistry.unregister();
        springContext.close();
    }
}
