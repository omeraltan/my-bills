package com.bills.boot.service;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import com.bills.boot.exception.FaturaNotFoundException;
import com.bills.boot.mapper.FaturaTipMapper;
import com.bills.boot.repository.FaturaTipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Description;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FaturaTipServiceTest {

    @Mock
    private FaturaTipRepository repository;

    @Mock
    private MessageSource messageSource;

    @Mock
    private FaturaTipMapper mapper;

    @InjectMocks
    private FaturaTipService faturaTipService;

    @Test
    @Description("Repository'den veri varsa, mapper ile DTO'ya çevriliyor ve sonuç olarak DTO listesi döndürülüyor.")
    void testGetAllFaturaTip_whenFaturaTipsExist_shouldReturnDTOs(){
        Pageable pageable = PageRequest.of(0, 10);
        FaturaTip faturaTip = new FaturaTip();  // Örnek entity
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();  // Örnek DTO
        Page<FaturaTip> faturaTipPage = new PageImpl<>(List.of(faturaTip));

        when(repository.findAll(pageable)).thenReturn(faturaTipPage);
        when(mapper.toDTO(faturaTip)).thenReturn(faturaTipDTO);

        Page<FaturaTipDTO> result = faturaTipService.getAllFaturaTip(pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).contains(faturaTipDTO);
        verify(repository).findAll(pageable);
        verify(mapper).toDTO(faturaTip);
    }

    @Test
    @Description("Repository boş bir liste dönerse, FaturaNotFoundException istisnası fırlatılıyor.")
    void testGetAllFaturaTip_whenNoFaturaTipFound_shouldThrowException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(Page.empty());
        when(messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale()))
            .thenReturn("Fatura tipi bulunamadı");

        assertThatThrownBy(() -> faturaTipService.getAllFaturaTip(pageable))
            .isInstanceOf(FaturaNotFoundException.class)
            .hasMessage("Fatura tipi bulunamadı");

        verify(repository).findAll(pageable);
    }

    @Test
    @Description("Bu testte bir DTO'nun doğru şekilde entity'ye çevrilip, kaydedilip ve DTO olarak döndürüldüğünü doğruluyoruz.")
    void testCreateFaturaTip_shouldSaveAndReturnDTO() {
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();
        FaturaTip faturaTip = new FaturaTip();

        when(mapper.toEntity(faturaTipDTO)).thenReturn(faturaTip);
        when(repository.save(faturaTip)).thenReturn(faturaTip);
        when(mapper.toDTO(faturaTip)).thenReturn(faturaTipDTO);

        // Act
        FaturaTipDTO result = faturaTipService.createFaturaTip(faturaTipDTO);

        // Assert
        assertThat(result).isEqualTo(faturaTipDTO);
        verify(mapper).toEntity(faturaTipDTO);
        verify(repository).save(faturaTip);
        verify(mapper).toDTO(faturaTip);
    }

    @Test
    @Description("Güncelleme başarılıysa, DTO'nun döndüğünü doğruluyoruz. Eğer fatura tipi bulunamazsa FaturaNotFoundException fırlatılacak (bu durumu da test edebiliriz).")
    void testUpdateFaturaTip_whenFaturaTipExists_shouldUpdateAndReturnDTO() {
        // Arrange
        Long id = 1L;
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();
        FaturaTip existingFaturaTip = new FaturaTip();

        when(repository.findById(id)).thenReturn(Optional.of(existingFaturaTip));
        doNothing().when(mapper).updateEntityFromDTO(faturaTipDTO, existingFaturaTip);
        when(repository.save(existingFaturaTip)).thenReturn(existingFaturaTip);
        when(mapper.toDTO(existingFaturaTip)).thenReturn(faturaTipDTO);

        // Act
        FaturaTipDTO result = faturaTipService.updateFaturaTip(id, faturaTipDTO);

        // Assert
        assertThat(result).isEqualTo(faturaTipDTO);
        verify(repository).findById(id);
        verify(mapper).updateEntityFromDTO(faturaTipDTO, existingFaturaTip);
        verify(repository).save(existingFaturaTip);
        verify(mapper).toDTO(existingFaturaTip);
    }

    @Test
    @Description("Bu test, var olan bir fatura tipinin aktif durumunun güncellenmesini doğruluyor.")
    void testUpdateActiveStatus_shouldUpdateActiveStatus() {
        Long id = 1L;
        FaturaTip existingFaturaTip = new FaturaTip();

        when(repository.findById(id)).thenReturn(Optional.of(existingFaturaTip));

        // Act
        faturaTipService.updateActiveStatus(id, true);

        // Assert
        assertThat(existingFaturaTip.isActive()).isTrue();
        verify(repository).findById(id);
        verify(repository).save(existingFaturaTip);
    }

    @Test
    @Description("Bu test, var olmayan bir fatura tipinin aktif durumu güncellenmeye çalışıldığında hata fırlatmayı doğruluyor.")
    void testUpdateActiveStatus_shouldThrowFaturaNotFoundException_whenFaturaTipDoesNotExist() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FaturaNotFoundException.class, () -> faturaTipService.updateActiveStatus(id, true));
        verify(repository).findById(id);
    }

    @Test
    @Description("Mevcut olmayan bir FaturaTip güncellenmeye çalışıldığında hata fırlatılıyor.")
    void testUpdateFaturaTip_shouldThrowException_whenFaturaTipNotFound() {
        Long id = 1L;
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();

        // Mevcut bir FaturaTip bulamadığımızı simüle et
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FaturaNotFoundException.class, () -> faturaTipService.updateFaturaTip(id, faturaTipDTO));
    }


    @Test
    @Description("Bu test, var olan bir fatura tipinin silinmesini doğruluyor.")
    void testDeleteFaturaTip_shouldDeleteFaturaTip() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        // Act
        faturaTipService.deleteFaturaTip(id);

        // Assert
        verify(repository).deleteById(id);
    }

    @Test
    @Description("Bu test, var olmayan bir fatura tipi silinmeye çalışıldığında hata fırlatmayı doğruluyor.")
    void testDeleteFaturaTip_shouldThrowFaturaNotFoundException_whenFaturaTipDoesNotExist() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(FaturaNotFoundException.class, () -> faturaTipService.deleteFaturaTip(id));
        verify(repository).existsById(id);
    }

}
