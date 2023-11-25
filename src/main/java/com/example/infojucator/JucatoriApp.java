package com.example.infojucator;

import com.example.infojucator.Jucator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JucatoriApp extends Application {

    Jucator jucator;

    @Override
    public void start(Stage stage) throws Exception {
        TableView<Jucator> table = new TableView<>();
        setupJucatoriTableView(table);
        loadDataFromDatabase(table);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/Design.css").toExternalForm());
        stage.setTitle("Informatii Jucatori");
        stage.show();
    }

    private void setupJucatoriTableView(TableView<Jucator> table) {
        // Definirea coloanelor și proprietăților aici
        TableColumn<Jucator, String> colJucatorUI = new TableColumn<>("Jucator UI");
        colJucatorUI.setCellValueFactory(new PropertyValueFactory<>("jucatorUI"));

        TableColumn<Jucator, String> colNumeUtilizator = new TableColumn<>("Nume Utilizator");
        colNumeUtilizator.setCellValueFactory(new PropertyValueFactory<>("NumeUtilizator"));

        TableColumn<Jucator, String> colClanUI = new TableColumn<>("Clan UI");
        colClanUI.setCellValueFactory(new PropertyValueFactory<>("clanUI"));

        TableColumn<Jucator, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Jucator, String> colFeedback = new TableColumn<>("Feedback");
        colFeedback.setCellValueFactory(new PropertyValueFactory<>("Feedback"));

        table.setRowFactory(tv -> {
            TableRow<Jucator> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Jucator jucatorSelectat = row.getItem();
                    arataDialogSchimbareClan(jucatorSelectat, table);
                }
            });
            return row;
        });

        table.getColumns().addAll(colJucatorUI, colNumeUtilizator, colClanUI, colEmail, colFeedback);
    }

    private void loadDataFromDatabase(TableView<Jucator> table) {
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Jucatori")) {

            while (rs.next()) {
                Jucator jucator = new Jucator(
                        rs.getString("JucatorUI"),
                        rs.getString("ClanUI"),
                        rs.getString("NumeUtilizator"),
                        rs.getString("Email"),
                        rs.getInt("Nivel"),
                        rs.getInt("PuncteExperienta"),
                        rs.getString("Inventar"),
                        rs.getDouble("MonedaVirtuala"),
                        rs.getString("Scoruri"),
                        rs.getString("IstoricChat"),
                        rs.getString("IstoricTranzactii"),
                        rs.getString("IstoricJoc"),
                        rs.getString("Feedback")
                );

                // Adăugarea listener-ului pentru actualizarea automată a bazei de date
                jucator.clanUIProperty().addListener((obs, oldClan, newClan) -> {
                    try {
                        actualizeazaClanJucator(jucator.getJucatorUI(), newClan);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Poți afișa un mesaj de eroare aici
                    }
                });

                table.getItems().add(jucator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void arataDialogSchimbareClan(Jucator jucatorSelectat, TableView<Jucator> table) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Schimbare Clan");
        dialog.setHeaderText("Alege un nou clan pentru jucătorul: " + jucatorSelectat.getNumeUtilizator());

        ButtonType butonSchimbaClan = new ButtonType("Schimbă Clanul", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(butonSchimbaClan, ButtonType.CANCEL);

        VBox dialogVBox = new VBox();
        ComboBox<String> comboClanuri = new ComboBox<>();
        comboClanuri.setPromptText("Alege un clan");

        List<String> numeClanuri = getNumeClanuri();
        comboClanuri.getItems().addAll(numeClanuri);

        dialogVBox.getChildren().add(comboClanuri);
        dialog.getDialogPane().setContent(dialogVBox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == butonSchimbaClan) {
                return comboClanuri.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(numeClanAles -> {
            String clanUI = mapNumeClanLaUI(numeClanAles);
            jucatorSelectat.setClanUI(clanUI);
            try {
                actualizeazaClanJucator(jucatorSelectat.getJucatorUI(), clanUI);
            } catch (SQLException e) {
                e.printStackTrace();
                // Poți afișa un mesaj de eroare aici
            }
            table.refresh(); // Reîmprospătează tabelul pentru a reflecta schimbarea
        });
    }

    private String mapNumeClanLaUI(String numeClan) {
        // Aici vom interoga baza de date pentru a obține UI-ul clanului pe baza numelui său
        String uiClan = ""; // Inițializare implicită
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";
        String query = "SELECT ClanUI FROM Clan WHERE NumeClan = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, numeClan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    uiClan = rs.getString("ClanUI");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestionarea excepțiilor
        }
        return uiClan;
    }


    private List<String> getUIClanuri() {
        List<String> uiClanuri = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";
        // Presupunând că aveți o coloană 'ClanUI' în tabelul 'Clan'
        String query = "SELECT ClanUI FROM Clan";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                uiClanuri.add(rs.getString("ClanUI"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Aici ar trebui să gestionați excepțiile
        }
        return uiClanuri;
    }


    private List<String> getNumeClanuri() {
        List<String> numeClanuri = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";
        String query = "SELECT NumeClan FROM Clan";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                numeClanuri.add(rs.getString("NumeClan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeClanuri;
    }

    private void actualizeazaClanJucator(String jucatorUI, String clanNou) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";
        String query = "UPDATE Jucatori SET ClanUI = ? WHERE JucatorUI = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, clanNou);
            pstmt.setString(2, jucatorUI);
            pstmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
