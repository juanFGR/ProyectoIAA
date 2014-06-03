package clasificacion;/**
 * Created by JuanFGR on 02/05/2014.
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Clasificacion {





        public Clasificacion() throws IOException {

        Parent root = FXMLLoader.load(ClasificacionController.class.getResource("Clasificacion.fxml"));
        Stage stage = new Stage();
        stage.setTitle("APRENDIZAJE");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();


}
}
