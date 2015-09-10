package kalkulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.*;

public class PDACFG {
    
    private int     jumstate;
    private int     jumtransisi;
    private String  transisi[];
    
    
    public void open(String namafile){
        File file = new File(namafile);
        BufferedReader reader = null;
        String ibuff[];
        int size, i, j;
        
        transisi = new String[2000];
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            text = reader.readLine();
            i = 0;
            size = 0;
            
            while (text != null) {
                transisi[i] = text;
                
                
                Pattern p = Pattern.compile("\\^\\(Q([\\d]),([\\s\\S]+),([\\s\\S])\\)=\\{\\(Q([\\d]),([\\s\\S]+)\\)\\}");
                Matcher mat = p.matcher(text);
                mat.matches();
                
                if (Integer.parseInt(mat.group(1)) > size) {
                    size = Integer.parseInt(mat.group(1));
                }

                if (Integer.parseInt(mat.group(4)) > size) {
                    size = Integer.parseInt(mat.group(4));
                }
                
                text = reader.readLine();
                i++;
            }
            
            jumstate = size;
            jumtransisi = i;
            
            
        } catch (FileNotFoundException e) {
            
            //e.printStackTrace();
        } catch (IOException e) {
            
            //e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }
    
    public void convert (String output){
        int i, j, k;
        
        try{
            PrintWriter writer = new PrintWriter(output, "UTF-8");
                        
            for (i=0; i<jumtransisi; i++){
                Pattern p = Pattern.compile("\\^\\(Q([\\d]),([\\s\\S]+),([\\s\\S])\\)=\\{\\(Q([\\d]),([\\s\\S]+)\\)\\}");
                Matcher mat = p.matcher(transisi[i]);
                mat.matches();

                if ("~".equals(mat.group(5))){
                    writer.println("[Q" + mat.group(1)  + " " + mat.group(3) + " Q" + mat.group(4) +   "]\t-> " + mat.group(2));
                    //out = out + "[Q" + mat.group(1)  + " " + mat.group(3) + " Q" + mat.group(4) +   "]\t-> " + mat.group(2) + "\n";
                } else if(mat.group(5).endsWith(mat.group(3)) && !mat.group(5).equals(mat.group(3))){
                    for(j=0; j<= jumstate; j++){
                        writer.print("[Q" + mat.group(1) + " " + mat.group(3) + " " + j + "]\t-> " + mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " 0][0 " + mat.group(2) + " " + j + "] ");
                        //out = out + "[Q" + mat.group(1) + " " + mat.group(3) + " " + j + "]\t-> " + mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " 0][0 " + mat.group(2) + " " + j + "] ";

                        for(k=1; k<= jumstate; k++){
                            writer.print("| " +  mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " " + k + "][" + k + " " + mat.group(2) + " " + j + "] ");
                            //out = out + "| " +  mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " " + k + "][" + k + " " + mat.group(2) + " " + j + "] ";
                        }
                        
                        writer.println("");

                        //out = out + "\n";
                    }
                }else{
                    for(j=0; j<= jumstate; j++){
                        writer.println("[Q" + mat.group(1) + " " + mat.group(3) + " " + j + "]\t-> " + mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " " + j + "]");
                        //out = out + "[Q" + mat.group(1) + " " + mat.group(3) + " " + j + "]\t-> " + mat.group(2) + " [Q" + mat.group(4) + " " + mat.group(5) + " " + j + "]\n";
                    }
                }
            }
            
            writer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
        
    }
}
