package Machine;

import GUI.TextFieldGui;
import javax.swing.JTextField;
<<<<<<< HEAD
import EnumerationData.ComponentEnum;
import Tools.MIC1;
=======
import EnumationData.ComponentEnum;
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9

/*! \brief  This class simulates the MIC1 Shifter component.
 *
 * It is implemented using the Singleton pattern.
 * Does four operation left shift, right shift, no shift, unused.
 * 
 * @author Thiago G Goncalves
 */

public class Shifter extends TextFieldGui {

    private static final Shifter instance = new Shifter(GUI.GUI.shifter);

    public static Shifter getInstance() {
        return instance;
    }

    private Shifter(JTextField textField) {
        super(ComponentEnum.SHIFTER, textField);
    }

    @Override
    public void Operations() {
        super.Operations();
        int decimal = convert(Pipe.MemoryInstruction.getSh());
<<<<<<< HEAD
        Short data = MIC1.MIC1_DATA.get(ComponentEnum.ALU).getData();
=======
        Short data = this.getData();
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
        switch (decimal) {
            case 0:
                // NO SHIFT
                break;
            case 1:

                // System.out.println(data);
                data = (short) (data >> 1);
                // System.out.println(data);
                break;

            case 2:
                //  System.out.println(data);
                data = (short) (data << 1);
                //   System.out.println(data);
                break;

            case 3:
                // not used
                break;
        }

        this.setData(data);

    }

}
