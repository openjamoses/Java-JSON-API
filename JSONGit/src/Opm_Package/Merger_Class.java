/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author john
 */
public class Merger_Class {
    String file = "/Users/john/Desktop/PROJECTS/JSON WORK/Release_dates_1GtihubAPI.xlsx";
    OpenFileName openFile = new OpenFileName();
    Merger_Class() throws Exception{
        doNameMerging();
    }
    /**
     * 
     * @param g 
     */
    public static void main(String[] g) throws Exception{
        new Merger_Class();
    }
    
    private int getWorksheets() throws Exception{
         Workbook workbook = openFile.readFileName(file);
         int sheetCounts = workbook.getNumberOfSheets();
         return sheetCounts;
    }

    private void doNameMerging() throws Exception {
       int numbers = getWorksheets();
       int count = 0;
       while(count < numbers){ 
         List<String> lists = new ArrayList<>();
         List<String> dateslists = new ArrayList<>();
         List< List<String> > allLists = new ArrayList<>();
       
         List<String> mergedList = new ArrayList<>();
       
      
      
        allLists = openFile.readCommits2(file,count);
        lists = allLists.get(0);
        
        dateslists = allLists.get(1);
        //System.out.println(lists.size()+"\t"+dateslists.size()); 
        
        for(int x=0; x<lists.size(); x++){
            if(lists.get(x).length() >10){
               // if(x<dateslists.size()){
                 System.out.print(dateslists.get(x)+" \t"+lists.get(x)); 
                //}
                 
             }
           System.out.println();
       }
      System.out.println("\n\n\n");
     
       String[] nameMerger_i = null;
       String[] nameMerger_j = null;
       String[][] nameMerger_new = null;
       String name_i = "", name_j = "", email_prefix_i = "", email_prefix_j = "", login_i = "", login_j = "", location_i = "", location_j = "", created_at_i = "", created_at_j = "";
		
       for(int i=0; i<lists.size(); i++){
          
           if(!lists.get(i).equals("") && lists.get(i).length() >10){
               String[] splits1 = lists.get(i).split(":-");
               for(int c=0;  c<splits1.length; c++){
                   nameMerger_i = splits1[c].split("/");
               }
               
               name_i = nameMerger_i[0];
               if(nameMerger_i[1].contains("@")){
                   email_prefix_i = nameMerger_i[1].substring(0,nameMerger_i[1].indexOf("@"));
               }else{
                   email_prefix_i = nameMerger_i[1];
               }
               login_i = nameMerger_i[2];
               location_i = nameMerger_i[3];
               
           }
           for (int j=0; j<lists.size(); j++){
               if(!lists.get(j).equals("") && lists.get(i).length() >10 ){
                  // String[] splits2 = lists.get(0).split(":-");
                   nameMerger_j = lists.get(j).split("/");
                   
                   if(nameMerger_j.length >2) {
                   name_j = nameMerger_j[0];
                   if(nameMerger_j[1].contains("@")){
                       email_prefix_j = nameMerger_j[1].substring(0,nameMerger_j[1].indexOf("@"));
                    }else{
                       email_prefix_j = nameMerger_j[1];
                    }
                    login_j = nameMerger_j[2];
                    location_j = nameMerger_j[3];
                    
                    String str1 = "",commits1 ="";
		    String str2 = "",commits2 = "";
                    //
                
               
		   if (login_i.equals(login_j) && !login_i.equals("login####") && location_j.equals("location") && !location_i.equals("location")){// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
		         str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			 str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
                         
			 lists.set(j, str1+""+str2);
		    }
		    else if (login_i.equals(login_j) && !login_i.equals("login####") && !location_j.equals("location") && location_i.equals("location")){// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
		         str1 = lists.get(j).substring(0,lists.get(j).lastIndexOf("/"));
			 str2 = lists.get(i).substring(lists.get(i).lastIndexOf("/"));
			 lists.set(i, str1+str2);
		    }
		    if (login_i.equals(login_j) && !login_i.equals("login####")){// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
		    }
		    else if(name_i.equals(name_j) && !login_j.equals("login####")){
			str1 = lists.get(j).substring(0,lists.get(j).lastIndexOf("/"));
			str2 = lists.get(i).substring(lists.get(i).lastIndexOf("/"));
			lists.set(i, str1+str2);
		    }
		    else if(name_i.equals(name_j) && !login_i.equals("login####")){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
			
		    }
		    else if(email_prefix_i.equals(email_prefix_j) && !login_j.equals("login####")){
			str1 = lists.get(j).substring(0,lists.get(j).lastIndexOf("/"));
			str2 = lists.get(i).substring(lists.get(i).lastIndexOf("/"));
			lists.set(i, str1+str2);
	//		
		    }
		    else if(email_prefix_i.equals(email_prefix_j) && !login_i.equals("login####")){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
	//	        
		    }
		    else if(name_i.equals(name_j)){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
	               
		    }
		    else if(email_prefix_i.equals(email_prefix_j)){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);                     ///...........
                       
		    }
	
               
                }
               }
           
            
           
           }
            System.out.println(i+"\t\t"+lists.get(i));
       } 
       
       
       System.out.println(lists.size());
       count ++;
      }// end of while loop...
    }
    
}
