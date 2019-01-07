package it.unisa.visitor.semantic;

public class SemanticData {
    private String identifier;
    private String kind;

    public SemanticData(String identifier, String kind) {
        this.identifier = identifier;
        this.kind = kind;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "SemanticData{" +
                "identifier='" + identifier + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
