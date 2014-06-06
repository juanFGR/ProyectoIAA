package main;

import loader.Vocabulary;

import java.util.TreeMap;

/**
 * Esta clase contiene variables estaticas que son accedidas de forma global por neustros programas y su principal
 * objetivo es hacer accesible desde cualqueir metodo al vocabulario obtenido
 *
 * @author Created by JuanFGR & Gonzalo J. García Martín on 02/06/2014.
 */
public  class  BasicConstants {

    //Vocabulario obtenido
    public static  Vocabulary _vocabulary = new Vocabulary();
    public static double probpos = 0.0;
    public static double probneg = 0.0;
}
