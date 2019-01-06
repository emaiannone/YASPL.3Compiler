package it.unisa.visitor.semantic.scope.identifier;

import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.statement.StatementNode;

public class IdentifierDefUseVisitor extends IdentifierVisitor {

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

}
