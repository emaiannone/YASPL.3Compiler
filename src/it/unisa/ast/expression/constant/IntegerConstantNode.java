package it.unisa.ast.expression.constant;

public class IntegerConstantNode extends ConstantNode {
    public IntegerConstantNode(Integer data) {
        super(data);
    }

    public String getXMLTag() {
        return "IntegerConstantNode";
    }
}
