package com.example.infojucator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jucator {
    private String jucatorUI;
    private String numeUtilizator;
    private String email;
    private int nivel;
    private int puncteExperienta;
    private String inventar;
    private double monedaVirtuala;
    private String scoruri;
    private String istoricChat;
    private String istoricTranzactii;
    private String istoricJoc;
    private String feedback;
    private final StringProperty clanUI; // Adăugăm proprietatea clanUI

    public Jucator(String jucatorUI, String clanUI, String numeUtilizator, String email,
                   int nivel, int puncteExperienta, String inventar, double monedaVirtuala,
                   String scoruri, String istoricChat, String istoricTranzactii,
                   String istoricJoc, String feedback) {
        this.jucatorUI = jucatorUI;
        this.clanUI = new SimpleStringProperty(clanUI); // Inițializăm clanUI ca StringProperty
        this.numeUtilizator = numeUtilizator;
        this.email = email;
        this.nivel = nivel;
        this.puncteExperienta = puncteExperienta;
        this.inventar = inventar;
        this.monedaVirtuala = monedaVirtuala;
        this.scoruri = scoruri;
        this.istoricChat = istoricChat;
        this.istoricTranzactii = istoricTranzactii;
        this.istoricJoc = istoricJoc;
        this.feedback = feedback;
    }

    public String getJucatorUI() {
        return jucatorUI;
    }

    public void setJucatorUI(String jucatorUI) {
        this.jucatorUI = jucatorUI;
    }

    // Adăugăm metoda pentru a obține clanUI ca StringProperty
    public StringProperty clanUIProperty() {
        return clanUI;
    }

    public String getClanUI() {
        return clanUI.get();
    }

    public void setClanUI(String clanUI) {
        this.clanUI.set(clanUI);
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuncteExperienta() {
        return puncteExperienta;
    }

    public void setPuncteExperienta(int puncteExperienta) {
        this.puncteExperienta = puncteExperienta;
    }

    public String getInventar() {
        return inventar;
    }

    public void setInventar(String inventar) {
        this.inventar = inventar;
    }

    public double getMonedaVirtuala() {
        return monedaVirtuala;
    }

    public void setMonedaVirtuala(double monedaVirtuala) {
        this.monedaVirtuala = monedaVirtuala;
    }

    public String getScoruri() {
        return scoruri;
    }

    public void setScoruri(String scoruri) {
        this.scoruri = scoruri;
    }

    public String getIstoricChat() {
        return istoricChat;
    }

    public void setIstoricChat(String istoricChat) {
        this.istoricChat = istoricChat;
    }

    public String getIstoricTranzactii() {
        return istoricTranzactii;
    }

    public void setIstoricTranzactii(String istoricTranzactii) {
        this.istoricTranzactii = istoricTranzactii;
    }

    public String getIstoricJoc() {
        return istoricJoc;
    }

    public void setIstoricJoc(String istoricJoc) {
        this.istoricJoc = istoricJoc;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
