package it.unisa.visitor.semantic.scope.type;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.type.*;
import it.unisa.visitor.MyVisitor;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@SuppressWarnings("unchecked")
public abstract class TypeVisitor implements MyVisitor {

    /**
     * Helper method to iterate the visit on all children
     *
     * @param n
     * @return
     */
    Object visitSubtree(MyNode n) {
        ArrayList<TypeNode> result = new ArrayList<>();
        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
                ArrayList<TypeNode> subResult = (ArrayList<TypeNode>) c.accept(this);
                if (subResult != null) {
                    result.addAll(subResult);
                }
            }
        }
        return result;
    }

    @Override
    public Object visit(TypeNode n) {
        ArrayList<TypeNode> typeNodes = new ArrayList<>();
        typeNodes.add(n);
        return typeNodes;
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
    public Object visit(IdentifierNode n) {
        return null;
    }
}