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
 * Header-based API versioning: v4 behaves like v2 (extended fields).
 * Version header: X-API-Version=4
 */
@RestController
@RequestMapping(value = "/api/users", headers = "X-API-Version=4")
class UserControllerV4 {

    @Operation(
            summary = "Get a user (v4 via header)",
            description = "Version 4 selected by header; behaves like v2 (extended fields)",
            tags = "users-v4"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserV2.class))),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/{id}")
    public UserV2 get(@PathVariable Long id) {
        return new UserV2(id, "a@b.com", "Ada", UserStatus.ACTIVE);
    }

    @Operation(
            summary = "Create a user (v4 via header)",
            tags = "users-v4"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserV2 create(@RequestBody CreateUserV2 req) {
        return new UserV2(2L, req.email(), req.name(), UserStatus.PENDING);
    }
}
