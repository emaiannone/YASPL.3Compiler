package it.unisa.ast.declaration.procedure.parameter;

import it.unisa.ast.MyNode;

public abstract class ParTypeNode extends MyNode {
    private String type;

    public ParTypeNode(String type) {
        super(null);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
