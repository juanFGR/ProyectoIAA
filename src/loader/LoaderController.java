package loader;

import aprendizaje.Aprendizaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.BasicConstants;
import org.apache.commons.io.FileUtils;
import java.io.*;


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
public class LoaderController {

    public class TypeOfCorpus {
        public static final int CORPUSNEGATIVE = 0;
        public static final int CORPUSPOSITIVE = 1;
    }

    Vocabulary vocabulary = new Vocabulary();

    @FXML Pane content;
    @FXML Button loadNegative,loadPositive,generateVocabulary;
    @FXML ProgressIndicator progressPositive,progressNegative,vocabularyLoaded;


    @FXML
    protected void LoadPositive(ActionEvent event)  {
        directoryFunctionForLoadCorpus(TypeOfCorpus.CORPUSPOSITIVE);
        progressPositive.setProgress(100);
    }

    @FXML
    protected void LoadNegative(ActionEvent event) {
        directoryFunctionForLoadCorpus(TypeOfCorpus.CORPUSNEGATIVE);
        progressNegative.setProgress(100);
    }




    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    @FXML
    protected void GenerateVocabulary(ActionEvent event) throws FileNotFoundException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Corpus to Build Vocabulary");
        File file = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(file.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine;

        try {
            while((aLine = br.readLine()) != null){
                vocabulary.addToVocabulary(aLine);
            }
            //vocabulary.addToVocabulary("<UNKNOWN>");//Añadida palabra desconocida al bocabulario
            vocabulary.printFile(file.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        vocabularyLoaded.setProgress(100);

    }



    @FXML
    protected void gotoAprendizaje(ActionEvent event) throws IOException {
        BasicConstants._vocabulary = getVocabulary();
        new Aprendizaje();

    }



    private void directoryFunctionForLoadCorpus(int typeOfCorpus) {

        String conjunto = "", clase = "";

        if (typeOfCorpus == TypeOfCorpus.CORPUSPOSITIVE){
            conjunto = "Positive";
        }else if (typeOfCorpus == TypeOfCorpus.CORPUSNEGATIVE){
            conjunto = "Negative";
        }

        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Load " + conjunto + " Set");
        File defaultDirectory = new File("C:/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(stage);



        if (selectedDirectory != null) {
            File[] listOfFiles = selectedDirectory.listFiles();
            try {
                String nameOfCorpus = null;
                if (typeOfCorpus == TypeOfCorpus.CORPUSPOSITIVE){
                    nameOfCorpus = selectedDirectory.getParent() + "\\corpusPos";
                    clase = "pos";
                    BasicConstants.probpos = listOfFiles.length;
                }else if (typeOfCorpus == TypeOfCorpus.CORPUSNEGATIVE){
                    nameOfCorpus = selectedDirectory.getParent() + "\\corpusNeg";
                    clase = "neg";
                    BasicConstants.probneg = listOfFiles.length;
                }

                FileWriter fileWritter = new FileWriter(selectedDirectory.getParent()+ "\\corpusTodo",true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                //Creando clasificación ok
                FileWriter fileWritter2 = new FileWriter(selectedDirectory.getParent()+ "\\clasificacion_OK",true);
                BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);

                PrintWriter writer = new PrintWriter(nameOfCorpus, "UTF-8");


                for (int i = 0; i < listOfFiles.length; i++) {
                    File file = listOfFiles[i];
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        try {
                            String content = FileUtils.readFileToString(file);
                            writer.println("Texto: " + content);
                            bufferWritter.write("Texto: " + content + "\n");
                            bufferWritter2.write("Clase: "+ clase + " Texto: " + content + "\n");

                        } catch (Exception e){}
                    }
                }
                if ((BasicConstants.probpos != 0.0) && (BasicConstants.probneg != 0.0)){
                    double suma = BasicConstants.probpos + BasicConstants.probneg;
                    BasicConstants.probpos = Math.log10((double)BasicConstants.probpos/(double) suma);
                    BasicConstants.probneg = Math.log10((double)BasicConstants.probneg /(double) suma);
                }
                writer.close();
                bufferWritter.close();
                bufferWritter2.close();
            } catch (Exception e){}
        }
    }





}
