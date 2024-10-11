package com.bills.boot.entity;

import com.bills.boot.repository.FaturaTipRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FaturaTipTest {
    @Autowired
    private FaturaTipRepository faturaTipRepository;

    @Test
    public void whenSavingEntityWithoutRequiredFields_thenThrowException() {
        FaturaTip faturaTip = new FaturaTip();
        // Zorunlu alanlar boş bırakılmış durumda
        assertThrows(ConstraintViolationException.class, () -> {
            faturaTipRepository.saveAndFlush(faturaTip);
        });
    }

    @Test
    public void whenSavingEntityWithValidFields_thenShouldPersist() {
        FaturaTip faturaTip = new FaturaTip(null, "Elektrik", "Elektrik faturası", new Date(), true);

        // Entity kaydediliyor
        FaturaTip savedFaturaTip = faturaTipRepository.saveAndFlush(faturaTip);

        // ID'nin otomatik oluşturulmuş olması gerekir
        assertNotNull(savedFaturaTip.getId());
        assertEquals("Elektrik", savedFaturaTip.getName());
        assertEquals("Elektrik faturası", savedFaturaTip.getDescription());
        assertTrue(savedFaturaTip.isActive());
    }

    @Test
    public void whenEntitiesAreEqual_thenEqualsShouldReturnTrue() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1, faturaTip2);
        assertEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    public void whenEntitiesAreNotEqual_thenEqualsShouldReturnFalse() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Elektrik", "Elektrik faturası", new Date(), false);

        assertNotEquals(faturaTip1, faturaTip2);
        assertNotEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    public void testEquals() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1, faturaTip2);
    }

    @Test
    public void testNotEquals() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Elektrik", "Elektrik faturası", new Date(), false);

        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    public void testHashCode() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    public void testConstructor() {
        Date date = new Date();
        FaturaTip faturaTip = new FaturaTip(1L, "Elektrik", "Elektrik faturası", date, true);

        assertEquals(1L, faturaTip.getId());
        assertEquals("Elektrik", faturaTip.getName());
        assertEquals("Elektrik faturası", faturaTip.getDescription());
        assertEquals(date, faturaTip.getNeedDate());
        assertTrue(faturaTip.isActive());
    }

    @Test
    public void testToString() {
        Date date = new Date();
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", date, true);
        String expected = "FaturaTip{id=1, name='Su', description='Su faturası', needDate=" + date + ", isActive=true}";

        assertEquals(expected, faturaTip.toString());
    }

    @Test
    public void testSettersAndGetters() {
        FaturaTip faturaTip = new FaturaTip();

        faturaTip.setId(1L);
        faturaTip.setName("Doğalgaz");
        faturaTip.setDescription("Doğalgaz faturası");
        Date date = new Date();
        faturaTip.setNeedDate(date);
        faturaTip.setActive(true);

        assertEquals(1L, faturaTip.getId());
        assertEquals("Doğalgaz", faturaTip.getName());
        assertEquals("Doğalgaz faturası", faturaTip.getDescription());
        assertEquals(date, faturaTip.getNeedDate());
        assertTrue(faturaTip.isActive());
    }

    @Test
    public void testEquals_SameObject() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertEquals(faturaTip, faturaTip); // Kendisiyle eşit olmalı
    }

    @Test
    public void testEquals_NullObject() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip, null); // Null ile eşit olmamalı
    }

    @Test
    public void testEquals_DifferentClass() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip, "String"); // Farklı sınıf ile eşit olmamalı
    }

    @Test
    public void testEquals_DifferentId() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Su", "Su faturası", new Date(), true);

        assertNotEquals(faturaTip1, faturaTip2); // Farklı id'ye sahip nesneler eşit olmamalı
    }

    @Test
    public void testEquals_DifferentName() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Elektrik", "Su faturası", new Date(), true);

        assertNotEquals(faturaTip1, faturaTip2); // Farklı isimlere sahip nesneler eşit olmamalı
    }

    @Test
    public void testEquals_DifferentDescription() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Elektrik faturası", new Date(), true);

        assertNotEquals(faturaTip1, faturaTip2); // Farklı açıklamalara sahip nesneler eşit olmamalı
    }

    @Test
    public void testEquals_DifferentNeedDate() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(System.currentTimeMillis() + 10000), true);

        assertNotEquals(faturaTip1, faturaTip2); // Farklı tarihlere sahip nesneler eşit olmamalı
    }

    @Test
    public void testEquals_DifferentIsActive() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), false);

        assertNotEquals(faturaTip1, faturaTip2); // Farklı isActive durumuna sahip nesneler eşit olmamalı
    }

    @Test
    public void testEquals_SameValues() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1, faturaTip2); // Tüm değerleri aynı olan nesneler eşit olmalı
    }

}
