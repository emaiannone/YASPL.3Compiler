package it.unisa.ast.initialization;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public class VarInitNode extends MyNode {
    public VarInitNode() {
        super(null);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "VarInitNode";
    }
}
