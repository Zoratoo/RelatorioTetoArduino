package com.example.relatoriotetoarduino;

import com.example.relatoriotetoarduino.Utils.Conexao;
import com.example.relatoriotetoarduino.Utils.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GraficoViewController implements Initializable {
    public ComboBox<String> cbGrafico;
    public Label lbMedia;
    public Label lbValorMedia;
    public Label lbTotal;
    public Label lbValorTotal;
    private boolean modoSemanal = true;
    @FXML
    private VBox containerPane;
    String selectedEnergia;

    // Os métodos a seguir servem para pegar os valores das tabelas da energia selecionada,
    // criar gráficos e separar por semanas
    public void criarGraficoAgua() {
            containerPane.getChildren().clear();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            if (DB.conectar()) {
                String sql = "SELECT data_registro, litros_dia FROM agua";
                try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String dataRegistro = resultSet.getString("data_registro");
                        dataset.addValue(resultSet.getFloat("litros_dia"), "Agua - " + dataRegistro, "Litros");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL Exception: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            JFreeChart chart = ChartFactory.createBarChart(
                    "Gráfico de Água",
                    "Período do Dia",
                    "Consumo",
                    dataset
            );
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(500, 300));
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(chartPanel);
            containerPane.getChildren().add(swingNode);
        }

    public void criarGraficoVentilador() {
        containerPane.getChildren().clear();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (DB.conectar()) {
            String sql = "SELECT data_registro, manha, tarde, noite FROM ventilador";
            try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String dataRegistro = resultSet.getString("data_registro");
                    dataset.addValue(resultSet.getFloat("manha"), "Ventilador - " + dataRegistro, "Manhã");
                    dataset.addValue(resultSet.getFloat("tarde"), "Ventilador - " + dataRegistro, "Tarde");
                    dataset.addValue(resultSet.getFloat("noite"), "Ventilador - " + dataRegistro, "Noite");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL Exception: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Ventilador",
                "Período do Dia",
                "Consumo",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);
        containerPane.getChildren().add(swingNode);
    }
    public void criarGraficoArcondicionado() {
        containerPane.getChildren().clear();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (DB.conectar()) {
            String sql = "SELECT data_registro, manha, tarde, noite FROM arcondicionado";
            try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String dataRegistro = resultSet.getString("data_registro");
                    dataset.addValue(resultSet.getFloat("manha"), "Ar condicionado - " + dataRegistro, "Manhã");
                    dataset.addValue(resultSet.getFloat("tarde"), "Ar condicionado - " + dataRegistro, "Tarde");
                    dataset.addValue(resultSet.getFloat("noite"), "Ar condicionado - " + dataRegistro, "Noite");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL Exception: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Ar Condicionado",
                "Período do Dia",
                "Consumo",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);
        containerPane.getChildren().add(swingNode);
    }
    public void criarGraficoLuz() {
        containerPane.getChildren().clear();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (DB.conectar()) {
            String sql = "SELECT data_registro, manha, tarde, noite FROM luz";
            try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String dataRegistro = resultSet.getString("data_registro");
                    dataset.addValue(resultSet.getFloat("manha"), "Luz - " + dataRegistro, "Manhã");
                    dataset.addValue(resultSet.getFloat("tarde"), "Luz - " + dataRegistro, "Tarde");
                    dataset.addValue(resultSet.getFloat("noite"), "Luz - " + dataRegistro, "Noite");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL Exception: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Luz",
                "Período do Dia",
                "Consumo",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);
        containerPane.getChildren().add(swingNode);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB.conectar();
        cbGrafico.setValue("Luz");
        cbGrafico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Luz" -> criarGraficoLuz();
                case "Ventilador" -> criarGraficoVentilador();
                case "Arcondicionado" -> {
                    criarGraficoArcondicionado();
                }
                case "Agua" -> criarGraficoAgua();
            }
        });
    }

    public void setStage(Stage graficoStage) {
    }


    @FXML //muda a média para mensal
    private void btMensal(ActionEvent event) {
        modoSemanal = false;
        atualizarLabels();
    }

    @FXML //muda a média para semanal
    private void btSemanal(ActionEvent event) {
        modoSemanal = true;
        atualizarLabels();
    }
    //atualiza as labels usando o cálculo de energia e a energia selecionada
    private void atualizarLabels() {
        selectedEnergia = cbGrafico.getValue();
        int id=1;
        double litros = 0,manha=0,tarde=0,noite=0;
        if("Agua".equals(selectedEnergia)) {
            while (id < 5){
                litros += obterValorColuna("litros_dia", id);
                id++;
            }
            if (modoSemanal) {
                lbMedia.setText("Média Semanal:");
                lbValorMedia.setText(String.format("%.2f litros",litros/7));
                lbTotal.setText("Total Economizado:");
                lbValorTotal.setText(String.format("%.2f litros",litros));
            } else {
                lbMedia.setText("Média Mensal:");
                lbValorMedia.setText(String.format("%.2f litros",litros/30));
                lbTotal.setText("Total Economizado:");
                lbValorTotal.setText(String.format("%.2f litros",litros));
            }
        } else {
            while(id<5){
            manha += obterValorColuna("manha", id);
            tarde += obterValorColuna("tarde", id);
            noite += obterValorColuna("noite", id);
            id++;
            }
            if (modoSemanal) {
                lbMedia.setText("Média Semanal:");
                lbValorMedia.setText(String.format("%.2f KW/h", (manha + tarde + noite) / 7));
                lbTotal.setText("Total Economizado:");
                lbValorTotal.setText(String.format("%.2f KW/h", manha + tarde + noite));
            } else {
                lbMedia.setText("Média Mensal:");
                lbValorMedia.setText(String.format("%.2f KW/h", (manha + tarde + noite) / 30));
                lbTotal.setText("Total Economizado:");
                lbValorTotal.setText(String.format("%.2f KW/h", manha + tarde + noite));
            }
        }
    }
    //determina a coluna e obtém os valores dela
    private double obterValorColuna(String coluna,int id) {
        double valor = 0.0;  // Valor padrão, pode ser ajustado conforme necessário
        String tableName = selectedEnergia.toLowerCase(),nomeId="";
        if ("Agua".equals(selectedEnergia))
            nomeId="id_agua";
        else if ("Luz".equals(selectedEnergia)) {
            nomeId="id_luz";
        } else if ("Arcondicionado".equals(selectedEnergia)) {
            nomeId="id_arcond";
        }
        else nomeId="id_vent";
        if (DB.conectar()) {
            String sql = "SELECT " + coluna + " FROM "+tableName+" WHERE "+nomeId+"="+id;

            try (PreparedStatement preparedStatement = Conexao.connect.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    valor = resultSet.getDouble(coluna);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL Exception: " + e.getMessage());
                throw new RuntimeException(e);
            } finally {
                // Certifique-se de fechar a conexão, se necessário
                Conexao.close();
            }
        }

        return valor;
    }
}
