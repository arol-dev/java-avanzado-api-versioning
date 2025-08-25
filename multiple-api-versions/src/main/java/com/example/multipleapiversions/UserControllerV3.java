package com.example.multipleapiversions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

/**
 * Header-based API versioning: v3 behaves like v1 (basic fields).
 * Version header: X-API-Version=3
 */
@RestController
@RequestMapping(value = "/api/users", headers = "X-API-Version=3")
class UserControllerV3 {

    @Operation(
            summary = "Get a user (v3 via header)",
            description = "Version 3 selected by header; behaves like v1 (basic fields)",
            tags = "users-v3"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserV1.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public UserV1 get(@PathVariable Long id) {
        return new UserV1(id, "a@b.com");
    }

    @Operation(
            summary = "Create a user (v3 via header)",
            tags = "users-v3"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserV1 create(@RequestBody CreateUserV1 req) {
        return new UserV1(1L, req.email());
    }
}
