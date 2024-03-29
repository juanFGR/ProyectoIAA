package validacion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.BasicConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Created by JuanFGR & Gonzalo J. García Martín on 02/05/2014.
 */
public class ValidacionController {

    public class TypeOfLoad {
        public static final int LEARNPOSITIVE = 0;
        public static final int LEARNNEGATIVE = 1;
        public static final int LOADCORPUS = 2;
    }

    public static final String DESCONOCIDO = "<UNK>";
    ArrayList <String> arraylistclasify = new ArrayList<String>();
    ArrayList <String> arraylistclassifyok = new ArrayList<String>();
    String selectedDirectory;

    @FXML    Pane content;
    @FXML      ProgressIndicator progressClassify,progressClassifyOK;

    File fileCorpus, fileClassify, fileClassifyOK;



    @FXML
    protected void LoadClassify (ActionEvent event)throws FileNotFoundException{
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Classify");
        fileClassify = fileChooser.showOpenDialog(stage);

        selectedDirectory = fileClassify.getParent();
        System.out.println("--->"+selectedDirectory);
        FileReader fr = new FileReader(fileClassify.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine;
        String [] column;

        try {
            while((aLine = br.readLine()) != null){
                column = aLine.split(" ");
                arraylistclasify.add(column[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressClassify.setProgress(100);

    }

    @FXML
    protected void LoadClassify_ok (ActionEvent event)throws FileNotFoundException{
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Classify OK");
        fileClassifyOK = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(fileClassifyOK.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine, column[];

        try {
            while((aLine = br.readLine()) != null){
                column = aLine.split(" ");
                arraylistclassifyok.add(column[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressClassifyOK.setProgress(100);

    }

    @FXML
    protected void execCode(ActionEvent event) throws FileNotFoundException {
        System.out.println(BasicConstants._vocabulary._vocabularyMap.size());

        String aLine, words[], clase;
        int conterr = 0, numText = 0;

        try {
            //Escribiendo el fichero de Error
            FileWriter fileWritter = new FileWriter(selectedDirectory + "\\error",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            String nameOfCorpus = selectedDirectory + "\\error";
            PrintWriter writer = new PrintWriter(nameOfCorpus, "UTF-8");
            System.out.println(nameOfCorpus);
            numText = arraylistclasify.size();
            for (int i = 0; i < arraylistclasify.size(); i++) {
                if (!arraylistclasify.get(i).equals(arraylistclassifyok.get(i))){
                    conterr++;
                }//if
            }//for
            double error = ((double)conterr/(double)numText) * 100;
            double rendimiento = (1 - ((double)conterr/(double)numText)) * 100;
            writer.println("Error del sistema: " + error + "%");
            writer.println("Rendimiento del sistema: " + rendimiento + "%");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        progressClassify.setProgress(100);

    }

}
