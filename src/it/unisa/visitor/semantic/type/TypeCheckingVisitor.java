package it.unisa.visitor.semantic.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.OpNode;
import it.unisa.ast.expression.operation.arithmetic.ArithOpNode;
import it.unisa.ast.expression.operation.bool.BoolOpNode;
import it.unisa.ast.expression.operation.relational.RelOpNode;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.expression.operation.unary.UnaryOpNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.semantic.typing.TypeChecker;
import it.unisa.semantic.typing.TypeSystem;
import it.unisa.visitor.semantic.SemanticVisitor;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class TypeCheckingVisitor extends SemanticVisitor {
    private TypeChecker typeChecker;

    public TypeCheckingVisitor() {
        typeChecker = new TypeChecker();
    }

    private ArrayList<String> assignType(OpNode n, int[][] typeTable) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, typeTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null && !typeCheckResult.equals("")) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(ProgrammaNode n) {
        typeChecker.startScope(n);
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        typeChecker.endCurrentScope();

        return new ArrayList<>(subResult);
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        typeChecker.startScope(n);
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        typeChecker.endCurrentScope();

        return new ArrayList<>(subResult);
    }

    @Override
    public Object visit(ArithOpNode n) {
        return assignType(n, TypeSystem.arithTable);
    }

    @Override
    public Object visit(BoolOpNode n) {
        return assignType(n, TypeSystem.boolTable);
    }

    @Override
    public Object visit(RelOpNode n) {
        return assignType(n, TypeSystem.relationalTable);
    }

    @Override
    public Object visit(UminusOpNode n) {
        return assignType(n, TypeSystem.uminusTable);
    }

    @Override
    public Object visit(NotOpNode n) {
        return assignType(n, TypeSystem.notTable);
    }

    //TODO Altre visite, una per classe di nodi che condividono una stessa tabella

    //TODO visite sui nodi Statement

    @Override
    public Object visit(IntegerConstantNode n) {
        typeChecker.assignType(n);
        return null;
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        typeChecker.assignType(n);
        return null;
    }

    @Override
    public Object visit(StringConstantNode n) {
        typeChecker.assignType(n);
        return null;
    }

    @Override
    public Object visit(CharConstantNode n) {
        typeChecker.assignType(n);
        return null;
    }

    @Override
    public Object visit(BoolConstantNode n) {
        typeChecker.assignType(n);
        return null;
    }

    @Override
    public Object visit(IdentifierNode n) {
        typeChecker.assignType(n);
        return null;
    }
}
