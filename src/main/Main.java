package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 * Ejecucion de la ventana de inicio del programa
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //La siguiente linea asocia a la escena vacia la vista definida en main.fxml
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Proyecto de IAA 2013/2014");
        primaryStage.setScene(new Scene(root, 300, 275));//Define la ventana y sus dimensiones
        primaryStage.show(); //Muestra la ventana
    }

    public static void main(String[] args) {
        launch(args);
    }
}
