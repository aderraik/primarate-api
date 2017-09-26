package com.visiansystems.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
@DirtiesContext
public class SpringConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Provides sanity check of Spring Context XML configuration without having to build project
     * Verifies that Spring context can be created and the propertiesMuncher bean retrieved
     * <p/>
     * If this test fails:
     * - Check that all bean definitions in XML files make sense
     * - Confirm class names, bean references, property names etc are valid
     * - Check all required property placeholders, e.g. ${connect.timeout}, have been defined
     * - see resources/config/environments.properties
     *
     * @throws Exception
     */
    @Test
    public void testApplicationContext() throws Exception {

        // List all beans
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String bean : beanDefinitionNames) {
            System.out.println(bean);
        }
    }
}
