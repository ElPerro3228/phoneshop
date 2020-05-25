package com.es.core.model.phone;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration("classpath:context/applicationContext-core.xml")
@PropertySource("classpath:conf/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {
}
