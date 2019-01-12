package it.unisa.visitor.semantic.scope.type;

import it.unisa.ast.type.TypeNode;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

public class TypeVisitor extends TreeVisitor {

    @Override
    public Object visit(TypeNode n) {
        ArrayList<TypeNode> typeNodes = new ArrayList<>();
        typeNodes.add(n);
        return typeNodes;
    }
}
