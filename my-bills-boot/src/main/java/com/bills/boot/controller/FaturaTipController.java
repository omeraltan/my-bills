package com.bills.boot.controller;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.service.FaturaTipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bills.boot.utility.FaturaTipUrls.*;

@RestController
@RequestMapping(FATURA_TIP_PROFILE)
@Tag(name = "operation.tag.name", description = "operation.tag.description")
public class FaturaTipController {

    private FaturaTipService service;

    public FaturaTipController(FaturaTipService service) {
        this.service = service;
    }

    @Operation(summary = "operation.summary.get.all", description = "operation.description.description.get.all")
    @GetMapping(GET_ALL_FATURA_TIP)
    public ResponseEntity<Page<FaturaTipDTO>> getAllFaturaTip(
        @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FaturaTipDTO> faturaTips = service.getAllFaturaTip(pageable);
        return ResponseEntity.ok().body(faturaTips);
    }

    @Operation(summary = "operation.summary.create", description = "operation.description.create")
    @PostMapping
    public ResponseEntity<FaturaTipDTO> createFaturaTip(@Valid @RequestBody FaturaTipDTO faturaTipDTO) {
        FaturaTipDTO createdFaturaTip = service.createFaturaTip(faturaTipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaturaTip);
    }

    @Operation(summary = "operation.summary.update", description = "operation.description.update")
    @PutMapping(ID)
    public ResponseEntity<FaturaTipDTO> updateFaturaTip(
        @PathVariable Long id,
        @Valid @RequestBody FaturaTipDTO faturaTipDTO) {

        FaturaTipDTO updatedFaturaTip = service.updateFaturaTip(id, faturaTipDTO);
        return ResponseEntity.ok(updatedFaturaTip);
    }

    @Operation(summary = "operation.summary.update.active.status", description = "operation.description.update.active.status")
    @PatchMapping(ACTIVE_STATUS)
    public ResponseEntity<Void> updateFaturaTipActiveStatus(@PathVariable Long id, @RequestParam boolean isActive) {
        service.updateActiveStatus(id, isActive);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "operation.summary.delete", description = "operation.description.delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaturaTip(@PathVariable Long id) {
        service.deleteFaturaTip(id);
        return ResponseEntity.noContent().build(); // Başarılıysa 204 No Content döner
    }

}
