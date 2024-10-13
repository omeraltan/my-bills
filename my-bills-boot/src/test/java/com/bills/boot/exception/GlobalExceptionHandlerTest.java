package com.bills.boot.exception;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

import com.bills.boot.repository.FaturaTipRepository;
import com.bills.boot.service.FaturaTipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        // MockMvc'yi yapılandır
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
    }

    @Test
    void testHandleFaturaNotFoundException() {
        FaturaNotFoundException exception = new FaturaNotFoundException("Fatura bulunamadı!");
        ResponseEntity<String> response = globalExceptionHandler.handleFaturaNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fatura bulunamadı!", response.getBody());
    }

    @Test
    void testHandleValidationExceptions() {
        // Mock BindingResult
        BindingResult bindingResult = mock(BindingResult.class);

        // Hata mesajlarını ayarla
        FieldError fieldError = new FieldError("objectName", "field", "field cannot be empty");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // Mock MethodArgumentNotValidException
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // ExceptionHandler'ı çağır
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        // Sonuçları kontrol et
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("field", "field cannot be empty");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedErrors, response.getBody());
    }
}
