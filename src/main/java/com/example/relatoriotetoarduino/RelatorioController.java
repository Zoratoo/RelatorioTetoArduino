package com.example.relatoriotetoarduino;

import com.example.relatoriotetoarduino.Utils.Conexao;
import com.example.relatoriotetoarduino.Utils.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    public ComboBox<String> cbSelEnergia;
    public Label lbManha;
    public TextField tfManha;
    public Label lbTarde;
    public TextField tfTarde;
    public Label lbNoite;
    public TextField tfNoite;
    public Label lbLitros;
    public TextField tfLitros;
    public Label lbKW1;
    public Label lbKW2;
    public Label lbKW3;

    public void setStage(Stage stage) {
    }

    // Default energy type
    private String selectedEnergyType;//selecionar o tipo de energia

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //pega o tipo de energia para cadastrar no BD
        cbSelEnergia.getSelectionModel().select(selectedEnergyType);
        //muda o Painel para as colunas que estão no BD
        mudarBox();

        // mudar o valor do comboBox
        cbSelEnergia.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // atualiza os valores para zero se mudar a energia
            selectedEnergyType = newValue;
            mudarBox();
        });
    }

    // atualiza as labels e TextFiles para a energia selecionada
    private void mudarBox() {
        String selectedEnergia = cbSelEnergia.getValue();
        boolean isAguaSelected = "Agua".equals(selectedEnergia);
        if(!isAguaSelected){
            lbNoite.setVisible(true); tfNoite.setVisible(true);
            lbTarde.setVisible(true); tfTarde.setVisible(true);
            lbManha.setText("Manhã:");
            lbTarde.setText("Tarde:");
            lbNoite.setText("Noite:");
            lbLitros.setVisible(false);
            lbKW1.setVisible(true);lbKW2.setVisible(true);lbKW3.setVisible(true);
            tfManha.setText("0");
            tfTarde.setText("0");
            tfNoite.setText("0");
            tfLitros.setVisible(false);
        }
        else{
            lbManha.setText("Litros:");
            tfManha.setText("0");
            lbNoite.setVisible(false); tfNoite.setVisible(false);
            lbTarde.setVisible(false); tfTarde.setVisible(false);
            lbKW1.setVisible(false);lbKW2.setVisible(false);lbKW3.setVisible(false);
        }
    }

    // Grava os valores no BD de acordo com a energia selecionada
    public void btGravar(ActionEvent actionEvent) {
        if (isEmptyField(tfManha) || isEmptyField(tfTarde) || isEmptyField(tfNoite)) {
            // se os campos estiverem vazios mostra um alerta e não deixa gravar
            showAlert("Todos os campos devem ser preenchidos.");
            return;
        }
        float manha = Float.parseFloat(tfManha.getText());
        float tarde = Float.parseFloat(tfTarde.getText());
        float noite = Float.parseFloat(tfNoite.getText());
        String sql;
        String selectedEnergia = cbSelEnergia.getValue();
        String tableName = selectedEnergyType.toLowerCase(); // Assuming table names are lowercase
        try {
            // estabelece a conexão com o BD
            if (DB.conectar()) {
                // O 'if' vai deixar possível o cadastro de valores nas tabelas de acordo com suas
                //respectivas chaves primárias
                if ("Agua".equals(selectedEnergia)) {
                    // Obter o valor do campo específico para "Agua"
                    sql = "INSERT INTO agua (data_registro, litros_dia) VALUES (CURRENT_DATE, ?)";
                    try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql, new String[]{"id_agua"})) {
                        preparedStatement.setFloat(1, manha);
                        preparedStatement.executeUpdate();
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("ID gerado: " + id);
                        } else {
                            System.out.println("Falha ao obter o ID gerado.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("SQL Exception: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                } else if ("Luz".equals(selectedEnergia)) {
                    sql = "INSERT INTO luz (data_registro, manha, tarde, noite) VALUES (CURRENT_DATE, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql, new String[]{"id_luz"})) {
                        preparedStatement.setFloat(1, manha);
                        preparedStatement.setFloat(2, tarde);
                        preparedStatement.setFloat(3, noite);
                        preparedStatement.executeUpdate();
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("ID gerado: " + id);
                        } else {
                            System.out.println("Falha ao obter o ID gerado.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } else if ("Arcondicionado".equals(selectedEnergia)) {
                    sql = "INSERT INTO arcondicionado (data_registro, manha, tarde, noite) VALUES (CURRENT_DATE, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql, new String[]{"id_arcond"})) {
                        preparedStatement.setFloat(1, manha);
                        preparedStatement.setFloat(2, tarde);
                        preparedStatement.setFloat(3, noite);
                        preparedStatement.executeUpdate();
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("ID gerado: " + id);
                        } else {
                            System.out.println("Falha ao obter o ID gerado.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }else if ("Ventilador".equals(selectedEnergia)) {
                    sql = "INSERT INTO ventilador (data_registro, manha, tarde, noite) VALUES (CURRENT_DATE, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql, new String[]{"id_vent"})) {
                        preparedStatement.setFloat(1, manha);
                        preparedStatement.setFloat(2, tarde);
                        preparedStatement.setFloat(3, noite);
                        preparedStatement.executeUpdate();
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("ID gerado: " + id);
                        } else {
                            System.out.println("Falha ao obter o ID gerado.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } else {
                    System.err.println("Failed to connect to the database.");
                }
            }
                Conexao.close();
            } catch(RuntimeException e){
                throw new RuntimeException(e);
            }
        }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isEmptyField(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    //Função para exibir os gráficos
    public void btExibir(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("grafico-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage graficoStage = new Stage();
            graficoStage.setTitle("Gráficos");
            graficoStage.setScene(scene);

            GraficoViewController graficoController = loader.getController();

            // Verificar se cbGrafico não é nulo antes de usá-lo
            if (graficoController.cbGrafico != null) {
                graficoController.cbGrafico.getItems().addAll("Luz", "Ventilador", "Arcondicionado", "Agua");
            } else {
                System.err.println("O ComboBox cbGrafico é nulo");
            }

            graficoController.setStage(graficoStage);
            graficoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}