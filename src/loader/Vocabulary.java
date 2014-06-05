package loader;

import main.BasicConstants;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by JuanFGR on 30/05/2014.
 */
public class Vocabulary {
    public TreeMap<String,Integer> _vocabularyMap = new TreeMap<String, Integer>();

   public void addWord(String word){

       if(  _vocabularyMap.containsKey(word)){
           _vocabularyMap.put(word,( _vocabularyMap.get(word)+1));
       }else{
           _vocabularyMap.put(word,1);
       }
   }

    public static final String REG_EXP = "(\\W)";

    public void addToVocabulary(String line){
        String [] buff = line.split(" ");
        String stringTmp = null;
        for (int i = 1; i < buff.length; i++) {
            stringTmp = buff[i].replaceAll(REG_EXP,"").replaceAll("\\s+","").replaceAll("_+","");
            if(stringTmp.length()>0 && !stringTmp.contains(" "))
                addWord(buff[i].replaceAll(REG_EXP,"").replaceAll("\\s+","").replaceAll("_+",""));

        }
    }

   /* public void printVector(){
        for (int i = 0; i < _vocabulary.size(); i++) {
            System.out.println(_vocabulary.get(i)+" || "+_vocabularyMap.get(_vocabulary.get(i)));
        }
    }*/


    public void printFile(String parent) {

        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(parent+ "\\Vocabulario",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write("Numero de palabras: "+ _vocabularyMap.size()+"\n");
            Set<String> keySet =  _vocabularyMap.keySet();
            for (String word:keySet) {
                        try {
                            if (!word.contains(" "))
                                bufferWritter.write("Palabra: "+word+"\n");
                        } catch (Exception e){}
            }
            bufferWritter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
