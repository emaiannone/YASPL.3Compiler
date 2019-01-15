package it.unisa.visitor.semantic.scope.identifier;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.initialization.VarInitNode;

import java.util.ArrayList;

public class IdentifierDeclarationVisitor extends IdentifierVisitor {

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        ArrayList<IdentifierNode> identifierNodes = new ArrayList<>();
        identifierNodes.add((IdentifierNode) n.getChild(0));
        return identifierNodes;
    }

    @Override
    public Object visit(VarInitNode n) {
        ArrayList<IdentifierNode> identifierNodes = new ArrayList<>();
        identifierNodes.add((IdentifierNode) n.getChild(0));
        return identifierNodes;
    }
}
