package it.unisa.visitor.semantic.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.arithmetic.PlusOpNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.visitor.semantic.SemanticVisitor;

import java.lang.reflect.Type;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class TypeCheckingVisitor extends SemanticVisitor {
    private TypeChecker typeChecker;

    public TypeCheckingVisitor() {
        typeChecker = new TypeChecker();
    }

    //TODO visite sui nodi Expression, e Statement principalmente

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
    public Object visit(PlusOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        String typeCheckResult = typeChecker.checkType(n, TypeSystem.PLUS_TABLE);

        ArrayList<String> result = new ArrayList<>(subResult);
        if (typeCheckResult != null && !typeCheckResult.equals("")) {
            result.add(typeCheckResult);
        }
        return result;
    }

    //TODO Altre visite una per tabella di tipo

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
