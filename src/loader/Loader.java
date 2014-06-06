package loader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 */
public class Loader   {
    public Loader() throws IOException {

        Parent root = FXMLLoader.load(LoaderController.class.getResource("Loader.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Generar Vocabulario");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();


    }
}
