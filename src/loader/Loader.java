package loader;/**
 * Created by JuanFGR on 02/05/2014.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Loader   {





public Loader() throws IOException {

        Parent root = FXMLLoader.load(LoaderController.class.getResource("Loader.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();


}
}
