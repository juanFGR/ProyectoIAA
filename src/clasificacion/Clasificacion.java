package clasificacion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 * En esta parte se clasificarán los documentos presentes en un corpus.
 *
 * Escribe un programa que tome como entrada las estimaciones de probabilidad de cada palabra y un corpus con documentos
 * a clasificar y devuelva los documentos clasificados en un fichero clasificación.txt donde cada línea del fichero de
 * salida con el corpus tenga la siguiente estructura:
 *
 *       Clase:<pos o neg> Texto:<cadena con texto del fichero>
 */
public class Clasificacion {

    public Clasificacion() throws IOException {
        //La siguiente linea asocia a la escena vacia la vista definida en Clasificacion.fxml
        Parent root = FXMLLoader.load(ClasificacionController.class.getResource("Clasificacion.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Clasificación");
        stage.setScene(new Scene(root, 300, 275)); //Define la ventana y sus dimensiones
        stage.show(); //Muestra la ventana
    }

}
