package it.unisa.visitor.semantic.type;

import it.unisa.ast.expression.constant.*;
import it.unisa.visitor.semantic.SemanticChecker;

/*
Type Check D.
    Se il nodo è legato ad una costante (int_const, true, etc.) allora
        node.type = tipo dato dalla costante
Type Check E.
    Se il nodo è legato ad un costrutto riguardante operatori di espressioni o istruzioni (qui bisogna
    fare riferimento alle regole del Type System di sotto) allora
    controlla se i tipi dei nodi figli rispettano le specifiche del type system
    Se il controllo ha avuto successo allora assegna al nodo il tipo indicato nel type system
    altrimenti
    restituisci “errore di tipo” [NB: in caso di successo nulla viene inserito nella
    tabella dei simboli, solo il nodo dell'albero viene aggiornato con il tipo]
 */

public class TypeChecker extends SemanticChecker {
    private static final String INTEGER = "int";
    private static final String DOUBLE = "double";
    private static final String STRING = "string";
    private static final String CHAR = "char";
    private static final String BOOL = "bool";

    //Type Check D
    public void setType(IntegerConstantNode n) {
        n.setType(INTEGER);
    }

    //Type Check D
    public void setType(DoubleConstantNode n) {
        n.setType(DOUBLE);
    }

    //Type Check D
    public void setType(StringConstantNode n) {
        n.setType(STRING);
    }

    //Type Check D
    public void setType(CharConstantNode n) {
        n.setType(CHAR);
    }

    //Type Check D
    public void setType(BoolConstantNode n) {
        n.setType(BOOL);
    }

    //TODO Type Check dei nodi espressione e nodi statement (che si avvalgono della symbol table)
        //TODO All'entrata di uno scope (ProgrammaNode e ProcedureDeclarationNode) attivare/disattivare il relativo scope
        //TODO Usare lookup per ottenere il tipo di dato (che deve starci memorizzato nella tabella dei simboli per recupero veloce)
}
