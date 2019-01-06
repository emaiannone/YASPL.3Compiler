package it.unisa.ast.expression.constant;

public class StringConstantNode extends ConstantNode {
    public StringConstantNode(String data) {
        super(data);
    }

    public String getXMLTag() {
        return "StringConstantNode";
    }
}
