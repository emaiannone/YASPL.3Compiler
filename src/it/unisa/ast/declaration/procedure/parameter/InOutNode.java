package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.visitor.MyVisitor;

public class InOutNode extends ParTypeNode {
    public static final String INOUT = "inout";

    public InOutNode() {
        super(INOUT);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "InOutNode";
    }
}
