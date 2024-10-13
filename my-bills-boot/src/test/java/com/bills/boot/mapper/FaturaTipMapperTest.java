package com.bills.boot.mapper;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FaturaTipMapperTest {

    private FaturaTipMapper faturaTipMapper;

    @BeforeEach
    void setUp() {
        faturaTipMapper = new FaturaTipMapper();
    }

    @Test
    void testToDTO_withValidEntity() {
        // Arrange
        FaturaTip faturaTip = new FaturaTip();
        faturaTip.setId(1L);
        faturaTip.setName("Elektrik");
        faturaTip.setDescription("Elektrik faturası");
        faturaTip.setNeedDate(new Date(2024, 10, 1));
        faturaTip.setActive(true);
        FaturaTipDTO dto = faturaTipMapper.toDTO(faturaTip);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Elektrik", dto.getName());
        assertEquals("Elektrik faturası", dto.getDescription());
        assertEquals(new Date(2024, 10, 1), dto.getNeedDate());
        assertTrue(dto.isActive());
    }

    @Test
    void testToDTO_withNullEntity2() {
        // Act
        FaturaTipDTO dto = faturaTipMapper.toDTO(null);

        // Assert
        assertNull(dto); // faturaTip null olduğunda, dönüş değeri de null olmalıdır.
    }

    @Test
    void testToDTO_withNullEntity() {
        FaturaTip faturaTip = new FaturaTip();
        faturaTip.setId(1L);
        faturaTip.setName("Elektrik");
        faturaTip.setDescription("Elektrik faturası");
        faturaTip.setNeedDate(new Date(2024, 10, 1));
        faturaTip.setActive(true);
        FaturaTipDTO dto = faturaTipMapper.toDTO(faturaTip);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Elektrik", dto.getName());
        assertEquals("Elektrik faturası", dto.getDescription());
        assertTrue(dto.getNeedDate().equals(new Date(2024, 10, 1))); // Güncelleme burada
        assertTrue(dto.isActive());
    }

    @Test
    void testToEntity_withValidDTO() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(1L);
        dto.setName("Su");
        dto.setDescription("Su faturası");
        dto.setNeedDate(new Date(2024, 10, 2));
        dto.setActive(true);
        FaturaTip faturaTip = faturaTipMapper.toEntity(dto);
        assertNotNull(faturaTip);
        assertEquals(1L, faturaTip.getId());
        assertEquals("Su", faturaTip.getName());
        assertEquals("Su faturası", faturaTip.getDescription());
        assertEquals(new Date(2024, 10, 2), faturaTip.getNeedDate());
        assertTrue(faturaTip.isActive());
    }

    @Test
    void testToEntity_withNullDTO() {
        FaturaTip faturaTip = faturaTipMapper.toEntity(null);
        assertNull(faturaTip);
    }

    @Test
    void testUpdateEntityFromDTO_withValidData() {
        FaturaTip existingFaturaTip = new FaturaTip();
        existingFaturaTip.setId(1L);
        existingFaturaTip.setName("Telefon");
        existingFaturaTip.setDescription("Telefon faturası");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.OCTOBER, 1, 0, 0, 0);
        existingFaturaTip.setNeedDate(calendar.getTime());
        existingFaturaTip.setActive(false);
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setName("Güncellenmiş Telefon");
        dto.setDescription("Güncellenmiş telefon faturası");
        calendar.set(2024, Calendar.OCTOBER, 2);
        dto.setNeedDate(calendar.getTime());
        dto.setActive(true);
        faturaTipMapper.updateEntityFromDTO(dto, existingFaturaTip);
        assertEquals("Güncellenmiş Telefon", existingFaturaTip.getName());
        assertEquals("Güncellenmiş telefon faturası", existingFaturaTip.getDescription());
        assertEquals(dto.getNeedDate().getTime(), existingFaturaTip.getNeedDate().getTime());
        assertTrue(existingFaturaTip.isActive());
    }

    @Test
    void testUpdateEntityFromDTO_withNullDTO() {
        FaturaTip existingFaturaTip = new FaturaTip();
        existingFaturaTip.setId(1L);
        faturaTipMapper.updateEntityFromDTO(null, existingFaturaTip);
        assertEquals(1L, existingFaturaTip.getId());
    }

    @Test
    void testUpdateEntityFromDTO_withNullEntity() {
        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setName("Yeni isim");
        faturaTipMapper.updateEntityFromDTO(dto, null);
    }

}
