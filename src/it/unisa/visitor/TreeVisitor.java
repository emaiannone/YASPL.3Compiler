package it.unisa.visitor;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.OpNode;
import it.unisa.ast.expression.operation.arithmetic.ArithOpNode;
import it.unisa.ast.expression.operation.bool.BoolOpNode;
import it.unisa.ast.expression.operation.relational.RelOpNode;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.BodyNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
import it.unisa.ast.type.TypeNode;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public abstract class TreeVisitor implements MyVisitor {

    /**
     * Helper method to iterate the visit on all children
     *
     * @param n
     * @return An ArrayList of String containing the error messages
     */
    protected Object visitSubtree(MyNode n) {
        ArrayList<String> result = new ArrayList<>();

        if (n.hasChildren()) {
            for (MyNode c : n.children()) {
                ArrayList<String> subResult = (ArrayList<String>) c.accept(this);
                if (subResult != null) {
                    result.addAll(subResult);
                }
            }
        }

        return result;
    }

    @Override
    public Object visit(MyNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ProgrammaNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(DeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarInitListNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarInitNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(StatementNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ArgsNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ExpressionNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(IntegerConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(StringConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(CharConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(BoolConstantNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(IdentifierNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(OpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ArithOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(BoolOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(RelOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(UminusOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(NotOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(AssignOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ConditionalStatementNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(CallOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ReadOpNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(BodyNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(TypeNode n) {
        return visitSubtree(n);
    }
}
