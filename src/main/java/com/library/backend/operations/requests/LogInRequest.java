package com.library.backend.operations.requests;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogInRequest implements Request {
    private String username;
    private String password;
}
