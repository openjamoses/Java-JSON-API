/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author john
 */
public class Excell_Dublicating {
    
    XSSFRow rows;
    int rowid = 0;
    XSSFWorkbook workbook2;
    XSSFSheet sheet;
    String projectName;
    Object[] datas = null;
    OpenFileName openOldfile;
    
    public void createExcel(ArrayList<Object[]> allobj,int number,String excelFilePath, String sheetName)
        throws IOException {
      FileOutputStream fos = null;
    try {
        XSSFWorkbook workbook = null;
        if (new File(excelFilePath).createNewFile()) {
            workbook = new XSSFWorkbook();
        } else {
            FileInputStream pfs = new FileInputStream(new File(excelFilePath));
            workbook = new XSSFWorkbook(pfs);
        }
        if (workbook.getSheet(sheetName) == null) {
            fos = new FileOutputStream(excelFilePath);
            sheet= workbook.createSheet(sheetName);
            ///
        int rowid2 = sheet.getLastRowNum();
        int x;
        for(x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             rows = sheet.createRow(rowid2++);
             Object [] objectArr = allobj.get(x);
             int cellid = 0;
             for (Object obj : objectArr){//Looping inside the object...
                  Cell cells = rows.createCell(cellid++);
                  if (obj instanceof String){
                       cells.setCellValue((String)obj);
                   }else if (obj instanceof Integer){
                        cells.setCellValue((int)obj);
                   }else if (obj instanceof Double){
                        cells.setCellValue((double)obj);
                   }
              } // End of for loop for object
          }//End of for loop for arraylist of object....
         
            workbook.write(fos);
            System.out.println("Excel File Created is : "+excelFilePath+" With sheet name :"+sheetName);
            System.out.println("\n\n");
        }

    } catch (IOException e) {
        throw e;
    } finally {
        if (fos != null) {
            fos.close();
        }
    }
}
    
}
