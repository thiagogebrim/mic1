/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

import Interfaces.Iterator;
import Tools.DataType;
import java.util.BitSet;
import Tools.MIC1;
<<<<<<< HEAD
import EnumerationData.ComponentEnum;
=======
import EnumationData.ComponentEnum;
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9

/*! \brief  This class simulates the MIC1 bus component.
*
 * It holds does not use singleton pattern because the MIC1 holds more than one bus.
 * 
 * @author Thiago G Goncalves
 */

public class Pipe extends DataType implements Iterator {

    protected static ComponentEnum currentState = ComponentEnum.DEFAULT;
    static protected MIR MemoryInstruction;

    public BitSet bitData;

    private Pipe next;

    private Pipe previous;

    public Pipe(ComponentEnum type) {
        super(type);
    }

    @Override
    final public void setNext(ComponentEnum nextEnum) {
        if (nextEnum != null) {
            this.next = MIC1.MIC1_DATA.get(nextEnum);
        }
    }

    @Override
    final public void setPrevious(ComponentEnum previousEnum) {
        this.previous = MIC1.MIC1_DATA.get(previousEnum);
    }

    @Override
    final public Pipe getPrevious() {
        int index;
        switch (this.type) {

            case MBR:

                int index_mbr = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getMbr()));
                int index_read = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getRd()));

                if (index_mbr == 1) {
                    setPrevious(ComponentEnum.C_BUS);

                } else if (index_read == 1) {
                    setPrevious(ComponentEnum.DATA_BUS_TO_MBR);
                } else {
                    System.out.println("ERROR!!!!!  BAD !!!!!");
                }
                break;

            case AMUX:
                index = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getAmux()));
                if (index == 1) {
                    setPrevious(ComponentEnum.MBR);
                } else {
                    setPrevious(ComponentEnum.A_LATCH);
                }

                break;
        }
        return this.previous;

    }

    @Override
    final public boolean hasNext() {
        return this.next != null;
    }

    @Override
    final public Pipe next() {
        int index;
        switch (type) {
            case AMUX:                index = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getMar()));
                if (index == 0) {
                    return next.next;
                }
                break;
            case C_BUS:
                index = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getMbr()));
                if (index != 1) { // index == 0

                    return this.next.next();
                }

                break;
            case MBR:

                index = Pipe.convert(String.valueOf(Pipe.MemoryInstruction.getEnc()));
                if (index == 0) {
                    return null;
                }
                break;
        }
        return this.next;
    }

    final protected void loadData() {

        switch (type) {
            case MEMORY:
<<<<<<< HEAD
            case SHIFTER:
            case ALU:
                return;
           
=======
            case ALU:
                return;
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
            case MAR:
                this.getPrevious();
                this.setData(this.previous.getData());
                this.transformDataTo12bitContainer();
                return;
            case A_BUS:
                int a = Pipe.convert(Pipe.MemoryInstruction.getA_bus());
                setData(Registers.getInstance().getDataStructureData((short) a).getData());
                return;

            case B_BUS:
                int b = Pipe.convert(Pipe.MemoryInstruction.getB_bus());
                setData(Registers.getInstance().getDataStructureData((short) b).getData());
                return;
        }
        this.getPrevious();
        this.setData(this.previous.getData());
    }

    static public int convert(String string) {
        BitSet bits = BitSet.valueOf(new long[]{Long.parseLong(string, 2)});
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return (int) value;
    }

    public void Operations() {
        this.loadData();
        currentState = type;
    }

    final static public void setMIR(MIR code) {
        Pipe.MemoryInstruction = code;
    }
}
