package it.unisa.visitor.semantic;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.ConstantNode;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.visitor.MyVisitor;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@SuppressWarnings("unchecked")
public abstract class SemanticVisitor implements MyVisitor {

    /**
     * Helper method to iterate the visit on all children
     *
     * @param n
     * @return
     */
    protected Object visitSubtree(MyNode n) {
        ArrayList<String> result = new ArrayList<>();
        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
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
    public Object visit(IdentifierNode n) {
        return visitSubtree(n);
    }

}
