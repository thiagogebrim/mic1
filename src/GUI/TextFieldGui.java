/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Interfaces.GuiInterfacei;
import Machine.Pipe;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTextField;
import Tools.MIC1;
import static Tools.MIC1.objectLock;
<<<<<<< HEAD
import EnumerationData.ComponentEnum;
import java.util.ArrayList;

/*! \brief class to give a text field a data component (JText Field).
 *
=======
import EnumationData.ComponentEnum;

/*! \brief class to give a text field a data component (JText Field).
*
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
 * @author Thiago G Goncalves
 */
public class TextFieldGui extends Pipe implements GuiInterfacei {

<<<<<<< HEAD
    final private JTextField textField;
    static final private ArrayList<TextFieldGui> selfObjects = new ArrayList<>();

    public TextFieldGui(ComponentEnum type, final JTextField textField) {
        super(type);
        this.setIsComponentStep(true);
        this.textField = textField;
        this.textField.addFocusListener(new Focus());
        selfObjects.add(this);
        
        
=======
    final public JTextField textField;

    public TextFieldGui(ComponentEnum type, final JTextField textField) {
        super(type);
        this.textField = textField;
        this.textField.addFocusListener(new Focus());
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
    }

    public JTextField getTextField() {
        return this.textField;
    }

    @Override
    public void update(ColorMap select) {
        if (GUI.mic1_gui) {
            switch (select) {
                case SELECT:
                    break;
                case INACTIVATE:
                    break;
                case UPDATE:
                    break;
                default:
                    throw new AssertionError(select.name());
            }
            this.getTextField().setText(this.toString());
            this.getTextField().requestFocus();

        }
    }
<<<<<<< HEAD
    
    public void updateData(){
        this.textField.setText(this.toString());
        this.textField.repaint();
    }
    
=======
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9

    @Override
    public JComponent getComponent() {
        return this.textField;
    }

    @Override
    public void setData(short data) {
<<<<<<< HEAD
        //super.setData(data);
        this.setDataRaw(data);
        if (GUI.mic1_gui) {
            this.update(ColorMap.UPDATE);
        }
=======
        super.setData(data);
        this.update(ColorMap.UPDATE);
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
        if (MIC1.step) {
            synchronized (objectLock) {
                try {
                    objectLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
<<<<<<< HEAD
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
    public static void clearSelfObject(){
        selfObjects.clear();
    }
    public static void repaint(){
        for(TextFieldGui obj : selfObjects){
            obj.updateData();
=======
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
        }
    }

    class Focus implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {

            textField.setBackground(UPDATE_COLOUR);
            textField.setForeground(INACTIVE_COLOUR);
        }

        @Override
        public void focusLost(FocusEvent e) {
            textField.setBackground(INACTIVE_COLOUR);
            textField.setForeground(UPDATE_COLOUR);
        }
    }
}
