package com.es.core.model.phone;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class JdbcPhoneDaoIntegrationTest extends AbstractTest {
    @Autowired
    private PhoneDao phoneDao;

    @Test
    public void testFindAll() {
        List<Phone> phones = phoneDao.findAll(0, 10);
        assertThat(phones).hasSize(10)
                .allMatch(phone -> phone.getColors().size() > 0);
    }

    @Test
    public void shouldGetNotEmptyPhone() {
        Optional<Phone> phone = phoneDao.get(1000L);
        assertThat(phone).isPresent();
    }

    @Test
    public void shouldInsertPhoneWithEmptyId() {
        Phone phone = new Phone();
        phone.setBrand("");
        phone.setModel("");
        phoneDao.save(phone);
        assertThat(phone.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateExistingPhone() {
        Phone phone = phoneDao.get(1000L).get();
        phone.setBrand("");
        phoneDao.save(phone);
        phone = phoneDao.get(1000L).get();
        assertThat(phone.getBrand()).isEqualTo("");
    }
}
