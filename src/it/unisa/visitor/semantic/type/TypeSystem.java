package it.unisa.visitor.semantic.type;

import it.unisa.ast.type.*;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class TypeSystem {
    public static final int PLUS_TABLE = 0;

    public static final int ERROR = -1;
    public static final int INTEGER = 0;
    public static final int DOUBLE = 1;
    public static final int STRING = 2;
    public static final int CHARACTER = 3;
    public static final int BOOLEAN = 4;

    private HashMap<String, Integer> typeMap;
    private int[][] plusTable;

    public TypeSystem() {
        typeMap = new HashMap<>();
        typeMap.put("", ERROR);
        typeMap.put(IntegerNode.INTEGER, INTEGER);
        typeMap.put(DoubleNode.DOUBLE, DOUBLE);
        typeMap.put(StringNode.STRING, STRING);
        typeMap.put(CharacterNode.CHARACTER, CHARACTER);
        typeMap.put(BooleanNode.BOOLEAN, BOOLEAN);

        initPlusTable();
    }

    public String checkTable(int tableNumber, String type1, String type2) {
        String result = "";

        int row = typeMap.get(type1);
        int column = typeMap.get(type2);
        if (row >= 0 && row < 5 && column >= 0 && column < 5) {
            int value = 0;
            switch (tableNumber) {
                case PLUS_TABLE:
                    value = plusTable[row][column];
                    break;
                //TODO Altre tabelle
            }

            switch (value) {
                case INTEGER:
                    result = IntegerNode.INTEGER;
                    break;
                case DOUBLE:
                    result = DoubleNode.DOUBLE;
                    break;
                case STRING:
                    result = StringNode.STRING;
                    break;
                case CHARACTER:
                    result = CharacterNode.CHARACTER;
                    break;
                case BOOLEAN:
                    result = BooleanNode.BOOLEAN;
                    break;
            }
        }
        return result;
    }

    private void initPlusTable() {
        plusTable = new int[5][5];
        plusTable[0][0] = INTEGER;
        plusTable[0][1] = DOUBLE;
        plusTable[0][2] = ERROR;
        plusTable[0][3] = ERROR;
        plusTable[0][4] = ERROR;

        plusTable[1][0] = DOUBLE;
        plusTable[1][1] = DOUBLE;
        plusTable[1][2] = ERROR;
        plusTable[1][3] = ERROR;
        plusTable[1][4] = ERROR;

        plusTable[2][0] = ERROR;
        plusTable[2][1] = ERROR;
        plusTable[2][2] = ERROR;
        plusTable[2][3] = ERROR;
        plusTable[2][4] = ERROR;

        plusTable[3][0] = ERROR;
        plusTable[3][1] = ERROR;
        plusTable[3][2] = ERROR;
        plusTable[3][3] = ERROR;
        plusTable[3][4] = ERROR;

        plusTable[4][0] = ERROR;
        plusTable[4][1] = ERROR;
        plusTable[4][2] = ERROR;
        plusTable[4][3] = ERROR;
        plusTable[4][4] = ERROR;
    }

}
