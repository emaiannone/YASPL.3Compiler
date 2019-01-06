package it.unisa.ast.expression;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class ExpressionNode extends MyNode {
    public ExpressionNode(Object data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}