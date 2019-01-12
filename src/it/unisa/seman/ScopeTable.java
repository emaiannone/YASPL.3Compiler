package it.unisa.seman;

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

    @Override
    public String toString() {
        return "ScopeTable{" +
                "table=" + table +
                '}';
    }
}
