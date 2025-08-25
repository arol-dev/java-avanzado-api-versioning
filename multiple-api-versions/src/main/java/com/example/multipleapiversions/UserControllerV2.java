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


enum UserStatus {ACTIVE, PENDING, BLOCKED}

@RestController
@RequestMapping("/api/v2/users")
class UserControllerV2 {

    @Operation(
            summary = "Get a user (v2)",
            description = "Version 2: more fields",
            tags = "users-v2"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserV1.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public UserV2 get(@PathVariable Long id) {
        return new UserV2(id, "a@b.com", "Ada", UserStatus.ACTIVE);
    }

    @Operation(summary = "Create a user (v2)", tags = "users-v2")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserV2 create(@RequestBody CreateUserV2 req) {
        return new UserV2(2L, req.email(), req.name(), UserStatus.PENDING);
    }
}

record UserV2(Long id, String email, String name, UserStatus status) {
}

record CreateUserV2(String email, String name) {
}
