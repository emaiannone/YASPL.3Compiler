package it.unisa.visitor.semantic.type;

import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.arithmetic.*;
import it.unisa.ast.expression.operation.bool.AndOpNode;
import it.unisa.ast.expression.operation.bool.BoolOpNode;
import it.unisa.ast.expression.operation.bool.OrOpNode;
import it.unisa.ast.expression.operation.relational.*;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.WriteOpNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
import it.unisa.ast.statement.conditional.IfThenElseOpNode;
import it.unisa.ast.statement.conditional.IfThenOpNode;
import it.unisa.ast.statement.conditional.WhileOpNode;
import it.unisa.seman.typing.TypeChecker;
import it.unisa.seman.typing.TypeSystem;
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
    public Object visit(TrueNode n) {
        return visit((BoolConstantNode) n);
    }

    @Override
    public Object visit(FalseNode n) {
        return visit((BoolConstantNode) n);
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

    @Override
    public Object visit(WriteOpNode n) {
        // It first goes to the inductive base to calculate the type of the inner-most operations
        return buildResult((ArrayList<String>) visitSubtree(n), typeChecker.assignType(n));
    }

    @Override
    public Object visit(PlusOpNode n) {
        return visit((ArithOpNode) n);
    }

    @Override
    public Object visit(MinusOpNode n) {
        return visit((ArithOpNode) n);
    }

    @Override
    public Object visit(TimesOpNode n) {
        return visit((ArithOpNode) n);
    }

    @Override
    public Object visit(DivOpNode n) {
        return visit((ArithOpNode) n);
    }

    @Override
    public Object visit(AndOpNode n) {
        return visit((BoolOpNode) n);
    }

    @Override
    public Object visit(OrOpNode n) {
        return visit((BoolOpNode) n);
    }

    @Override
    public Object visit(EQOpNode n) {
        return visit((RelOpNode) n);
    }

    @Override
    public Object visit(GEOpNode n) {
        return visit((RelOpNode) n);
    }

    @Override
    public Object visit(GTOpNode n) {
        return visit((RelOpNode) n);
    }

    @Override
    public Object visit(LEOpNode n) {
        return visit((RelOpNode) n);
    }

    @Override
    public Object visit(LTOpNode n) {
        return visit((RelOpNode) n);
    }

    @Override
    public Object visit(IfThenOpNode n) {
        return visit((ConditionalStatementNode) n);
    }

    @Override
    public Object visit(IfThenElseOpNode n) {
        return visit((ConditionalStatementNode) n);
    }

    @Override
    public Object visit(WhileOpNode n) {
        return visit((ConditionalStatementNode) n);
    }
}
