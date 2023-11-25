package com.example.infojucator;

import com.example.infojucator.Clan;
import com.example.infojucator.ComponentaClan;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ClanApp extends Application {

    private TableView<ComponentaClan> tableMembri;

    @Override
    public void start(Stage stage) throws Exception {
        // TableView pentru clanuri
        TableView<Clan> tableClanuri = new TableView<>();
        setupClanTableView(tableClanuri);

        // TableView pentru membrii clanului
        tableMembri = new TableView<>();
        setupMembriTableView();

        // Setarea unui row factory personalizat pentru tabelul clanurilor
        tableClanuri.setRowFactory(tv -> {
            TableRow<Clan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Clan clickedRow = row.getItem();
                    loadMembriFromDatabase(clickedRow.getNumeClan());
                }
            });
            return row;
        });

        loadDataFromDatabase(tableClanuri);

        VBox vbox = new VBox(tableClanuri, tableMembri);
        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/Design.css").toExternalForm());
        stage.setTitle("Lista Clanurilor și Membrii lor");
        stage.show();
    }

    private void setupClanTableView(TableView<Clan> table) {
        TableColumn<Clan, String> colClanUI = new TableColumn<>("Clan UI");
        colClanUI.setCellValueFactory(new PropertyValueFactory<>("ClanUI"));
        TableColumn<Clan, String> colNumeClan = new TableColumn<>("Nume Clan");
        colNumeClan.setCellValueFactory(new PropertyValueFactory<>("NumeClan"));
        TableColumn<Clan, Integer> colNrMembri = new TableColumn<>("Nr Membri");
        colNrMembri.setCellValueFactory(new PropertyValueFactory<>("NrMembri"));
        TableColumn<Clan, String> colWelcome = new TableColumn<>("Welcome");
        colWelcome.setCellValueFactory(new PropertyValueFactory<>("Welcome"));

        table.getColumns().addAll(colClanUI, colNumeClan, colNrMembri, colWelcome);
    }

    private void setupMembriTableView() {
        TableColumn<ComponentaClan, String> colMembru = new TableColumn<>("Membru");
        colMembru.setCellValueFactory(new PropertyValueFactory<>("membru"));
        colMembru.setPrefWidth(200);

        tableMembri.getColumns().addAll(colMembru);
    }

    private void loadDataFromDatabase(TableView<Clan> table) {
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clan")) {

            while (rs.next()) {
                Clan clan = new Clan(
                        rs.getString("ClanUI"),
                        rs.getString("NumeClan"),
                        rs.getInt("NrMembri"),
                        rs.getString("Welcome")
                );

                table.getItems().add(clan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMembriFromDatabase(String numeClan) {
        String url = "jdbc:mysql://localhost:3306/Lab4";
        String user = "root";
        String password = "dima123";

        ObservableList<ComponentaClan> membri = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ComponentaClan WHERE numeClan = ?")) {

            pstmt.setString(1, numeClan);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                for (int i = 1; i <= 10; i++) {
                    String membru = rs.getString("membru" + i);
                    if (membru != null && !membru.isEmpty()) {
                        membri.add(new ComponentaClan(numeClan, membru));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableMembri.setItems(membri);
    }

    public static void main(String[] args) {
        launch(args);
    }
}