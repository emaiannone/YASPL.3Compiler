package it.unisa.visitor.semantic.type;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.type.*;
import it.unisa.visitor.semantic.SemanticChecker;
import it.unisa.visitor.semantic.SemanticData;
import it.unisa.visitor.semantic.SymbolTable;

import java.util.ArrayList;

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

class TypeChecker extends SemanticChecker {

    //Type Check D
    void assignType(IntegerConstantNode n) {
        n.setType(IntegerNode.INTEGER);
    }

    //Type Check D
    void assignType(DoubleConstantNode n) {
        n.setType(DoubleNode.DOUBLE);
    }

    //Type Check D
    void assignType(StringConstantNode n) {
        n.setType(StringNode.STRING);
    }

    //Type Check D
    void assignType(CharConstantNode n) {
        n.setType(CharacterNode.CHARACTER);
    }

    //Type Check D
    void assignType(BoolConstantNode n) {
        n.setType(BooleanNode.BOOLEAN);
    }

    // Partial Check E
    void assignType(IdentifierNode n) {
        SymbolTable st = getSymbolTable();
        SemanticData semanticData = st.lookup((String) n.data());
        if (semanticData != null) {
            n.setType(semanticData.getType());
        }
        //TODO: Else: Devo mettere il tipo void alle variabili fuori la symbol table?
    }

    ArrayList<String> checkType(ExpressionNode n) {
        ArrayList<String> errors = new ArrayList<>();

        //TODO Controlla se è ArithOpNode, BoolOpNode, RelOpNode, UnaryOpNode
        System.out.println(n);
        SymbolTable st = getSymbolTable();
        return errors;
    }

    //TODO Type Check dei nodi espressione e nodi statement (che si avvalgono della symbol table)
    //1. Viene lanciato il visitatore
    //2. All'entrata di uno scope (ProgrammaNode e ProcedureDeclarationNode) e attivare il relativo scope
    //3. Visitare i figli
    //4. Se si trova un nodo ExpressionNode, lanciare typeCheck()
    //TODO 4.1 Il typeCheck() recupera tutte le sotto espressioni
    //TODO 4.2 Recupera i tipi dei nodi costanti col loro getType()
    //TODO 4.3 Recupera i tipi dei nodi identificatori con la lookup() della tabella dei simboli
    //TODO 4.4 Segue le regole del Type System per verificare la correttezza
    //TODO 4.5 Se tutto okay, settare il tipo, altrimenti inserire errore
    //TODO Se si trova un nodo StatementNode, lanciare typeCheck()
    //0. Disattivare lo scope all'uscita
}
