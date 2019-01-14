package it.unisa.ast.type;

import it.unisa.visitor.MyVisitor;

public class CharacterNode extends TypeNode {
    public static final String CHARACTER = "char";

    public CharacterNode() {
        super(CHARACTER);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "CharacterNode";
    }
}
