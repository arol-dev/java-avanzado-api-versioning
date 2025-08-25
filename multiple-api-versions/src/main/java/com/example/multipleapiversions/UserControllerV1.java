package com.example.multipleapiversions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
class UserControllerV1 {

    @Operation(
            summary = "Get a user (v1)",
            description = "Version 1: basic fields",
            tags = "users-v1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/{id}")
    public UserV1 get(@PathVariable Long id) {
        return new UserV1(id, "a@b.com"); // demo
    }

    @Operation(summary = "Creates a user (v1)", tags = "users-v1")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserV1 create(@RequestBody CreateUserV1 req) {
        return new UserV1(1L, req.email());
    }
}

record UserV1(Long id, String email) {
}

record CreateUserV1(@Schema(example = "a@b.com") String email) {
}
