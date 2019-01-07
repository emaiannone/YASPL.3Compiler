package it.unisa.visitor.xml;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.ParDeclarationNode;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.OpNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.StatementNode;
import it.unisa.ast.type.TypeNode;
import it.unisa.visitor.MyVisitor;

import java.util.LinkedHashSet;

// Visitor that is able to convert the tree of MyNode into an XML format
@SuppressWarnings("unchecked")
public class XMLMyVisitor implements MyVisitor {
    @Override
    public Object visit(MyNode n) {
        StringBuilder xml = new StringBuilder();
        xml.append(openTag(n.getXMLTag()));

        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
                xml.append(c.accept(this));
            }
        }

        xml.append(closeTag(n.getXMLTag()));
        return xml.toString();
    }

    @Override
    public Object visit(ConstantNode n) {
        StringBuilder xml = new StringBuilder();
        xml.append(openTag(n.getXMLTag()));
        // Removes the < in favor of &lt;
        if (n.data() != null) {
            String wrongString = n.data().toString();
            String correctString = wrongString.replaceAll("<", "&lt;");
            xml.append(correctString);
        }

        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
                xml.append(c.accept(this));
            }
        }

        xml.append(closeTag(n.getXMLTag()));
        return xml.toString();
    }

    @Override
    public Object visit(IdentifierNode n) {
        StringBuilder xml = new StringBuilder();
        xml.append(openTag(n.getXMLTag()));
        xml.append(n.data());

        if (!n.subtrees().isEmpty()) {
            LinkedHashSet<MyNode> children = (LinkedHashSet<MyNode>) n.subtrees();
            for (MyNode c : children) {
                xml.append(c.accept(this));
            }
        }

        xml.append(closeTag(n.getXMLTag()));
        return xml.toString();
    }

    @Override
    public Object visit(IntegerConstantNode n) {
        return visit((ConstantNode) n);
    }

    @Override
    public Object visit(DoubleConstantNode n) {
        return visit((ConstantNode) n);
    }

    @Override
    public Object visit(StringConstantNode n) {
        return visit((ConstantNode) n);
    }

    @Override
    public Object visit(CharConstantNode n) {
        return visit((ConstantNode) n);
    }

    @Override
    public Object visit(BoolConstantNode n) {
        return visit((ConstantNode) n);
    }

    // Reuse the MyNode visit
    @Override
    public Object visit(ProgrammaNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(DeclarationNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(ProcedureDeclarationNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(VarDeclarationNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(ParDeclarationNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(VarInitListNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(VarInitNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(StatementNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(ArgsNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(ExpressionNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(OpNode n) {
        return visit((MyNode) n);
    }

    @Override
    public Object visit(TypeNode n) {
        return visit((MyNode) n);
    }

    private String openTag(String tag) {
        return "<" + tag + ">";
    }

    private String closeTag(String tag) {
        return "</" + tag + ">";
    }
}
