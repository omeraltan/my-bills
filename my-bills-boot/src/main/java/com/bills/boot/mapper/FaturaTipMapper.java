package com.bills.boot.mapper;

import com.bills.boot.dto.FaturaTipDTO;
import com.bills.boot.entity.FaturaTip;
import org.springframework.stereotype.Component;

@Component
public class FaturaTipMapper {

    // Entity'den DTO'ya dönüşüm
    public FaturaTipDTO toDTO(FaturaTip faturaTip) {
        if (faturaTip == null) {
            return null;
        }

        FaturaTipDTO dto = new FaturaTipDTO();
        dto.setId(faturaTip.getId());
        dto.setName(faturaTip.getName());
        dto.setDescription(faturaTip.getDescription());
        dto.setNeedDate(faturaTip.getNeedDate());
        dto.setActive(faturaTip.isActive());
        return dto;
    }

    // DTO'dan Entity'ye dönüşüm
    public static FaturaTip toEntity(FaturaTipDTO dto) {
        if (dto == null) {
            return null;
        }

        FaturaTip faturaTip = new FaturaTip();
        faturaTip.setId(dto.getId());
        faturaTip.setName(dto.getName());
        faturaTip.setDescription(dto.getDescription());
        faturaTip.setNeedDate(dto.getNeedDate());
        faturaTip.setActive(dto.isActive());
        return faturaTip;
    }

}
