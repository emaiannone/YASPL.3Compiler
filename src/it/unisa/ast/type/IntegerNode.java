package it.unisa.ast.type;

import it.unisa.visitor.MyVisitor;

public class IntegerNode extends TypeNode {
    public static final String INTEGER = "int";

    public IntegerNode() {
        super(INTEGER);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "IntegerNode";
    }
}
