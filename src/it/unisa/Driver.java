package it.unisa;

import it.unisa.ast.programma.ProgrammaNode;
import it.unisa.lexer.Yylex;
import it.unisa.parser.Parser;
import it.unisa.parser.ParserSym;
import it.unisa.visitor.semantic.scope.ScopeCheckingVisitor;
import it.unisa.visitor.semantic.type.TypeCheckingVisitor;
import it.unisa.visitor.xml.XMLMyVisitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Driver {
    public static void main(String[] args) {
        ProgrammaNode root = null;
        try {
            System.out.println("\nParsing... ");
            root = parse("res/ProgramTests/programmaErroriType.yaspl");
            System.out.println("Parsing successful!");
        } catch (Exception e) {
            System.out.println("Parsing error.");
        }
        try {
            if (root != null) {
                writeToFile("res/outxml/AST.xml", getXML(root));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Writing to XML file error.");
        }
        try {
            if (root != null) {
                System.out.println("\nStarting scope checking...");
                ScopeCheckingVisitor scv = new ScopeCheckingVisitor();
                ArrayList<String> scopeErrors = (ArrayList<String>) root.accept(scv);

                if (scopeErrors.isEmpty()) {
                    System.out.println("Scope checking successful!");
                    System.out.println("\nStarting type checking...");
                    TypeCheckingVisitor tcv = new TypeCheckingVisitor();
                    ArrayList<String> typeErrors = (ArrayList<String>) root.accept(tcv);
                    if (typeErrors.isEmpty()) {
                        System.out.println("Type checking successful!");

                        //TODO Lanciare visitor che genera il codice C

                    } else {
                        System.out.println("Type checking failed:");
                        for (String e : typeErrors) {
                            System.out.println('\t' + e);
                        }
                    }
                } else {
                    System.out.println("Scope checking failed:");
                    //scopeErrors.removeAll(Collections.singleton(null));  // Discards the null elements
                    for (String e : scopeErrors) {
                        System.out.println('\t' + e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Checking error.");
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
        XMLMyVisitor v = new XMLMyVisitor();

        return (String) root.accept(v);
    }

    private static void writeToFile(String fileName, String content) throws FileNotFoundException {
        PrintWriter xmlAST = new PrintWriter(fileName);
        xmlAST.print(content);
        xmlAST.close();
    }
}
