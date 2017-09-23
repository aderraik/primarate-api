package com.visiansystems.bl.bankRateFeed.ecb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
public class EcbRpcTest {

    @Autowired
    private ApplicationContext applicationContext;

    private EcbRpc ecbRpc;

    @Before
    public void setup() {
        ecbRpc = new EcbRpc();
    }

    @Test
    public void testNumElementsWhenLoadingLastDayRatesXml() {
        try {
            String filename = "/tmp/ecb.xml";
            ecbRpc.selectLastDayRates();
            ecbRpc.setCacheFileName(filename);
            ecbRpc.refreshCacheFile();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filename);
            NodeList list = doc.getElementsByTagName("Cube");

            Assert.assertEquals(list.getLength(),
                                33); //31 currencies + 1 main element + 1 time element
        }
        catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testDefaultCacheFileCreation() {
        try {
            ecbRpc.refreshCacheFile();
            File f = new File(ecbRpc.getCacheFileName());
            Boolean ret = false;
            if (f.exists() && !f.isDirectory()) {
                ret = true;
            }
            Assert.assertTrue(ret);
        }

        catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}
