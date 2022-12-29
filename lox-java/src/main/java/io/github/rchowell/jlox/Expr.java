package io.github.rchowell.jlox;

import java.util.List;

abstract class Expr {

  abstract <R> R accept(Visitor<R> visitor);
  interface Visitor<R> {
    R visitAssignExpr(Assign expr);
    R visitBinaryExpr(Binary expr);
    R visitGroupingExpr(Grouping expr);
    R visitLiteralExpr(Literal expr);
    R visitLogicalExpr(Logical expr);
    R visitUnaryExpr(Unary expr);
    R visitCallExpr(Call expr);
    R visitTernaryExpr(Ternary expr);
    R visitVariableExpr(Variable expr);
    R visitFunctionExpr(Function expr);
  }
  static class Assign extends Expr {
    Assign(Token name, Expr value) {
      this.name = name;
      this.value = value;
    }

    final Token name;
    final Expr value;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitAssignExpr(this);
    }
  }
  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    final Expr left;
    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }
  }
  static class Grouping extends Expr {
    Grouping(Expr expression) {
      this.expression = expression;
    }

    final Expr expression;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGroupingExpr(this);
    }
  }
  static class Literal extends Expr {
    Literal(Object value) {
      this.value = value;
    }

    final Object value;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLiteralExpr(this);
    }
  }
  static class Logical extends Expr {
    Logical(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    final Expr left;
    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLogicalExpr(this);
    }
  }
  static class Unary extends Expr {
    Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    final Token operator;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnaryExpr(this);
    }
  }
  static class Call extends Expr {
    Call(Expr callee, Token paren, List<Expr> arguments) {
      this.callee = callee;
      this.paren = paren;
      this.arguments = arguments;
    }

    final Expr callee;
    final Token paren;
    final List<Expr> arguments;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitCallExpr(this);
    }
  }
  static class Ternary extends Expr {
    Ternary(Expr condition, Expr left, Expr right) {
      this.condition = condition;
      this.left = left;
      this.right = right;
    }

    final Expr condition;
    final Expr left;
    final Expr right;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitTernaryExpr(this);
    }
  }
  static class Variable extends Expr {
    Variable(Token name) {
      this.name = name;
    }

    final Token name;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitVariableExpr(this);
    }
  }
  static class Function extends Expr {
    Function(List<Token> params, List<Stmt> body) {
      this.params = params;
      this.body = body;
    }

    final List<Token> params;
    final List<Stmt> body;

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitFunctionExpr(this);
    }
  }
}
