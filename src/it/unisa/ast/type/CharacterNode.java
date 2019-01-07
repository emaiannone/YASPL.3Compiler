package it.unisa.ast.type;

public class CharacterNode extends TypeNode {
    public static final String CHARACTER = "char";

    public CharacterNode() {
        super(CHARACTER);
    }

    public String getXMLTag() {
        return "CharacterNode";
    }
}
