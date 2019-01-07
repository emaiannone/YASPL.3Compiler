package it.unisa.visitor.semantic.type;

import it.unisa.ast.expression.constant.*;
import it.unisa.visitor.semantic.SemanticVisitor;

public class TypeCheckingVisitor extends SemanticVisitor {
    private TypeChecker typeChecker;

    public TypeCheckingVisitor() {
        typeChecker = new TypeChecker();
    }

    //TODO visite sui nodi Expression, e Statement principalmente

    @Override
    public Object visit(IntegerConstantNode n) {
        typeChecker.setType(n);
        return null;
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        typeChecker.setType(n);
        return null;
    }

    @Override
    public Object visit(StringConstantNode n) {
        typeChecker.setType(n);
        return null;
    }

    @Override
    public Object visit(CharConstantNode n) {
        typeChecker.setType(n);
        return null;
    }

    @Override
    public Object visit(BoolConstantNode n) {
        typeChecker.setType(n);
        return null;
    }
}
