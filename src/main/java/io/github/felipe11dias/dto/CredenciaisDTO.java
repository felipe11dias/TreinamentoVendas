package io.github.felipe11dias.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

// Usado para o login
public class CredenciaisDTO {
    private String login;
    private String senha;
}