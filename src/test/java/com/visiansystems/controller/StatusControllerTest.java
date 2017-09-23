package com.visiansystems.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO: Create test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
//@WebAppConfiguration
public class StatusControllerTest {

    /*
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getFoo() throws Exception {
        this.mockMvc.perform(get("/status"))
                .andExpect(status().isOk());
//                .andExpect(content().i);
    }
    */

    @Test
    public void foo() {
        Assert.assertTrue(true);
    }
}