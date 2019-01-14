package it.unisa.ast.type;

import it.unisa.visitor.MyVisitor;

public class BooleanNode extends TypeNode {
    public static final String BOOLEAN = "bool";

    public BooleanNode() {
        super(BOOLEAN);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "BooleanNode";
    }
}
