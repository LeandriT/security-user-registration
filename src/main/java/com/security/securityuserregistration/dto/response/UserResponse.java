package com.security.securityuserregistration.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID uuid;
    /*private String name;
    private String email;
    private String password;*/
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastLogin;
    private String token;
    private String additionalToken;
    @Builder.Default
    private boolean isActive = true;
    //private List<PhoneResponse> phones = new ArrayList<>();


}
