package com.es.core.model.phone;

import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ContextConfiguration("classpath:context/applicationContext-core.xml")
@PropertySource("classpath:conf/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${db.testScript}")
    private String testScript;

    protected void insertTestData() throws IOException {
        jdbcTemplate.execute(IOUtils.resourceToString(testScript, StandardCharsets.UTF_8));
    }

    protected void clearTables() throws IOException {
        jdbcTemplate.execute(IOUtils.resourceToString("/db/schema.sql", StandardCharsets.UTF_8));
    }
}
