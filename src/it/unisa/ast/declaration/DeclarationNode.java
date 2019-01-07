package it.unisa.ast.declaration;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class DeclarationNode extends MyNode {
    private String kind;

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public DeclarationNode(String kind) {
        super(null);
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}
