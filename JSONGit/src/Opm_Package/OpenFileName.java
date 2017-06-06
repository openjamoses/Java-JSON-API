/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class reads the file location and returns the result to the caller
 * @author OJUKO
 */
public class OpenFileName {
    /**
     * 
     * @return  workbook
     * @throws Exception 
     */
    
     public XSSFWorkbook readFileName(String excelFilePath) throws Exception{
        // Spacifying the path to the excel to be read...
        
        FileInputStream fname = new FileInputStream(new File("/Users/john/Desktop/PROJECTS/JSON WORK/Repo_Names.xlsx"));

        // creating the workbook with the file ......
	XSSFWorkbook workbook = new XSSFWorkbook(fname);
        //returning the workbook to the caller
        return workbook;
    }
     
     public List<String> readReposNames(String file) throws Exception{
        int x=0;
        OpenFileName fname = new OpenFileName();
        // array list to store the Repos names
        ArrayList <String> list = new ArrayList <String> ();
        //calling the file name.....
        XSSFWorkbook workbook = readFileName(file);
       // setting the sheet number...
        XSSFSheet spreadsheet = workbook.getSheetAt(x);
        String sname = workbook.getSheetName(x);
      
	Row row;
        Cell cell=null;
        for (int j=0; j< spreadsheet.getLastRowNum()+1; ++j) {//To loop thru the rows in a sheet
           row = spreadsheet.getRow(j);
           cell = row.getCell(0); //forks are in the eighth column...
           switch (cell.getCellType()){
               //Checking for strings values inthe cells..
               case Cell.CELL_TYPE_STRING:
                   if (!cell.getStringCellValue().equals("")){
                      // adding the call value to the arraylist called forksList 
                      list.add(cell.getStringCellValue());
                    }//end of if statement...
                    break;
                //Checking for numeric values inthe cells..
                case Cell.CELL_TYPE_NUMERIC:
                   list.add( String.valueOf(cell.getNumericCellValue()) );
                   break;
                //Checking for bank in the cells..
                case Cell.CELL_TYPE_BLANK:
                    break;
             }//end of switch statement
         
       }// end of  for loop for the rows..
        
       //returns the arraylist to the main class....
       return list;
    } 
     
     
}
