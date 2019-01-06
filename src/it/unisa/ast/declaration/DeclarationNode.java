package it.unisa.ast.declaration;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class DeclarationNode extends MyNode {
    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public DeclarationNode() {
        super(null);
    }
}
