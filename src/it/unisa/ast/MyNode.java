package it.unisa.ast;

import com.scalified.tree.multinode.ArrayMultiTreeNode;
import it.unisa.visitor.MyVisitor;

import java.util.Iterator;
import java.util.LinkedHashSet;

@SuppressWarnings("unchecked")
public abstract class MyNode extends ArrayMultiTreeNode<Object> {
    public MyNode(Object data) {
        super(data);
    }

    public LinkedHashSet<MyNode> children() {
        if (subtrees().isEmpty()) {
            return null;
        }
        return (LinkedHashSet<MyNode>) this.subtrees();
    }

    public boolean hasChildren() {
        return children() != null;
    }

    public int childrenNumber() {
        if (hasChildren()) {
            return children().size();
        }
        return 0;
    }

    public MyNode getChild(int n) {
        if (n < 0 || n >= childrenNumber()) {
            return null;
        }
        LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) this.subtrees();
        Iterator<MyNode> iterator = children.iterator();
        MyNode child = null;
        for (int i = 0; i < n + 1; i++) {
            child = iterator.next();
        }
        return child;
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
