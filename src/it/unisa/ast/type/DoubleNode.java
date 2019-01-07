package it.unisa.ast.type;

public class DoubleNode extends TypeNode {
    public static final String DOUBLE = "double";

    public DoubleNode() {
        super(DOUBLE);
    }

    public String getXMLTag() {
        return "DoubleNode";
    }
}
