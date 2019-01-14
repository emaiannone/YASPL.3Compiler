package it.unisa.visitor.semantic.scope.type;

import it.unisa.ast.type.*;
import it.unisa.visitor.TreeVisitor;

import java.util.ArrayList;

public class TypeVisitor extends TreeVisitor {

    @Override
    public Object visit(TypeNode n) {
        ArrayList<TypeNode> typeNodes = new ArrayList<>();
        typeNodes.add(n);
        return typeNodes;
    }

    @Override
    public Object visit(IntegerNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(DoubleNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(CharacterNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(StringNode n) {
        return visit((TypeNode) n);
    }

    @Override
    public Object visit(BooleanNode n) {
        return visit((TypeNode) n);
    }
}
