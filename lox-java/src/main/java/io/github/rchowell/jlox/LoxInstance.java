package io.github.rchowell.jlox;

import java.util.*;

class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    public Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }
        LoxFunction method = klass.findMethod(name.lexeme);
        if (method != null) {
            return method.bind(this);
        }
        throw new RuntimeError(name, "Undefined property '" + name + "'.");
    }

    public void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}