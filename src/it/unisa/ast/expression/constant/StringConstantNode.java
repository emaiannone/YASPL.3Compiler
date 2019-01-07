package it.unisa.ast.expression.constant;

import it.unisa.visitor.MyVisitor;

public class StringConstantNode extends ConstantNode {
    public StringConstantNode(String data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "StringConstantNode";
    }
}
