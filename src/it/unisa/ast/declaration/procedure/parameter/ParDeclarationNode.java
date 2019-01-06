package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.visitor.MyVisitor;

public class ParDeclarationNode extends DeclarationNode {

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "ParDeclarationNode";
    }
}
