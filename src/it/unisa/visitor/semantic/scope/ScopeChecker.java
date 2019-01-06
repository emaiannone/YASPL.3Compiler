package it.unisa.visitor.semantic.scope;

import it.unisa.ast.MyNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.visitor.semantic.scope.identifier.IdentifierDeclarationVisitor;
import it.unisa.visitor.semantic.scope.identifier.IdentifierDefUseVisitor;

import java.util.ArrayList;

/*
Scope check A:
Se il nodo è legato ad un costrutto di creazione di nuovo scope (ProgramOp, ProcDeclOp) allora
    se il nodo è visitato per la prima volta
        allora crea una nuova tabella, legala al nodo corrente e inseriscila al top dello stack (push)
se il nodo è visitato per l’ultima volta (tutti i figli sono stati già visitati) allora
    eliminala dal top dello stack (pop) [la tabella resta comunque legata al nodo]

Scope check B:
Se il nodo è legato ad un costrutto di dichiarazione variabile o funzione (VarDeclOp, ParOp)
    se la tabella al top dello stack contiene già la dichiarazione dell’identificatore coinvolto (probe ritorna true)
        allora restituisci “errore di dichiarazione multipla”
        altrimenti aggiungi dichiarazione alla tabella al top dello stack

Scope check C:
Se il nodo è legato ad un costrutto statement che usa degli identificatori (StatementNode)
    se uno dei nodi figli è un nodo legato ad un costrutto di identificatore non presente
    in tutto nello stack della tabella dei simboli (lookup ritorna null)
        allora restituisci "errore identificatore non dichiarato"
*/

@SuppressWarnings("unchecked")
class ScopeChecker {
    private SymbolTable symbolTable;
    private static final String MULTIPLE_DECLARATION = "Multiple declaration of the identifier: ";
    private static final String NO_SCOPE = "No active scope";
    private static final String NO_DECLARATION = "No declaration of the identifier: ";

    ScopeChecker(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    /**
     * Creates a new scope if needed and pushes it to the scope table stack.
     * The created scope table is attatched to the argument object.
     * It only works for <i>ProgrammaNode</i> or <i>ProcedureDeclarationNode</i> object.
     *
     * @param n the <i>ProgrammaNode</i> or <i>ProcedureDeclarationNode</i> object.
     */
    void scopeCheckAPreOrder(MyNode n) {
        if (n instanceof ProgrammaNode || n instanceof ProcedureDeclarationNode) {
            if (n.data() == null) {
                n.setData(new ScopeTable());
            }
            ScopeTable scopeTable = (ScopeTable) n.data();
            symbolTable.push(scopeTable); // Its scope is active
        }
    }

    /**
     * Disables the current scope, by popping it out of the scope table stack.
     */
    void scopeCheckAPostOrder() {
        symbolTable.pop(); // Its scope is now disabled
    }

    /**
     * Check for multiple declaration of an identifier in the same scope.
     * It also adds the declaration to the active scope table.
     *
     * @param n The <i>DeclarationNode</i> object under exam.
     * @return An <i>ArrayList</i> of <i>String</i> containing the multiple declarations error messages. It is empty if everything is okay.
     */
    ArrayList<String> scopeCheckB(DeclarationNode n) {
        ArrayList<String> errors = new ArrayList<>();
        if (!symbolTable.empty()) {
            IdentifierDeclarationVisitor idv = new IdentifierDeclarationVisitor();
            ArrayList<IdentifierNode> identifiers = (ArrayList<IdentifierNode>) n.accept(idv);
            if (identifiers != null && !identifiers.isEmpty()) {
                for (IdentifierNode i : identifiers) {
                    String identifierName = (String) i.data();
                    if (symbolTable.probe(identifierName)) {
                        System.out.println("ciao");
                        errors.add(MULTIPLE_DECLARATION + identifierName);
                    }
                    SemanticData semanticData = new SemanticData(identifierName, SemanticData.PARAMETER);
                    symbolTable.getCurrentScope().getTable().put(identifierName, semanticData);
                }
            }
        } else {
            errors.add(NO_SCOPE);
        }
        return errors;
    }

    /**
     * Check the usages of the undeclared identifiers in statements.
     *
     * @param n The <i>StatementNode</i> object under exam.
     * @return An <i>ArrayList</i> of <i>String</i> containing the no declaration error messages. It is empty if everything is okay.
     */
    ArrayList<String> scopeCheckC(StatementNode n) {
        ArrayList<String> errors = new ArrayList<>();
        if (!symbolTable.empty()) {
            IdentifierDefUseVisitor iv = new IdentifierDefUseVisitor();
            ArrayList<IdentifierNode> identifiers = (ArrayList<IdentifierNode>) n.accept(iv);
            if (identifiers != null && !identifiers.isEmpty()) {
                for (IdentifierNode i : identifiers) {
                    String identifierName = (String) i.data();
                    SemanticData semanticData = symbolTable.lookup(identifierName);
                    if (semanticData == null) {
                        errors.add(NO_DECLARATION + identifierName);
                    }
                }
            }
        } else {
            errors.add(NO_SCOPE);
        }
        return errors;
    }

//
//    /**
//     * Adds the declared procedure to the active scope table.
//     * It also looks for multiple declaration of the same procedure identifier in the same block.
//     *
//     * @param n The <i>ProcedureDeclarationNode</i> object under exam.
//     * @return A <i>String</i> object containing the error message, <i>null</i> if ok.
//     */
//    String scopeCheckB(ProcedureDeclarationNode n) {
//        if (symbolTable.empty()) {
//            return NO_SCOPE;
//        }
//        IdentifierNode identifier = (IdentifierNode) n.subtrees().iterator().next();
//        String identifierName = (String) identifier.data();
//        if (symbolTable.probe(identifierName)) {
//            return MULTIPLE_DECLARATION + identifierName;
//        }
//        SemanticData semanticData = new SemanticData(identifierName, SemanticData.PROCEDURE);
//        symbolTable.getCurrentScope().getTable().put(identifierName, semanticData);
//        return null;
//    }
//
//    /**
//     * Adds all the declared variable in a block to the active scope table.
//     * It also looks for multiple declaration of the same variable identifier in the same block.
//     *
//     * @param n The <i>VarDeclarationNode</i> object under exam.
//     * @return A <i>String</i> object containing the error message, <i>null</i> if ok.
//     */
//    String scopeCheckB(VarDeclarationNode n) {
//        if (symbolTable.empty()) {
//            return NO_SCOPE;
//        }
//        Iterator<MyNode> varDeclarationIterator = (Iterator<MyNode>) n.subtrees().iterator();
//        TypeNode type = (TypeNode) varDeclarationIterator.next();
//        VarInitListNode varInitList = (VarInitListNode) varDeclarationIterator.next();
//
//        // Fetch all declared variables under the same type, if any
//        if (!varInitList.subtrees().isEmpty()) {
//            LinkedHashSet<VarInitNode> children = (LinkedHashSet<VarInitNode>) varInitList.subtrees();
//            for (VarInitNode c : children) {
//                IdentifierNode identifier = (IdentifierNode) c.subtrees().iterator().next();
//                String identifierName = (String) identifier.data();
//
//                if (symbolTable.probe(identifierName)) {
//                    return MULTIPLE_DECLARATION + identifierName;
//                }
//                SemanticData semanticData = new SemanticData(identifierName, SemanticData.VARIABLE);
//                symbolTable.getCurrentScope().getTable().put(identifierName, semanticData);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Adds all the parameters of a procedure to the active scope table.
//     * It also looks for multiple declaration of the same parameter identifier in the procedure.
//     *
//     * @param n The <i>ParameterDeclarationNode</i> object under exam.
//     * @return A <i>String</i> object containing the error message, <i>null</i> if ok.
//     */
//    String scopeCheckB(ParDeclarationNode n) {
//        if (symbolTable.empty()) {
//            return NO_SCOPE;
//        }
//        Iterator<MyNode> parDeclarationIterator = (Iterator<MyNode>) n.subtrees().iterator();
//        ParTypeNode parType = (ParTypeNode) parDeclarationIterator.next();
//        TypeNode type = (TypeNode) parDeclarationIterator.next();
//        IdentifierNode identifier = (IdentifierNode) parDeclarationIterator.next();
//        String identifierName = (String) identifier.data();
//        if (symbolTable.probe(identifierName)) {
//            return MULTIPLE_DECLARATION + identifierName;
//        }
//        SemanticData semanticData = new SemanticData(identifierName, SemanticData.PARAMETER);
//        symbolTable.getCurrentScope().getTable().put(identifierName, semanticData);
//        return null;
//    }
}
