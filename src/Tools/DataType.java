/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import EnumerationData.ConvertEnum;
import EnumerationData.ComponentEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Tools.MIC1.objectLock;

/*! \brief Holds the data type inside of the MIC1 components
 *
 * @author Thiago Gebrim Goncalves
 */
public class DataType {

    private boolean isAscii = false;
    private boolean isInstruction = false;
    final protected ComponentEnum type;
    static private ConvertEnum convert = ConvertEnum.DECIMAL;
    private Short data = 0;
    public boolean isComponentStep = false;
    private String instruction;
    
    /**
     * 
     * @param place
     */
    public DataType(ComponentEnum place) {
        this.type = place;
     
    }

    public void setIsComponentStep(boolean isComponentStep) {
        this.isComponentStep = isComponentStep;
    }

    
    /**
     *
     * @return will return the private data field of the object type short
     */
    public short getData() {
        return data;
    }
    protected void setDataRaw(short data){
        this.data = data;
    }
    public void setData(short data) {
        this.data = data;

        if (MIC1.step && isComponentStep) {
            synchronized (objectLock) {
                try {
                    objectLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DataType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void setConvert(ConvertEnum aConvert) {
        convert = aConvert;
    }
  

    protected void transformDataTo12bitContainer() {
        String decimal = Integer.toBinaryString(0x10000 | data).substring(1);
        if (data < 0) {
            decimal = decimal.substring(19);
        } else {
            decimal = decimal.substring(4);
        }
        data = (short) Integer.parseInt(decimal, 2);
    }

    public boolean isIsAscii() {
        return isAscii;
    }

    public void setIsAscii(boolean isAscii) {
        
        this.isAscii = isAscii;
    }

    @Override
    public String toString() {
        switch (convert) {
            case BINARY:
                switch (this.type) {
                    case MAR:
                        return this.stringBuild(Integer.toBinaryString(0x10000 | data).substring(5), true);
                    default:
                        return this.stringBuild(Integer.toBinaryString(0x10000 | data).substring(1), true);
                }
            case DECIMAL:
                if(this.isAscii){
                    return this.buildAscii(data);
                }
                if(this.isInstruction){
                    return this.instruction;
                }
                return String.valueOf(this.data);
            case HEXADECIMAL:
                return Integer.toHexString(data);
            default:
                throw new AssertionError(convert.name());
        }
    }

    private String buildAscii(short data) {

        String s = stringBuild(Integer.toBinaryString(0x10000 | data).substring(1), false);
        String left_str = s.substring(0, 8);
        String right_str = s.substring(8);
        int left_int = Integer.parseInt(left_str, 2);
        int right_int = Integer.parseInt(right_str, 2);
        String temp = ((char) left_int) + "" + ((char) right_int);
      //  System.out.println("s:"+ s + " left:"+left_str+  " "+left_int + "   right:"+right_str + " "+ right_int+ "  data"+this.data);
        
        return temp;

    }

    private String stringBuild(String s, boolean space) {
        if (s.length() > 16) {
            s = s.substring(15);
        }
        if (space) {
            return s.replaceAll("(.{4})(?!$)", "$1 ");
        } else {
            return s;
        }
    }

    public void setIsInstruction(boolean isInstruction) {
        this.isInstruction = isInstruction;
    }
    public void setInstruction(String instruction){
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
    
    
    public String ComponentType(){
        return this.type.toString();
    }
}
