 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

import GUI.TableGui;
import Tools.MIC1;
import EnumationData.ComponentEnum;

/*! \brief  This class simulates the MIC1 Main Memory component.
*
 * It is implemented using the Singleton pattern.
 * This is simulates the segments of virtual space such as CODE, DATA, STACK
 * 
 * @author Thiago G Goncalves
 */

public class MainMemory extends TableGui {

    private int mac1StructionCount = 0;
    private short memoryLocation = 0;
    private Short data = 0;
    public boolean readTwriteF = false;
    private int read = 0;
    private int write = 0;
    public Pipe begin;
    public Pipe end;
    
    
    private short current_allocation_location = 100;

    private static final MainMemory instance = new MainMemory();

    private MainMemory() {
        super(ComponentEnum.MEMORY, GUI.GUI.memory, (short)4096);
    }

    public static MainMemory getInstance() {
        return instance;
    }


    public void pushDecodedInstruction(short code) {
        this.setDataStructureValue(this.mac1StructionCount,code);
        this.mac1StructionCount++;
    }

    @Override
    public void Operations() {

        if (this.readTwriteF) { // read
            if (read % 2 == 0) {
                this.memoryLocation = MIC1.MIC1_DATA.get(ComponentEnum.ADRESS_BUS).getData();
                this.setData(this.getDataStructureData(this.memoryLocation).getData());
            }
        } else { // write

            if (write % 2 == 0) {
                this.memoryLocation = MIC1.MIC1_DATA.get(ComponentEnum.ADRESS_BUS).getData();
                this.data = MIC1.MIC1_DATA.get(ComponentEnum.DATA_BUS_TO_MEMORY).getData();
                this.setDataStructureValue(this.memoryLocation, this.data);

            }
        }
    }

    public boolean setCycle() {
        char r = MemoryInstruction.getRd();
        char w = MemoryInstruction.getWr();

        if (r == '1') {
            this.readTwriteF = true;
            if (read % 2 == 1) {
                begin = MIC1.MIC1_DATA.get(ComponentEnum.MEMORY);
                end = MIC1.MIC1_DATA.get(ComponentEnum.MBR);
            } else {
                begin = MIC1.MIC1_DATA.get(ComponentEnum.ADRESS_BUS);
                end = MIC1.MIC1_DATA.get(ComponentEnum.MEMORY);
            }
            read++;
            write = 0;
            return true;
        } else if (w == '1') {
            this.readTwriteF = false;
            if (write % 2 == 1) {
                begin = MIC1.MIC1_DATA.get(ComponentEnum.MEMORY);
                end = MIC1.MIC1_DATA.get(ComponentEnum.MEMORY);
            } else {
                begin = MIC1.MIC1_DATA.get(ComponentEnum.DATA_BUS_TO_MEMORY);
                end = MIC1.MIC1_DATA.get(ComponentEnum.ADRESS_BUS);
            }
            write++;
            read = 0;
            return true;
        }
        return false;
    }

    public int allocatedData(short data, boolean isAscii) {
        this.setDataStructureValue(current_allocation_location, data);
        this.getDataStructureData(current_allocation_location).setIsAscii(isAscii);
        this.current_allocation_location++;
        return this.current_allocation_location - 1;
    }

}
