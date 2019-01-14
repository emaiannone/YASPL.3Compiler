package it.unisa.ast.expression.operation.arithmetic;

import it.unisa.visitor.MyVisitor;

public class DivOpNode extends ArithOpNode {
    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "DivOpNode";
    }
}
