package io.github.rchowell.jlox;

import java.util.*;

interface LoxCallable {

    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}
