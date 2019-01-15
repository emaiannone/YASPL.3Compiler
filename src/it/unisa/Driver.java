package it.unisa;

import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.lexer.Yylex;
import it.unisa.parser.Parser;
import it.unisa.parser.ParserSym;
import it.unisa.visitor.generation.CGeneratorVisitor;
import it.unisa.visitor.semantic.scope.ScopeCheckingVisitor;
import it.unisa.visitor.semantic.type.TypeCheckingVisitor;
import it.unisa.visitor.xml.XMLVisitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@SuppressWarnings({"unchecked", "Duplicates"})
public class Driver {
    public static void main(String[] args) {
        String programName = args[0];

        System.out.print("Parsing... ");
        ProgrammaNode root = null;
        try {
            root = parse(programName);
            System.out.println("successful!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed!");
        }

        if (root != null) {
            System.out.print("Writing XML... ");
            try {
                writeToFile("res/outxml/AST.xml", getXML(root));
                System.out.println("successful!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("failed!");
            }

            System.out.print("Scope checking...");
            boolean scopeOK = false;
            try {
                ArrayList<String> scopeErrors = scopeCheck(root);
                if (scopeErrors.isEmpty()) {
                    System.out.println("successful!");
                    scopeOK = true;
                } else {
                    System.out.println("failed:");
                    printErrors(scopeErrors);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("failed!");
            }

            if (scopeOK) {
                System.out.print("Type checking...");
                boolean typeOK = false;
                try {
                    ArrayList<String> typeErrors = typeCheck(root);
                    if (typeErrors.isEmpty()) {
                        System.out.println("successful!");
                        typeOK = true;
                    } else {
                        System.out.println("failed:");
                        printErrors(typeErrors);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("failed!");
                }

                if (typeOK) {
                    System.out.print("Generating C Source Code... ");
                    try {
                        writeToFile("res/outc/out.c", generateCSource(root));
                        System.out.println("successful!");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("failed!");
                    }

                    //TODO Fare l'altro programma chiesto nel pdf

                    //TODO Compilare con clang

                    //TODO Permettere il lancio da linea di comando

                    //TODO Finire documentazione
                }
            }
        }
    }

    private static String getTokenStream(String fileName) throws IOException {
        Yylex yylex = new Yylex(new FileReader(fileName));
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
        Yylex yylex = new Yylex(new FileReader(fileName));
        Parser p = new Parser(yylex);
        return (ProgrammaNode) p.parse().value;
    }

    private static String getXML(ProgrammaNode root) {
        XMLVisitor v = new XMLVisitor();
        return (String) root.accept(v);
    }

    private static void writeToFile(String fileName, String content) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.print(content);
        pw.close();
    }

    private static ArrayList<String> scopeCheck(ProgrammaNode root) {
        ScopeCheckingVisitor scv = new ScopeCheckingVisitor();
        return (ArrayList<String>) root.accept(scv);
    }

    private static ArrayList<String> typeCheck(ProgrammaNode root) {
        TypeCheckingVisitor tcv = new TypeCheckingVisitor();
        return (ArrayList<String>) root.accept(tcv);
    }

    private static void printErrors(ArrayList<String> errors) {
        for (String e : errors) {
            System.out.println('\t' + e);
        }
    }

    private static String generateCSource(ProgrammaNode root) {
        CGeneratorVisitor cgv = new CGeneratorVisitor();
        return (String) root.accept(cgv);
    }
}
