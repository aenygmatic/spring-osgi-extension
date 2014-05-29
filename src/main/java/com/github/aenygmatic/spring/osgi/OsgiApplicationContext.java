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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import com.github.aenygmatic.spring.osgi.registration.SpringBundleRegistry;

/**
 * Spring application context which also an OSGI bundle activator. To use this class for connect the Spring context to
 * the OSGI container, you should extend this class and pass your spring application context in the constructor. After
 * doing that you can set that class as your bundle activator.
 * <p>
 * @author Balazs Berkes
 */
public class OsgiApplicationContext implements ConfigurableApplicationContext, BundleActivator {

    private ConfigurableApplicationContext springContext;
    private SpringBundleRegistry boundleRegistry;

    public OsgiApplicationContext(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        springContext.setParent(this);
        springContext.getBeanFactory().registerSingleton("ApplicationContext", this);
        springContext.refresh();
        boundleRegistry = new SpringBundleRegistry(springContext, bundleContext);
        boundleRegistry.register();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        boundleRegistry.unregister();
        close();
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        springContext.refresh();
        boundleRegistry.unregister();
        boundleRegistry.register();
    }

    @Override
    public void setId(String string) {
        springContext.setId(string);
    }

    @Override
    public void setParent(ApplicationContext ac) {
        springContext.setParent(ac);
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        return springContext.getEnvironment();
    }

    @Override
    public void setEnvironment(ConfigurableEnvironment ce) {
        springContext.setEnvironment(ce);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor bfpp) {
        springContext.addBeanFactoryPostProcessor(bfpp);
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> al) {
        springContext.addApplicationListener(al);
    }

    @Override
    public void registerShutdownHook() {
        springContext.registerShutdownHook();
    }

    @Override
    public void close() {
        springContext.close();
    }

    @Override
    public boolean isActive() {
        return springContext.isActive();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return springContext.getBeanFactory();
    }

    @Override
    public String getId() {
        return springContext.getId();
    }

    @Override
    public String getApplicationName() {
        return springContext.getApplicationName();
    }

    @Override
    public String getDisplayName() {
        return springContext.getDisplayName();
    }

    @Override
    public long getStartupDate() {
        return springContext.getStartupDate();
    }

    @Override
    public ApplicationContext getParent() {
        return springContext.getParent();
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return springContext.getAutowireCapableBeanFactory();
    }

    @Override
    public boolean containsBeanDefinition(String string) {
        return springContext.containsBeanDefinition(string);
    }

    @Override
    public int getBeanDefinitionCount() {
        return springContext.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return springContext.getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return springContext.getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean bln, boolean bln1) {
        return springContext.getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return springContext.getBeansOfType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean bln, boolean bln1) throws BeansException {
        return springContext.getBeansOfType(type, bln1, bln1);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> type) {
        return springContext.getBeanNamesForAnnotation(type);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> type) throws BeansException {
        return springContext.getBeansWithAnnotation(type);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String string, Class<A> type) throws NoSuchBeanDefinitionException {
        return springContext.findAnnotationOnBean(string, type);
    }

    @Override
    public Object getBean(String string) throws BeansException {
        return springContext.getBean(string);
    }

    @Override
    public <T> T getBean(String string, Class<T> type) throws BeansException {
        return springContext.getBean(string, type);
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeansException {
        return springContext.getBean(type);
    }

    @Override
    public Object getBean(String string, Object... os) throws BeansException {
        return springContext.getBean(string, os);
    }

    @Override
    public boolean containsBean(String string) {
        return springContext.containsBean(string);
    }

    @Override
    public boolean isSingleton(String string) throws NoSuchBeanDefinitionException {
        return springContext.isSingleton(string);
    }

    @Override
    public boolean isPrototype(String string) throws NoSuchBeanDefinitionException {
        return springContext.isPrototype(string);
    }

    @Override
    public boolean isTypeMatch(String string, Class<?> type) throws NoSuchBeanDefinitionException {
        return springContext.isTypeMatch(string, type);
    }

    @Override
    public Class<?> getType(String string) throws NoSuchBeanDefinitionException {
        return springContext.getType(string);
    }

    @Override
    public String[] getAliases(String string) {
        return springContext.getAliases(string);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return springContext.getParentBeanFactory();
    }

    @Override
    public boolean containsLocalBean(String string) {
        return springContext.containsLocalBean(string);
    }

    @Override
    public String getMessage(String string, Object[] os, String string1, Locale locale) {
        return springContext.getMessage(string, os, string1, locale);
    }

    @Override
    public String getMessage(String string, Object[] os, Locale locale) throws NoSuchMessageException {
        return springContext.getMessage(string, os, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable msr, Locale locale) throws NoSuchMessageException {
        return springContext.getMessage(msr, locale);
    }

    @Override
    public void publishEvent(ApplicationEvent ae) {
        springContext.publishEvent(ae);
    }

    @Override
    public Resource[] getResources(String string) throws IOException {
        return springContext.getResources(string);
    }

    @Override
    public Resource getResource(String string) {
        return springContext.getResource(string);
    }

    @Override
    public ClassLoader getClassLoader() {
        return springContext.getClassLoader();
    }

    @Override
    public void start() {
        springContext.start();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    @Override
    public boolean isRunning() {
        return springContext.isRunning();
    }
}
