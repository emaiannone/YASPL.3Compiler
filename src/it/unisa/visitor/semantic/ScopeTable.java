package it.unisa.visitor.semantic;

import it.unisa.visitor.semantic.scope.SemanticData;

import java.util.HashMap;

// This table is attatched to ProgrammaNode and ProcedureDeclarationNode
public class ScopeTable {
    private HashMap<String, SemanticData> table;

    public ScopeTable() {
        table = new HashMap<>();
    }

    public HashMap<String, SemanticData> getTable() {
        return table;
    }
}
