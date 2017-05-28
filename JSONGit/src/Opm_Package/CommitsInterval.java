package Opm_Package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public void useTagDatesInterval(String projectName, ArrayList<Object[]> allobj) throws ParseException {
        for(int x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             /// Getting the all the Objects in the arrayList...
             Object [] objectArr = allobj.get(x);
             Object tagDateObj = objectArr[1];
             
             if(tagDateObj instanceof String){
                 System.out.println(tagDateObj.toString());
                 
             }
             
             /** **************************************
              ** Getting the first and the last tag Date here
              **/
             Object [] firstObject = allobj.get((allobj.size()-1));
             Object [] lastObject = allobj.get(0);
             Object fDate = firstObject[1];
             Object lDate = lastObject[1];
             /// Checking if Both First Date and the Last are all String.....
             if(fDate instanceof String && lDate instanceof String ){
                 ///Preparing to add date by 7 after removing Z from the String 
                 for(int i=0; i< (firstObject.length - 1); i++){
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     String subString = fDate.toString().substring(10, fDate.toString().length()-1);
                     Calendar c = Calendar.getInstance();
                     c.setTime(sdf.parse(fDate.toString()));
                     c.add(Calendar.DATE, 7*i);  // number of days to add
                     String dt2 = sdf.format(c.getTime());  // dt is now the new date
                     
                     //int increamented = Integer.parseInt(dt.substring(17,19)) + 1;
                     int increamented  = Integer.parseInt(subString.substring(subString.length()-3,subString.length()-1)) + 1;/// increament the min by 1
                     String i_Z = increamented+"Z";
        
                     String sub = subString.substring(subString.length()-3,subString.length()-1);
                     String replace_sub = subString.replace(sub, increamented+"");
        
                     String n_dt2 = dt2+""+replace_sub;
                     
                     System.out.println(fDate.toString()+" - "+dt2+""+subString);
                 }
             }
             /** 
              ** End of Getting the first and the last tag Date here
              ******************************************************/
          }//End of for loop for arraylist of object....
         
    }
    
}
