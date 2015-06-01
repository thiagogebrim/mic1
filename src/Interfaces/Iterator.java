/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Machine.Pipe;
import Machine.Pipe;
<<<<<<< HEAD
import EnumerationData.ComponentEnum;
=======
import EnumationData.ComponentEnum;
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9

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
