package it.unisa.visitor.semantic.scope.identifier;

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
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.type.TypeNode;
import it.unisa.visitor.MyVisitor;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@SuppressWarnings("unchecked")
public abstract class IdentifierVisitor implements MyVisitor {

    /**
     * Helper method to iterate the visit on all children
     *
     * @param n
     * @return
     */
    Object visitSubtree(MyNode n) {
        ArrayList<IdentifierNode> result = new ArrayList<>();
        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
                ArrayList<IdentifierNode> subResult = (ArrayList<IdentifierNode>) c.accept(this);
                if (subResult != null) {
                    result.addAll(subResult);
                }
            }
        }
        return result;
    }

    @Override
    public Object visit(IdentifierNode n) {
        ArrayList<IdentifierNode> identifierNodes = new ArrayList<>();
        identifierNodes.add(n);
        return identifierNodes;
    }

    @Override
    public Object visit(MyNode n) {
        return null;
    }

    @Override
    public Object visit(ProgrammaNode n) {
        return null;
    }

    @Override
    public Object visit(DeclarationNode n) {
        return null;
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        return null;
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        return null;
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        return null;
    }

    @Override
    public Object visit(VarInitListNode n) {
        return null;
    }

    @Override
    public Object visit(VarInitNode n) {
        return null;
    }

    @Override
    public Object visit(StatementNode n) {
        return null;
    }

    @Override
    public Object visit(ArgsNode n) {
        return null;
    }

    @Override
    public Object visit(ExpressionNode n) {
        return null;
    }

    @Override
    public Object visit(ConstantNode n) {
        return null;
    }

    @Override
    public Object visit(IntegerConstantNode n) {
        return null;
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        return null;
    }

    @Override
    public Object visit(StringConstantNode n) {
        return null;
    }

    @Override
    public Object visit(CharConstantNode n) {
        return null;
    }

    @Override
    public Object visit(BoolConstantNode n) {
        return null;
    }

    @Override
    public Object visit(OpNode n) {
        return null;
    }

    @Override
    public Object visit(ArithOpNode n) {
        return null;
    }

    @Override
    public Object visit(BoolOpNode n) {
        return null;
    }

    @Override
    public Object visit(RelOpNode n) {
        return null;
    }

    @Override
    public Object visit(UminusOpNode n) {
        return null;
    }

    @Override
    public Object visit(NotOpNode n) {
        return null;
    }

    @Override
    public Object visit(AssignOpNode n) {
        return null;
    }

    @Override
    public Object visit(TypeNode n) {
        return null;
    }
}
