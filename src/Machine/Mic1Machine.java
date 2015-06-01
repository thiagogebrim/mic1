/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

import GUI.TextFieldGui;
import java.util.HashMap;
import Tools.MIC1;

<<<<<<< HEAD
import EnumerationData.ComponentEnum;
import EnumerationData.RegisterEnum;
=======
import EnumationData.ComponentEnum;
import EnumationData.RegisterEnum;
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9

public final class Mic1Machine {

    HashMap<ComponentEnum, Pipe> map = new HashMap<>();

    private final Pipe ABus = new Pipe(ComponentEnum.A_BUS);
    private final Pipe BBus = new Pipe(ComponentEnum.B_BUS);
    private final Pipe AddresBus = new Pipe(ComponentEnum.ADRESS_BUS);
    private final Pipe CBus = new Pipe(ComponentEnum.C_BUS);
    private final Pipe DataBusToMbr = new Pipe(ComponentEnum.DATA_BUS_TO_MBR);
    private final Pipe DataBusToMemory = new Pipe(ComponentEnum.DATA_BUS_TO_MEMORY);

    private final TextFieldGui ALatch = new TextFieldGui(ComponentEnum.A_LATCH, GUI.GUI.alatch);
    private final TextFieldGui BLatch = new TextFieldGui(ComponentEnum.B_LATCH, GUI.GUI.blatch);
    private final TextFieldGui AMux = new TextFieldGui(ComponentEnum.AMUX, GUI.GUI.amux);
    private final TextFieldGui MAR = new TextFieldGui(ComponentEnum.MAR, GUI.GUI.mar);
    private final TextFieldGui MBR = new TextFieldGui(ComponentEnum.MBR, GUI.GUI.mbr);

    private final ALU alu = ALU.getInstance();
    private final Shifter shifter = Shifter.getInstance();

    private final Registers registers = Registers.getInstance();
    private final MainMemory mainMemory = MainMemory.getInstance();

    static final private Mic1Machine instance = new Mic1Machine((short) 1024);

    private Mic1Machine(Short memorySize) {
        map.put(ComponentEnum.REGISTER, this.registers);
        map.put(ComponentEnum.A_BUS, this.ABus);
        map.put(ComponentEnum.B_BUS, this.BBus);
        map.put(ComponentEnum.A_LATCH, this.ALatch);
        map.put(ComponentEnum.B_LATCH, this.BLatch);
        map.put(ComponentEnum.AMUX, this.AMux);
        map.put(ComponentEnum.ALU, this.alu);
        map.put(ComponentEnum.SHIFTER, this.shifter);
        map.put(ComponentEnum.C_BUS, this.CBus);
        map.put(ComponentEnum.MAR, this.MAR);
        map.put(ComponentEnum.MBR, this.MBR);
        map.put(ComponentEnum.ADRESS_BUS, this.AddresBus);
        map.put(ComponentEnum.DATA_BUS_TO_MBR, this.DataBusToMbr);
        map.put(ComponentEnum.DATA_BUS_TO_MEMORY, this.DataBusToMemory);
        map.put(ComponentEnum.MEMORY, this.mainMemory);

        registers.setRegisterValue(RegisterEnum.SP, memorySize);
<<<<<<< HEAD
        this.ABus.setIsComponentStep(false);
        this.BBus.setIsComponentStep(false);
        this.AddresBus.setIsComponentStep(false);
        this.CBus.setIsComponentStep(false);
        this.DataBusToMbr.setIsComponentStep(false);
        this.DataBusToMemory.setIsComponentStep(false);
=======
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
       
    }

    public static Mic1Machine getInstance() {
        return instance;
    }

    public Pipe get(ComponentEnum type) {
        return map.get(type);
    }

    public void arrageMachine() {
        this.ABus.setNext(ComponentEnum.B_BUS);
        this.BBus.setNext(ComponentEnum.A_LATCH);
        this.ALatch.setNext(ComponentEnum.B_LATCH);
        this.BLatch.setNext(ComponentEnum.AMUX);
        this.AMux.setNext(ComponentEnum.MAR);
        this.MAR.setNext(ComponentEnum.ALU);
        this.alu.setNext(ComponentEnum.SHIFTER);
        this.shifter.setNext(ComponentEnum.C_BUS);
        this.CBus.setNext(ComponentEnum.MBR);
        this.MBR.setNext(ComponentEnum.REGISTER);
        this.registers.setNext(null);

        this.DataBusToMemory.setNext(ComponentEnum.ADRESS_BUS);
        this.AddresBus.setNext(ComponentEnum.MEMORY);
        this.mainMemory.setNext(ComponentEnum.DATA_BUS_TO_MBR);
        this.DataBusToMbr.setNext(ComponentEnum.MBR);

        //////// previous
        this.registers.setPrevious(ComponentEnum.C_BUS);

        this.ABus.setPrevious(ComponentEnum.REGISTER);
        this.BBus.setPrevious(ComponentEnum.REGISTER);

        this.MAR.setPrevious(ComponentEnum.B_LATCH);

        this.ALatch.setPrevious(ComponentEnum.A_BUS);
        this.BLatch.setPrevious(ComponentEnum.B_BUS);

        this.shifter.setPrevious(ComponentEnum.ALU);
        this.CBus.setPrevious(ComponentEnum.SHIFTER);
        this.AddresBus.setPrevious(ComponentEnum.MAR);

        this.DataBusToMbr.setPrevious(ComponentEnum.MEMORY);
        this.DataBusToMemory.setPrevious(ComponentEnum.MBR);

    }

    public boolean condition(MIR instruction) {
        int cond = Integer.parseInt(instruction.getCond(), 2);
        int addr = Integer.parseInt(instruction.getAddr(), 2);
        switch (cond) {
            case 0:
                MIC1.currentCode++;
                break;
            case 1: // n
                if (this.alu.NFLAG) {
                    MIC1.currentCode = addr;
                } else {
                    MIC1.currentCode++;
                }
                break;
            case 2: // Z
                if (this.alu.ZFLAG) {
                    MIC1.currentCode = addr;
                } else {
                    MIC1.currentCode++;
                }
                break;
            case 3: // always
                MIC1.currentCode = addr;
                break;
        }

        return false;
    }

    public void readOrWriteCycle() {
        if (MainMemory.getInstance().setCycle()) {
            Pipe start = MainMemory.getInstance().begin;
            Pipe end = MainMemory.getInstance().end;
            for (Pipe current = start; current != end.next(); current = current.next()) {
                current.Operations();

            }
        }
    }

    public void FullCycle() {
        for (Pipe current = this.ABus; current != null; current = current.next()) {
<<<<<<< HEAD
          //  System.out.println(current.ComponentType());
=======
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
            current.Operations();
        }
    }
}
