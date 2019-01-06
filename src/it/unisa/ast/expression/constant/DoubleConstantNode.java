package it.unisa.ast.expression.constant;

public class DoubleConstantNode extends ConstantNode {
    public DoubleConstantNode(Double data) {
        super(data);
    }

    public String getXMLTag() {
        return "DoubleConstantNode";
    }
}
