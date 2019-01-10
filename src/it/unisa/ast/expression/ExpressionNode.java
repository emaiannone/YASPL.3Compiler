package it.unisa.ast.expression;

import it.unisa.ast.ExpressionStatementNode;
import it.unisa.visitor.MyVisitor;

public abstract class ExpressionNode extends ExpressionStatementNode {

    public ExpressionNode(Object data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}