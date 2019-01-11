package it.unisa.ast.statement.conditional;

import it.unisa.visitor.MyVisitor;

public class IfThenOpNode extends ConditionalStatementNode {

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "IfThenOpNode";
    }
}
