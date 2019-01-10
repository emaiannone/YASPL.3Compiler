package it.unisa.ast.expression.operation.relational;

import it.unisa.ast.expression.operation.OpNode;
import it.unisa.visitor.MyVisitor;

public abstract class RelOpNode extends OpNode {

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
