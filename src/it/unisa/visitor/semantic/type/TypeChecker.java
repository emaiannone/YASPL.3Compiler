package it.unisa.visitor.semantic.type;

import it.unisa.ast.expression.constant.*;
import it.unisa.ast.type.*;
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

    //Type Check D
    public void setType(IntegerConstantNode n) {
        n.setType(IntegerNode.INTEGER);
    }

    //Type Check D
    public void setType(DoubleConstantNode n) {
        n.setType(DoubleNode.DOUBLE);
    }

    //Type Check D
    public void setType(StringConstantNode n) {
        n.setType(StringNode.STRING);
    }

    //Type Check D
    public void setType(CharConstantNode n) {
        n.setType(CharacterNode.CHARACTER);
    }

    //Type Check D
    public void setType(BoolConstantNode n) {
        n.setType(BooleanNode.BOOLEAN);
    }

    //TODO Type Check dei nodi espressione e nodi statement (che si avvalgono della symbol table)
        //1. Viene lanciato il visitatore
        //2. All'entrata di uno scope (ProgrammaNode e ProcedureDeclarationNode) e attivare il relativo scope
        //3. Visitare i figli
        //4. Se si trova un nodo ExpressionNode, lanciare typeCheck() passandogli tutta la tabella dei simboli attuale
            //4.1 Il typeCheck() recupera tutte le sotto espressioni
            //4.2 Recupera i tipi dei nodi costanti col loro getType()
            //4.3 Recupera i tipi dei nodi identificatori con la lookup() della tabella dei simboli
            //4.4 Segue le regole del Type System per verificare la correttezza
        //TODO 5. Se si trova un nodo StatementNode, lanciare typeCheck() passandogli tutta la tabella dei simboli attuale
        //6. Disattivare lo scope all'uscita
}
