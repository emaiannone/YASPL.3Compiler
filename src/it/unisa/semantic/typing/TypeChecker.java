package it.unisa.semantic.typing;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
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
    public static final String TYPE_MISMATCH_EXPRESSION = "Type mismatch on expression: ";
    public static final String TYPE_MISMATCH_ASSIGN = "Type mismatch on assign: ";
    public static final String TYPE_MISMATCH_CONDITIONAL = "Type mismatch on condition: it cannot be a ";
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

    // TODO Farsi passare dal visitor una stringa riportante il nome dell'operazione?
    // Partial Type Check E. It checks the two children types or the first child type, according to the number of children
    public String assignType(ExpressionNode n, int[][] typeTable) {
        //TODO Remove
        System.out.println(n);

        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
            //TODO Remove
            System.out.println("Error: " + NO_TYPE);
            return NO_TYPE;
        }
        int row = TypeSystem.stringToInt(firstType);
        int column = 0; // Ready for unary op as default
        ExpressionNode secondChild = (ExpressionNode) n.getChild(1);
        String secondType = null;
        if (secondChild != null) {
            // Executed for Binary Operations
            secondType = secondChild.getType();
            if (secondType == null) {
                //TODO Remove
                System.out.println("Error: " + NO_TYPE);
                return NO_TYPE;
            }
            column = TypeSystem.stringToInt(secondType);
        }
        String type = TypeSystem.intToString(typeTable[row][column]);
        if (type == null) {
            if (secondType != null) {
                //TODO Remove
                System.out.println(TYPE_MISMATCH_EXPRESSION + firstType + " and " + secondType + " are incompatible with this operation");
                return TYPE_MISMATCH_EXPRESSION + firstType + " and " + secondType + " are incompatible with this operation";
            } else {
                //TODO Remove
                System.out.println(TYPE_MISMATCH_EXPRESSION + firstType + " is incompatible with this operation");
                return TYPE_MISMATCH_EXPRESSION + firstType + " is incompatible with this operation";
            }
        }
        //TODO Remove
        System.out.println("Type set: " + type);
        n.setType(type);
        return null;
    }

    public String assignType(AssignOpNode n) {
        //TODO Remove
        System.out.println(n);

        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
            //TODO Remove
            System.out.println("Error: " + NO_TYPE);
            return NO_TYPE;
        }
        ExpressionNode secondChild = (ExpressionNode) n.getChild(1);
        String secondType = secondChild.getType();
        if (secondType == null) {
            //TODO Remove
            System.out.println("Error: " + NO_TYPE);
            return NO_TYPE;
        }
        int row = TypeSystem.stringToInt(firstType);
        int column = TypeSystem.stringToInt(secondType);
        String type = TypeSystem.intToString(TypeSystem.assignTable[row][column]);
        if (type == null) {
            //TODO Remove
            System.out.println(TYPE_MISMATCH_ASSIGN + secondType + " cannot be assigned to a " + firstType);
            return TYPE_MISMATCH_ASSIGN + secondType + " cannot be assigned to a " + firstType;
        }
        //TODO Remove
        System.out.println("Type set: " + type);
        n.setType(type);
        return null;
    }

    public String assignType(ConditionalStatementNode n) {
        //TODO Remove
        System.out.println(n);

        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
            //TODO Remove
            System.out.println("Error: " + NO_TYPE);
            return NO_TYPE;
        }
        int row = TypeSystem.stringToInt(firstType);
        String type = TypeSystem.intToString(TypeSystem.conditionalTable[row][0]);
        if (type == null) {
            //TODO Remove
            System.out.println(TYPE_MISMATCH_CONDITIONAL + firstType);
            return TYPE_MISMATCH_CONDITIONAL + firstType;
        }
        //TODO Remove
        System.out.println("Type set: " + type);
        n.setType(type);
        return null;
    }

    //TODO In particolare il fatto di ReadOpNode che controlla se la lista di ArgsNode siano solo IdentifierNode e non altri fratelli
    //TODO CallOp: check correttezza parametri!!!!!! (consultare la tabella dei simboli?)
}
