package it.unisa.ast.type;

import it.unisa.visitor.MyVisitor;

public class StringNode extends TypeNode {
    public static final String STRING = "string";

    public StringNode() {
        super(STRING);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "StringNode";
    }
}
