package it.unisa.visitor;

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
import it.unisa.ast.type.*;

// Interface for the visitor that is able to visit the tree of concrete MyNode(s)
public interface MyVisitor {
    Object visit(MyNode n);

    Object visit(ProgrammaNode n);

    Object visit(DeclarationNode n);

    Object visit(ProcedureDeclarationNode n);

    Object visit(VarDeclarationNode n);

    Object visit(ParDeclarationNode n);

    Object visit(VarInitListNode n);

    Object visit(VarInitNode n);

    Object visit(StatementNode n);

    Object visit(ArgsNode n);

    Object visit(ExpressionNode n);

    Object visit(ConstantNode n);

    Object visit(IntegerConstantNode n);

    Object visit(DoubleConstantNode n);

    Object visit(StringConstantNode n);

    Object visit(CharConstantNode n);

    Object visit(BoolConstantNode n);

    Object visit(IdentifierNode n);

    Object visit(OpNode n);

    Object visit(TypeNode n);
}
