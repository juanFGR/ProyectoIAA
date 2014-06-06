package loader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 *
 *   Crea un corpus con nombre corpus<pos o neg>.txt que una todos los documentos
 * con opinión positiva y otro corpus con todos los documentos con opinión negativa.
 *   Cada línea del fichero de salida en el corpus debe tener la siguiente estructura:
 *          Texto:<cadena con texto del fichero>
 *
 *   Para ello examina el fichero corpustodo.txt y  obtén las palabras del vocabulario a partir del texto.
 *      Debes generar un fichero de salida vocabulario.txt con cabecera
 *      Numero de palabras:<Número entero> Palabra:<cadena>
 */
public class Loader   {
    public Loader() throws IOException {
        //La siguiente linea asocia a la escena vacia la vista definida en Loader.fxml
        Parent root = FXMLLoader.load(LoaderController.class.getResource("Loader.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Generar Vocabulario");
        stage.setScene(new Scene(root, 300, 275));//Define la ventana y sus dimensiones
        stage.show(); //Muestra la ventana


    }
}
