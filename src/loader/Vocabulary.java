package loader;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by JuanFGR on 30/05/2014.
 */
public class Vocabulary {
    public  HashMap<String,Integer> _vocabularyMap = new HashMap<String, Integer>();
    public Vector<String> _vocabulary = new Vector<String>();

   public void addWord(String word){
       if(_vocabularyMap.containsKey(word)){
           _vocabularyMap.put(word,(_vocabularyMap.get(word)+1));
       }else{
           updateVocabulary(word);
       }
   }

    private void updateVocabulary(String word) {
        _vocabularyMap.put(word,1);
        _vocabulary.add(word);

    }

    public void addToVocabulary(String line){
        String [] buff = line.split(" ");
        for (int i = 1; i < buff.length; i++) {
            addWord(buff[i]);
        }
    }

    public void printVector(){
        for (int i = 0; i < _vocabulary.size(); i++) {
            System.out.println(_vocabulary.get(i)+" || "+_vocabularyMap.get(_vocabulary.get(i)));
        }
    }


    public void printFile(String parent) {
        Collections.sort(_vocabulary);
        HashSet hs = new HashSet();
        hs.addAll(_vocabulary);
        _vocabulary.clear();
        _vocabulary.addAll(hs);

        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(parent+ "\\Vocabulario",true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write("Numero de palabras: "+_vocabulary.size()+"\n");
            for (int i = 0; i < _vocabulary.size(); i++) {

                        try {
                        bufferWritter.write("Palabra: "+_vocabulary.get(i)+"\n");
                    } catch (Exception e){}

            }
            bufferWritter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
