package aprendizaje;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author  Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 *
 * La estimación de las probabilidades se escribirá en un fichero de texto llamado aprendizaje<pos o neg>.txt.
 * En el fichero de texto debe aparecer:
 *          Cabecera: Numero de documentos del corpus :<número entero> Número de palabras del corpus:<número entero>
 *
 * Por cada palabra de vocabulario.txt,  su frecuencia en el corpus y una estimación del logaritmo de su probabilidad
 * mediante suavizado laplaciano con tratamiento de palabras desconocidas. Las palabras en los ficheros de aprendizaje
 * estarán ordenadas alfabéticamente.
 *          Palabra:<cadena> Frec:<número entero> LogProb:<número real>
 */
public class Aprendizaje {

    public Aprendizaje() throws IOException {
        //La siguiente linea asocia a la escena vacia la vista definida en Aprendizaje.fxml
        Parent root = FXMLLoader.load(AprendizjeController.class.getResource("Aprendizaje.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Aprendizaje");
        stage.setScene(new Scene(root, 300, 275));//Define la ventana y sus dimensiones
        stage.show(); //Muestra la ventana
    }
}
