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
package integrationtest.registration.context;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.github.aenygmatic.spring.osgi.OsgiService;

@OsgiService(registration = java.lang.Runnable.class)
public class ApplicationContextHolder implements Callable<Void>, Runnable {

    @Autowired
    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run() {
    }

    @Override
    public Void call() throws Exception {
        return null;
    }
}
