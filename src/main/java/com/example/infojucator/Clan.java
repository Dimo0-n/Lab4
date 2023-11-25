package com.example.infojucator;

public class Clan {
    private String ClanUI;
    private String NumeClan;
    private int NrMembri;
    private String Welcome;

    public Clan(String ClanUI, String NumeClan, int NrMembri, String Welcome) {
        this.ClanUI = ClanUI;
        this.NumeClan = NumeClan;
        this.NrMembri = NrMembri;
        this.Welcome = Welcome;
    }

    public String getClanUI() {
        return ClanUI;
    }

    public void setClanUI(String clanUI) {
        ClanUI = clanUI;
    }

    public String getNumeClan() {
        return NumeClan;
    }

    public void setNumeClan(String numeClan) {
        NumeClan = numeClan;
    }

    public int getNrMembri() {
        return NrMembri;
    }

    public void setNrMembri(int nrMembri) {
        NrMembri = nrMembri;
    }

    public String getWelcome() {
        return Welcome;
    }

    public void setWelcome(String welcome) {
        Welcome = welcome;
    }
}
