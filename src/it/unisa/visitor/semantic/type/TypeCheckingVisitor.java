package it.unisa.visitor.semantic.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.arithmetic.ArithOpNode;
import it.unisa.ast.expression.operation.bool.BoolOpNode;
import it.unisa.ast.expression.operation.relational.RelOpNode;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
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

    private ArrayList<String> buildResult(ArrayList<String> subResult, String newResult) {
        ArrayList<String> result = new ArrayList<>(subResult);
        if (newResult != null) {
            result.add(newResult);
        }
        return result;
    }

    private ArrayList<String> buildResult(ArrayList<String> subResult, ArrayList<String> newResult) {
        ArrayList<String> result = new ArrayList<>(subResult);
        if (newResult != null) {
            result.addAll(newResult);
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

    @Override
    public Object visit(ArithOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n, TypeSystem.arithTable));
    }

    @Override
    public Object visit(BoolOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n, TypeSystem.boolTable));
    }

    @Override
    public Object visit(RelOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n, TypeSystem.relationalTable));
    }

    @Override
    public Object visit(UminusOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n, TypeSystem.uminusTable));
    }

    @Override
    public Object visit(NotOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n, TypeSystem.notTable));
    }

    @Override
    public Object visit(AssignOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n));
    }

    @Override
    public Object visit(ConditionalStatementNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n));
    }

    @Override
    public Object visit(CallOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n));
    }

    @Override
    public Object visit(ReadOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n));
    }
}
