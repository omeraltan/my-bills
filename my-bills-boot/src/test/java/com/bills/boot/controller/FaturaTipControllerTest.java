package com.bills.boot.controller;

import static com.bills.boot.utility.FaturaTipUrls.FATURA_TIP_PROFILE;
import static com.bills.boot.utility.FaturaTipUrls.GET_ALL_FATURA_TIP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.service.FaturaTipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

@WebMvcTest(FaturaTipController.class)
public class FaturaTipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FaturaTipService service;

    @InjectMocks
    private FaturaTipController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Description("Serviceden dönen FaturaTipDTO listesinin ilk elemanının name alanının 'Telefon' olduğunu kontrol eder.")
    void testGetAllFaturaTip() throws Exception {
        // FaturaTipDTO nesnesini name ile birlikte oluşturun
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();
        faturaTipDTO.setName("Telefon"); // Burada name değerini ayarlıyoruz

        Page<FaturaTipDTO> faturaTipPage = new PageImpl<>(Collections.singletonList(faturaTipDTO), PageRequest.of(0, 10), 1);
        when(service.getAllFaturaTip(any(Pageable.class))).thenReturn(faturaTipPage);

        mockMvc.perform(get(FATURA_TIP_PROFILE + GET_ALL_FATURA_TIP))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content[0].name").value("Telefon"));
    }

    @Test
    @Description("FaturaTipDTO nesnesinin oluşturulmasını test eder ve doğru yanıtın 201 Created durum kodu ile döndüğünü doğrular.")
    void testCreateFaturaTip() throws Exception {
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();
        faturaTipDTO.setName("Telefon");
        faturaTipDTO.setDescription("Telefon description");
        faturaTipDTO.setNeedDate(new Date());
        when(service.createFaturaTip(any(FaturaTipDTO.class))).thenReturn(faturaTipDTO);

        mockMvc.perform(post(FATURA_TIP_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faturaTipDTO)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Telefon"));
    }

    @Test
    @Description("FaturaTipDTO nesnesinin güncellenmesini test eder ve doğru yanıtın 200 OK durum kodu ile döndüğünü doğrular.")
    void testUpdateFaturaTip() throws Exception {
        Long id = 1L;
        FaturaTipDTO faturaTipDTO = new FaturaTipDTO();
        faturaTipDTO.setName("Yeni Telefon");
        faturaTipDTO.setDescription("Yeni Telefon description");
        faturaTipDTO.setNeedDate(new Date());
        when(service.updateFaturaTip(eq(id), any(FaturaTipDTO.class))).thenReturn(faturaTipDTO);

        mockMvc.perform(put(FATURA_TIP_PROFILE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faturaTipDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Yeni Telefon"));
    }

    @Test
    @Description("FaturaTipDTO nesnesinin aktif durumunun güncellenmesini test eder ve doğru yanıtın 204 NO CONTENT durum kodu ile döndüğünü doğrular.")
    void testUpdateFaturaTipActiveStatus() throws Exception {
        Long id = 1L;
        boolean isActive = true;
        doNothing().when(service).updateActiveStatus(eq(id), eq(isActive));
        mockMvc.perform(patch(FATURA_TIP_PROFILE + "/" + id + "/active-status")
                .param("isActive", String.valueOf(isActive)))
            .andExpect(status().isNoContent());
    }

    @Test
    @Description("FaturaTipDTO nesnesinin silinmesini test eder ve doğru yanıtın 204 NO CONTENT durum kodu ile döndüğünü doğrular.")
    void testDeleteFaturaTip() throws Exception {
        Long id = 1L;
        doNothing().when(service).deleteFaturaTip(eq(id));
        mockMvc.perform(delete(FATURA_TIP_PROFILE + "/" + id))
            .andExpect(status().isNoContent());
    }



}
