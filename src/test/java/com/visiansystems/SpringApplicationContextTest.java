package com.visiansystems;

import com.visiansystems.context.TestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Provides sanity check of Spring Context XML configuration without having to build project
 * Verifies that Spring context can be created and the Spring Boot application bean retrieved
 * <p/>
 * If this test fails:
 * - Check that all bean definitions in context classes make sense;
 * - Confirm class names, bean references, property names etc are valid;
 * - Check all required property placeholders, e.g. ${connect.timeout}, have been defined;
 */
@ActiveProfiles({ "unit-test" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
@WebAppConfiguration
public class SpringApplicationContextTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    public void testContextLoads() throws Exception {
        assertNotNull(ctx);

        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String bean : beanDefinitionNames) {
            System.out.println("Found bean: " + bean);
        }

        assertTrue(this.ctx.containsBean("testContext"));
    }
}
