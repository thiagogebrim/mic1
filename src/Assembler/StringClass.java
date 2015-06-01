/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembler;

import java.util.ArrayList;

/*! \brief  Belongs to I/O, which is not used in this version
 * 
 *  UNDER UPDATES
 *
 * @author Thiago G Goncalves
 */

public class StringClass {

    final static private StringClass instance = new StringClass();

    private StringClass() {

    }

    static public StringClass getInstance() {
        return StringClass.instance;
    }

    public ArrayList<Short> convertStringToNumbers(String str_string) {
        String[] str_string_a = str_string.split("\"");
        str_string = str_string_a[1];
        str_string = str_string + "\0";
        ArrayList<Short> data = new ArrayList<>();
        char[] arry = str_string.toCharArray();

        for (int i = 0; i < arry.length; i = i + 2) {
            String str = "";
            try {
                str = fill(Integer.toBinaryString((int) arry[i]));
<<<<<<< HEAD
               // System.out.println(str + "  " + arry[i]);
                str = fill(Integer.toBinaryString((int) arry[i + 1])) + str;
               // System.out.println(str + "  " + arry[i + 1]);
=======
                System.out.println(str + "  " + arry[i]);
                str = fill(Integer.toBinaryString((int) arry[i + 1])) + str;
                System.out.println(str + "  " + arry[i + 1]);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                str = fill("") + str;
                //   System.out.println(str  );
            }
           // System.out.println(str);

            short decimalValue = (short) Integer.parseInt(str, 2);
<<<<<<< HEAD
          //  System.out.println(decimalValue + " " + str);
=======
            System.out.println(decimalValue + " " + str);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
            data.add(decimalValue);
        }
        return data;
    }

    public String stringBuild(String[] str, int begin, int end) {
        String string = "";
        for (int i = begin; i < end + 1; i++) {
            string += " " + str[i];
        }
        return string;
    }

    private String fill(String str) {
        if (str.length() < 8) {
            return fill("0" + str);
        }
        return str;
    }

    public int extractString(String[] data, int location) {
        int asterisk1 = data[location].indexOf('\"');
        boolean hasTwoAsterisks = asterisk1 != -1 && data[location].indexOf('\"', asterisk1 + 1) != -1;
        if (hasTwoAsterisks) {
            return location;
        }
        if (data[location].contains("\"")) {
            for (int i = location + 1; i < data.length; i++) {
                if (data[i].contains("\"")) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int extractInteger(String[] data, int location) {
        int i;
        for (i = location; i < data.length; i++) {
            if (!InstructionTranslator.getInstance().isNumeric(data[i])) {
                return i - 1;
            }
        }
        if (i == data.length) {
            return i - 1;
        } else {
            return -1;
        }
    }
}
