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

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.context.ApplicationContext;

import com.github.aenygmatic.spring.osgi.OsgiService;

public class OsgiSpringComponentCollectorTest {

    @Mock
    private ApplicationContext context;

    @InjectMocks
    private OsgiSpringComponentCollector underTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindOsgiComponentsWithRegistrationGiven() {
        OsgiObjectWithDireclyGivenInterface expected = new OsgiObjectWithDireclyGivenInterface();
        givenAnnotatedSpringComponent(expected);

        List<SpringOsgiComponent> components = underTest.findOsgiComponents(context);

        assertProperties(components.get(0), expected, Callable.class, "java.util.concurrent.Callable");
    }

    @Test
    public void testFindOsgiComponentsWithImpementingInterface() {
        OsgiObjectWithInterface expected = new OsgiObjectWithInterface();
        givenAnnotatedSpringComponent(expected);

        List<SpringOsgiComponent> components = underTest.findOsgiComponents(context);

        assertProperties(components.get(0), expected, Runnable.class, "java.lang.Runnable");
    }

    @Test(expected = NotRegistrableServiceException.class)
    public void testFindOsgiComponentsWithoutRegistrableProperty() {
        OsgiObjectWithoutRegistrableInterface expected = new OsgiObjectWithoutRegistrableInterface();
        givenAnnotatedSpringComponent(expected);

        underTest.findOsgiComponents(context);
    }

    @Test(expected = NotRegistrableServiceException.class)
    public void testFindOsgiComponentsWithNotImpementedRegistration() {
        OsgiObjectWithNotImplementedRegistration expected = new OsgiObjectWithNotImplementedRegistration();
        givenAnnotatedSpringComponent(expected);

        underTest.findOsgiComponents(context);
    }

    private void assertProperties(SpringOsgiComponent component, Object expected, Class<?> type, String typeAsString) {
        assertEquals(expected, component.getBean());
        assertEquals(type, component.getRegistration());
        assertEquals(typeAsString, component.getRegistrationsAsString());
    }

    private void givenAnnotatedSpringComponent(Object bean) {
        Map<String, Object> beans = new HashMap<>();
        beans.put("", bean);
        given(context.getBeansWithAnnotation(OsgiService.class)).willReturn(beans);
    }

    @OsgiService(registration = Callable.class)
    private class OsgiObjectWithDireclyGivenInterface implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            return null;
        }
    }

    @OsgiService
    private class OsgiObjectWithInterface implements Runnable {

        @Override
        public void run() {
        }
    }

    @OsgiService(registration = Callable.class)
    private class OsgiObjectWithNotImplementedRegistration implements Runnable {

        @Override
        public void run() {
        }
    }

    @OsgiService
    private class OsgiObjectWithoutRegistrableInterface {
    }
}
