/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Assembler.InstructionTranslator;
import Machine.MIR;
import Machine.MainMemory;
import Machine.Registers;
import Machine.Mic1Machine;
import Machine.Pipe;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Assembler.MAC1Assembler;
import EnumerationData.RegisterEnum;
import GUI.Decoder;
import static GUI.GUI.MIR_mal;
import static GUI.GUI.a_txt;
import static GUI.GUI.addr_txt;
import static GUI.GUI.alu_txt;
import static GUI.GUI.amux_txt;
import static GUI.GUI.assemblyCodeData;
import static GUI.GUI.assemblyRadio;
import static GUI.GUI.b_txt;
import static GUI.GUI.c_txt;
import static GUI.GUI.cond_txt;
import static GUI.GUI.enc_txt;
import static GUI.GUI.mar_txt;
import static GUI.GUI.mbr_txt;
import static GUI.GUI.memoryFile;
import static GUI.GUI.opcodeArea;
import static GUI.GUI.rd_txt;
import static GUI.GUI.registerFile;
import static GUI.GUI.sh_txt;
import static GUI.GUI.totalCycle_lbl;
import static GUI.GUI.wr_txt;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thiago G Goncalves
 */
public class MIC1 implements Runnable {

    public static Mic1Machine MIC1_DATA = Mic1Machine.getInstance();
    final public static Object objectLock = new Object();
    static public int totalCycle = 0;
    static public boolean step = false;
    public static boolean speedTime = false;
    public static int timer = 20;
    static public boolean fullCycle = false;
    static public int currentCode = 0;
    final static private ArrayList<MIR> tracker = new ArrayList<>();
    public final static Object doneLock = new Object();

    static public boolean end = false;
    Registers registers = Registers.getInstance();
    MainMemory mainMemory = MainMemory.getInstance();

    ArrayList<MIR> InstructionSet = new ArrayList<>();
    MAC1Assembler mac;

    @Override
    public void run() {
        
        totalCycle = 0;
        totalCycle_lbl.setText(String.valueOf(totalCycle));
        reset();
        initializeData();
        FileReader fileReader = FileReader.getInstance();
        if (assemblyRadio.isSelected()) {
            fileReader.readText(assemblyCodeData.getText());

            mac = new MAC1Assembler();
            mac.loadData(fileReader.getOutput());

            for (String intcode : mac.getBinaryCode()) {
                this.mainMemory.pushDecodedInstruction(intcode);
            }
        }
        //fileReader.readText(opcodeArea.getText());
        // fileReader.parseMACInstruction();
        this.InstructionSet = Decoder.opCodeContainer;

        // System.out.println(this.InstructionSet.size() + " " + currentCode);
        while (!end) {

            waitOnObject();
            fullRun(this.InstructionSet.get(currentCode));
            totalCycle++;
            //   System.out.println(tracker.get(tracker.size() - 1).getMAL() + " MAL");
            //  System.out.println("\n************************ CODE line " + currentCode + "  " + tracker.get(tracker.size() - 1).getAll() + " \n");
            totalCycle_lbl.setText(String.valueOf(totalCycle));

        }

        System.out.println("TOTAL  CYCLE " + totalCycle);
        for (MIR trace : tracker) {
            //  System.out.println(trace.getAll());
        }
        synchronized (doneLock) {
            doneLock.notify();
        }
        GUI.GUI.loadGUI();
        System.out.println("STARRTT");
    }

    static public void reset() {
        end = false;
        tracker.clear();
        currentCode = 0;
        currentCode = 0;
        MIC1_DATA.arrageMachine();

    }

    static public void releaseObject() {
        end = true;
        step = false;
        fullCycle = false;

        synchronized (objectLock) {
            objectLock.notify();
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
        this.setGUI(code);
        tracker.add(code);
       // MIR_mal.setText(currentCode + ":" + tracker.get(tracker.size() - 1).getMAL());
        /// MIR_binary.setText(tracker.get(tracker.size() - 1).getAll());
        // System.out.println("code  " + code.getAll() + "  " + currentCode);
        Pipe.setMIR(code);

        MIC1_DATA.FullCycle();
        MIC1_DATA.condition(code);
        MIC1_DATA.readOrWriteCycle();
        if (currentCode >= this.InstructionSet.size()) {
            end = true;
        }
    }

    private void initializeData() {

        int row = registerFile.getModel().getRowCount();
        for (int i = 0; i < row; i++) {

            short data = (short) (((DefaultTableModel) registerFile.getModel()).getValueAt(i, 1));
            this.registers.setRegisterValue(RegisterEnum.values()[i], data);
        }

        row = memoryFile.getModel().getRowCount();
        for (int i = 0; i < row; i++) {
            Object data = (((DefaultTableModel) memoryFile.getModel()).getValueAt(i, 1));
            Object rowValue = (((DefaultTableModel) memoryFile.getModel()).getValueAt(i, 0));
            if (data != null || rowValue != null) {
                this.mainMemory.setDataStructureValue((short) rowValue, (short) data);
            }
        }
    }

    private void setGUI(MIR mir) {
        if (GUI.GUI.mic1_gui) {
            MIR_mal.setText(currentCode + ":" + mir.getMAL());

         //   MIR mir = this.InstructionSet.get(currentCode);
            amux_txt.setText("" + mir.getAmux());
            cond_txt.setText("" + mir.getCond());
            alu_txt.setText("" + mir.getAlu());
            sh_txt.setText("" + mir.getSh());
            mbr_txt.setText("" + mir.getMbr());
            mar_txt.setText("" + mir.getMar());
            rd_txt.setText("" + mir.getRd());
            wr_txt.setText("" + mir.getWr());
            enc_txt.setText("" + mir.getEnc());
            c_txt.setText("" + mir.getC_bus());
            b_txt.setText("" + mir.getB_bus());
            a_txt.setText("" + mir.getA_bus());
            addr_txt.setText("" + mir.getAddr());
        }
    }
}
