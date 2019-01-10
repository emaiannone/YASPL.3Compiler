package it.unisa.ast.expression.operation.unary;

import it.unisa.visitor.MyVisitor;

public class UminusOpNode extends UnaryOpNode {
    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "UminusOpNode";
    }
}
