package it.unisa.ast.statement;

import it.unisa.ast.ExpressionStatementNode;
import it.unisa.visitor.MyVisitor;

public abstract class StatementNode extends ExpressionStatementNode {

    public StatementNode() {
        super(null);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
