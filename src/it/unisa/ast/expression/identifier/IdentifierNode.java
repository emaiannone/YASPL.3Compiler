package it.unisa.ast.expression.identifier;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.visitor.MyVisitor;

public class IdentifierNode extends ExpressionNode {
    public IdentifierNode(String data) {
        super(data);
    }

    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "IdentifierNode";
    }
}
