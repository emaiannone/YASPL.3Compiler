package it.unisa.ast;

import it.unisa.visitor.MyVisitor;

public abstract class ExpressionStatementNode extends MyNode {
    private String type;

    public ExpressionStatementNode(Object data) {
        super(data);
        type = null;
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
    public String getXMLTag() {
        return "ExpressionStatementNode";
    }

    @Override
    public String toString() {
        return super.toString() + "Type: " + type;
    }
}
