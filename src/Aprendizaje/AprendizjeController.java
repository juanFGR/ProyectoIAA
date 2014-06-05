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
import loader.LoaderController;
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

    @FXML    Pane content;
    @FXML    Button loadNegative,generateVocabulary;

    @FXML ProgressIndicator progressPositive,progressNegative;
    File fileCorpus;

    @FXML
    protected void LoadPCorpusPOS(ActionEvent event) throws FileNotFoundException {
        loader(TypeOfLoad.LOADCORPUSPOSITIVE);
        progressPositive.setProgress(100);
    }

    @FXML
    protected void LoadPCorpusNEG(ActionEvent event) throws FileNotFoundException {
        loader(TypeOfLoad.LOADCORPUSNEGATIVE);
        progressNegative.setProgress(100);
    }

    private void loader(int loader) throws FileNotFoundException  {
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
                if(loader == TypeOfLoad.LOADCORPUSPOSITIVE)
                    TreemapForcorpus_Pos.addToVocabulary(aLine);
                else
                    TreemapForcorpus_Neg.addToVocabulary(aLine);
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

        Set<String> listOfKeys = BasicConstants._vocabulary._vocabularyMap.keySet();

        double Prob = 0.0;
        int unk = 0;

        FileWriter fileWriterPos = null, fileWriterNeg = null;
        try {
            fileWriterPos = new FileWriter(fileCorpus.getParent() + "\\Aprendizajepos", true);
            BufferedWriter bufferWriterPos = new BufferedWriter(fileWriterPos);

            Object [] bufferValues = listOfKeys.toArray();
            for (int it=0;it<listOfKeys.size()/100;it++) {
                //------------POSITIVO--------------------
                if(TreemapForcorpus_Pos._vocabularyMap.containsKey(bufferValues[it])){
                    Prob = Math.log10((double)TreemapForcorpus_Pos._vocabularyMap.get(bufferValues[it])+1/(double)(TreemapForcorpus_Pos._vocabularyMap.size()+countWords(listOfKeys)));
                    try {
                        bufferWriterPos.write("Palabra:"+bufferValues[it]+" Frec:"+   TreemapForcorpus_Pos._vocabularyMap.get(bufferValues[it])+" LogProb:"+Prob+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } //if POS
                else {
                    unk++;
                } //else

            }//for
            Prob = Math.log10((double)(unk+1)/(double)(TreemapForcorpus_Pos._vocabularyMap.size()+countWords(listOfKeys)));
            bufferWriterPos.write("Palabra:<UNK> Frec:" + unk + " Prob:"+Prob);
            bufferWriterPos.close();

            //------------NEGATIVO--------------------
            Prob = 0.0;
            unk = 0;
            fileWriterNeg = new FileWriter(fileCorpus.getParent() + "\\Aprendizajeneg", true);
            BufferedWriter bufferWriterNeg = new BufferedWriter(fileWriterNeg);

            for (int it=0;it<listOfKeys.size()/100;it++) {
                if(TreemapForcorpus_Neg._vocabularyMap.containsKey(bufferValues[it])){
                    Prob = Math.log10((double)TreemapForcorpus_Neg._vocabularyMap.get(bufferValues[it])+1/(double)(TreemapForcorpus_Neg._vocabularyMap.size()+countWords(listOfKeys)));
                    try {
                        bufferWriterNeg.write("Palabra:"+bufferValues[it]+" Frec:"+   TreemapForcorpus_Neg._vocabularyMap.get(bufferValues[it])+" LogProb:"+Prob+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//if NEG
                else{
                    unk++;
                }//else

            }//for
            Prob = Math.log10((double)(unk+1)/(double)(TreemapForcorpus_Pos._vocabularyMap.size()+countWords(listOfKeys)));
            bufferWriterNeg.write("Palabra:<UNK> Frec:" + unk + " Prob:"+Prob);
            bufferWriterNeg.close();


        //try
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
        File defaultDirectory = new File("ficheros/");
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