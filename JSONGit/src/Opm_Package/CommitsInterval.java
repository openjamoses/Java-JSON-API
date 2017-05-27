package Opm_Package;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author john
 */
public class CommitsInterval {
    /**
     * 
     * @param projectName
     * @param allobj 
     */
    public void useTagDatesInterval(String projectName, ArrayList<Object[]> allobj) {
        for(int x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             /// Getting the all the Objects in the arrayList...
             Object [] objectArr = allobj.get(x);
             Object tagDateObj = objectArr[1];
             if(tagDateObj instanceof String){
                 System.out.println(tagDateObj.toString());
             }
          }//End of for loop for arraylist of object....
         
    }
    
}
