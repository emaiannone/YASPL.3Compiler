package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.visitor.MyVisitor;

public class OutNode extends ParTypeNode {
    public static final String OUT = "out";

    public OutNode() {
        super(OUT);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "OutNode";
    }
}
