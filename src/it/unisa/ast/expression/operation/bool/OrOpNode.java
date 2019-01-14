package it.unisa.ast.expression.operation.bool;

import it.unisa.visitor.MyVisitor;

public class OrOpNode extends BoolOpNode {
    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "OrOpNode";
    }
}
