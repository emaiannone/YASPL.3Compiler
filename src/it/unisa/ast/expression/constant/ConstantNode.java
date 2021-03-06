package it.unisa.ast.expression.constant;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.visitor.MyVisitor;

public abstract class ConstantNode extends ExpressionNode {
    public ConstantNode(Object data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
