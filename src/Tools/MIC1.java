/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Machine.MIR;
import Machine.MainMemory;
import Machine.Registers;
import Machine.Mic1Machine;
import Machine.Pipe;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Assembler.MAC1Assembler;
import static GUI.GUI.assemblyCodeData;
import static GUI.GUI.opcodeArea;

/**
 *
 * @author Thiago G Goncalves
 */
public class MIC1 implements Runnable {

    public static Mic1Machine MIC1_DATA = Mic1Machine.getInstance();
    final public static Object objectLock = new Object();
    static public int totalCycle = 0;
    static public boolean step = false;
    static public boolean fullCycle = false;
    static public int currentCode = 0;
    final static private ArrayList<MIR> tracker = new ArrayList<>();

    static public boolean end = false;
    Registers registers = Registers.getInstance();
    MainMemory mainMemory = MainMemory.getInstance();

    ArrayList<MIR> InstructionSet = new ArrayList<>();

    @Override
    public void run() {
        MIC1_DATA.arrageMachine();

       // this.registers.setRegisterValue(0, (short) 0);
        this.registers.setDataStructureValue(1, (short) 40);
        this.registers.setDataStructureValue(2, (short) 1024);

        this.registers.setDataStructureValue(3, (short) 0);
        this.registers.setDataStructureValue(4, (short) 0);
        this.registers.setDataStructureValue(5, (short) 0);
        this.registers.setDataStructureValue(6, (short) 1);
        this.registers.setDataStructureValue(7, (short) -1);
        this.registers.setDataStructureValue(8, (short) 4095);
        this.registers.setDataStructureValue(9, (short) 255);
        this.registers.setDataStructureValue(10, (short) 42);
        this.registers.setDataStructureValue(11, (short) 0);
        this.registers.setDataStructureValue(12, (short) 0);
       // this.registers.setRegisterValue(13, (short) 8);
       // this.registers.setRegisterValue(14, (short) 12);
        this.registers.setDataStructureValue(15, (short) 40);
        this.mainMemory.setDataStructureValue(100, (short) -100);

        FileReader fileReader = FileReader.getInstance();
        fileReader.readText(assemblyCodeData.getText());

        MAC1Assembler mac = new MAC1Assembler();
        mac.loadData(fileReader.getOutput());

        for (Short intcode : mac.getBinaryCode()) {
            this.mainMemory.pushDecodedInstruction(intcode);
        }

        fileReader.readText(opcodeArea.getText());
        fileReader.parseMACInstruction();
        this.InstructionSet = fileReader.opCode();

        while (!end) {
            waitOnObject();
            fullRun(this.InstructionSet.get(currentCode));
            totalCycle++;
            System.out.println("\n************************ CODE line " + currentCode + "  " + tracker.get(tracker.size() - 1).getAll() + " \n");
        }

        System.out.println("TOTAL  CYCLE " + totalCycle);
        for (MIR trace : tracker) {
            System.out.println(trace.getAll());
        }
    }

    private void waitOnObject() {
        if (MIC1.fullCycle || MIC1.step) {
            synchronized (objectLock) {
                try {
                    objectLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MIC1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void fullRun(MIR code) {
        tracker.add(code);
        System.out.println("code  " + code.getAll() + "  " + currentCode);
        Pipe.setMIR(code);

        MIC1_DATA.FullCycle();
        MIC1_DATA.condition(code);
        MIC1_DATA.readOrWriteCycle();
        if (currentCode >= this.InstructionSet.size()) {
            end = true;
        }
    }
}
