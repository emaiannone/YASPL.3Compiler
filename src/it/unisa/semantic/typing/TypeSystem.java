package it.unisa.semantic.typing;

import it.unisa.ast.type.*;

import java.util.HashMap;

public class TypeSystem {
    public static final int ERROR = -1;
    public static final int VOID = 0;
    public static final int INTEGER = 1;
    public static final int DOUBLE = 2;
    public static final int STRING = 3;
    public static final int CHARACTER = 4;
    public static final int BOOLEAN = 5;
    public static final int[][] arithTable = initArithTable();
    public static final int[][] boolTable = initBoolTable();
    public static final int[][] relationalTable = initRelationalTable();
    public static final int[][] uminusTable = initUminusTable();
    public static final int[][] notTable = initNotTable();
    public static final int[][] assignTable = initAssignTable();
    public static final int[][] conditionalTable = initConditionalTable();

    //TODO Le altre tabelle

    private static HashMap<String, Integer> typeMap = initTypeMap();

    public static int stringToInt(String type) {
        return typeMap.get(type);
    }

    public static String intToString(int i) {
        switch (i) {
            case VOID:
                return TypeNode.VOID;
            case INTEGER:
                return IntegerNode.INTEGER;
            case DOUBLE:
                return DoubleNode.DOUBLE;
            case STRING:
                return StringNode.STRING;
            case CHARACTER:
                return CharacterNode.CHARACTER;
            case BOOLEAN:
                return BooleanNode.BOOLEAN;
        }
        return null;
    }

    private static HashMap<String, Integer> initTypeMap() {
        typeMap = new HashMap<>();

        typeMap.put(null, ERROR);
        typeMap.put(TypeNode.VOID, VOID);
        typeMap.put(IntegerNode.INTEGER, INTEGER);
        typeMap.put(DoubleNode.DOUBLE, DOUBLE);
        typeMap.put(StringNode.STRING, STRING);
        typeMap.put(CharacterNode.CHARACTER, CHARACTER);
        typeMap.put(BooleanNode.BOOLEAN, BOOLEAN);

        return typeMap;
    }

    private static int[][] initArithTable() {
        int[][] arithTable = new int[6][6];

        arithTable[VOID][VOID] = ERROR;
        arithTable[VOID][INTEGER] = ERROR;
        arithTable[VOID][DOUBLE] = ERROR;
        arithTable[VOID][STRING] = ERROR;
        arithTable[VOID][CHARACTER] = ERROR;
        arithTable[VOID][BOOLEAN] = ERROR;

        arithTable[INTEGER][VOID] = ERROR;
        arithTable[INTEGER][INTEGER] = INTEGER;
        arithTable[INTEGER][DOUBLE] = DOUBLE;
        arithTable[INTEGER][STRING] = ERROR;
        arithTable[INTEGER][CHARACTER] = ERROR;
        arithTable[INTEGER][BOOLEAN] = ERROR;

        arithTable[DOUBLE][VOID] = ERROR;
        arithTable[DOUBLE][INTEGER] = DOUBLE;
        arithTable[DOUBLE][DOUBLE] = DOUBLE;
        arithTable[DOUBLE][STRING] = ERROR;
        arithTable[DOUBLE][CHARACTER] = ERROR;
        arithTable[DOUBLE][BOOLEAN] = ERROR;

        arithTable[STRING][VOID] = ERROR;
        arithTable[STRING][INTEGER] = ERROR;
        arithTable[STRING][DOUBLE] = ERROR;
        arithTable[STRING][STRING] = ERROR;
        arithTable[STRING][CHARACTER] = ERROR;
        arithTable[STRING][BOOLEAN] = ERROR;

        arithTable[CHARACTER][VOID] = ERROR;
        arithTable[CHARACTER][INTEGER] = ERROR;
        arithTable[CHARACTER][DOUBLE] = ERROR;
        arithTable[CHARACTER][STRING] = ERROR;
        arithTable[CHARACTER][CHARACTER] = ERROR;
        arithTable[CHARACTER][BOOLEAN] = ERROR;

        arithTable[BOOLEAN][VOID] = ERROR;
        arithTable[BOOLEAN][INTEGER] = ERROR;
        arithTable[BOOLEAN][DOUBLE] = ERROR;
        arithTable[BOOLEAN][STRING] = ERROR;
        arithTable[BOOLEAN][CHARACTER] = ERROR;
        arithTable[BOOLEAN][BOOLEAN] = ERROR;

        return arithTable;
    }

    private static int[][] initBoolTable() {
        int[][] boolTable = new int[6][6];

        boolTable[VOID][VOID] = ERROR;
        boolTable[VOID][INTEGER] = ERROR;
        boolTable[VOID][DOUBLE] = ERROR;
        boolTable[VOID][STRING] = ERROR;
        boolTable[VOID][CHARACTER] = ERROR;
        boolTable[VOID][BOOLEAN] = ERROR;

        boolTable[INTEGER][VOID] = ERROR;
        boolTable[INTEGER][INTEGER] = ERROR;
        boolTable[INTEGER][DOUBLE] = ERROR;
        boolTable[INTEGER][STRING] = ERROR;
        boolTable[INTEGER][CHARACTER] = ERROR;
        boolTable[INTEGER][BOOLEAN] = ERROR;

        boolTable[DOUBLE][VOID] = ERROR;
        boolTable[DOUBLE][INTEGER] = ERROR;
        boolTable[DOUBLE][DOUBLE] = ERROR;
        boolTable[DOUBLE][STRING] = ERROR;
        boolTable[DOUBLE][CHARACTER] = ERROR;
        boolTable[DOUBLE][BOOLEAN] = ERROR;

        boolTable[STRING][VOID] = ERROR;
        boolTable[STRING][INTEGER] = ERROR;
        boolTable[STRING][DOUBLE] = ERROR;
        boolTable[STRING][STRING] = ERROR;
        boolTable[STRING][CHARACTER] = ERROR;
        boolTable[STRING][BOOLEAN] = ERROR;

        boolTable[CHARACTER][VOID] = ERROR;
        boolTable[CHARACTER][INTEGER] = ERROR;
        boolTable[CHARACTER][DOUBLE] = ERROR;
        boolTable[CHARACTER][STRING] = ERROR;
        boolTable[CHARACTER][CHARACTER] = ERROR;
        boolTable[CHARACTER][BOOLEAN] = ERROR;

        boolTable[BOOLEAN][VOID] = ERROR;
        boolTable[BOOLEAN][INTEGER] = ERROR;
        boolTable[BOOLEAN][DOUBLE] = ERROR;
        boolTable[BOOLEAN][STRING] = ERROR;
        boolTable[BOOLEAN][CHARACTER] = ERROR;
        boolTable[BOOLEAN][BOOLEAN] = BOOLEAN;

        return boolTable;
    }

    private static int[][] initRelationalTable() {
        int[][] relationalTable = new int[6][6];

        relationalTable[VOID][VOID] = ERROR;
        relationalTable[VOID][INTEGER] = ERROR;
        relationalTable[VOID][DOUBLE] = ERROR;
        relationalTable[VOID][STRING] = ERROR;
        relationalTable[VOID][CHARACTER] = ERROR;
        relationalTable[VOID][BOOLEAN] = ERROR;

        relationalTable[INTEGER][VOID] = ERROR;
        relationalTable[INTEGER][INTEGER] = BOOLEAN;
        relationalTable[INTEGER][DOUBLE] = BOOLEAN;
        relationalTable[INTEGER][STRING] = ERROR;
        relationalTable[INTEGER][CHARACTER] = ERROR;
        relationalTable[INTEGER][BOOLEAN] = ERROR;

        relationalTable[DOUBLE][VOID] = ERROR;
        relationalTable[DOUBLE][INTEGER] = BOOLEAN;
        relationalTable[DOUBLE][DOUBLE] = BOOLEAN;
        relationalTable[DOUBLE][STRING] = ERROR;
        relationalTable[DOUBLE][CHARACTER] = ERROR;
        relationalTable[DOUBLE][BOOLEAN] = ERROR;

        relationalTable[STRING][VOID] = ERROR;
        relationalTable[STRING][INTEGER] = ERROR;
        relationalTable[STRING][DOUBLE] = ERROR;
        relationalTable[STRING][STRING] = BOOLEAN;
        relationalTable[STRING][CHARACTER] = ERROR;
        relationalTable[STRING][BOOLEAN] = ERROR;

        relationalTable[CHARACTER][VOID] = ERROR;
        relationalTable[CHARACTER][INTEGER] = ERROR;
        relationalTable[CHARACTER][DOUBLE] = ERROR;
        relationalTable[CHARACTER][STRING] = ERROR;
        relationalTable[CHARACTER][CHARACTER] = BOOLEAN;
        relationalTable[CHARACTER][BOOLEAN] = ERROR;

        relationalTable[BOOLEAN][VOID] = ERROR;
        relationalTable[BOOLEAN][INTEGER] = ERROR;
        relationalTable[BOOLEAN][DOUBLE] = ERROR;
        relationalTable[BOOLEAN][STRING] = ERROR;
        relationalTable[BOOLEAN][CHARACTER] = ERROR;
        relationalTable[BOOLEAN][BOOLEAN] = ERROR;

        return relationalTable;
    }

    private static int[][] initUminusTable() {
        int[][] uminusTable = new int[6][1];

        uminusTable[VOID][0] = ERROR;
        uminusTable[INTEGER][0] = INTEGER;
        uminusTable[DOUBLE][0] = DOUBLE;
        uminusTable[STRING][0] = ERROR;
        uminusTable[CHARACTER][0] = ERROR;
        uminusTable[BOOLEAN][0] = ERROR;

        return uminusTable;
    }

    private static int[][] initNotTable() {
        int[][] notTable = new int[6][1];

        notTable[VOID][0] = ERROR;
        notTable[INTEGER][0] = ERROR;
        notTable[DOUBLE][0] = ERROR;
        notTable[STRING][0] = ERROR;
        notTable[CHARACTER][0] = ERROR;
        notTable[BOOLEAN][0] = BOOLEAN;

        return notTable;
    }

    private static int[][] initAssignTable() {
        int[][] assignTable = new int[6][6];

        assignTable[VOID][VOID] = ERROR;
        assignTable[VOID][INTEGER] = ERROR;
        assignTable[VOID][DOUBLE] = ERROR;
        assignTable[VOID][STRING] = ERROR;
        assignTable[VOID][CHARACTER] = ERROR;
        assignTable[VOID][BOOLEAN] = ERROR;

        assignTable[INTEGER][VOID] = ERROR;
        assignTable[INTEGER][INTEGER] = VOID;
        assignTable[INTEGER][DOUBLE] = ERROR;
        assignTable[INTEGER][STRING] = ERROR;
        assignTable[INTEGER][CHARACTER] = ERROR;
        assignTable[INTEGER][BOOLEAN] = ERROR;

        assignTable[DOUBLE][VOID] = ERROR;
        assignTable[DOUBLE][INTEGER] = VOID;
        assignTable[DOUBLE][DOUBLE] = VOID;
        assignTable[DOUBLE][STRING] = ERROR;
        assignTable[DOUBLE][CHARACTER] = ERROR;
        assignTable[DOUBLE][BOOLEAN] = ERROR;

        assignTable[STRING][VOID] = ERROR;
        assignTable[STRING][INTEGER] = ERROR;
        assignTable[STRING][DOUBLE] = ERROR;
        assignTable[STRING][STRING] = VOID;
        assignTable[STRING][CHARACTER] = ERROR;
        assignTable[STRING][BOOLEAN] = ERROR;

        assignTable[CHARACTER][VOID] = ERROR;
        assignTable[CHARACTER][INTEGER] = ERROR;
        assignTable[CHARACTER][DOUBLE] = ERROR;
        assignTable[CHARACTER][STRING] = ERROR;
        assignTable[CHARACTER][CHARACTER] = VOID;
        assignTable[CHARACTER][BOOLEAN] = ERROR;

        assignTable[BOOLEAN][VOID] = ERROR;
        assignTable[BOOLEAN][INTEGER] = ERROR;
        assignTable[BOOLEAN][DOUBLE] = ERROR;
        assignTable[BOOLEAN][STRING] = ERROR;
        assignTable[BOOLEAN][CHARACTER] = ERROR;
        assignTable[BOOLEAN][BOOLEAN] = VOID;

        return assignTable;
    }

    private static int[][] initConditionalTable() {
        int[][] conditionalTable = new int[6][1];

        conditionalTable[VOID][0] = ERROR;
        conditionalTable[INTEGER][0] = ERROR;
        conditionalTable[DOUBLE][0] = ERROR;
        conditionalTable[STRING][0] = ERROR;
        conditionalTable[CHARACTER][0] = ERROR;
        conditionalTable[BOOLEAN][0] = VOID;

        return conditionalTable;
    }
}
