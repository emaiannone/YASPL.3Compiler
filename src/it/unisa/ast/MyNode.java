package it.unisa.ast;

import com.scalified.tree.multinode.ArrayMultiTreeNode;
import it.unisa.visitor.MyVisitor;

public abstract class MyNode extends ArrayMultiTreeNode<Object> {
    private String type;

    public MyNode(Object data) {
        super(data);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public abstract String getXMLTag();
}
