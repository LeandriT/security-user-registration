package com.security.securityuserregistration.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneRequest {
    private String number;
    private Integer cityCode;
    private Integer countryCode;


}
