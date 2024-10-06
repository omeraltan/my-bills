package com.bills.boot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class FaturaTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "{error.validation.empty.name}")
    private String name;

    @NotBlank(message = "{error.validation.empty.description}")
    private String description;

    @NotNull(message = "{error.validation.empty.date}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date needDate;

    private boolean isActive;

    public FaturaTip() {
    }

    public FaturaTip(Long id, String name, String description, Date needDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.needDate = needDate;
        this.isActive = isActive;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FaturaTip faturaTip = (FaturaTip) o;
        return isActive() == faturaTip.isActive() && Objects.equals(getId(), faturaTip.getId()) && Objects.equals(getName(), faturaTip.getName()) && Objects.equals(getDescription(), faturaTip.getDescription()) && Objects.equals(getNeedDate(), faturaTip.getNeedDate());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getDescription());
        result = 31 * result + Objects.hashCode(getNeedDate());
        result = 31 * result + Boolean.hashCode(isActive());
        return result;
    }

    @Override
    public String toString() {
        return "FaturaTip{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", needDate=" + needDate +
            ", isActive=" + isActive +
            '}';
    }
}
