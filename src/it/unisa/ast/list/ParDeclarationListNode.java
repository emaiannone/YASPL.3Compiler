package it.unisa.ast.list;

import it.unisa.visitor.MyVisitor;

public class ParDeclarationListNode extends ListNode {

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }

    public String getXMLTag() {
        return "ParDeclarationListNode";
    }
}
