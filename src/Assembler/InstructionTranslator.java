/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembler;

import static GUI.GUI.InstructionTable;
import java.util.ArrayList;
import java.util.HashMap;

/*! \brief  MAC1 Interpreter Class, Machine level class
 * 
 *  UNDER UPDATES
 *  It will take input machine code and bring it to the MIC1 components.
 *
 * @author Thiago G Goncalves
 */
public class InstructionTranslator {

    private final HashMap<String, Short> keymap = new HashMap<>();
    private final ArrayList<String> NoParemsInstruction = new ArrayList<>();
    private final ArrayList<String> jumpInstruction = new ArrayList<>();
    private static final InstructionTranslator instance = new InstructionTranslator();

    private InstructionTranslator() {
        jumpInstruction.add("JPOS");
        jumpInstruction.add("JZER");
        jumpInstruction.add("JUMP");
        jumpInstruction.add("JNEG");
        jumpInstruction.add("CALL");
         // keymap.put(".LOC", (short)0);

         // NoParemsInstruction.add(".LOC");

        this.parseSupportedInstruction();
    }
    protected void reset(){
        instance.keymap.clear();
        instance.NoParemsInstruction.clear();
         this.parseSupportedInstruction();
    }

    private void parseSupportedInstruction() {
        int row = InstructionTable.getModel().getRowCount();
        for (int i = 0; i < row; i++) {

            String instruction = "";
            short operandValue = 0;
            boolean argument = false;

            next:
            for (int j = 0; j < 2; j++) {
                String data = (String) InstructionTable.getModel().getValueAt(i, j).toString();

                switch (j) {
                    case 0:
                        if (data == null || data.isEmpty()) {

                            break next;
                        }
                        instruction = data.toUpperCase();
                        break;
                    case 1:

                        Object arg = InstructionTable.getModel().getValueAt(i, 2);
                        if (arg == null) {
                            argument = false;
                        } else {

                            int size = ((String) arg).length();
                            argument = size != 0;

                            for (int k = 0; k < size; k++) {
                                data = data + "0";
                            }
                        }
                        operandValue = (short) Integer.parseInt(data, 2);
                        break;
                }

            }

            keymap.put(instruction, operandValue);
            if (!argument) {
                this.NoParemsInstruction.add(instruction);
            }
        }

    }

    protected static InstructionTranslator getInstance() {
        
        return InstructionTranslator.instance;
    }

    protected boolean InstructionHasArgument(String str) {
        if (str == null) {
            return false;
        }
        str = str.toUpperCase();
        if (!isValid(str)) {

            return false;
        }
        for (String temp : NoParemsInstruction) {
            if (str.equals(temp)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isJumpInstruction(String instruction) {
        instruction = instruction.toUpperCase();
        for (String temp : jumpInstruction) {
            if (instruction.equals(temp)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isValid(String st) {
        if (st == null) {
            return false;
        }
        st = st.trim();
        st = st.toUpperCase();

        for (String str : keymap.keySet()) {
            if (str.equals(st)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    protected ArrayList<String> translate(ArrayList<String> instructions) {

        final ArrayList<String> decodedInstruction = new ArrayList<>();
        for (String instruction_temp : instructions) {
            short decoded;
            instruction_temp = instruction_temp.toUpperCase();
            String[] instruction_array = instruction_temp.split(" ");
            
            if (InstructionHasArgument(instruction_array[0])) {
                decoded = keymap.get(instruction_array[0]);
               // System.out.println(decoded);
                decoded += Short.valueOf(instruction_array[1]);
               // System.out.println("ALL : "+decoded);
            } else {
                decoded = keymap.get(instruction_array[0]);
            }
            decodedInstruction.add(decoded + "#" + instruction_temp);
        }
        return decodedInstruction;
    }
}
