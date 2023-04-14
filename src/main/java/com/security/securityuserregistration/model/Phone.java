package com.security.securityuserregistration.model;

import com.security.securityuserregistration.model.abstracts.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder()
@Table(name = "phones")
@AllArgsConstructor
@NoArgsConstructor
public class Phone extends BaseModel {
    private String number;
    private Integer cityCode;
    private Integer countryCode;
}
