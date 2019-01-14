package it.unisa.seman;

public class ParameterData {
    private String identifier;
    private String parType;
    private String type;

    public ParameterData() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParameterData{" +
                "identifier='" + identifier + '\'' +
                ", parType='" + parType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
