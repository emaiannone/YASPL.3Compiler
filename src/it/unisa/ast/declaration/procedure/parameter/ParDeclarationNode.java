package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.visitor.MyVisitor;

public class ParDeclarationNode extends DeclarationNode {
    public static final String PARAMETER = "parameter";

    public ParDeclarationNode() {
        super(PARAMETER);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "ParDeclarationNode";
    }
}
