package it.unisa.semantic.typing;

import it.unisa.ast.ExpressionStatementNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.type.*;
import it.unisa.semantic.SemanticChecker;
import it.unisa.semantic.SemanticData;
import it.unisa.semantic.SymbolTable;

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
    public static final String TYPE_MISMATCH_EXPRESSION = "Type mismatch: ";
    public static final String NO_TYPE = "Cannot apply type checking on nodes without a type";

    //Type Check D
    public void assignType(IntegerConstantNode n) {
        n.setType(IntegerNode.INTEGER);
    }

    //Type Check D
    public void assignType(DoubleConstantNode n) {
        n.setType(DoubleNode.DOUBLE);
    }

    //Type Check D
    public void assignType(StringConstantNode n) {
        n.setType(StringNode.STRING);
    }

    //Type Check D
    public void assignType(CharConstantNode n) {
        n.setType(CharacterNode.CHARACTER);
    }

    //Type Check D
    public void assignType(BoolConstantNode n) {
        n.setType(BooleanNode.BOOLEAN);
    }

    // Partial Check E
    public void assignType(IdentifierNode n) {
        SymbolTable st = getSymbolTable();
        SemanticData semanticData = st.lookup((String) n.data());
        if (semanticData != null) {
            n.setType(semanticData.getType());
        } else {
            n.setType(null);
        }
    }

    // TODO Improve code quality
    // TODO Farsi passare dal visitor una stringa riportante il nome dell'operazione/statement?
    // Partial Type Check E. It checks the two children types or the first child type, according to the number of children
    public String assignType(ExpressionStatementNode n, int[][] typeTable) {
        String error = null;

        //TODO Remove
        System.out.println(n);

        ExpressionStatementNode firstChild = (ExpressionStatementNode) n.getChild(0);
        String firstType = firstChild.getType();
        String secondType = null;
        if (firstType == null) {
            //TODO Remove
            System.out.println("Error: " + NO_TYPE);
            return NO_TYPE;
        } else {
            int row = TypeSystem.stringToInt(firstType);
            int entry;
            String type;

            ExpressionStatementNode secondChild = (ExpressionStatementNode) n.getChild(1);
            if (secondChild != null) {
                // Binary Operations
                secondType = secondChild.getType();
                if (secondType == null) {
                    System.out.println(NO_TYPE);
                    return NO_TYPE;
                } else {
                    int column = TypeSystem.stringToInt(secondType);
                    entry = typeTable[row][column];
                }
            } else {
                // Unary Operations
                entry = typeTable[row][0];
            }
            type = TypeSystem.intToString(entry);
            n.setType(type);
            //TODO Remove
            System.out.println("Type set: " + type);
            if (type == null) {
                if (secondType != null) {
                    error = TYPE_MISMATCH_EXPRESSION + firstType + " and " + secondType + " are incompatible with this operation";
                } else {
                    error = TYPE_MISMATCH_EXPRESSION + firstType + " is incompatible with this operation";
                }
                //TODO Remove
                System.out.println(error);
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
