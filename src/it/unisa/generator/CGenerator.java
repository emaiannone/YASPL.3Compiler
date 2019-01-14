package it.unisa.generator;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.InNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParTypeNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.ConstantNode;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.arithmetic.DivOpNode;
import it.unisa.ast.expression.operation.arithmetic.MinusOpNode;
import it.unisa.ast.expression.operation.arithmetic.PlusOpNode;
import it.unisa.ast.expression.operation.arithmetic.TimesOpNode;
import it.unisa.ast.expression.operation.bool.AndOpNode;
import it.unisa.ast.expression.operation.bool.OrOpNode;
import it.unisa.ast.expression.operation.relational.*;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.ParDeclarationListNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.WriteOpNode;
import it.unisa.ast.statement.conditional.IfThenElseOpNode;
import it.unisa.ast.statement.conditional.IfThenOpNode;
import it.unisa.ast.statement.conditional.WhileOpNode;
import it.unisa.ast.type.*;
import it.unisa.seman.ParameterData;
import it.unisa.seman.ScopeTable;
import it.unisa.seman.SymbolTable;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
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
    private ArrayList<ParameterData> activeParameterList;
    private boolean readOp;

    public CGenerator() {
        symbolTable = new SymbolTable();
        activeParameterList = null;
        readOp = false;
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

    public ArrayList<ParameterData> getActiveParameterList() {
        return activeParameterList;
    }

    public void setActiveParameterList(ArrayList<ParameterData> activeParameterList) {
        this.activeParameterList = activeParameterList;
    }

    public boolean isReadOp() {
        return readOp;
    }

    public void setReadOp(boolean readOp) {
        this.readOp = readOp;
    }

    public String buildString(ProgrammaNode n, String declarationPart, String mainPart) {
        return STDIO + NEWLINE + declarationPart + MAIN + WHITESPACE + OPEN_BRACKET + NEWLINE + mainPart + CLOSE_BRACKET + NEWLINE;
    }

    public String buildString(ProcedureDeclarationNode n, String procedureName, String pars, String body) {
        return VOID + WHITESPACE + procedureName + pars + WHITESPACE + OPEN_BRACKET + NEWLINE + body + CLOSE_BRACKET + NEWLINE;
    }

    public String buildString(VarDeclarationNode n, String type, String varInits) {
        if (type.equals(StringNode.STRING)) {
            type = CHAR;
        }
        if (type.equals(BooleanNode.BOOLEAN)) {
            type = INT;
        }
        return type + WHITESPACE + varInits + SEMICOLON + NEWLINE;
    }

    public String buildString(VarInitListNode n, ArrayList<String> vars) {
        StringBuilder varList = new StringBuilder();
        varList.append(vars.get(0));
        for (int i = 1; i < vars.size(); i++) {
            varList.append(COMMA + WHITESPACE);
            varList.append(vars.get(i));
        }
        return varList.toString();
    }

    public String buildString(VarInitNode n, String var, String initValue) {
        IdentifierNode idNode = (IdentifierNode) n.getChild(0);
        if (idNode.getType().equals(StringNode.STRING)) {
            var += ARRAY;
        }
        if (!initValue.equals("")) {
            initValue = WHITESPACE + ASSIGN + WHITESPACE + initValue;
        }
        return var + initValue;
    }

    public String buildString(ParDeclarationListNode n, ArrayList<String> pars) {
        StringBuilder parList = new StringBuilder();
        if (!pars.isEmpty()) {
            parList.append(pars.get(0));
            for (int i = 1; i < pars.size(); i++) {
                parList.append(COMMA + WHITESPACE);
                parList.append(pars.get(i));
            }
        }
        return OPEN_PAR + parList.toString() + CLOSE_PAR;
    }

    public String buildString(ParDeclarationNode n, String type, String parName) {
        return type + WHITESPACE + parName;
    }

    public String buildString(ArgsNode n, ArrayList<String> args) {
        // Add & if this is called in a context of a read statement
        String optionalAmpersand = "";
        if (readOp) {
            optionalAmpersand = AMPERSAND;
        }
        StringBuilder argList = new StringBuilder();
        argList.append(optionalAmpersand);
        argList.append(args.get(0));
        for (int i = 1; i < args.size(); i++) {
            argList.append(COMMA + WHITESPACE);
            argList.append(optionalAmpersand);
            argList.append(args.get(i));
        }
        return argList.toString();
    }

    public String buildString(ConstantNode n) {
        return n.data().toString();
    }

    public String buildString(IdentifierNode n) {
        String idName = (String) n.data();
        // Adding the * in case this identifer represent an output parameter
        if (activeParameterList != null) {
            int i = 0;
            boolean found = false;
            while (i < activeParameterList.size() && !found) {
                ParameterData parameterData = activeParameterList.get(i);
                if (idName.equals(parameterData.getIdentifier())) {
                    found = true;
                } else {
                    i++;
                }
            }
            if (found) {
                String parType = activeParameterList.get(i).getParType();
                if (!parType.equals(InNode.IN)) {
                    idName = STAR + idName;
                }
            }
        }
        return idName;
    }

    public String buildString(PlusOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + PLUS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(MinusOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + MINUS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(TimesOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + STAR + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(DivOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + DIV + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(AndOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + AND + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(OrOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + OR + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(EQOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(GEOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + GREATER_EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(GTOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + GREATER_THAN + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(LEOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + LESSER_EQUALS + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(LTOpNode n, String firstOperand, String secondOperand) {
        return OPEN_PAR + firstOperand + WHITESPACE + LESSER_THAN + WHITESPACE + secondOperand + CLOSE_PAR;
    }

    public String buildString(UminusOpNode n, String firstOperand) {
        return OPEN_PAR + MINUS + firstOperand + CLOSE_PAR;
    }

    public String buildString(NotOpNode n, String firstOperand) {
        return OPEN_PAR + NOT + firstOperand + CLOSE_PAR;
    }

    public String buildString(AssignOpNode n, String firstOperand, String secondOperand) {
        return firstOperand + WHITESPACE + ASSIGN + WHITESPACE + secondOperand + SEMICOLON + NEWLINE;
    }

    public String buildString(IfThenOpNode n, String condition, String thenBranch) {
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + thenBranch + CLOSE_BRACKET + NEWLINE;
    }

    public String buildString(IfThenElseOpNode n, String condition, String thenBranch, String elseBranch) {
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + thenBranch + CLOSE_BRACKET + WHITESPACE + ELSE + WHITESPACE + OPEN_BRACKET + NEWLINE
                + elseBranch + CLOSE_BRACKET + NEWLINE;
    }

    public String buildString(WhileOpNode n, String condition, String body) {
        return WHILE + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE
                + body + CLOSE_BRACKET + NEWLINE;
    }

    public String buildString(ReadOpNode n, ArrayList<ExpressionNode> argList, String args) {
        StringBuilder format = new StringBuilder();
        for (ExpressionNode arg : argList) {
            switch (arg.getType()) {
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
        return SCANF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE + args + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    public String buildString(WriteOpNode n, ArrayList<ExpressionNode> argList, String args) {
        StringBuilder format = new StringBuilder();
        for (ExpressionNode arg : argList) {
            switch (arg.getType()) {
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
        return PRINTF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE + args + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    public String buildString(TypeNode n) {
        return n.getType();
    }

    public String buildString(ParTypeNode n) {
        return n.getType();
    }
}
