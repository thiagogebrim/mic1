package GUI;

import Machine.MIR;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Decoder {

    ArrayList<MIR> opCodeContainer = new ArrayList<>();
    HashMap<String, String> registers = new HashMap<>();
    BitSet micro = new BitSet(32);
    Queue<String> codeQueue = new LinkedList<>();
    String answerOp = "";

    public Decoder() {
        // set dictionary for keywords
        registers.put("pc", "0000");
        registers.put("ac", "0001");
        registers.put("sp", "0010");
        registers.put("ir", "0011");
        registers.put("tir", "0100");
        registers.put("0", "0101");
        registers.put("1", "0110");
        registers.put("-1", "0111");
        registers.put("amask", "1000");
        registers.put("smask", "1001");
        registers.put("a", "1010"); // a registry
        registers.put("b", "1011");
        registers.put("c", "1100");
        registers.put("d", "1101");
        registers.put("e", "1110");
        registers.put("f", "1111");
    }

    //check for a space after number Ex: 0: ac
    public String[] checkColon(String[] splitForNumberID){
        String[] num = null;
        if(!(splitForNumberID[0].charAt(splitForNumberID[0].length()-1) == ':')){
            num = splitForNumberID[0].split(":");  
            splitForNumberID = num;
        } 
        return splitForNumberID;
    }
    
    public void populateQueueWithLine(String line) {
        if (!line.isEmpty() && Character.isDigit(line.charAt(0))) {
            if(line.charAt(line.length()-1) == ':'){
                return;
            }
            String[] splitForNumberID = line.split(" ");
            String[] num = checkColon(splitForNumberID);
            String newLine = num[1];
            if (splitForNumberID.length == 1) {
                line = newLine;
            } else {
                for (int x = 1; x < splitForNumberID.length; x++) {
                    newLine += " " + splitForNumberID[x];
                    line = newLine;
                }
            }
        }

        String[] segments = line.split(";");
        for (String segment : segments) {
            segment = segment.trim();
            if (!(segment.isEmpty())) {
                // System.out.println("segment: " + segment);
                this.codeQueue.add(segment);
            }
        }
        this.codeQueue.add("EOL");
    }

    public void populateQueueWithFile() {
        InputStream infile = Decoder.class.getResourceAsStream("macro.txt");
        Scanner file = new Scanner(infile);
        while (file.hasNextLine()) {
            String[] lineSplitter = file.nextLine().split("\\r?\\n");
            for (String line : lineSplitter) {
                populateQueueWithLine(line);
            }
            // System.out.println(codeQueue);
        }
        file.close();
    }

    public String decode(String malCode) {
        this.micro = new BitSet();
        this.populateQueueWithLine(malCode);
        // System.out.println("Queue: "+this.codeQueue);
        String malLine = "";
        while (!this.codeQueue.isEmpty()) {
            String macroSegment = this.codeQueue.poll();

            if (!(macroSegment.equals("EOL"))) {
                malLine += macroSegment;
                containOperator(macroSegment);
                System.out.println(macroSegment);
                System.out.println(this.convertBitSetToString(micro));
            } else {
                MIR code = new MIR(this.convertBitSetToString(micro), malLine);
                malLine = "";
                //answerOp = convertBitSetToString(micro);
                //System.out.println(this.convertBitSetToString(micro));
                //System.out.println();
                this.opCodeContainer.add(code);
                answerOp = convertBitSetToString(micro);
                this.micro = new BitSet();

            }

            //System.out.println(macroSegment);
            //System.out.println(this.convertBitSetToString(micro));
        }
        return answerOp;
        //System.out.println(this.convertBitSetToString(micro));
        //System.out.println(this.opCodeContainer);
    }

    public void containOperator(String macroSegment) {
        String[] array;
        array = macroSegment.split(" ");
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(":=")) {
                if (array.length == 3) {
                    if (array[2].startsWith("ls") || array[2].startsWith("rs")
                            || array[2].startsWith("ba")
                            || array[2].startsWith("in")) {
                        containAction(array);
                    } else {
                        noAddition(array);
                    }
                } else if ((array.length > 3)) { // length must be 5 to add
                    containAction(array); // lshift, rshift, inv, band
                    for (int k = 0; k < array.length; k++) {
                        array[k] = stripOffBrackets(array[k]);
                        // System.out.println(i + " = "+array[k]);
                    }
                    if (array[3].equals("+")) {
                        if (!containAction(array)) {
                            addingRegisters(array);
                        }
                    }
                }
            }
            noOperator(array);
        }
    }

    public void noAddition(String[] array) {
        String left = array[0];
        if (registers.containsKey(left)) {
            if (array[2].equals("mar")) {
                System.out.println("Cannot decode!");
            } else {
                micro.set(3); // alu = 2
                micro.set(11);
                convertStringToBitSet(registers.get(array[0]), 12);  // c bus
                if (registers.containsKey(array[2])) { // ac := pc
                    // a_latch, amux = 0, alu = 2 (10 binary), shifter = 0, c_bus =
                    // leftHand
                    convertStringToBitSet(registers.get(array[2]), 20); // a_latch
                } else if (array[2].equals("mbr")) { // Ex: ac := mbr (must be mbr)
                    micro.set(0); // amux = 1
                }
            }
        } else { // mbr := pc, mar := ac, alu := ac
            switch (array[0]) {
                case "mbr":
                    // a_latch get the address of rightHand, mbr = true
                    micro.set(7);
                    micro.set(3); //alu = 2
                    convertStringToBitSet(registers.get(array[2]), 20);
                    break;
                case "mar": // mar := ac
                    // b_latch get the address of rightHand, mar = true
                    micro.set(8);
                    convertStringToBitSet(registers.get(array[2]), 16);
                    break;
                case "alu":
                    // b_latch or a_latch, alu = 2 (10 binary), amux = 0
                    micro.set(3); // alu
                    convertStringToBitSet(registers.get(array[2]), 20);
                    break;
                default:
                    break;
            }
        }
        // printStringArray(array);

    }

    public void ifStatement(String[] array) {
        // implement the goto method by calling noOperator again
        // if n/z then goto #;

        String[] gotoStrings = new String[2];
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("n") || array[i].equals("N")) {
                micro.set(2); // cond = 1
            } else if (array[i].equals("z") || array[i].equals("Z")) {
                micro.set(1); // cond = 2
            }

            /*if (array[i].equals("goto")) {
             gotoStrings[0] = array[i];
             gotoStrings[1] = array[i + 1];
             gotoFunc(gotoStrings);
             }*/
        }

        //array[1] not it
        String addr = convertIntToBinary(array[array.length - 1]);
        convertStringToBitSet(addr, 24);
    }

    public void gotoFunc(String[] array) {
        micro.set(1); // cond = 3
        micro.set(2);
        String addr = convertIntToBinary(array[1]);
        convertStringToBitSet(addr, 24);
    }

    public String convertIntToBinary(String addr) {
        return String.format("%8s",
                Integer.toBinaryString(Integer.valueOf(addr)))
                .replace(' ', '0');
    }

    public void noOperator(String[] array) {
        // segment has no := (goto 0; rd; wr; if statement)
        String start = array[0];
        switch (start) {
            case "rd":
                micro.set(9);
                break;
            case "wr":
                micro.set(10);
                break;
            case "goto":
                gotoFunc(array);
                break;
            case "if":
                ifStatement(array);
                break;
            default:
                break;
        }
    }

    public void addingRegisters(String[] array) { // ac := a + ac
        // ALU = 0 and only store to the register in form c = a + b
        // String[] strArray = containBrackets(array);
        // (alu, register, mbr) := (-1) + sp
        convertStringToBitSet(registers.get(array[2]), 16);
        convertStringToBitSet(registers.get(array[4]), 20);
        if (registers.containsKey(array[0])) { // register := (-1) + sp
            micro.set(11);
            convertStringToBitSet(registers.get(array[0]), 12);
        } else if (array[0].equals("mbr")) {
            micro.set(7);
        }
        if (array[2].equals("mbr") || array[4].equals("mbr")) {
            micro.set(0);
        }
    }

    public boolean containAction(String[] array) { // a := inv(mbr)
        // System.out.println(array[2] + ": is string with bracket");
        if ((array.length >= 3) && (array[2].length() >= 3)
                && (!(array[2].substring(0, 1).equals("(")))) {
            // inv=inv, lsh=lshift, rsh=rshift, ban=band
            printStringArray(array);
            String startAction = String.valueOf(array[2].charAt(0))
                    + String.valueOf(array[2].charAt(1));
            // System.out.println("start "+startAction);
            switch (startAction) {
                case "in": // set ALU to 3(11 binary)
                    String inner = stripOffBrackets(array[2]);
                    // System.out.println("inner: "+ inner);
                    if (registers.containsKey(inner)) {
                        // checking inner register (register)
                        invertInnerRegister(array, inner);
                    } else { // invert "mbr" to store at registry or mbr itself
                        invertInner(array, inner);
                    }
                    return true;
                case "ls":
                    micro.set(5); // sh = 2
                    shifting(array);
                    return true;
                case "rs":
                    micro.set(6); // sh = 1
                    shifting(array);
                    return true;
                case "ba": // set ALU to 1 = 01 in binary
                    band(array);
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    public void band(String[] array) {
        // alu = band(ir, ir), register = band(ir, ir)
        if (array.length > 3) {
            String first = array[2].substring(array[2].indexOf('(') + 1,
                    array[2].indexOf(','));
            String second = array[3].substring(0, array[3].indexOf(')'));

            // alu = band(ir, ir)
            micro.set(4);
            convertStringToBitSet(registers.get(first), 16); // a_bus
            convertStringToBitSet(registers.get(second), 20); // b_bus
            if (registers.containsKey(array[0])) { // register = band(ir, ir)
                micro.set(11);
                convertStringToBitSet(registers.get(array[0]), 12); // c_bus
            } else if (array[0].equals("mbr")) { // mbr = band(ir, ir)
                micro.set(7);
            }
        }
    }

    public void shifting(String[] array) { // can't store to alu and mar
        if (array.length == 3) { // f := lshift(sp); mbr := lshift(sp)
            String inner = stripOffBrackets(array[2]);
            micro.set(3); // alu = 2 (10 in binary)
            convertStringToBitSet(registers.get(inner), 20); // a_bus
            if (registers.containsKey(array[0])) { // f := lshift(sp)
                micro.set(11);
                convertStringToBitSet(registers.get(array[0]), 12); // c_bus
            } else { // mbr := lshift(sp)
                micro.set(7);
            }
        } else {
            // f = lshift(ir + ir); mbr = lshift(ir + ir)
            // System.out.println("second: "+array[2]);
            String first = array[2].substring(array[2].indexOf('(') + 1);
            String second = array[4].substring(0, array[4].indexOf(')'));

            convertStringToBitSet(registers.get(first), 20);
            convertStringToBitSet(registers.get(second), 16);
            if (registers.containsKey(array[0])) { // f = lshift(ir + ir)
                micro.set(11);
                convertStringToBitSet(registers.get(array[0]), 12); // c_bus
            } else { // mbr = lshift(ir + ir)
                micro.set(7); // mbr = 1
            }
        }
    }

    public void invertInnerRegister(String[] array, String innerRegister) {
        // can store to alu, mbr, register
        micro.set(3); // alu = 3 (11 binary)
        micro.set(4);
        convertStringToBitSet(registers.get(innerRegister), 20); // a_bus =
        // innerR
        if (registers.containsKey(array[0])) { // f := inv(sp)
            // a_bus, c_bus, amux = 0, alu = 3
            micro.set(11);
            convertStringToBitSet(registers.get(array[0]), 12); // c_bus =
            // address of
            // leftHand
        } else if (array[0].equals("mbr")) { // mbr := inv(sp)
            // a_bus, alu = 3, mbr = 1, amux = 0
            micro.set(7); // mbr = 1
        }
    }

    public void invertInner(String[] array, String inner) {
        // mbr = inv(mbr) or f = inv(mbr)
        if (inner.equals("mbr")) {
            micro.set(0); // amux = 1
            micro.set(3); // alu = 3 (11 in binary)
            micro.set(4);

            if (registers.containsKey(array[0])) { // f = inv(mbr)
                micro.set(11);
                convertStringToBitSet(registers.get(array[0]), 12); // c_bus
            } else {
                micro.set(7); // mbr = 1
            }
        }
    }

    // strip off brackets of (a) -> a
    public String stripOffBrackets(String str) {
        if (str.contains("(") && str.endsWith(")")) {
            str = str.substring(str.indexOf('(') + 1, str.indexOf(')'));
            // System.out.println("After stripping brackets: "+ str);
        }
        return str;
    }

    public String convertBitSetToString(BitSet micro) {
        String binaryString = "";
        for (int i = 0; i < 32; i++) {
            if (micro.get(i)) {
                binaryString += '1';
            } else {
                binaryString += '0';
            }
        }
        return binaryString;
    }

    // convert string and set micro in T/F
    public void convertStringToBitSet(String str, int begin) {
        if (str == null) {
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                micro.set(i + begin);
            }
        }
        // System.out.println(convertBitSetToString(micro));
    }

    public void printStringArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            // System.out.println(array[i] + " ");
        }
    }

    public ArrayList<MIR> getDecodedCode() {
        return this.opCodeContainer;
    }
}
