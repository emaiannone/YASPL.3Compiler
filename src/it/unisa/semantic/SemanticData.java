package it.unisa.semantic;

public class SemanticData {
    private String identifier;
    private String kind;
    private String type;

    public SemanticData() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SemanticData{" +
                "identifier='" + identifier + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
