package it.unisa.ast.statement;

import it.unisa.ast.MyNode;
import it.unisa.visitor.MyVisitor;

public abstract class StatementNode extends MyNode {
    public StatementNode() {
        super(null);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
