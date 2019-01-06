package it.unisa.ast.args;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public class ArgsNode extends MyNode {
    public ArgsNode() {
        super(null);
    }

    public String getXMLTag() {
        return "ArgsNode";
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
