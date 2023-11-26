package com.library.backend.operations.requests;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogInRequest implements OperationInput{
    private String username;
    private String password;
}
