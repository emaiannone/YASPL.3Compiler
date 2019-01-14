package it.unisa.visitor.generation;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.*;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.constant.*;
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
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.WriteOpNode;
import it.unisa.ast.statement.conditional.IfThenElseOpNode;
import it.unisa.ast.statement.conditional.IfThenOpNode;
import it.unisa.ast.statement.conditional.WhileOpNode;
import it.unisa.ast.type.*;
import it.unisa.seman.ParameterData;
import it.unisa.seman.ScopeTable;
import it.unisa.seman.SemanticData;
import it.unisa.seman.SymbolTable;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

public class CGeneratorVisitor extends TreeVisitor {
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
    private static final String EQUALS = "==";
    private static final String GREATER_EQUALS = ">=";
    private static final String GREATER_THAN = ">=";
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

    public CGeneratorVisitor() {
        symbolTable = new SymbolTable();
        activeParameterList = null;
    }

    private void startScope(MyNode n) {
        if (n instanceof ProgrammaNode || n instanceof ProcedureDeclarationNode) {
            ScopeTable scopeTable = (ScopeTable) n.data();
            symbolTable.push(scopeTable);
        }
    }

    private void endCurrentScope() {
        symbolTable.pop();
    }


    protected String visitSubtree(MyNode n) {
        StringBuilder result = new StringBuilder();

        if (n.hasChildren()) {
            for (MyNode c : n.children()) {
                String subResult = (String) c.accept(this);
                if (subResult != null) {
                    result.append(subResult);
                }
            }
        }

        return result.toString();
    }

    @Override
    public Object visit(ProgrammaNode n) {
        startScope(n);

        String declarationPart = (String) n.getChild(0).accept(this);
        String mainPart = (String) n.getChild(1).accept(this);

        endCurrentScope();

        return STDIO + NEWLINE + declarationPart + MAIN + WHITESPACE + OPEN_BRACKET + NEWLINE + mainPart + CLOSE_BRACKET + NEWLINE;
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        startScope(n);
        String procedureName = (String) n.getChild(0).accept(this);
        activeParameterList = symbolTable.lookup(procedureName).getParameterList();
        String pars = (String) n.getChild(1).accept(this);
        String body = (String) n.getChild(2).accept(this);

        endCurrentScope();
        activeParameterList = null;

        return VOID + WHITESPACE + procedureName + pars + WHITESPACE + OPEN_BRACKET + NEWLINE + body + CLOSE_BRACKET + NEWLINE;
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        String type = (String) n.getChild(0).accept(this);
        if (type.equals(StringNode.STRING)) {
            type = CHAR;
        }
        if (type.equals(BooleanNode.BOOLEAN)) {
            type = INT;
        }
        String varInits = (String) n.getChild(1).accept(this);
        return type + WHITESPACE + varInits + SEMICOLON + NEWLINE;
    }

    @Override
    public Object visit(VarInitListNode n) {
        StringBuilder vars = new StringBuilder();
        vars.append((String) n.getChild(0).accept(this));
        for (int i = 1; i < n.childrenNumber(); i++) {
            vars.append(COMMA + WHITESPACE);
            String var = (String) n.getChild(i).accept(this);
            vars.append(var);
        }
        return vars.toString();
    }

    @Override
    public Object visit(VarInitNode n) {
        String var = (String) n.getChild(0).accept(this);
        SemanticData semanticData = symbolTable.lookup((String) n.getChild(0).data());
        if (semanticData.getType().equals(StringNode.STRING)) {
            var += ARRAY;
        }
        String initValue = "";
        if (n.getChild(1) != null) {
            initValue = WHITESPACE + ASSIGN + WHITESPACE + n.getChild(1).accept(this);
        }
        return var + initValue;
    }

    @Override
    public Object visit(ParDeclarationListNode n) {
        StringBuilder pars = new StringBuilder();
        if (n.getChild(0) != null) {
            pars.append((String) n.getChild(0).accept(this));
            for (int i = 1; i < n.childrenNumber(); i++) {
                pars.append(COMMA + WHITESPACE);
                String arg = (String) n.getChild(i).accept(this);
                pars.append(arg);
            }
        }
        return OPEN_PAR + pars.toString() + CLOSE_PAR;
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        String type = (String) n.getChild(1).accept(this);
        String parName = (String) n.getChild(2).accept(this);

        return type + WHITESPACE + parName;
    }

    @Override
    public Object visit(ArgsNode n) {
        StringBuilder args = new StringBuilder();
        args.append((String) n.getChild(0).accept(this));
        for (int i = 1; i < n.childrenNumber(); i++) {
            args.append(COMMA + WHITESPACE);
            String arg = (String) n.getChild(i).accept(this);
            args.append(arg);
        }
        return args.toString();
    }

    @Override
    public Object visit(IntegerConstantNode n) {
        return n.data().toString();
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        return n.data().toString();
    }

    @Override
    public Object visit(StringConstantNode n) {
        return n.data().toString();
    }

    @Override
    public Object visit(CharConstantNode n) {
        return n.data().toString();
    }

    //TODO TrueNode deve tornare 1, mentre FalseNode 0
    @Override
    public Object visit(BoolConstantNode n) {
        return n.data().toString();
    }

    @Override
    public Object visit(IdentifierNode n) {
        String idName = n.data().toString();
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

    @Override
    public Object visit(PlusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + PLUS + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(MinusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + MINUS + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(TimesOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + STAR + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(DivOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + DIV + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(AndOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + AND + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(OrOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + OR + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(EQOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + EQUALS + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(GEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + GREATER_EQUALS + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(GTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + GREATER_THAN + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(LEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + LESSER_EQUALS + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(LTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return OPEN_PAR + first + WHITESPACE + LESSER_THAN + WHITESPACE + second + CLOSE_PAR;
    }

    @Override
    public Object visit(UminusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return OPEN_PAR + MINUS + first + CLOSE_PAR;
    }

    @Override
    public Object visit(NotOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return OPEN_PAR + NOT + first + CLOSE_PAR;
    }

    @Override
    public Object visit(AssignOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return first + " = " + second + SEMICOLON + NEWLINE;
    }

    @Override
    public Object visit(IfThenOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBody = (String) n.getChild(1).accept(this);
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE + thenBody + CLOSE_BRACKET + NEWLINE;
    }

    @Override
    public Object visit(IfThenElseOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBody = (String) n.getChild(1).accept(this);
        String elseBody = (String) n.getChild(2).accept(this);
        return IF + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE + thenBody + CLOSE_BRACKET + WHITESPACE + ELSE + WHITESPACE + OPEN_BRACKET + NEWLINE + elseBody + CLOSE_BRACKET + NEWLINE;
    }

    @Override
    public Object visit(WhileOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String whileBody = (String) n.getChild(1).accept(this);
        return WHILE + WHITESPACE + OPEN_PAR + condition + CLOSE_PAR + WHITESPACE + OPEN_BRACKET + NEWLINE + whileBody + CLOSE_BRACKET + NEWLINE;
    }

    //TODO Mandare & se il corrispondente parametro è di out o inout
    @Override
    public Object visit(CallOpNode n) {
        String procedureName = (String) n.getChild(0).accept(this);
        String argsList = "";
        if (n.getChild(1) != null) {
            argsList = (String) n.getChild(1).accept(this);
        }
        return procedureName + OPEN_PAR + argsList + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    // TODO Mancano le & e consulatare la tab dei simboli per la stringa di stringa di formattazione
    @Override
    public Object visit(ReadOpNode n) {
        String args = (String) n.getChild(0).accept(this);
        String format = "%s";
        return SCANF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE + args + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    // TODO Consulatare la tab dei simboli per la stringa di formattazione
    @Override
    public Object visit(WriteOpNode n) {
        String args = (String) n.getChild(0).accept(this);
        String format = "%s";
        return PRINTF + OPEN_PAR + QUOTES + format + QUOTES + COMMA + WHITESPACE + args + CLOSE_PAR + SEMICOLON + NEWLINE;
    }

    @Override
    public Object visit(TypeNode n) {
        return n.getType();
    }

    @Override
    public Object visit(IntegerNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(DoubleNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(CharacterNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(StringNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(BooleanNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(ParTypeNode n) {
        return n.getType();
    }

    @Override
    public Object visit(InNode n) {
        return visit((ParTypeNode) n);
    }

    @Override
    public Object visit(OutNode n) {
        return visit((ParTypeNode) n);
    }

    @Override
    public Object visit(InOutNode n) {
        return visit((ParTypeNode) n);
    }
}
