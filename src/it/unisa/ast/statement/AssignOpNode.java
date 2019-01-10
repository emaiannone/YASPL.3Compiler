package it.unisa.ast.statement;

import it.unisa.visitor.MyVisitor;

public class AssignOpNode extends StatementNode {

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "AssignOpNode";
    }
}
