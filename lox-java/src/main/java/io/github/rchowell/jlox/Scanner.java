package io.github.rchowell.jlox;

import java.util.*;

import static io.github.rchowell.jlox.TokenType.*;

public class Scanner {
    private final String source;


    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '?':
                addToken(QUESTION);
                break;
            case ':':
                addToken(COLON);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    // Skip C-style /* ... */ block comments
                    multilineComment();
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    /**
     * The advance() method consumes the next character in the source file and returns it
     */
    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * Only consume the current character if it's what we're looking for
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    /**
     * Look ahead without consuming the character
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }
        advance();
        // trim each "
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek())) advance();
        // Look for a fractional part
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume '.'
            advance();
            while (isDigit(peek())) advance();
        }
        // The interpreter uses Java's Double to represent Numbers
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void identifier() {
        // max munch
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        // reserved word?
        TokenType type = keywords.get(text);
        // identifier
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    private void multilineComment() {
        while (!(peek() == '*' && peekNext() == '/')) {
            if (isAtEnd()) {
                Lox.error(line, "Unterminated comment.");
                return;
            }
            if (peek() == '\n') line += 1;
            advance();
        }
        // consume the '*' '/'
        advance();
        advance();
    }
}
