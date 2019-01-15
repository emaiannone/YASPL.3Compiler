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
import it.unisa.ast.statement.WriteOpNode;
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
    private static final String NUMBER_MISMATCH_CALL = "The number of parameters in the procedure call is not valid";
    private static final String TYPE_MISMATCH_READ = "Expressions and constants cannot be used as read argument";
    private static final String NO_TYPE = "Type checking cannot be applied to nodes without a type";

    private String buildBinaryOpMismatch(String firstType, String secondType) {
        return "Incompatibility between " + firstType + " and " + secondType + " with this operation";
    }

    private String buildUnaryOpMismatch(String type) {
        return "Incompatibility of " + type + " with this operation";
    }

    private String buildAssignMismatch(String firstType, String secondType) {
        return "A " + secondType + " cannot be assigned to a " + firstType;
    }

    private String buildConditionalMismatch(String type) {
        return "The condition cannot be " + type;
    }

    private String buildCallMismatch(String firstType, String secondType) {
        return "A " + secondType + " parameter cannot be passed to a " + firstType + " parameter";
    }

    private String buildCallIllegalParameter(String procedureName) {
        return "The procedure name " + procedureName + " cannot be passed used as actual parameter";
    }

    private String buildCallKindViolation(String expressionType) {
        return "An " + expressionType + " expression cannot be passed to an output parameter";
    }

    private String buildReadIllegalProcedure(String procedureName) {
        return "The procedure name " + procedureName + " cannot be used as a read argument";
    }

    private String buildWriteIllegalProcedure(String procedureName) {
        return "The procedure name " + procedureName + " cannot be used as a write argument";
    }

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
                return buildBinaryOpMismatch(firstType, secondType);
            } else {
                return buildUnaryOpMismatch(firstType);
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
            return buildAssignMismatch(firstType, secondType);
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
            return buildConditionalMismatch(firstType);
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
            errors.add(NUMBER_MISMATCH_CALL);
        } else {
            for (int i = 0; i < formalNumber; i++) {
                ParameterData formalPar = formalParameters.get(i);
                ExpressionNode actualPar = (ExpressionNode) actualParameters.getChild(i);

                // Type check that reuses the assignTable
                int row = TypeSystem.stringToInt(formalPar.getType());
                int column = TypeSystem.stringToInt(actualPar.getType());
                if (column != TypeSystem.ERROR) {
                    String type = TypeSystem.intToString(TypeSystem.assignTable[row][column]);
                    if (type == null) {
                        errors.add(buildCallMismatch(formalPar.getType(), actualPar.getType()));
                    } else {
                        // ParType check. We have error if the formal par is out or inout and actual is a non identifier
                        if (!formalPar.getParType().equals(InNode.IN) && !(actualPar instanceof IdentifierNode)) {
                            errors.add(buildCallKindViolation(actualPar.getType()));
                        }
                    }
                } else {
                    errors.add(buildCallIllegalParameter((String) actualPar.data()));
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
            ExpressionNode arg = (ExpressionNode) varList.getChild(i);
            if (arg instanceof IdentifierNode) {
                String kind = getSymbolTable().lookup((String) arg.data()).getKind();
                if (kind.equals(ProcedureDeclarationNode.PROCEDURE)) {
                    errors.add(buildReadIllegalProcedure((String) arg.data()));
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

    public ArrayList<String> assignType(WriteOpNode n) {
        ArrayList<String> errors = new ArrayList<>();

        ArgsNode exprList = (ArgsNode) n.getChild(0);
        for (int i = 0; i < exprList.childrenNumber(); i++) {
            ExpressionNode expr = (ExpressionNode) exprList.getChild(i);
            if (expr.data() != null) {
                SemanticData exprData = getSymbolTable().lookup(expr.data().toString());
                if (exprData != null) {
                    String kind = exprData.getKind();
                    if (kind.equals(ProcedureDeclarationNode.PROCEDURE)) {
                        errors.add(buildWriteIllegalProcedure((String) expr.data()));
                    }
                }
            }
        }
        if (errors.isEmpty()) {
            n.setType(TypeNode.VOID);
        }
        return errors;
    }
}
