package com.visiansystems.controller;

import com.visiansystems.docs.DocsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DocsControllerTest {
    private MockMvc mvc;

    @InjectMocks
    private DocsController underTest;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    public void slashDocsRedirectsToSwaggerUI() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/docs/"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Assert.assertEquals("/swagger-ui.html", result.getResponse().getHeader("Location"));
    }
}
