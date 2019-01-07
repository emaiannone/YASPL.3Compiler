package it.unisa.ast.declaration.variable;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.visitor.MyVisitor;

public class VarDeclarationNode extends DeclarationNode {
    public static final String VARIABLE = "variable";

    public VarDeclarationNode() {
        super(VARIABLE);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "VarDeclarationNode";
    }
}
