package it.unisa.visitor.semantic.scope;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
import it.unisa.seman.scoping.ScopeChecker;
import it.unisa.visitor.semantic.SemanticVisitor;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ScopeCheckingVisitor extends SemanticVisitor {
    private ScopeChecker scopeChecker;

    public ScopeCheckingVisitor() {
        scopeChecker = new ScopeChecker();
    }

    @Override
    public Object visit(ProgrammaNode n) {
        scopeChecker.startScope(n);
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        scopeChecker.endCurrentScope();

        return new ArrayList<>(subResult);
    }

    @Override
    public Object visit(DeclarationNode n) {
        ArrayList<String> checkBResult = scopeChecker.checkMultipleDeclarations(n);
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);

        ArrayList<String> result = new ArrayList<>(checkBResult);
        result.addAll(subResult);
        return result;
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        ArrayList<String> checkBResult = scopeChecker.checkMultipleDeclarations(n);

        scopeChecker.startScope(n);
        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        scopeChecker.endCurrentScope();

        ArrayList<String> result = new ArrayList<>(checkBResult);
        result.addAll(subResult);
        return result;
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        return visit((DeclarationNode) n);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        return visit((DeclarationNode) n);
    }

    @Override
    public Object visit(StatementNode n) {
        ArrayList<String> result = new ArrayList<>(scopeChecker.checkUndeclarations(n));

        ArrayList<String> subResult = (ArrayList<String>) visitSubtree(n);
        result.addAll(subResult);
        return result;
    }

    @Override
    public Object visit(AssignOpNode n) {
        return visit((StatementNode) n);
    }

    @Override
    public Object visit(ConditionalStatementNode n) {
        return visit((StatementNode) n);
    }

    @Override
    public Object visit(CallOpNode n) {
        return visit((StatementNode) n);
    }

    @Override
    public Object visit(ReadOpNode n) {
        return visit((StatementNode) n);
    }
}
