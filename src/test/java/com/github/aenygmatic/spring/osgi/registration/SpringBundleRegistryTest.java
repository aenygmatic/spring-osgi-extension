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

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.context.ApplicationContext;

public class SpringBundleRegistryTest {

    @Spy
    private List<ServiceRegistration> registrations = new ArrayList<>();
    @Mock
    private ServiceRegistration registrationA;
    @Mock
    private ServiceRegistration registrationB;
    @Mock
    private ApplicationContext springContext;
    @Mock
    private BundleContext bundleContext;
    @Mock
    private OsgiSpringComponentCollector collector;

    @InjectMocks
    private SpringBundleRegistry underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        ArrayList<SpringOsgiComponent> components = new ArrayList<>();
        given(collector.findOsgiComponents(springContext)).willReturn(components);

        givenExpectedSpringServiceRegisteredAs(registrationA, components);
        givenExpectedSpringServiceRegisteredAs(registrationB, components);

        underTest.register();

        assertTrue(registrations.contains(registrationA));
        assertTrue(registrations.contains(registrationB));
    }

    @Test
    public void testUnregister() {
        registrations.add(registrationA);
        registrations.add(registrationB);

        underTest.unregister();

        verify(registrationA).unregister();
        verify(registrationB).unregister();
        assertTrue(registrations.isEmpty());
    }

    private void givenExpectedSpringServiceRegisteredAs(ServiceRegistration registration, ArrayList<SpringOsgiComponent> components) {
        Object bean = new Object();
        DummyDictionary beanParameters = new DummyDictionary();
        components.add(new SpringOsgiComponent(Object.class, bean, beanParameters));
        given(bundleContext.registerService("java.lang.Object", bean, beanParameters)).willReturn(registration);
    }

    private class DummyDictionary extends Dictionary<String, Object> {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Enumeration<String> keys() {
            return null;
        }

        @Override
        public Enumeration<Object> elements() {
            return null;
        }

        @Override
        public Object get(Object key) {
            return null;
        }

        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Override
        public Object remove(Object key) {
            return null;
        }
    }
}
