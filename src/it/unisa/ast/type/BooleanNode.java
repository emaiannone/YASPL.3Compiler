package it.unisa.ast.type;

public class BooleanNode extends TypeNode {
    public static final String BOOLEAN = "bool";

    public BooleanNode() {
        super(BOOLEAN);
    }

    public String getXMLTag() {
        return "BooleanNode";
    }
}
