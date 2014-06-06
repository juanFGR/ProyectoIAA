package clasificacion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loader.Vocabulary;
import main.BasicConstants;
import org.apache.commons.io.FileUtils;
import validacion.Validacion;

import java.io.*;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 */
public class ClasificacionController {

    public class TypeOfLoad {
        public static final int LEARNPOSITIVE = 0;
        public static final int LEARNNEGATIVE = 1;
        public static final int LOADCORPUS = 2;
    }

    public static final String DESCONOCIDO = "<UNK>";
    TreeMap<String, String> Treemappos = new TreeMap<String, String>();
    TreeMap<String, String> Treemapneg = new TreeMap<String, String>();
    String selectedDirectory;

    @FXML    Pane content;
    @FXML      ProgressIndicator progressLoadCorpus,progressLoadAprendizajePOS,progressLoadAprendizajeNEG;

    File fileCorpus, fileLearnPositive, fileLearnNegative;


    @FXML
    protected void goToEvaluation(ActionEvent event) throws IOException {
        new Validacion();
    }

    @FXML
    protected void LoadLearnPositive (ActionEvent event)throws FileNotFoundException{
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Positive Learning");
        fileLearnPositive = fileChooser.showOpenDialog(stage);

        selectedDirectory = fileLearnPositive.getParent();
        System.out.println("--->"+selectedDirectory);
        FileReader fr = new FileReader(fileLearnPositive.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine;
        String [] colum;

        try {
            while((aLine = br.readLine()) != null){
                colum = aLine.split(" ");
                Treemappos.put(colum[0].split(":")[1], colum[2].split(":")[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressLoadAprendizajePOS.setProgress(100);

    }

    @FXML
    protected void LoadLearnNegative (ActionEvent event)throws FileNotFoundException{
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Negative Learning");
        fileLearnNegative = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(fileLearnNegative.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine, colum[];

        try {
            while((aLine = br.readLine()) != null){
                colum = aLine.split(" ");
                Treemapneg.put(colum[0].split(":")[1], colum[2].split(":")[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressLoadAprendizajeNEG.setProgress(100);

    }

    @FXML
    protected void LoadCorpusandClassify(ActionEvent event) throws FileNotFoundException {
        System.out.println(BasicConstants._vocabulary._vocabularyMap.size());


        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load corpus to classify");
        fileCorpus = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(fileCorpus.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine, words[], clase;
        double probPos = 0.0, probNeg = 0.0;

        try {
            //Escribiendo el fichero de clasificación
            FileWriter fileWritter = new FileWriter(selectedDirectory + "\\clasificacion",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            String nameOfCorpus = selectedDirectory + "\\clasificacion";
            PrintWriter writer = new PrintWriter(nameOfCorpus, "UTF-8");
            System.out.println(nameOfCorpus);
            while((aLine = br.readLine()) != null){
                //TreemapForcorpus_Pos.addToVocabulary(aLine);
                aLine = aLine.split(":")[1];
                words = aLine.split(" ");
                probNeg = probPos = 0.0;
                //Calculo de Probabilidades
                for (String palabra: words) {
                    if(Treemappos.containsKey(palabra)) {
                        probPos += Double.parseDouble(Treemappos.get(palabra));
                    }
                    else {
                        probPos += Double.parseDouble(Treemappos.get(DESCONOCIDO));
                    }//else
                    if(Treemapneg.containsKey(palabra)) {
                        probNeg += Double.parseDouble(Treemapneg.get(palabra));
                    }
                    else {
                        probNeg += Double.parseDouble(Treemapneg.get(DESCONOCIDO));
                    }//else
                }//for
                if (Math.max(probPos, probNeg) == probPos) {
                    clase = "pos";
                }//if
                else {
                    clase = "neg";
                }//else
                writer.println("Clase: "+ clase + " Texto: " + aLine);
            }//while
            writer.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        progressLoadCorpus.setProgress(100);

    }

}
