/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores1;

import com.sun.javafx.scene.EnteredExitedHandler;
import compiladores1.model.Conversor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Leonardo
 */
public class Compiladores1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Conversor C = new Conversor();
        Button btn = new Button();
        TextField infix = new TextField();
        infix.setTooltip(new Tooltip("INFIXA"));
        TextField posfix = new TextField();
        posfix.setTooltip(new Tooltip("POSFIXA"));
        btn.setText("Converter");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (infix.getText().compareTo("") != 0) {
                    posfix.setText(C.converter(infix.getText()));
                }
            }
        });
        infix.setOnAction((event) -> {
            if (infix.getText().compareTo("") != 0) {
                posfix.setText(C.converter(infix.getText()));
            }
        });
        AnchorPane root = new AnchorPane();
        root.getChildren().add(infix);
        root.getChildren().get(0).setLayoutX(50);
        root.getChildren().get(0).setLayoutY(50);
        root.getChildren().add(posfix);
        root.getChildren().get(1).setLayoutX(50);
        root.getChildren().get(1).setLayoutY(80);
        root.getChildren().add(btn);
        root.getChildren().get(2).setLayoutX(210);
        root.getChildren().get(2).setLayoutY(65);

        Scene scene = new Scene(root, 350, 150);

        primaryStage.setTitle("Trabalho 1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
