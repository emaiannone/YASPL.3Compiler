package it.unisa.visitor;

import it.unisa.ast.MyNode;
import it.unisa.ast.args.ArgsNode;
import it.unisa.ast.declaration.DeclarationNode;
import it.unisa.ast.declaration.procedure.ProcedureDeclarationNode;
import it.unisa.ast.declaration.procedure.parameter.*;
import it.unisa.ast.declaration.variable.VarDeclarationNode;
import it.unisa.ast.expression.ExpressionNode;
import it.unisa.ast.expression.constant.*;
import it.unisa.ast.expression.identifier.IdentifierNode;
import it.unisa.ast.expression.operation.OpNode;
import it.unisa.ast.expression.operation.arithmetic.*;
import it.unisa.ast.expression.operation.bool.AndOpNode;
import it.unisa.ast.expression.operation.bool.BoolOpNode;
import it.unisa.ast.expression.operation.bool.OrOpNode;
import it.unisa.ast.expression.operation.relational.*;
import it.unisa.ast.expression.operation.unary.NotOpNode;
import it.unisa.ast.expression.operation.unary.UminusOpNode;
import it.unisa.ast.initialization.VarInitNode;
import it.unisa.ast.list.BodyNode;
import it.unisa.ast.list.ParDeclarationListNode;
import it.unisa.ast.list.StatementListNode;
import it.unisa.ast.list.VarInitListNode;
import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.ast.statement.*;
import it.unisa.ast.statement.conditional.ConditionalStatementNode;
import it.unisa.ast.statement.conditional.IfThenElseOpNode;
import it.unisa.ast.statement.conditional.IfThenOpNode;
import it.unisa.ast.statement.conditional.WhileOpNode;
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

    Object visit(TrueNode n);

    Object visit(FalseNode n);

    Object visit(IdentifierNode n);

    Object visit(OpNode n);

    Object visit(ArithOpNode n);

    Object visit(PlusOpNode n);

    Object visit(MinusOpNode n);

    Object visit(TimesOpNode n);

    Object visit(DivOpNode n);

    Object visit(BoolOpNode n);

    Object visit(AndOpNode n);

    Object visit(OrOpNode n);

    Object visit(RelOpNode n);

    Object visit(EQOpNode n);

    Object visit(GEOpNode n);

    Object visit(GTOpNode n);

    Object visit(LEOpNode n);

    Object visit(LTOpNode n);

    Object visit(UminusOpNode n);

    Object visit(NotOpNode n);

    Object visit(AssignOpNode n);

    Object visit(ConditionalStatementNode n);

    Object visit(IfThenOpNode n);

    Object visit(IfThenElseOpNode n);

    Object visit(WhileOpNode n);

    Object visit(CallOpNode n);

    Object visit(ReadOpNode n);

    Object visit(WriteOpNode n);

    Object visit(BodyNode n);

    Object visit(StatementListNode n);

    Object visit(TypeNode n);

    Object visit(IntegerNode n);

    Object visit(DoubleNode n);

    Object visit(CharacterNode n);

    Object visit(StringNode n);

    Object visit(BooleanNode n);

    Object visit(ParTypeNode n);

    Object visit(InNode n);

    Object visit(OutNode n);

    Object visit(InOutNode n);

    Object visit(ParDeclarationListNode n);
}
