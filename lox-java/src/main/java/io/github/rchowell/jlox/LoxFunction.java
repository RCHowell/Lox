package io.github.rchowell.jlox;

import java.util.*;

public class LoxFunction implements LoxCallable {

    private final Stmt.Function declaration;
    private final Environment closure;

    public LoxFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            // bind each argument to a variable in the given environment (scope)
            environment.define(declaration.params.get(i).lexeme, arguments.get(i));
        }
        // Execute the block with the new environment (scope)
        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returns) {
            return returns.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }
}
