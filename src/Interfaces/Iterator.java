/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Machine.Pipe;
import Machine.Pipe;
import EnumationData.ComponentEnum;

/*! \brief  Interface that implements the Iterator Design Pattern.
*  All the MIC1 Component uses this interface. 
 * @author Thiago G Goncalves
 */

public interface Iterator {
   public boolean hasNext();
   public Pipe next();
   void setNext(ComponentEnum nextEnum);
   public void setPrevious(ComponentEnum previousEnum);
   public Pipe getPrevious();
}
