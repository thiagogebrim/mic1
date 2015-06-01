/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Machine.MIR;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Thiago G Goncalves
 */
public class FileReader {

    private final ArrayList<String> inputCode = new ArrayList<>();
    private final ArrayList<MIR> instruction = new ArrayList<>();

    private String output;
    
    private static final FileReader instance = new FileReader();
    
    private FileReader(){
        
    }
    public static FileReader getInstance(){
        return instance;
    }
    
    public ArrayList<MIR> opCode() {
        return this.instruction;
    }

    public String getOutput() {
        return output;
    }
    
    
 
    public void inputString(String input){
        String[] tokens = input.split("\n");
        for(String token : tokens){
            token = token.trim();
            if(!token.isEmpty()){
               this.inputCode.add(token);
            }
        }
        this.output = input;
    }
    public void readText(String text){
        this.inputCode.clear();
        output = "";
        Scanner file = new Scanner(text);
        while (file.hasNextLine()) {
            String temp_c = file.nextLine();
            
            temp_c = temp_c.trim();
            if (!temp_c.isEmpty()) {
            this.inputCode.add(temp_c);
                output += temp_c + "\n";
            }
        }
    }
    public void readFile(String fileName) {
        this.inputCode.clear();
        output = "";
        InputStream infile = FileReader.class.getResourceAsStream(fileName);
        Scanner file = new Scanner(infile);
        while (file.hasNextLine()) {
            String temp_c = file.nextLine();
            this.inputCode.add(temp_c);
            temp_c = temp_c.trim();
            if (!temp_c.isEmpty()) {
                output += temp_c + "\n";
            }
        }
    }
}
