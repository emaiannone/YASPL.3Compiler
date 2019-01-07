package it.unisa.visitor.semantic.scope.identifier;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.VarInitListNode;

public class IdentifierDeclarationVisitor extends IdentifierVisitor {

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarInitListNode n) {
        return visitSubtree(n);
    }

    @Override
    public Object visit(VarInitNode n) {
        return visitSubtree(n);
    }
}
