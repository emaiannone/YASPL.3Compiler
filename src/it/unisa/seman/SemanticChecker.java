package it.unisa.seman;

import it.unisa.ast.MyNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.programma.ProgrammaNode;

/*
Scope check A:
Se il nodo è legato ad un costrutto di creazione di nuovo scope (ProgramOp, ProcDeclOp) allora
    se il nodo è visitato per la prima volta
        allora crea una nuova tabella, legala al nodo corrente e inseriscila al top dello stack (push)
se il nodo è visitato per l’ultima volta (tutti i figli sono stati già visitati) allora
    eliminala dal top dello stack (pop) [la tabella resta comunque legata al nodo]
 */

public abstract class SemanticChecker {
    private SymbolTable symbolTable;

    public SemanticChecker() {
        symbolTable = new SymbolTable();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Creates a new scope for the node n, if needed, and pushes it to the scope table stack.
     * The created scope table is attatched to the argument object.
     * It only works for <i>ProgrammaNode</i> or <i>ProcedureDeclarationNode</i> object.
     *
     * @param n the <i>ProgrammaNode</i> or <i>ProcedureDeclarationNode</i> object.
     */
    //Scope Check A
    public void startScope(MyNode n) {
        if (n instanceof ProgrammaNode || n instanceof ProcedureDeclarationNode) {
            if (n.data() == null) {
                n.setData(new ScopeTable());
            }
            ScopeTable scopeTable = (ScopeTable) n.data();
            symbolTable.push(scopeTable); // Its scope is active
        }
    }

    /**
     * Ends the current scope, by popping it out of the scope table stack.
     */
    //Scope Check A
    public void endCurrentScope() {
        symbolTable.pop(); // Its scope is now disabled
    }
}
