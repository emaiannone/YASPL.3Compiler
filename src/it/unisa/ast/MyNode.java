package it.unisa.ast;

import com.scalified.tree.multinode.ArrayMultiTreeNode;
import it.unisa.visitor.MyVisitor;

public abstract class MyNode extends ArrayMultiTreeNode<Object> {
    public MyNode(Object data) {
        super(data);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public abstract String getXMLTag();

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
