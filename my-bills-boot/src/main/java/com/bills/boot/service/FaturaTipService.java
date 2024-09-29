package com.bills.boot.service;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import com.bills.boot.exception.FaturaNotFoundException;
import com.bills.boot.mapper.FaturaTipMapper;
import com.bills.boot.repository.FaturaTipRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<FaturaTipDTO> getAllFaturaTip() {
        List<FaturaTip> faturaTipList = repository.findAll();
        if (faturaTipList.isEmpty()) {
            String errorMessage = messageSource.getMessage("fatura.tip.notfound", null, LocaleContextHolder.getLocale());
            throw new FaturaNotFoundException(errorMessage);
        }
        return faturaTipList.stream().map(mapper::toDTO).collect(Collectors.toList());
    }


    public FaturaTipDTO createFaturaTip(FaturaTipDTO faturaTipDTO) {
        FaturaTip faturaTip = mapper.toEntity(faturaTipDTO);
        faturaTip = repository.save(faturaTip);
        return mapper.toDTO(faturaTip);
    }
}
