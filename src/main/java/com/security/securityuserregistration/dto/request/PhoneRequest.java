package com.security.securityuserregistration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {
    private String number;
    private Integer cityCode;
    private Integer countryCode;


}
