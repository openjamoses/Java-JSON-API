/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
        
        FileInputStream fname = new FileInputStream(new File(excelFilePath));

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
    
     public List<String> readCommits(String file) throws Exception{
       // String excelFilePath = "Books.xlsx";
        //FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        List<String> lists = new ArrayList<>();
        Workbook workbook = readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             int y = 0;
            while (cellIterator.hasNext()) {
             
                Cell cell = cellIterator.next();
                 
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if(y > 7 & !cell.getStringCellValue().equals("-")){
                            lists.add(cell.getStringCellValue());
                        }
                        //System.out.print(cell.getStringCellValue());
                        
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
               // System.out.print(" - ");
                   y ++;
            }
            //System.out.println();
        }
         
        workbook.close();
       // inputStream.close();
       for(int x=0; x<lists.size(); x++){
           String[] splits = lists.get(x).split(":-");
           //System.out.println(lists.get(x)+"\t length = "+splits.length);
       }
       
       return lists;
    
    }
     
     public List<List<String>> readCommits2(String file, int count) throws Exception{
       // String excelFilePath = "Books.xlsx";
        //FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        List<String> lists = new ArrayList<>();
        List<String> datelists = new ArrayList<>();
        List< List<String> > pullslists = new ArrayList<>();
       List< List<String> > alllists = new ArrayList<>();
       
       List<String> prOpen = new ArrayList<>();
       List<String> prClosed = new ArrayList<>();
       List<String> isOpen = new ArrayList<>();
       List<String> isClosed = new ArrayList<>();
       List<String> forks = new ArrayList<>();
       List<String> watch = new ArrayList<>();
        
        Workbook workbook = readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(count);
        Iterator<Row> iterator = firstSheet.iterator();
         int p =0;
        while (iterator.hasNext()) {
            p ++;
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             int y = 0,d=0;
             List<String> cList = new ArrayList<>();
             List<String> plist = new ArrayList<>();
            while (cellIterator.hasNext()) {
             
                Cell cell = cellIterator.next();
               
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if(y == 0  && p >1){
                            datelists.add(cell.getStringCellValue());
                        }
                        if(y > 7 && p >1){
                            d = 1;
                            lists.add(cell.getStringCellValue());
                         }
                        if(y>8  && p >1 ){
                             datelists.add(datelists.get(datelists.size()-1));
                         }
                        if(cell.getStringCellValue().equals(null)){
                             System.out.print(y+"\t null");
                        }
                        if(y == 1 && p >1 ){
                            prOpen.add(cell.getStringCellValue());
                        }
                        if(y == 2 && p >1 ){
                            prClosed.add(cell.getStringCellValue());
                        }
                        if(y == 3 && p >1 ){
                            isOpen.add(cell.getStringCellValue());
                        }
                        if(y == 4 && p >1 ){
                            isClosed.add(cell.getStringCellValue());
                        }
                        if(y == 5 && p >1 ){
                            forks.add(cell.getStringCellValue());
                        }
                        if(y == 6 && p >1 ){
                            watch.add(cell.getStringCellValue());
                        }
                        cList.add(cell.getStringCellValue());
                        System.out.print(y+"\t "+cell.getStringCellValue());
                        
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                      // System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                       // System.out.print(cell.getNumericCellValue());
                        break;
                        case Cell.CELL_TYPE_BLANK:
                          //  System.out.print(y+"\t NoData ");
                            break;
                       
                }
                ///System.out.print(" - ");
                   y ++;
            }
            pullslists.add(plist);
           if(cList.size() == 8 ){
             lists.add("-");
            }
            //System.out.print("\t\t "+pullslists+"\t\t"+1);
           System.out.println();
        }
         
        workbook.close();
       // inputStream.close();
       for(int x=0; x<lists.size(); x++){
           String[] splits = lists.get(x).split(":-");
           //System.out.println(lists.get(x)+"\t length = "+splits.length);
       }
       
       /// Add all the Lists to the new List
       alllists.add(lists);
       alllists.add(datelists);
       alllists.add(prOpen);
       alllists.add(prClosed);
       alllists.add(isOpen);
       alllists.add(isClosed);
       alllists.add(forks);
       alllists.add(watch);
       
       /// Return the lists to the Merger_Class  ...
       return alllists;
    
    }
     
}
