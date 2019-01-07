package it.unisa.ast.declaration.procedure;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.visitor.MyVisitor;

public class ProcedureDeclarationNode extends DeclarationNode {
    public static final String PROCEDURE = "procedure";

    public ProcedureDeclarationNode() {
        super(PROCEDURE);
    }

    // Method to be visited by a Visitor
    @Override
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "ProcedureDeclarationNode";
    }
}
