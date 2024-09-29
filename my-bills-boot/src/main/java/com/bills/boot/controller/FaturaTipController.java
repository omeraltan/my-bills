package com.bills.boot.controller;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import com.bills.boot.service.FaturaTipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bills.boot.utility.FaturaTipUrls.*;

@RestController
@RequestMapping(FATURA_TIP_PROFILE)
public class FaturaTipController {

    private FaturaTipService service;

    public FaturaTipController(FaturaTipService service) {
        this.service = service;
    }

    @GetMapping(GET_ALL_FATURA_TIP)
    public ResponseEntity<List<FaturaTipDTO>> getAllFaturaTip(){
        return ResponseEntity.ok().body(service.getAllFaturaTip());
    }

    @PostMapping
    public ResponseEntity<FaturaTipDTO> createFaturaTip(@Valid @RequestBody FaturaTipDTO faturaTipDTO) {
        FaturaTipDTO createdFaturaTip = service.createFaturaTip(faturaTipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaturaTip);
    }

}
