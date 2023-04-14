package com.security.securityuserregistration.controller;

import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> update(@RequestParam UUID uuid, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.update(uuid, userRequest), HttpStatus.OK);
    }

}
