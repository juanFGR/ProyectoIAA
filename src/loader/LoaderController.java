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
 * Created by JuanFGR on 02/05/2014.
 */
public class LoaderController {

    public class TypeOfCorpus {
        public static final int CORPUSNEGATIVE = 0;
        public static final int CORPUSPOSITIVE = 1;
    }

Vocabulary vocabulary = new Vocabulary();

    @FXML
    Pane content;
    @FXML
    Button loadNegative,loadPositive,generateVocabulary;
    @FXML
    ProgressIndicator progressPositive;

    @FXML
    protected void gotoAprendizaje(ActionEvent event) throws IOException {
        BasicConstants._vocabulary = getVocabulary();
        new Aprendizaje();

    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    @FXML
    protected void GenerateVocabulary(ActionEvent event) throws FileNotFoundException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(file.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine;

        try {
            while((aLine = br.readLine()) != null){
               vocabulary.addToVocabulary(aLine);
            }

            vocabulary.printFile(file.getParent());



        } catch (IOException e) {
            e.printStackTrace();
        }
   //    vocabulary.printVector();
        System.out.println("------------------------------>>>>>FIN DE VOCABULARIO");
    }


    @FXML
    protected void LoadPositive(ActionEvent event)  {

        for (int i = 0; i < 50; i++) {
            progressPositive.setProgress(i);
        }

    directoryFunctionForLoadCorpus(TypeOfCorpus.CORPUSPOSITIVE);
    }

    private void directoryFunctionForLoadCorpus(int typeOfCorpus) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");
        File defaultDirectory = new File("c:/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(stage);



        if (selectedDirectory != null) {
            File[] listOfFiles = selectedDirectory.listFiles();
            try {
                String nameOfCorpus = null;
                if (typeOfCorpus == TypeOfCorpus.CORPUSPOSITIVE){
                    nameOfCorpus = selectedDirectory.getParent() + "\\corpusPos";
                }else if (typeOfCorpus == TypeOfCorpus.CORPUSNEGATIVE){
                    nameOfCorpus = selectedDirectory.getParent() + "\\corpusNeg";
                }

                FileWriter fileWritter = new FileWriter(selectedDirectory.getParent()+ "\\corpusTodo",true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


                PrintWriter writer = new PrintWriter(nameOfCorpus, "UTF-8");

                for (int i = 0; i < listOfFiles.length; i++) {
                    File file = listOfFiles[i];
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        try {
                            String content = FileUtils.readFileToString(file);
                            writer.println("Texto: " + content);
                            bufferWritter.write("Texto: " + content + "\n");

                        } catch (Exception e){}
                    }
                }
                writer.close();
                bufferWritter.close();
            } catch (Exception e){}
        }
    }


    @FXML
    protected void LoadNegative(ActionEvent event) {
        directoryFunctionForLoadCorpus(TypeOfCorpus.CORPUSNEGATIVE);

    }

}
