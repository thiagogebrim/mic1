/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Interfaces.GuiInterfacei;
import Tools.DataType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Tools.MIC1;
import static Tools.MIC1.objectLock;
import EnumerationData.ComponentEnum;
import static Interfaces.GuiInterfacei.INACTIVE_COLOUR;
import static Interfaces.GuiInterfacei.UPDATE_COLOUR;
import Machine.Pipe;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/*! \brief  Abstract class to give a table a data structure component (JTable).
 *
 * @author Thiago G Goncalves
 */
public abstract class TableGui extends Pipe implements GuiInterfacei {

    final private JTable table;
    private int dynamicRow;
    final private int column = 1;
    final private short data_structure_size;
    final private ArrayList<DataType> data_structure = new ArrayList<>();

    public TableGui(final ComponentEnum type, final JTable table, final short structure_size) {
        super(type);
        this.table = table;
        this.data_structure_size = structure_size;
       // System.out.println(data_structure_size);
        for (int i = 0; i < data_structure_size; i++) {
            DataType data_ty = new DataType(ComponentEnum.DEFAULT);
            data_ty.setIsComponentStep(true);
            getModel().addRow(new Object[]{(short) i, data_ty});
            data_structure.add(data_ty);
        }

    }

    private JTable getTable() {
        return this.table;
    }

    /**
     * Will call a series of update function calls from JTable
     *
     * @param update updates the JTable with the given ColorMap
     */
    @Override
    public void update(final ColorMap update) {

        if (GUI.mic1_gui) {
            switch (update) {
                case SELECT:

                    this.getModel().fireTableCellUpdated(dynamicRow, column);
                    this.getTable().setRowSelectionInterval(dynamicRow, dynamicRow);
                    this.getTable().setSelectionBackground(SELECT_COLOUR);
                    this.getTable().scrollRectToVisible(this.getTable().getCellRect(dynamicRow, 0, true));
                    this.getTable().repaint();
                    break;
                case INACTIVATE:

                    break;
                case UPDATE:
                    this.getModel().fireTableCellUpdated(dynamicRow, column);
                    this.getTable().setRowSelectionInterval(dynamicRow, dynamicRow);
                    this.getTable().setSelectionBackground(UPDATE_COLOUR);
                    this.getTable().scrollRectToVisible(this.getTable().getCellRect(dynamicRow, 0, true));
                    this.getTable().repaint();
                    break;
                default:
                    throw new AssertionError(update.name());
            }
        }
    }

    /**
     *
     * @return the private JTable
     */
    @Override
    public JComponent getComponent() {
        return this.table;
    }

    /**
     *
     * @return DefaultTableModel of the private JTable
     */
    public final DefaultTableModel getModel() {
        return (DefaultTableModel) this.getTable().getModel();
    }

    /**
     * Will update the row position for the next update call
     *
     * @param row the location in the JTable to be updated
     */
    public void requestUpdateWrapper(int row) {
        this.dynamicRow = row;
    }

    /**
     * Will put the current process in wait mode, until the user click continues
     * that is notify();
     */
    public void step() {
        if (GUI.mic1_gui) {
            this.update(ColorMap.UPDATE);
        }
        if (MIC1.step) {
            synchronized (objectLock) {
                try {
                    objectLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DataType.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(MIC1.speedTime){
            synchronized (objectLock) {
                try {
                    objectLock.wait(MIC1.timer);
                } catch (InterruptedException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @param index location in the JTable
     * @param value value to be in set in the desired location
     */
    public void setDataStructureValue(int index, Short value) {

        data_structure.get(index).setData(value);
        requestUpdateWrapper(index);

        step();

    }

    public void clearDataStructure() {
        for (DataType data : this.data_structure) {
            data.setData((short) -1);
        }
        this.table.updateUI();
    }

    /**
     *
     * @param index the location in the JTable
     * @return the DataType object in the location value
     */
    public DataType getDataStructureData(int index) {
        return data_structure.get(index);
    }

    
}
