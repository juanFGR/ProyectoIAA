package aprendizaje;

import clasificacion.Clasificacion;
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

import java.io.*;
import java.util.Set;


/**
 * Created by JuanFGR on 02/05/2014.
 */
public class AprendizjeController {

    public class TypeOfLoad {
        public static final int LOADCORPUSPOSITIVE = 0;
        public static final int LOADCORPUSNEGATIVE = 1;
        public static final int LOADVOCABULARY = 2;
    }


    Vocabulary TreemapForcorpus_Pos = new Vocabulary();
    Vocabulary TreemapForcorpus_Neg = new Vocabulary();

    @FXML
    Pane content;
    @FXML
    Button loadNegative,loadCorpus,generateVocabulary;
    @FXML
    ProgressIndicator progressPositive;

    File fileCorpus;

    @FXML
    protected void LoadPCorpus(ActionEvent event) throws FileNotFoundException {
            System.out.println(BasicConstants._vocabulary._vocabularyMap.size());


        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
         fileCorpus = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(fileCorpus.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        String aLine;

        try {
            while((aLine = br.readLine()) != null){
                TreemapForcorpus_Pos.addToVocabulary(aLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    protected void goToClasificacion(ActionEvent event)throws IOException{
        new Clasificacion();
    }


   @FXML
    protected void generateLearning(ActionEvent event)  {
        System.out.println(BasicConstants._vocabulary._vocabularyMap.size());
       System.out.println(TreemapForcorpus_Pos._vocabularyMap.size());

       Set<String> listOfKeys = BasicConstants._vocabulary._vocabularyMap.keySet();

    double Prob = 0;

       FileWriter fileWritter = null;
       try {
           fileWritter = new FileWriter(fileCorpus.getParent() + "\\Aprendizaje", true);
           BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


           for (int it=0;it<listOfKeys.size();it++) {
               if(TreemapForcorpus_Pos._vocabularyMap.containsKey(listOfKeys.toArray()[it])){
                   Prob = Math.log1p(TreemapForcorpus_Pos._vocabularyMap.get(listOfKeys.toArray()[it])/(TreemapForcorpus_Pos._vocabularyMap.size()+countWords(listOfKeys)));
                   try {
                       bufferWritter.write("Palabra:"+listOfKeys.toArray()[it]+" Frec:"+   TreemapForcorpus_Pos._vocabularyMap.get(listOfKeys.toArray()[it])+" LogProb"+Prob+"\n");
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
           bufferWritter.close();



       }catch (IOException e) {
           e.printStackTrace();
       }




    }




    private Integer countWords(Set<String> listOfKeys) {
        int cont=0;
        for (String it:listOfKeys) {
            cont += BasicConstants._vocabulary._vocabularyMap.get(it);
        }
        return cont;
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
                if (typeOfCorpus == TypeOfLoad.LOADCORPUSPOSITIVE){
                    nameOfCorpus = selectedDirectory.getParent() + "\\corpusPos";
                }else if (typeOfCorpus == TypeOfLoad.LOADCORPUSNEGATIVE){
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
    //    directoryFunctionForLoadCorpus(TypeOfCorpus.CORPUSNEGATIVE);

    }

}
