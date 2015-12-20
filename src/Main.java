import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {
    private static final int ITERATIONS = 10000;
    private static final HashMap<Integer, XYChart.Series> N = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        N.put(10, new XYChart.Series());
        N.put(15, new XYChart.Series());
        N.put(20, new XYChart.Series());
        N.put(25, new XYChart.Series());
        N.put(30, new XYChart.Series());

        CurvedFittedAreaChart chart = new CurvedFittedAreaChart(
                new NumberAxis("filling, %", 0, 100, 10), new NumberAxis("probability, %",0, 100, 10));
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        N.entrySet().forEach(n -> {
            for (int i = 0; i <= 100; i++) {
                int conflictNumber = 0;
                for (int j = 0; j < ITERATIONS; j++) {
                    Matrix matrix = new Matrix(n.getKey(), i);
                    if (matrix.hasConflict()) {
                        conflictNumber++;
                    }
                }
                n.getValue().getData().add(new XYChart.Data<>(i, ((float)conflictNumber/ITERATIONS)*100));
            }
        });

        VBox root = new VBox(5d);
        ToggleGroup sizesGroup = new ToggleGroup();
        List<ToggleButton> buttons = N.entrySet().stream().sorted((o1, o2) -> Integer.compare(o1.getKey(), o2.getKey())).map(n -> {
            ToggleButton button = new ToggleButton(String.valueOf(n.getKey()));
            button.setToggleGroup(sizesGroup);
            button.setUserData(n.getKey());
            return button;
        }).collect(Collectors.toList());
        sizesGroup.selectedToggleProperty().addListener((ov, toggle, newToggle)->{
            chart.getData().clear();
            chart.getData().add(N.get(sizesGroup.getSelectedToggle().getUserData()));
        });
        sizesGroup.getToggles().get(0).setSelected(true);
        HBox buttonHolder = new HBox();
        buttonHolder.getStyleClass().add("hbox");
        buttonHolder.setFillHeight(false);
        buttons.forEach(b->buttonHolder.getChildren().add(b));
        root.getChildren().addAll(chart, buttonHolder);
        VBox.setVgrow(chart, Priority.ALWAYS);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(("CurveFittedChart.css"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
