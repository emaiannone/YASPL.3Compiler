package it.unisa.seman;

import java.util.ArrayList;

public class SemanticData {
    private String identifier;
    private String kind;
    private String type;
    private String parType;
    private ArrayList<ParameterData> parameterList;

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

    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    public ArrayList<ParameterData> getParameterList() {
        return parameterList;
    }

    public void setParameterList(ArrayList<ParameterData> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String toString() {
        return "SemanticData{" +
                "identifier='" + identifier + '\'' +
                ", kind='" + kind + '\'' +
                ", type='" + type + '\'' +
                ", parType='" + parType + '\'' +
                ", parameterList=" + parameterList +
                '}';
    }
}
