package it.unisa.ast.expression.constant;

import it.unisa.visitor.MyVisitor;

public class CharConstantNode extends ConstantNode {
    public CharConstantNode(Character data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "CharConstantNode";
    }
}
