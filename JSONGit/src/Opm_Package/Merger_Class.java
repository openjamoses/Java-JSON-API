/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Merger_Class {
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

    private void doNameMerging() throws Exception {
       List<String> lists = new ArrayList<>();
       OpenFileName openFile = new OpenFileName();
       String file = "/Users/john/Desktop/PROJECTS/JSON WORK/FINAL_WORKSS#2.xlsx";
       lists = openFile.readCommitsFromExceell(file);
       // System.out.println(lists);
       String[] nameMerger_i = null;
       String[] nameMerger_j = null;
       String[][] nameMerger_new = null;
       String name_i = "", name_j = "", email_prefix_i = "", email_prefix_j = "", login_i = "", login_j = "", location_i = "", location_j = "", created_at_i = "", created_at_j = "";
		
       for(int i=0; i<lists.size(); i++){
           //System.out.println(lists.get(i));
           if(!lists.get(i).equals("")){
               nameMerger_i = lists.get(i).split("/");
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
               if(!lists.get(i).equals("")){
                   nameMerger_j = lists.get(j).split("/");
                   name_j = nameMerger_j[0];
                   if(nameMerger_j[1].contains("@")){
                       email_prefix_j = nameMerger_j[1].substring(0,nameMerger_j[1].indexOf("@"));
                    }else{
                       email_prefix_j = nameMerger_j[1];
                    }
                    login_j = nameMerger_j[2];
                    location_j = nameMerger_j[3];
                    
                    String str1 = "";
		    String str2 = "";
                    //
		   if (login_i.equals(login_j) && !login_i.equals("login####") && location_j.equals("location") && !location_i.equals("location")){// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
		         str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			 str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			 System.out.println("created_at_i = "+str1);
			 System.out.println("created_at_i = "+str2);
			 lists.set(j, str1+str2);
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
			System.out.println("created_at_i = "+str1);
			System.out.println("created_at_i = "+str2);
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
			System.out.println("created_at_i = "+str1);
			System.out.println("created_at_i = "+str2);
	//              NamesMerged.set(j, namesMerged.get(i));
		    }
		    else if(email_prefix_i.equals(email_prefix_j) && !login_j.equals("login####")){
			str1 = lists.get(j).substring(0,lists.get(j).lastIndexOf("/"));
			str2 = lists.get(i).substring(lists.get(i).lastIndexOf("/"));
			lists.set(i, str1+str2);
	//		System.out.println("created_at_i = "+str1);
	//		System.out.println("created_at_i = "+str2);
	//              NamesMergedC1.set(i, namesMergedC2.get(i));
		    }
		    else if(email_prefix_i.equals(email_prefix_j) && !login_i.equals("login####")){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
	//	        System.out.println("created_at_i = "+str1);
	//		System.out.println("created_at_i = "+str2);
	//              namesMergedC1.set(i, namesMergedC2.get(i));
		    }
		    else if(name_i.equals(name_j)){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);
	                System.out.println("created_at_i = "+str1);
			System.out.println("created_at_i = "+str2);
	//              namesMerged.set(j, namesMerged.get(i));
		    }
		    else if(email_prefix_i.equals(email_prefix_j)){
			str1 = lists.get(i).substring(0,lists.get(i).lastIndexOf("/"));
			str2 = lists.get(j).substring(lists.get(j).lastIndexOf("/"));
			lists.set(j, str1+str2);                     ///...........
                      System.out.println("created_at_i = "+str1);///...........
                    System.out.println("created_at_i = "+str2);
        //             namesMergedC1.set(i, namesMergedC2.get(i));
		    }
	
               
                }
           }
           
       }
    }
    
}
