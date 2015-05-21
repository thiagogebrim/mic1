/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

import GUI.TableGui;
import EnumationData.ComponentEnum;
import EnumationData.RegisterEnum;

/*! \brief  This class simulates the MIC1 Registers file .
*
 * It is implemented using the Singleton pattern.
 * It is a 16 (registers) data structure memory space.
 * 
 * @author Thiago G Goncalves
 */

public final class Registers extends TableGui {

    final static private Registers instance = new Registers();

    private Registers() {
        super(ComponentEnum.REGISTER, GUI.GUI.Register, (short) 16);
    }

    public static Registers getInstance() {
        return instance;
    }

    public void setRegisterValue(RegisterEnum register, Short value) {
        this.setDataStructureValue(register.ordinal(), value);
    }

    @Override
    public void Operations() {
        int c_data = Pipe.convert(Pipe.MemoryInstruction.getC_bus());
        this.setDataStructureValue(c_data, this.getPrevious().getData());
    }
}
