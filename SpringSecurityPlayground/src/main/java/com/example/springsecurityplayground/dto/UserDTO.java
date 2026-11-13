package com.example.springsecurityplayground.dto;

public record UserDTO(String username, String[] authorities) {
}
