/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author john
 */
public class TestDate {
    static ArrayList<String> dateList1 = new ArrayList<String>();
    static ArrayList<String> dateList2 = new ArrayList<String>();
    
    public static void main(String[] args) throws ParseException{
        String dt = "2016-11-13T10:31:35Z";  // Start date
        int i2 =2;
        for(int i=1; i<20; i++){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String subString = dt.substring(10, 20);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 7*i);  // number of days to add
        String dt2 = sdf.format(c.getTime());  // dt is now the new date
        
        //int increamented = Integer.parseInt(dt.substring(17,19)) + 1;
        int increamented  = Integer.parseInt(subString.substring(subString.length()-3,subString.length()-1)) + 1;
        String i_Z = increamented+"Z";
        
        String sub = subString.substring(subString.length()-3,subString.length()-1);
        String replace_sub = subString.replace(sub, increamented+"");
        
        String n_dt2 = dt2+""+replace_sub;
        dateList1.add(dt2+""+subString);
        //String i_dt = dt2+""+subString
        System.out.println(dt+" - "+dt2+""+subString+" \t\t"+increamented+"\t\t"+sub+"\t\t"+n_dt2);
       
        
        }
    }
    
}
