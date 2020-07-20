/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.connection.EntityManager;
import java.awt.Container;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author son.ph173344
 */
public class FXChart {

    public static Container initializePieChart() {

        final JFXPanel fxPanel = new JFXPanel();
        JPanel jp = new JPanel();
        jp.add(fxPanel);
        jp.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFXPieChart(fxPanel);
            }
        });

        return jp;
    }

    private static void initFXPieChart(JFXPanel fxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, javafx.scene.paint.Color.ALICEBLUE);
        
        PieChart chart;
        chart = new PieChart();
        List<Pair<String, Double>> data = EntityManager.productDAO.getQuantityByCategoty();
        for(Pair<String, Double> p : data){
            chart.getData().add(new PieChart.Data(p.getKey(), p.getValue()));
        }
        
        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 11 arial;");

        for (final PieChart.Data pdata : chart.getData()) {
            pdata.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());

                    caption.setText(String.valueOf(pdata.getPieValue()));
                }
            });
        }

        
         chart.getData().forEach(chartData ->
                chartData.nameProperty().bind(
                        Bindings.concat(
                                chartData.getName(), ": ", chartData.pieValueProperty()
                        )
                )
        );
        
        chart.setTitle("Tỷ lệ hàng trong kho");
        chart.setStyle("-fx-font-size: 10 px;");
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(false);

        chart.setPrefSize(417, 250);
        chart.setMinSize(417, 250);
        chart.setMaxSize(417, 250);

        root.getChildren().add(chart);
        fxPanel.setScene(scene);
    }
    
    public static Container initializePieChartMonth() {

        final JFXPanel fxPanel = new JFXPanel();
        JPanel jp = new JPanel();
        jp.add(fxPanel);
        jp.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFXPieChartMonth(fxPanel);
            }
        });

        return jp;
    }

    private static void initFXPieChartMonth(JFXPanel fxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, javafx.scene.paint.Color.ALICEBLUE);
        
        PieChart chart;
        chart = new PieChart();
        List<Pair<String, Double>> data = EntityManager.invoiceDAO.getSaleCategotyMonth();
        for(Pair<String, Double> p : data){
            chart.getData().add(new PieChart.Data(p.getKey(), p.getValue()));
        }
        
        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 11 arial;");

        for (final PieChart.Data pdata : chart.getData()) {
            pdata.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());

                    caption.setText(String.valueOf(pdata.getPieValue()));
                }
            });
        }

        
         chart.getData().forEach(chartData ->
                chartData.nameProperty().bind(
                        Bindings.concat(
                                chartData.getName(), ": ", chartData.pieValueProperty()
                        )
                )
        );
        
        chart.setTitle("Tỷ lệ hàng bán trong tháng");
        chart.setStyle("-fx-font-size: 10 px;");
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(false);

        chart.setPrefSize(400, 250);
        chart.setMinSize(400, 250);
        chart.setMaxSize(400, 250);

        root.getChildren().add(chart);
        fxPanel.setScene(scene);
    }

    public static Container initializeAreaChart(String category, String year1, String year2) {

        final JFXPanel fxPanel = new JFXPanel();
        JPanel jp = new JPanel();
        jp.add(fxPanel);
        jp.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFXAreaChat(fxPanel, category, year1, year2);
            }
        });

        return jp;
    }

    private static void initFXAreaChat(JFXPanel fxPanel, String category, String year1, String year2) {
        Group root = new Group();
        Scene scene = new Scene(root, javafx.scene.paint.Color.ALICEBLUE);

        final NumberAxis xAxis = new NumberAxis(1, 12, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> chart = new AreaChart<Number, Number>(xAxis, yAxis);
        chart.setTitle("Biểu đồ tương quan bán " + category);
        chart.setStyle("-fx-font-size: 10 px;");

        chart.setLegendSide(Side.BOTTOM);

        // Chuỗi dữ liệu của năm 1
        XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series1.setName(year1);

        List<XYChart.Data<Number, Number>> list1 = EntityManager.invoiceDAO.getSaleCategoryByMonth(category, year1);
        for (XYChart.Data<Number, Number> d : list1) {
            series1.getData().add(d);
        }

        // Chuỗi dữ liệu của năm 2
        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        series2.setName(year2);

        List<XYChart.Data<Number, Number>> list2 = EntityManager.invoiceDAO.getSaleCategoryByMonth(category, year2);
        for (XYChart.Data<Number, Number> d : list2) {
            series2.getData().add(d);
        }

        chart.getData().addAll(series1, series2);

        chart.setPrefSize(417, 250);
        chart.setMinSize(417, 250);
        chart.setMaxSize(417, 250);

        root.getChildren().add(chart);
        fxPanel.setScene(scene);
    }

}
