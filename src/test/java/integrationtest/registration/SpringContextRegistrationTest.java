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
package integrationtest.registration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Dictionary;

import integrationtest.registration.context.ApplicationContextHolder;
import integrationtest.registration.context.SpringBundleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.framework.BundleContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.aenygmatic.spring.osgi.OsgiApplicationContext;

public class SpringContextRegistrationTest {

    private AnnotationConfigApplicationContext springContext;
    @Mock
    private BundleContext bundleContext;

    private OsgiApplicationContext underTest;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        springContext = new AnnotationConfigApplicationContext("integrationtest.registration.context");
        underTest = new OsgiApplicationContext(springContext);
        underTest.start(bundleContext);
    }

    @Test
    public void springComponentRegistration() {
        verify(bundleContext).registerService(eq("integrationtest.registration.context.BundleService"), any(SpringBundleService.class), any(Dictionary.class));
        verify(bundleContext).registerService(eq("java.lang.Runnable"), any(ApplicationContextHolder.class), any(Dictionary.class));
    }

    @Test
    public void osgiAutowiring() {
        assertEquals(springContext, springContext.getBean(ApplicationContextHolder.class).getContext());
    }

    @After
    public void tearDown() {
        springContext.close();
    }
}
