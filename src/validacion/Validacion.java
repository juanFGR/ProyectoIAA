package validacion;/**
 * Created by JuanFGR on 02/05/2014.
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Validacion {





        public Validacion() throws IOException {

        Parent root = FXMLLoader.load(ValidacionController.class.getResource("Validacion.fxml"));
        Stage stage = new Stage();
        stage.setTitle("VALIDACION");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();


}
}
