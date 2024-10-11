package com.bills.boot.entity;

import com.bills.boot.repository.FaturaTipRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FaturaTipTest {
    @Autowired
    private FaturaTipRepository faturaTipRepository;

    @Test
    @Description("Zorunlu Alanlar Boş Bırakılarak Kaydedilmeye Çalışıldığında Bir Hata Fırlatması Gerekmektedir.")
    public void whenSavingEntityWithoutRequiredFields_thenThrowException() {
        FaturaTip faturaTip = new FaturaTip();
        assertThrows(ConstraintViolationException.class, () -> {
            faturaTipRepository.saveAndFlush(faturaTip);
        });
    }

    @Test
    @Description("Doğru Değerler İle Fatura Tipi Kaydedilmeye Çalışıldığında Kaydetme İşleminin Gerçekleştirilmesi Gerekmektedir.")
    public void whenSavingEntityWithValidFields_thenShouldPersist() {
        FaturaTip faturaTip = new FaturaTip(null, "Elektrik", "Elektrik faturası", new Date(), true);
        FaturaTip savedFaturaTip = faturaTipRepository.saveAndFlush(faturaTip);
        assertNotNull(savedFaturaTip.getId());
        assertEquals("Elektrik", savedFaturaTip.getName());
        assertEquals("Elektrik faturası", savedFaturaTip.getDescription());
        assertTrue(savedFaturaTip.isActive());
    }

    @Test
    @Description("Fatura Tip Entity sine Değerleri Aynı Olan Nesneler Oluşturulduğunda Eşitliklerinin Aynı Olması Gerekmektedir.")
    public void whenEntitiesAreEqual_thenEqualsShouldReturnTrue() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertEquals(faturaTip1, faturaTip2);
        assertEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    @Description("Fatura Tip Entity sine Değerleri Farklı Olan Nesneler Oluşturulduğunda Eşitliklerinin Olmaması Gerekmektedir.")
    public void whenEntitiesAreNotEqual_thenEqualsShouldReturnFalse() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Elektrik", "Elektrik faturası", new Date(), false);

        assertNotEquals(faturaTip1, faturaTip2);
        assertNotEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    @Description("Aynı Değerlere Sahip Nesnelerin Eşitliğinin Test Edilmesi Gerekmektedir.")
    public void testEquals() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Farklı Değerlere Sahip Nesnelerin Eşitsizliğinin Test Edilmesi Gerekmektedir.")
    public void testNotEquals() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Elektrik", "Elektrik faturası", new Date(), false);

        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Aynı Değerlere Sahip Nesnelerin HashCode Uyumluluğunun Test Edilmesi Gerekmektedir.")
    public void testHashCode() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);

        assertEquals(faturaTip1.hashCode(), faturaTip2.hashCode());
    }

    @Test
    @Description("FaturaTip Sınıfının Constructor'ını Test Eder; Nesnenin Sağlanan Parametrelerle Doğru Bir Şekilde Başlatıldığını Doğrular.")
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
    @Description("FaturaTip Sınıfının toString() Metodunun Doğru Bir Şekilde Çalıştığını Test Eder; Nesnenin String Temsilinin Beklenen Formatla Eşleştiğini Doğrular.")
    public void testToString() {
        Date date = new Date();
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", date, true);
        String expected = "FaturaTip{id=1, name='Su', description='Su faturası', needDate=" + date + ", isActive=true}";

        assertEquals(expected, faturaTip.toString());
    }

    @Test
    @Description("FaturaTip Sınıfının Setters Ve Getters Metodlarının Doğru Bir Şekilde Çalıştığını Test Eder; FaturaTip Nesnesinin Özelliklerinin Beklenildiği Gibi Ayarlandığını Ve Alındığını Doğrular.")
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
    @Description("FaturaTip Nesnesinin Kendisiyle Eşit Olduğunu Test Eder; Aynı Nesne Üzerinde Yapılan Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_SameObject() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertEquals(faturaTip, faturaTip);
    }

    @Test
    @Description("FaturaTip Nesnesinin Null Değeriyle Eşit Olmadığını Test Eder; Null İle Yapılan Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_NullObject() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip, null);
    }

    @Test
    @Description("FaturaTip Nesnesinin Farklı Bir Sınıfla Eşit Olmadığını Test Eder; Farklı Sınıf Türünde Bir Nesne İle Yapılan Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentClass() {
        FaturaTip faturaTip = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip, "String");
    }

    @Test
    @Description("Farklı Id Değerlerine Sahip İki FaturaTip Nesnesinin Eşit Olmadığını Test Eder; Aynı Diğer Özelliklere Sahip Olmalarına Rağmen, Farklı Id'lerin Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentId() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(2L, "Su", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Farklı İsimlere Sahip İki FaturaTip Nesnesinin Eşit Olmadığını Test Eder; Aynı Id'ye Sahip Olmalarına Rağmen, Farklı İsimlerin Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentName() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Elektrik", "Su faturası", new Date(), true);
        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Farklı Açıklamalara Sahip İki FaturaTip Nesnesinin Eşit Olmadığını Test Eder; Aynı Id Ve İsimlere Sahip Olmalarına Rağmen, Farklı Açıklamaların Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentDescription() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Elektrik faturası", new Date(), true);
        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Farklı Tarihlere Sahip İki FaturaTip Nesnesinin Eşit Olmadığını Test Eder; Aynı Id, İsim Ve Açıklamalara Sahip Olmalarına Rağmen, Farklı Tarihlerin Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentNeedDate() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(System.currentTimeMillis() + 10000), true);
        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Farklı IsActive Durumlarına Sahip İki FaturaTip Nesnesinin Eşit Olmadığını Test Eder; Aynı Id, İsim, Açıklama Ve Tarihlere Sahip Olmalarına Rağmen, Farklı IsActive Değerlerinin Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_DifferentIsActive() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), false);
        assertNotEquals(faturaTip1, faturaTip2);
    }

    @Test
    @Description("Tüm Değerleri Aynı Olan İki FaturaTip Nesnesinin Eşit Olduğunu Test Eder; Aynı Id, İsim, Açıklama, Tarih Ve IsActive Değerlerine Sahip İki Nesnenin Eşitlik Kontrolünde Beklenilen Sonucun Doğru Olduğunu Doğrular.")
    public void testEquals_SameValues() {
        FaturaTip faturaTip1 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        FaturaTip faturaTip2 = new FaturaTip(1L, "Su", "Su faturası", new Date(), true);
        assertEquals(faturaTip1, faturaTip2);
    }

}
