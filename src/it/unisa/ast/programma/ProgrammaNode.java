package it.unisa.ast.programma;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public class ProgrammaNode extends MyNode {
    public ProgrammaNode() {
        super(null);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    @Override
    public String getXMLTag() {
        return "ProgrammaNode";
    }
}
