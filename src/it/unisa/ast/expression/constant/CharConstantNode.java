package it.unisa.ast.expression.constant;

public class CharConstantNode extends ConstantNode {
    public CharConstantNode(Character data) {
        super(data);
    }

    public String getXMLTag() {
        return "CharConstantNode";
    }
}
