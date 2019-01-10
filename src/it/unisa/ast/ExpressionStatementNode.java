package it.unisa.ast;

public abstract class ExpressionStatementNode extends MyNode {
    private String type;

    public ExpressionStatementNode(Object data) {
        super(data);
        type = null;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getXMLTag() {
        return "ExpressionStatementNode";
    }

    @Override
    public String toString() {
        return super.toString() + "Type: " + type;
    }
}
