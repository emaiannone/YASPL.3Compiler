package it.unisa.ast.statement;

import it.unisa.visitor.MyVisitor;

public class ReadOpNode extends StatementNode {
    public String getXMLTag() {
        return "ReadOpNode";
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
