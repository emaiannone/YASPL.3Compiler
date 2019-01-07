package it.unisa.visitor.semantic.scope.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;

public class TypeDeclarationVisitor extends TypeVisitor {
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

}
