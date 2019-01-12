package it.unisa.semantic.scoping;

import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParTypeNode;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.type.TypeNode;
import it.unisa.semantic.ParameterData;
import it.unisa.semantic.SemanticChecker;
import it.unisa.semantic.SemanticData;
import it.unisa.visitor.semantic.scope.identifier.IdentifierDeclarationVisitor;
import it.unisa.visitor.semantic.scope.identifier.IdentifierVisitor;
import it.unisa.visitor.semantic.scope.parameter.FormalParameterVisitor;
import it.unisa.visitor.semantic.scope.type.TypeDeclarationVisitor;

import java.util.ArrayList;

/*
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
public class ScopeChecker extends SemanticChecker {
    public static final String MULTIPLE_DECLARATION = "Multiple declaration of the identifier: ";
    public static final String NO_SCOPE = "No active scope";
    public static final String NO_DECLARATION = "No declaration of the identifier: ";

    /**
     * Check for multiple declaration of an identifier in the same scope.
     * It also adds the declaration to the active scope table.
     *
     * @param n The <i>DeclarationNode</i> object under exam.
     * @return An <i>ArrayList</i> of <i>String</i> containing the multiple declarations error messages. It is empty if everything is okay.
     */
    //Scope Check B
    public ArrayList<String> checkMultipleDeclarations(DeclarationNode n) {
        ArrayList<String> errors = new ArrayList<>();
        if (!getSymbolTable().empty()) {
            IdentifierDeclarationVisitor idv = new IdentifierDeclarationVisitor();
            ArrayList<IdentifierNode> identifiers = (ArrayList<IdentifierNode>) n.accept(idv);
            if (identifiers != null && !identifiers.isEmpty()) {
                for (IdentifierNode i : identifiers) {
                    String identifierName = (String) i.data();
                    if (getSymbolTable().probe(identifierName)) {
                        errors.add(MULTIPLE_DECLARATION + identifierName);
                    } else {
                        SemanticData semanticData = new SemanticData();
                        semanticData.setIdentifier(identifierName);
                        semanticData.setKind(n.getKind());
                        // Look for eventual type
                        TypeDeclarationVisitor tv = new TypeDeclarationVisitor();
                        ArrayList<TypeNode> types = (ArrayList<TypeNode>) n.accept(tv);
                        if (types != null && !types.isEmpty()) {
                            TypeNode typeNode = types.get(0);
                            semanticData.setType(typeNode.getType());
                        } else {
                            // This is a procedure
                            semanticData.setType(null);

                            // Fetch the formal paramater data
                            FormalParameterVisitor fpv = new FormalParameterVisitor();
                            ArrayList<ParDeclarationNode> pars = (ArrayList<ParDeclarationNode>) n.accept(fpv);
                            if (pars != null && !pars.isEmpty()) {
                                ArrayList<ParameterData> parameterDataList = new ArrayList<>();
                                for (ParDeclarationNode t : pars) {
                                    ParTypeNode parTypeNode = (ParTypeNode) t.getChild(0);
                                    TypeNode typeNode = (TypeNode) t.getChild(1);

                                    ParameterData parameterData = new ParameterData();
                                    parameterData.setParType(parTypeNode.getType());
                                    parameterData.setType(typeNode.getType());
                                    parameterDataList.add(parameterData);
                                }
                                semanticData.setParameterList(parameterDataList);
                            }
                        }
                        getSymbolTable().getCurrentScope().getTable().put(identifierName, semanticData);
                    }
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
    //Scope Check C
    public ArrayList<String> checkUndeclarations(StatementNode n) {
        ArrayList<String> errors = new ArrayList<>();
        if (!getSymbolTable().empty()) {
            IdentifierVisitor iv = new IdentifierVisitor();
            ArrayList<IdentifierNode> identifiers = (ArrayList<IdentifierNode>) n.accept(iv);
            if (identifiers != null && !identifiers.isEmpty()) {
                for (IdentifierNode i : identifiers) {
                    String identifierName = (String) i.data();
                    SemanticData semanticData = getSymbolTable().lookup(identifierName);
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
}
