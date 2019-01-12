package it.unisa.visitor.semantic.scope.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;

public class TypeDeclarationVisitor extends TypeVisitor {

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        return null;
    }
}
