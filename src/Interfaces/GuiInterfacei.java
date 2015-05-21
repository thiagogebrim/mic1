/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Color;
import javax.swing.JComponent;

/*! \brief  It will give a GUI Interface for the MIC1 component.
 *
 * 
 * @author Thiago G Goncalves
 */

public interface GuiInterfacei {

    static public final Color UPDATE_COLOUR = Color.BLUE;
    static public final Color INACTIVE_COLOUR = Color.WHITE;
    static public final Color SELECT_COLOUR = Color.GRAY;

    public void update(ColorMap type);

    public void setData(short data);

    public JComponent getComponent();

    enum ColorMap {

        SELECT, INACTIVATE, UPDATE
    }
}
