package it.unisa.ast.type;

public class IntegerNode extends TypeNode {
    public static final String INTEGER = "int";

    public IntegerNode() {
        super(INTEGER);
    }

    public String getXMLTag() {
        return "IntegerNode";
    }
}
