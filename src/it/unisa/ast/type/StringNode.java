package it.unisa.ast.type;

public class StringNode extends TypeNode {
    public static final String STRING = "string";

    public StringNode() {
        super(STRING);
    }

    public String getXMLTag() {
        return "StringNode";
    }
}
