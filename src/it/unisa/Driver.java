package it.unisa;

import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.lexer.Yylex;
import it.unisa.parser.Parser;
import it.unisa.parser.ParserSym;
import it.unisa.visitor.semantic.scope.ScopeCheckingVisitor;
import it.unisa.visitor.semantic.scope.SymbolTable;
import it.unisa.visitor.xml.XMLMyVisitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unchecked")
public class Driver {
    public static void main(String[] args) {
        //TODO Migliorare gestione errori del parser e dello scanning
        try {
            System.out.println("\nParsing... ");
            ProgrammaNode root = parse("programmaErroriScope.yaspl");
            System.out.println("Parsing successful!");
            writeToFile("AST.xml", getXML(root));

            System.out.println("\nStarting scope checking...");
            SymbolTable symbolTable = new SymbolTable();
            ScopeCheckingVisitor scv = new ScopeCheckingVisitor(symbolTable);
            ArrayList<String> errors = (ArrayList<String>) root.accept(scv);
            if (errors.isEmpty()) {
                System.out.println("Scope checking successful!");
            } else {
                System.out.println("Scope checking failed:");
                errors.removeAll(Collections.singleton(null));  // Discards the null elements
                for (String e : errors) {
                    System.out.println('\t' + e);
                }
            }

            //TODO Type checking

        } catch (FileNotFoundException e) {
            System.out.println("Writing to XML file error.");
        } catch (Exception e) {
            System.out.println("Parsing error.");
        }
    }

    private static String getTokenStream(String fileName) throws IOException {
        Yylex yylex = new Yylex(new FileReader("src/testPrograms/" + fileName));
        int s;
        StringBuilder string = new StringBuilder();
        do {
            s = yylex.next_token().sym;
            string.append(ParserSym.terminalNames[s]);
        }
        while (s != 0);
        return String.valueOf(string);
    }

    private static ProgrammaNode parse(String fileName) throws Exception {
        Yylex yylex = new Yylex(new FileReader("src/testPrograms/" + fileName));

        Parser p = new Parser(yylex);

        return (ProgrammaNode) p.parse().value;
    }

    private static String getXML(ProgrammaNode root) {
        XMLMyVisitor v = new XMLMyVisitor();

        return (String) root.accept(v);
    }

    private static void writeToFile(String fileName, String content) throws FileNotFoundException {
        PrintWriter xmlAST = new PrintWriter("src/ASTs/" + fileName);
        xmlAST.print(content);
        xmlAST.close();
    }
}
