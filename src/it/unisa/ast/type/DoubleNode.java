package it.unisa.ast.type;

import it.unisa.visitor.MyVisitor;

public class DoubleNode extends TypeNode {
    public static final String DOUBLE = "double";

    public DoubleNode() {
        super(DOUBLE);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "DoubleNode";
    }
}
