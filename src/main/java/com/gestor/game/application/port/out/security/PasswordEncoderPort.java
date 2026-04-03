package com.gestor.game.application.port.out.security;

public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
