package it.unisa.visitor.generation;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.*;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
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
import it.unisa.generator.CGenerator;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
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
    private static final String AMPERSAND = "&";
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

    private CGenerator cGenerator;

    public CGeneratorVisitor() {
        cGenerator = new CGenerator();
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
        cGenerator.startScope(n);

        String declarationPart = (String) n.getChild(0).accept(this);
        String mainPart = (String) n.getChild(1).accept(this);
        String result = cGenerator.buildString(n, declarationPart, mainPart);

        cGenerator.endCurrentScope();

        return result;
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        cGenerator.startScope(n);

        String procedureName = (String) n.getChild(0).accept(this);
        cGenerator.setActiveParameterList(cGenerator.getSymbolTable().lookup(procedureName).getParameterList());
        String pars = (String) n.getChild(1).accept(this);
        String body = (String) n.getChild(2).accept(this);

        String result = cGenerator.buildString(n, procedureName, pars, body);

        cGenerator.endCurrentScope();
        cGenerator.setActiveParameterList(null);

        return result;
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        String type = (String) n.getChild(0).accept(this);
        String varInits = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, type, varInits);
    }

    @Override
    public Object visit(VarInitListNode n) {
        ArrayList<String> vars = new ArrayList<>();
        for (int i = 0; i < n.childrenNumber(); i++) {
            vars.add((String) n.getChild(i).accept(this));
        }
        return cGenerator.buildString(n, vars);
    }

    @Override
    public Object visit(VarInitNode n) {
        String var = (String) n.getChild(0).accept(this);
        String initValue = "";
        if (n.getChild(1) != null) {
            initValue = (String) n.getChild(1).accept(this);
        }
        return cGenerator.buildString(n, var, initValue);
    }

    @Override
    public Object visit(ParDeclarationListNode n) {
        ArrayList<String> pars = new ArrayList<>();
        for (int i = 0; i < n.childrenNumber(); i++) {
            pars.add((String) n.getChild(i).accept(this));
        }
        return cGenerator.buildString(n, pars);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        String type = (String) n.getChild(1).accept(this);
        String parName = (String) n.getChild(2).accept(this);

        return cGenerator.buildString(n, type, parName);
    }

    @Override
    public Object visit(ArgsNode n) {
        ArrayList<String> args = new ArrayList<>();
        for (int i = 0; i < n.childrenNumber(); i++) {
            args.add((String) n.getChild(i).accept(this));
        }
        return cGenerator.buildString(n, args);
    }

    @Override
    public Object visit(IntegerConstantNode n) {
        return cGenerator.buildString(n);
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        return cGenerator.buildString(n);
    }

    @Override
    public Object visit(StringConstantNode n) {
        return cGenerator.buildString(n);
    }

    @Override
    public Object visit(CharConstantNode n) {
        return cGenerator.buildString(n);
    }

    //TODO TrueNode deve tornare 1, mentre FalseNode 0
    @Override
    public Object visit(BoolConstantNode n) {
        return cGenerator.buildString(n);
    }

    @Override
    public Object visit(IdentifierNode n) {
        return cGenerator.buildString(n);
    }

    @Override
    public Object visit(PlusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(MinusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(TimesOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(DivOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(AndOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(OrOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(EQOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(GEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(GTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(LEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(LTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(UminusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return cGenerator.buildString(n, first);
    }

    @Override
    public Object visit(NotOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return cGenerator.buildString(n, first);
    }

    @Override
    public Object visit(AssignOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, first, second);
    }

    @Override
    public Object visit(IfThenOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBranch = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, condition, thenBranch);
    }

    @Override
    public Object visit(IfThenElseOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBranch = (String) n.getChild(1).accept(this);
        String elseBranch = (String) n.getChild(2).accept(this);
        return cGenerator.buildString(n, condition, thenBranch, elseBranch);
    }

    @Override
    public Object visit(WhileOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String body = (String) n.getChild(1).accept(this);
        return cGenerator.buildString(n, condition, body);
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

    @Override
    public Object visit(ReadOpNode n) {
        cGenerator.setReadOp(true);
        ArgsNode argsNode = (ArgsNode) n.getChild(0);
        ArrayList<ExpressionNode> argList = new ArrayList<>();
        for (int i = 0; i < argsNode.childrenNumber(); i++) {
            argList.add((ExpressionNode) argsNode.getChild(i));
        }

        String args = (String) argsNode.accept(this);
        String result = cGenerator.buildString(n, argList, args);
        cGenerator.setReadOp(false);

        return result;
    }

    // TODO stringa di formattazione
    @Override
    public Object visit(WriteOpNode n) {
        ArgsNode argsNode = (ArgsNode) n.getChild(0);
        ArrayList<ExpressionNode> argList = new ArrayList<>();
        for (int i = 0; i < argsNode.childrenNumber(); i++) {
            argList.add((ExpressionNode) argsNode.getChild(i));
        }

        String args = (String) argsNode.accept(this);
        return cGenerator.buildString(n, argList, args);
    }

    @Override
    public Object visit(TypeNode n) {
        return cGenerator.buildString(n);
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
        return cGenerator.buildString(n);
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
