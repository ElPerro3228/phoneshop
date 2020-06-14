package com.es.core.model.phone;

import com.es.core.services.PhoneService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PhoneServiceTest extends AbstractIntegrationTest {
    @Autowired
    private PhoneService phoneService;

    @Test
    public void shouldReturnFirstTenPhonesWhichMatchSearchQueryAndHaveNotEmptyStockAndPrice() {
        String searchQuery = "ARCHOS";
        int page = 1;
        List<Phone> phones = phoneService.getPhoneList(page, searchQuery, "price", SortOrder.ASC);
        assertThat(phones).hasSize(10)
                .allMatch(phone -> phone.getPrice().doubleValue() > 0);
    }

    @Test
    public void shouldReturnCorrectPagesNumber() {
        int pagesNumber = phoneService.getPagesNumber();
        assertThat(pagesNumber).isEqualTo(2);
    }
}
