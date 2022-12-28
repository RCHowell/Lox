package io.github.rchowell.jlox;

public class RuntimeError extends RuntimeException {

    final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}