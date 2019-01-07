package it.unisa.ast.expression;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class ExpressionNode extends MyNode {
    private String type;

    public ExpressionNode(Object data) {
        super(data);
        type = "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return super.toString() + "Type: " + type;
    }
}