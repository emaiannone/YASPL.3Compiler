package it.unisa.ast.declaration.procedure.parameter;

public class OutNode extends ParTypeNode {
    public static final String OUT = "out";

    public OutNode() {
        super(OUT);
    }

    public String getXMLTag() {
        return "OutNode";
    }
}
