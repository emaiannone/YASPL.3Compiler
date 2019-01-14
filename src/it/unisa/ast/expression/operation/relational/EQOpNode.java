package it.unisa.ast.expression.operation.relational;

import it.unisa.visitor.MyVisitor;

public class EQOpNode extends RelOpNode {
    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "EQOpNode";
    }
}
