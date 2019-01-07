package it.unisa.ast.type;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class TypeNode extends MyNode {
    private String type;

    public TypeNode(String type) {
        super(null);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
