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

public class CGeneratorVisitor extends TreeVisitor {
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

    private ArrayList<String> buildStrings(MyNode n) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < n.childrenNumber(); i++) {
            strings.add((String) n.getChild(i).accept(this));
        }
        return strings;
    }

    @Override
    public Object visit(ProgrammaNode n) {
        cGenerator.startScope(n);

        String declarationPart = (String) n.getChild(0).accept(this);
        String mainPart = (String) n.getChild(1).accept(this);
        String result = cGenerator.buildProgrammaString(declarationPart, mainPart);

        cGenerator.endCurrentScope();
        return result;
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        cGenerator.startScope(n);
        String procedureName = (String) n.getChild(0).accept(this);

        String pars = (String) n.getChild(1).accept(this);
        String body = (String) n.getChild(2).accept(this);
        String result = cGenerator.buildProcedureString(procedureName, pars, body);

        cGenerator.endCurrentScope();
        return result;
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        String type = (String) n.getChild(0).accept(this);
        String varInits = (String) n.getChild(1).accept(this);
        return cGenerator.buildVarDeclarationString(type, varInits);
    }

    @Override
    public Object visit(VarInitListNode n) {
        ArrayList<String> vars = buildStrings(n);
        return cGenerator.buildVarInitListString(vars);
    }

    @Override
    public Object visit(VarInitNode n) {
        String varType = ((IdentifierNode) n.getChild(0)).getType();
        String var = (String) n.getChild(0).accept(this);
        String initValue = "";
        if (n.getChild(1) != null) {
            initValue = (String) n.getChild(1).accept(this);
        }
        return cGenerator.buildVarInitString(varType, var, initValue);
    }

    @Override
    public Object visit(ParDeclarationListNode n) {
        ArrayList<String> pars = buildStrings(n);
        return cGenerator.buildParDeclarationListString(pars);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        String parType = (String) n.getChild(0).accept(this);
        String type = (String) n.getChild(1).accept(this);
        String parName = (String) n.getChild(2).accept(this);

        return cGenerator.buildParDeclarationString(parType, type, parName);
    }

    @Override
    public Object visit(ArgsNode n) {
        ArrayList<String> args = buildStrings(n);
        return cGenerator.buildArgsString(args);
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
        return cGenerator.buildCharConstantString(n.data().toString());
    }

    @Override
    public Object visit(TrueNode n) {
        return "1";
    }

    @Override
    public Object visit(FalseNode n) {
        return "0";
    }

    @Override
    public Object visit(IdentifierNode n) {
        return cGenerator.buildIdentifierString((String) n.data());
    }

    @Override
    public Object visit(PlusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildPlusString(first, second);
    }

    @Override
    public Object visit(MinusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildMinusString(first, second);
    }

    @Override
    public Object visit(TimesOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildTimesString(first, second);
    }

    @Override
    public Object visit(DivOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildDivString(first, second);
    }

    @Override
    public Object visit(AndOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildAndString(first, second);
    }

    @Override
    public Object visit(OrOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildOrString(first, second);
    }

    @Override
    public Object visit(EQOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildEQString(first, second);
    }

    @Override
    public Object visit(GEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildGEString(first, second);
    }

    @Override
    public Object visit(GTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildGTString(first, second);
    }

    @Override
    public Object visit(LEOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildLEString(first, second);
    }

    @Override
    public Object visit(LTOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildLTString(first, second);
    }

    @Override
    public Object visit(UminusOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return cGenerator.buildUminusString(first);
    }

    @Override
    public Object visit(NotOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        return cGenerator.buildNotString(first);
    }

    @Override
    public Object visit(AssignOpNode n) {
        String first = (String) n.getChild(0).accept(this);
        String second = (String) n.getChild(1).accept(this);
        return cGenerator.buildAssignString(first, second);
    }

    @Override
    public Object visit(IfThenOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBranch = (String) n.getChild(1).accept(this);
        return cGenerator.buildIfThenString(condition, thenBranch);
    }

    @Override
    public Object visit(IfThenElseOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String thenBranch = (String) n.getChild(1).accept(this);
        String elseBranch = (String) n.getChild(2).accept(this);
        return cGenerator.buildIfThenElseString(condition, thenBranch, elseBranch);
    }

    @Override
    public Object visit(WhileOpNode n) {
        String condition = (String) n.getChild(0).accept(this);
        String body = (String) n.getChild(1).accept(this);
        return cGenerator.buildWhileString(condition, body);
    }

    @Override
    public Object visit(CallOpNode n) {
        String procedureName = (String) n.getChild(0).accept(this);
        ArgsNode argsNode = (ArgsNode) n.getChild(1);
        ArrayList<String> args = null;
        if (argsNode != null) {
            args = buildStrings(argsNode);
        }
        return cGenerator.buildCallString(procedureName, args);
    }

    @Override
    public Object visit(ReadOpNode n) {
        ArgsNode argsNode = (ArgsNode) n.getChild(0);
        ArrayList<ExpressionNode> exprs = new ArrayList<>();
        for (int i = 0; i < argsNode.childrenNumber(); i++) {
            exprs.add((ExpressionNode) argsNode.getChild(i));
        }
        ArrayList<String> args = buildStrings(argsNode);
        return cGenerator.buildReadString(exprs, args);
    }

    @Override
    public Object visit(WriteOpNode n) {
        ArgsNode argsNode = (ArgsNode) n.getChild(0);
        ArrayList<ExpressionNode> exprs = new ArrayList<>();
        for (int i = 0; i < argsNode.childrenNumber(); i++) {
            exprs.add((ExpressionNode) argsNode.getChild(i));
        }
        ArrayList<String> args = buildStrings(argsNode);
        return cGenerator.buildWriteString(exprs, args);
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
