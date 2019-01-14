package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class ParTypeNode extends MyNode {
    private String type;

    public ParTypeNode(String type) {
        super(null);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
