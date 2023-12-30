package com.agrocare.agrocare.configuration.jwt_pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtRequest {
    private String email;
    private String password;
}
