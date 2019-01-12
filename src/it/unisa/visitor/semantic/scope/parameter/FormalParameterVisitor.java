package it.unisa.visitor.semantic.scope.parameter;

import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

public class FormalParameterVisitor extends TreeVisitor {

    @Override
    public Object visit(ParDeclarationNode n) {
        ArrayList<ParDeclarationNode> typeNodes = new ArrayList<>();
        typeNodes.add(n);
        return typeNodes;
    }

}
