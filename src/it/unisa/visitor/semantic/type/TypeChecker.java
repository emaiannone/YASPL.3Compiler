package it.unisa.visitor.semantic.type;

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
    //TODO Type Check dei nodi costanti (easy)

    //TODO Type Check dei nodi espressione e nodi statement (che si avvalgono della symbol table)
        //TODO All'entrata di uno scope (ProgrammaNode e ProcedureDeclarationNode) attivare/disattivare il relativo scope
        //TODO Usare lookup per ottenere il tipo di dato (che deve starci memorizzato nella tabella dei simboli per recupero veloce)

}
