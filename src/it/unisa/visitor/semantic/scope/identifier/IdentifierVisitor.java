package it.unisa.visitor.semantic.scope.identifier;

import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

public class IdentifierVisitor extends TreeVisitor {

    @Override
    public Object visit(IdentifierNode n) {
        ArrayList<IdentifierNode> identifierNodes = new ArrayList<>();
        identifierNodes.add(n);
        return identifierNodes;
    }
}
