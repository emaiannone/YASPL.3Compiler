package it.unisa.seman.typing;

import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.InNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.statement.AssignOpNode;
import it.unisa.ast.statement.CallOpNode;
import it.unisa.ast.statement.ReadOpNode;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
import it.unisa.ast.type.*;
import it.unisa.seman.ParameterData;
import it.unisa.seman.SemanticChecker;
import it.unisa.seman.SemanticData;

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

public class TypeChecker extends SemanticChecker {
    public static final String TYPE_MISMATCH_EXPRESSION = "Type mismatch on expression: ";
    public static final String TYPE_MISMATCH_ASSIGN = "Type mismatch on assign: ";
    public static final String TYPE_MISMATCH_CONDITIONAL = "Type mismatch on condition: it cannot be a ";
    public static final String NUMBER_MISMATCH_PARAMETER = "Number of parameters on procedure call is invalid";
    public static final String TYPE_MISMATCH_CALL = "Type mismatch on procedure call: ";
    public static final String TYPE_MISMATCH_PARAMETER = "Cannot pass a non identifier to an output parameter on procedure call ";
    public static final String TYPE_MISMATCH_READ = "Cannot read a non identifier ";
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
        SemanticData semanticData = getSymbolTable().lookup((String) n.data());
        if (semanticData != null) {
            n.setType(semanticData.getType());
        } else {
            n.setType(null);
        }
    }

    // TODO Farsi passare dal visitor una stringa riportante il nome dell'operazione?
    // Partial Type Check E. It checks the two children types or the first child type, according to the number of children
    public String assignType(ExpressionNode n, int[][] typeTable) {
        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
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
                return NO_TYPE;
            }
            column = TypeSystem.stringToInt(secondType);
        }
        String type = TypeSystem.intToString(typeTable[row][column]);
        if (type == null) {
            if (secondType != null) {
                return TYPE_MISMATCH_EXPRESSION + firstType + " and " + secondType + " are incompatible with this operation";
            } else {
                return TYPE_MISMATCH_EXPRESSION + firstType + " is incompatible with this operation";
            }
        }
        n.setType(type);
        return null;
    }

    public String assignType(AssignOpNode n) {
        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
            return NO_TYPE;
        }
        ExpressionNode secondChild = (ExpressionNode) n.getChild(1);
        String secondType = secondChild.getType();
        if (secondType == null) {
            return NO_TYPE;
        }
        int row = TypeSystem.stringToInt(firstType);
        int column = TypeSystem.stringToInt(secondType);
        String type = TypeSystem.intToString(TypeSystem.assignTable[row][column]);
        if (type == null) {
            return TYPE_MISMATCH_ASSIGN + secondType + " cannot be assigned to a " + firstType;
        }
        n.setType(type);
        return null;
    }

    public String assignType(ConditionalStatementNode n) {
        ExpressionNode firstChild = (ExpressionNode) n.getChild(0);
        String firstType = firstChild.getType();
        if (firstType == null) {
            return NO_TYPE;
        }
        int row = TypeSystem.stringToInt(firstType);
        String type = TypeSystem.intToString(TypeSystem.conditionalTable[row][0]);
        if (type == null) {
            return TYPE_MISMATCH_CONDITIONAL + firstType;
        }
        n.setType(type);
        return null;
    }

    public ArrayList<String> assignType(CallOpNode n) {
        ArrayList<String> errors = new ArrayList<>();

        ArgsNode actualParameters = (ArgsNode) n.getChild(1);
        String procedureName = (String) n.getChild(0).data();
        ArrayList<ParameterData> formalParameters = getSymbolTable().lookup(procedureName).getParameterList();

        // Parameter number check
        int formalNumber;
        if (formalParameters == null) {
            formalNumber = 0;
        } else {
            formalNumber = formalParameters.size();
        }
        int actualNumber;
        if (actualParameters == null) {
            actualNumber = 0;
        } else {
            actualNumber = actualParameters.childrenNumber();
        }
        if (formalNumber != actualNumber) {
            errors.add(NUMBER_MISMATCH_PARAMETER);
        } else {
            for (int i = 0; i < formalNumber; i++) {
                ParameterData formalPar = formalParameters.get(i);
                ExpressionNode actualPar = (ExpressionNode) actualParameters.getChild(i);

                // Type check that reuses the assignTable
                int row = TypeSystem.stringToInt(formalPar.getType());
                int column = TypeSystem.stringToInt(actualPar.getType());
                String type = TypeSystem.intToString(TypeSystem.assignTable[row][column]);
                if (type == null) {
                    errors.add(TYPE_MISMATCH_CALL + actualPar.getType() + " cannot be assigned to a " + formalPar.getType());
                } else {
                    // ParType check. We have error if the formal par is out or inout and actual is a non identifier
                    if (!formalPar.getParType().equals(InNode.IN) && !(actualPar instanceof IdentifierNode)) {
                        errors.add(TYPE_MISMATCH_PARAMETER);
                    }
                }
            }
        }
        if (errors.isEmpty()) {
            n.setType(TypeNode.VOID);
        }
        return errors;
    }

    public ArrayList<String> assignType(ReadOpNode n) {
        ArrayList<String> errors = new ArrayList<>();

        ArgsNode varList = (ArgsNode) n.getChild(0);
        for (int i = 0; i < varList.childrenNumber(); i++) {
            ExpressionNode var = (ExpressionNode) varList.getChild(i);
            if (var instanceof IdentifierNode) {
                String kind = getSymbolTable().lookup((String) var.data()).getKind();
                if (kind.equals(ProcedureDeclarationNode.PROCEDURE)) {
                    errors.add(TYPE_MISMATCH_READ);
                }
            } else {
                errors.add(TYPE_MISMATCH_READ);
            }
        }
        if (errors.isEmpty()) {
            n.setType(TypeNode.VOID);
        }
        return errors;
    }
}
