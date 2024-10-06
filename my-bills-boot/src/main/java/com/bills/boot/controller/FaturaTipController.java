package com.bills.boot.controller;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.service.FaturaTipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bills.boot.utility.FaturaTipUrls.*;

@RestController
@RequestMapping(FATURA_TIP_PROFILE)
@Tag(name = "Fatura Tipleri", description = "Fatura Tipleri Tanımlama Sayfası ile ilgili Operasyonlar")
public class FaturaTipController {

    private FaturaTipService service;

    public FaturaTipController(FaturaTipService service) {
        this.service = service;
    }

    @Operation(summary = "Bütün Faura Tiplerini Listeleme", description = "Tanımladığımız yani kaydettiğimiz bütün fatura tiplerini listeler.")
    @GetMapping(GET_ALL_FATURA_TIP)
    public ResponseEntity<List<FaturaTipDTO>> getAllFaturaTip(){
        return ResponseEntity.ok().body(service.getAllFaturaTip());
    }

    @Operation(summary = "Yeni Bir Fatura Tipi Kaydetme", description = "İhtiyaç duyulan yeni bir fatura tipini kaydeder (Örnek: Elektrik, Su, Doğalgaz).")
    @PostMapping
    public ResponseEntity<FaturaTipDTO> createFaturaTip(@Valid @RequestBody FaturaTipDTO faturaTipDTO) {
        FaturaTipDTO createdFaturaTip = service.createFaturaTip(faturaTipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaturaTip);
    }

}
