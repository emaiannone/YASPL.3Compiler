package it.unisa.ast.declaration.procedure.parameter;

public class InNode extends ParTypeNode {
    public static final String IN = "in";

    public InNode() {
        super(IN);
    }

    public String getXMLTag() {
        return "InNode";
    }
}
