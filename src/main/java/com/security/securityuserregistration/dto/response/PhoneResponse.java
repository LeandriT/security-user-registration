package com.security.securityuserregistration.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PhoneResponse {
    private UUID uuid;
    private String number;
    private Integer cityCode;
    private Integer countryCode;


}
