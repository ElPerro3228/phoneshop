package com.es.core.model.phone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class JdbcPhoneDaoTest extends AbstractTest{
    @Autowired
    private PhoneDao phoneDao;

    @Before
    public void setup() throws IOException {
        insertTestData();
    }

    @After
    public void finish() throws IOException {
        clearTables();
    }

    @Test
    public void testFindAll() {
        List<Phone> phones = phoneDao.findAll(0, 10);
        assertEquals(10, phones.size());
        for (Phone phone : phones) {
            assertTrue(phone.getColors().size() > 0);
        }
    }

    @Test
    public void shouldGetNotEmptyPhone() {
        Optional<Phone> phone = phoneDao.get(1000L);
        assertTrue(phone.isPresent());
    }

    @Test
    public void shouldInsertPhoneWithEmptyId() {
        Phone phone = new Phone();
        phone.setBrand("");
        phone.setModel("");
        phoneDao.save(phone);
        assertNotNull(phone.getId());
    }

    @Test
    public void shouldUpdateExistingPhone() {
        Phone phone = phoneDao.get(1000L).get();
        phone.setBrand("");
        phoneDao.save(phone);
        phone = phoneDao.get(1000L).get();
        assertEquals("", phone.getBrand());
    }
}
