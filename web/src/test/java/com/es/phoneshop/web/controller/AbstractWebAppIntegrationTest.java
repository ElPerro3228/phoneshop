package com.es.phoneshop.web.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
@WebAppConfiguration(value = "classpath:web.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractWebAppIntegrationTest {
}
