package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.visitor.MyVisitor;

public class InNode extends ParTypeNode {
    public static final String IN = "in";

    public InNode() {
        super(IN);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "InNode";
    }
}
