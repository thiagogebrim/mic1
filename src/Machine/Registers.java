/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

import GUI.TableGui;
<<<<<<< HEAD
import EnumerationData.ComponentEnum;
import EnumerationData.RegisterEnum;

/*! \brief  This class simulates the MIC1 Registers file .
 *
=======
import EnumationData.ComponentEnum;
import EnumationData.RegisterEnum;

/*! \brief  This class simulates the MIC1 Registers file .
*
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
 * It is implemented using the Singleton pattern.
 * It is a 16 (registers) data structure memory space.
 * 
 * @author Thiago G Goncalves
 */
<<<<<<< HEAD
public final class Registers extends TableGui {

    final static private Registers instance = new Registers();
    private boolean DecimalView = false;

    private Registers() {
        super(ComponentEnum.REGISTER, GUI.GUI.Register, (short) 16);

=======

public final class Registers extends TableGui {

    final static private Registers instance = new Registers();

    private Registers() {
        super(ComponentEnum.REGISTER, GUI.GUI.Register, (short) 16);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
    }

    public static Registers getInstance() {
        return instance;
    }

    public void setRegisterValue(RegisterEnum register, Short value) {
<<<<<<< HEAD
        // this.view(register.ordinal());
        if (RegisterEnum.IR != register) {

            this.setDataStructureValue(register.ordinal(), value);
        }
    }

    private void viewIR(int index) {

        if (index == RegisterEnum.IR.ordinal()) {

            int pc = this.getDataStructureData(RegisterEnum.PC.ordinal()).getData() - 1;
            String instruction = MainMemory.getInstance().getDataStructureData(pc).getInstruction();
            this.getDataStructureData(RegisterEnum.IR.ordinal()).setInstruction(instruction);
            if (this.DecimalView == false) {
                this.DecimalView = true;
                this.getDataStructureData(RegisterEnum.IR.ordinal()).setIsInstruction(true);
            }
        }
    }

    @Override
    public void setDataStructureValue(int index, Short value) {
        viewIR(index);
        super.setDataStructureValue(index, value); //To change body of generated methods, choose Tools | Templates.
=======
        this.setDataStructureValue(register.ordinal(), value);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
    }

    @Override
    public void Operations() {
        int c_data = Pipe.convert(Pipe.MemoryInstruction.getC_bus());
        this.setDataStructureValue(c_data, this.getPrevious().getData());
    }
}
