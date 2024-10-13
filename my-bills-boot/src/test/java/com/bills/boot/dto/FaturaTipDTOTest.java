package com.bills.boot.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FaturaTipDTOTest {

    private Validator validator;

    @BeforeEach
    @Description("Validator setup: Doğrulama için gerekli Validator nesnesi oluşturuluyor.")
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Description("Geçerli DTO: Tüm alanlar doğru şekilde doldurulmuş olmalı ve doğrulama hatasız geçmeli.")
    void testValidDTO() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        dto.setName("Elektrik Faturası");
        dto.setDescription("Aylık elektrik fatura tipi");
        dto.setNeedDate(new Date());
        dto.setActive(true);

        Set<ConstraintViolation<FaturaTipDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO geçerli olmalı");
    }

    @Test
    @Description("Geçersiz İsim: Ad alanı boş bırakıldığı durumda doğrulama hatası alınmalı.")
    void testInvalidName() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        dto.setName(""); // Geçersiz
        dto.setDescription("Aylık elektrik fatura tipi");
        dto.setNeedDate(new Date());
        dto.setActive(true);

        Set<ConstraintViolation<FaturaTipDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Ad alanı boş olamaz");
    }

    @Test
    @Description("Geçersiz Açıklama: Açıklama alanı boş bırakıldığı durumda doğrulama hatası alınmalı.")
    void testInvalidDescription() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        dto.setName("Elektrik Faturası");
        dto.setDescription(""); // Geçersiz
        dto.setNeedDate(new Date());
        dto.setActive(true);

        Set<ConstraintViolation<FaturaTipDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Açıklama boş olamaz");
    }

    @Test
    @Description("Geçersiz Tarih: Tarih alanı boş bırakıldığı durumda doğrulama hatası alınmalı.")
    void testInvalidNeedDate() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        dto.setName("Elektrik Faturası");
        dto.setDescription("Aylık elektrik fatura tipi");
        dto.setNeedDate(null); // Geçersiz
        dto.setActive(true);

        Set<ConstraintViolation<FaturaTipDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Tarih boş olamaz");
    }

    @Test
    @Description("ID alanı doğru bir şekilde döndürülmeli")
    void testGetId() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        assertEquals(1L, dto.getId(), "ID alanı doğru döndürülmeli");
    }

    @Test
    @Description("İsim alanı doğru bir şekilde döndürülmeli")
    void testGetName() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setName("Elektrik Faturası");
        assertEquals("Elektrik Faturası", dto.getName(), "İsim alanı doğru döndürülmeli");
    }

    @Test
    @Description("Açıklama alanı doğru bir şekilde döndürülmeli")
    void testGetDescription() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setDescription("Aylık elektrik fatura tipi");
        assertEquals("Aylık elektrik fatura tipi", dto.getDescription(), "Açıklama alanı doğru döndürülmeli");
    }

    @Test
    @Description("Tarih alanı doğru bir şekilde döndürülmeli")
    void testGetNeedDate() {
        Date date = new Date();
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setNeedDate(date);
        assertEquals(date, dto.getNeedDate(), "Tarih alanı doğru döndürülmeli");
    }

    @Test
    @Description("Aktif durumu doğru bir şekilde döndürülmeli")
    void testIsActive() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setActive(true);
        assertTrue(dto.isActive(), "Aktif durumu doğru döndürülmeli");
    }

}
