package Opm_Package;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author john
 */
public class Main {

    static String  pname = "Integreight/1Sheeld-Android-App";
       
    /**
     * @param args the command line arguments
     */
     
       public static void main(String[] args) throws Exception {
        // TODO code application logic here
        System.out.println("Please wait ...");
        new ReadCommits().readCommitsNow(pname);
    }

    
}
