package com.bills.boot.service;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import com.bills.boot.exception.FaturaNotFoundException;
import com.bills.boot.mapper.FaturaTipMapper;
import com.bills.boot.repository.FaturaTipRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FaturaTipService {

    FaturaTipRepository repository;
    private final MessageSource messageSource;
    private final FaturaTipMapper mapper;

    public FaturaTipService(FaturaTipRepository repository, MessageSource messageSource, FaturaTipMapper mapper) {
        this.repository = repository;
        this.messageSource = messageSource;
        this.mapper = mapper;
    }

    public Page<FaturaTipDTO> getAllFaturaTip(Pageable pageable) {
        Page<FaturaTip> faturaTipPage = repository.findAll(pageable);
        if (faturaTipPage.isEmpty()) {
            String errorMessage = messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale());
            throw new FaturaNotFoundException(errorMessage);
        }

        return faturaTipPage.map(mapper::toDTO);
    }


    public FaturaTipDTO createFaturaTip(FaturaTipDTO faturaTipDTO) {
        FaturaTip faturaTip = mapper.toEntity(faturaTipDTO);
        faturaTip = repository.save(faturaTip);
        return mapper.toDTO(faturaTip);
    }

    public FaturaTipDTO updateFaturaTip(Long id, FaturaTipDTO faturaTipDTO) {
        FaturaTip existingFaturaTip = repository.findById(id)
            .orElseThrow(() -> {
                String errorMessage = messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale());
                return new FaturaNotFoundException(errorMessage);
            });
        mapper.updateEntityFromDTO(faturaTipDTO, existingFaturaTip);
        FaturaTip updatedFaturaTip = repository.save(existingFaturaTip);
        return mapper.toDTO(updatedFaturaTip);
    }

    public void updateActiveStatus(Long id, boolean isActive) {
        FaturaTip existingFaturaTip = repository.findById(id)
            .orElseThrow(() -> {
                String errorMessage = messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale());
                return new FaturaNotFoundException(errorMessage);
            });
        existingFaturaTip.setActive(isActive);
        repository.save(existingFaturaTip);
    }

    public void deleteFaturaTip(Long id) {
        if (!repository.existsById(id)) {
            String errorMessage = messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale());
            throw new FaturaNotFoundException(errorMessage);
        }
        repository.deleteById(id);
    }
}
