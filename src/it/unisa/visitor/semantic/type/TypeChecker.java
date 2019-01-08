package it.unisa.visitor.semantic.type;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.OpNode;
import it.unisa.ast.type.*;
import it.unisa.visitor.semantic.SemanticChecker;
import it.unisa.visitor.semantic.SemanticData;
import it.unisa.visitor.semantic.SymbolTable;

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

@SuppressWarnings("unchecked")
class TypeChecker extends SemanticChecker {
    public static final String TYPE_MISMATCH_EXPRESSION = "Type mismatch: ";
    public static final String NO_TYPE = "Cannot apply type checking on identifiers without a declared type";

    private TypeSystem typeSystem;

    public TypeChecker() {
        typeSystem = new TypeSystem();
    }

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
    }

    // Type Check E
    String checkType(OpNode n, int tableNumber) {
        String error = null;

        System.out.println(n);
        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        ExpressionNode secondChild = (ExpressionNode) n.getChild(1);
        String firstType = firstChild.getType();
        String secondType = secondChild.getType();
        if (firstType.equals("") || secondType.equals("")) {
            error = NO_TYPE;
            System.out.println("Non riuscito: " + error);
        } else {
            String type = typeSystem.checkTable(tableNumber, firstType, secondType);
            if (type != null && !type.equals("")) {
                n.setType(type);
                System.out.println("Riuscito: " + type);
            } else {
                error = TYPE_MISMATCH_EXPRESSION + "cannot add a " + firstType + " with a " + secondType;
                System.out.println("Non riuscito: " + error);
            }
        }
        return error;
    }

    //1. Viene lanciato il visitatore
    //2. All'entrata di uno scope (ProgrammaNode e ProcedureDeclarationNode) e attivare il relativo scope
    //3. Visitare i figli
    //4. Se si trova un nodo OpNode, lanciare typeCheck()
    //4.1 Recupera i tipi dei nodi figli nodi con i getType()
    //4.2 Segue le regole del Type System per verificare la correttezza
    //4.3 Se tutto okay, settare il tipo, altrimenti inserire errore
    //TODO Se si trova un nodo StatementNode, lanciare typeCheck() e gestire...
    //TODO In particolare il fatto di ReadOpNode che controlla se la lista di ArgsNode siano solo IdentifierNode e non ConstantNode o OpNode
    //0. Disattivare lo scope all'uscita
}
