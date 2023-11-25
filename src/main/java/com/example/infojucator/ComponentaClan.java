package com.example.infojucator;

public class ComponentaClan {
    private String numeClan;
    private String membru;
    private String jucatorUI; // UI-ul jucătorului

    // Constructorul modificat
    public ComponentaClan(String numeClan, String membru) {
        this.numeClan = numeClan;
        this.membru = membru;
    }

    public String getNumeClan() {
        return numeClan;
    }

    public void setNumeClan(String numeClan) {
        this.numeClan = numeClan;
    }

    public String getMembru() {
        return membru;
    }

    public void setMembru(String membru) {
        this.membru = membru;
    }

    // Getteri și setteri, inclusiv pentru jucatorUI
    public String getJucatorUI() {
        return jucatorUI;
    }

    public void setJucatorUI(String jucatorUI) {
        this.jucatorUI = jucatorUI;
    }

    // Restul getterilor și setterilor
}
