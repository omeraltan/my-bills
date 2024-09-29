package com.bills.boot.dto;

import java.util.Date;

public class FaturaTipDTO {
    private Long id;
    private String name;
    private String description;
    private Date needDate;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getNeedDate() {
        return needDate;
    }

    public void setNeedDate(Date needDate) {
        this.needDate = needDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
