package it.unisa.ast.statement.conditional;

import it.unisa.ast.statement.StatementNode;
import it.unisa.visitor.MyVisitor;

public abstract class ConditionalStatementNode extends StatementNode {

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

}
