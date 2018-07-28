package com.gusmendez.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class ReadUtil {
    public static String readString() {
        String cadena ="";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            cadena = br.readLine();
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return(cadena);
    }


    public static int readInt(){
        try {
            return(Integer.parseInt(readString()));
        } catch(NumberFormatException e) {
            return(Integer.MIN_VALUE);
        }
    }

    public static boolean isNumber(String word){
        try {
            Integer.parseInt(word);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }


}
