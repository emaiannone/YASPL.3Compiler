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
import it.unisa.ast.statement.conditional.IfThenElseOpNode;
import it.unisa.ast.statement.conditional.IfThenOpNode;
import it.unisa.ast.statement.conditional.WhileOpNode;
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

    /*
    private ArrayList<String> assignType(ExpressionStatementNode n, int[][] typeTable) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, typeTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }
    */

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
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, TypeSystem.arithTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(BoolOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, TypeSystem.boolTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(RelOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, TypeSystem.relationalTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(UminusOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, TypeSystem.uminusTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(NotOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n, TypeSystem.notTable);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(AssignOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(WhileOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(IfThenOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    @Override
    public Object visit(IfThenElseOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.assignType(n);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null) {
            result.add(typeCheckResult);
        }
        return result;
    }

    //TODO visite sui nodi While, IFThen e IfThenElse

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
