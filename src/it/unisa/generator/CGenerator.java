package it.unisa.generator;

import it.unisa.ast.MyNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.InOutNode;
import it.unisa.ast.declaration.procedure.parameter.OutNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.type.*;
import it.unisa.seman.ScopeTable;
import it.unisa.seman.SemanticData;
import it.unisa.seman.SymbolTable;

import java.util.ArrayList;

public class CGenerator {
    private static final String STDIO = "#include <stdio.h>";
    private static final String MAIN = "int main()";
    private static final String SCANF = "scanf";
    private static final String PRINTF = "printf";
    private static final String IF = "if";
    private static final String ELSE = "else";
    private static final String WHILE = "while";
    private static final String VOID = "void";
    private static final String INT = "int";
    private static final String CHAR = "char";
    private static final String ARRAY = "[]";
    private static final String ASSIGN = "=";
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String STAR = "*";
    private static final String DIV = "/";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "==";
    private static final String GREATER_EQUALS = ">=";
    private static final String GREATER_THAN = ">";
    private static final String LESSER_EQUALS = "<=";
    private static final String LESSER_THAN = "<";
    private static final String NOT = "!";
    private static final String AND = "&&";
    private static final String OR = "||";
    private static final String OPEN_PAR = "(";
    private static final String CLOSE_PAR = ")";
    private static final String OPEN_BRACKET = "{";
    private static final String CLOSE_BRACKET = "}";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";
    private static final String NEWLINE = "\n";
    private static final String WHITESPACE = " ";
    private static final String QUOTES = "\"";

    private SymbolTable symbolTable;

    public CGenerator() {
        symbolTable = new SymbolTable();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void startScope(MyNode n) {
        if (n instanceof ProgrammaNode || n instanceof ProcedureDeclarationNode) {
            ScopeTable scopeTable = (ScopeTable) n.data();
            symbolTable.push(scopeTable);
        }
    }

    public void endCurrentScope() {
        symbolTable.pop();
    }

    public String buildProgrammaString(String declarationPart, String mainPart) {
        return STDIO + NEWLINE + declarationPart + MAIN + WHITESPACE + OPEN_BRACKET + NEWLINE + mainPart + CLOSE_BRACKET + NEWLINE;
    }

    public String buildProcedureString(String procedureName, String pars, String body) {
        return VOID + WHITESPACE + procedureName + pars + WHITESPACE + OPEN_BRACKET + NEWLINE + body + CLOSE_BRACKET + NEWLINE;
    }

    public String buildVarDeclarationString(String type, String varInits) {
        if (type.equals(StringNode.STRING)) {
            type = CHAR;
        }
        if (type.equals(BooleanNode.BOOLEAN)) {
            type = INT;
        }
        return type + WHITESPACE + varInits + SEMICOLON + NEWLINE;
    }

    public String buildVarInitListString(ArrayList<String> vars) {
        StringBuilder varList = new StringBuilder();
        for (int i = 0; i < vars.size(); i++) {
            varList.append(vars.get(i));
            if (i < vars.size() - 1) {
                varList.append(COMMA + WHITESPACE);
            }
        }
        return varList.toString();
    }

    public String buildVarInitString(String varType, String var, String initValue) {
        if (varType.equals(StringNode.STRING)) {
            var += ARRAY;
        }
        if (!initValue.equals("")) {
            initValue = WHITESPACE + ASSIGN + WHITESPACE + initValue;
        }
        return var + initValue;
    }

    public String buildParDeclarationListString(ArrayList<String> pars) {
        StringBuilder parList = new StringBuilder();
        for (int i = 0; i < pars.size(); i++) {
            parList.append(pars.get(i));
            if (i < pars.size() - 1) {
                parList.append(COMMA + WHITESPACE);
            }
        }
        return OPEN_PAR + parList.toString() + CLOSE_PAR;
    }

    public String buildParDeclarationString(String parType, String type, String parName) {
        return type + WHITESPACE + parName;
    }

    public String buildArgsString(ArrayList<String> args) {
        StringBuilder argList = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            argList.append(args.get(i));
            if (i < args.size() - 1) {
                argList.append(COMMA + WHITESPACE);
            }
        }
        return argList.toString();
    }

    public String buildIdentifierString(String identifierName) {
        SemanticData semanticData = symbolTable.lookup(identifierName);
        String parType = semanticData.getParType();
        if (parType != null) {
            if (parType.equals(OutNode.OUT) || parType.equals(InOutNode.INOUT)) {
                identifierName = STAR + identifierName;
            }
        }
        return identifierName;
    }

    public String buildPlusString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + PLUS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildMinusString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + MINUS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildTimesString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + STAR + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildDivString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + DIV + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildAndString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + AND + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildOrString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + OR + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildEQString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildGEString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + GREATER_EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildGTString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + GREATER_THAN + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildLEString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + LESSER_EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildLTString(String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + LESSER_THAN + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildUminusString(String firstOperand) {
        return OPEN_PAR + MINUS + firstOperand + CLOSE_PAR;
    }

    public String buildNotString(String firstOperand) {
        return OPEN_PAR + NOT + firstOperand + CLOSE_PAR;
    }

    public String buildAssignString(String firstOperand, String secondOperand) {
        return firstOperand + WHITESPACE + ASSIGN + WHITESPACE + secondOperand + SEMICOLON + NEWLINE;
    }

    public String buildIfThenString(String condition, String thenBranch) {
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + thenBranch + CLOSE_BRACKET + NEWLINE;
    }

    public String buildIfThenElseString(String condition, String thenBranch, String elseBranch) {
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + thenBranch + CLOSE_BRACKET + WHITESPACE + ELSE + WHITESPACE + OPEN_BRACKET + NEWLINE
                + elseBranch + CLOSE_BRACKET + NEWLINE;
    }

    public String buildWhileString(String condition, String body) {
        return WHILE + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + body + CLOSE_BRACKET + NEWLINE;
    }

    public String buildCallString(String procedureName, ArrayList<String> args) {
        StringBuilder argList = new StringBuilder();
        if (args != null) {
            for (int i = 0; i < args.size(); i++) {
                String parType = symbolTable.lookup(procedureName).getParameterList().get(i).getParType();
                if (parType.equals(OutNode.OUT) || parType.equals(InOutNode.INOUT)) {
                    argList.append(AMPERSAND);
                }
                argList.append(args.get(i));
                if (i < args.size() - 1) {
                    argList.append(COMMA + WHITESPACE);
                }
            }
        }
        return procedureName + OPEN_PAR + argList + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    public String buildReadString(ArrayList<ExpressionNode> exprs, ArrayList<String> args) {
        String format = buildFormatString(exprs);
        StringBuilder argList = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            argList.append(AMPERSAND);
            argList.append(args.get(i));
            if (i < args.size() - 1) {
                argList.append(COMMA + WHITESPACE);
            }
        }
        return SCANF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE
                + argList.toString() + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    public String buildWriteString(ArrayList<ExpressionNode> exprs, ArrayList<String> args) {
        String format = buildFormatString(exprs);
        StringBuilder argList = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            argList.append(args.get(i));
            if (i < args.size() - 1) {
                argList.append(COMMA + WHITESPACE);
            }
        }
        return PRINTF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE
                + argList.toString() + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    private String buildFormatString(ArrayList<ExpressionNode> exprs) {
        StringBuilder format = new StringBuilder();
        for (ExpressionNode expr : exprs) {
            switch (expr.getType()) {
                case IntegerNode.INTEGER:
                    format.append("%d");
                    break;
                case DoubleNode.DOUBLE:
                    format.append("%lf");
                    break;
                case CharacterNode.CHARACTER:
                    format.append("%c");
                    break;
                case StringNode.STRING:
                    format.append("%s");
                    break;
                case BooleanNode.BOOLEAN:
                    format.append("%d");
                    break;
            }
        }
        return format.toString();
    }
}
