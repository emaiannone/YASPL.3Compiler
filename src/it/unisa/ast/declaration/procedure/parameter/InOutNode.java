package it.unisa.ast.declaration.procedure.parameter;

public class InOutNode extends ParTypeNode {
    public static final String INOUT = "inout";

    public InOutNode() {
        super(INOUT);
    }

    public String getXMLTag() {
        return "InOutNode";
    }
}
