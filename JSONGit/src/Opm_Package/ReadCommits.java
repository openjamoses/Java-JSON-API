package Opm_Package;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author john
 */
public class ReadCommits {
    ArrayList < Object[]  > allobj = new ArrayList <Object[] > ();
    XSSFRow rows;
    int rowid = 0;
    XSSFWorkbook workbook2;
    XSSFSheet[] sheet;
    String projectName,sheetName;
    OpenFileName openOldfile;
    
    /**
     * 
     * @param projectName
     * @throws ParseException
     * @throws Exception 
     */
    public void readCommitsNow(String projectName,String[] toks,String sheetName,int number,XSSFSheet[] sheet) throws ParseException, Exception{
        ////Excell Header goes here....
         Object[] datas = null;
         this.projectName = projectName;
         this.sheetName = sheetName;
         this.sheet = sheet;
         /// Writing the Headers of the excell documents..
         datas =  new Object[]{"Tag Date","Months","PR Open",
                     "PR Closed","Stars","Forks","Project",
                     "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                 
                     };// end of assigning the header to the object..
            /// putting the header in to the arraylist..
            allobj.add(datas);
            ///#########################################################################
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int ct=0;
            int count =0;
            while (true){////loop thru the pagess....
        	if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
              String jsonString = callURL("https://api.github.com/repos/"+projectName+"/commits?page="+p+"&per_page=100&access_token="+toks[ct++]);
	       String sha = null ;
               JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]")){
                    /// Break out of the loop, when empty array is found!
	                	break;
	                }
                  for (Object jsonObj : jsonArray) {
                      count ++;
                      
                      /** Please remove this code, it was only used for testing...**/
                      //if(count == 5){
                      //    break;
                      //}
                      /** Remove Up to here  **/
                      JSONObject jsonObject = (JSONObject) jsonObj; 
                      String shaa = (String) jsonObject.get("sha");
                      JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                      JSONObject authorObj = (JSONObject) commitsObj.get("author");
                      ///..........................................
                      String name = (String) authorObj.get("name");
                      String email = (String) authorObj.get("email");
                      String date = (String) authorObj.get("date");
                      //......................................
                      /// Print something to the console.....
                      System.out.print(count+"\tname : "+name+", Email: "+email+", Date: "+date);
                      ///###########################################..
                      if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      }
                      /// Now we also need to get the Login Details,,the corresponding followes and following
                      JSONObject loginAuthorObj = (JSONObject) jsonObject.get("author");
                      
                      String login = "";
                      String loginURL = "";
                      JSONObject loginObj = null;
                      ///..........................................
                      String location = "";
                      long public_repos = 0;
                      long public_gists = 0;
                      long followers = 0;
                      long following = 0;
                      String createdAt = "";
                      String updatedAt = "";
                      
                      /// Checking for null objects...
                      if(loginAuthorObj != null){
                          login = (String) loginAuthorObj.get("login");
                          loginURL = callURL("https://api.github.com/users/"+login+"?access_token="+toks[ct++]);
                          loginObj = (JSONObject)parser.parse(loginURL);
                          ///..........................................
                          location = (String) loginObj.get("location");
                          public_repos = (long) loginObj.get("public_repos");
                          public_gists = (long) loginObj.get("public_gists");
                          followers = (long) loginObj.get("followers");
                          following = (long) loginObj.get("following");
                          createdAt = (String) loginObj.get("created_at");
                          updatedAt = (String) loginObj.get("updated_at");
                          //**********************************************
                      }else if (loginAuthorObj == null){
                          location = null;
                          public_repos = 0;
                          public_gists = 0;
                          followers = 0;
                          following = 0;
                          createdAt = "";
                          updatedAt = "";
                          //**********************************************
                      }
                      //......................................
                      /// Print something to the console.....
                      System.out.print("\t login: "+login+", Locate: "+location+", p_repos: "+public_repos+", p_gist: "+public_gists+", follower: "+followers+", following: "+following);
                      ////#################################################################
                      ///To get the Changed, added and deleted line  
                      if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      }
                      String commitsShaURL = callURL("https://api.github.com/repos/"+projectName+"/commits/"+shaa+"?access_token="+toks[ct++]);
                      JSONObject allObj = (JSONObject)parser.parse(commitsShaURL);
                      ////
                      JSONArray fileArray = (JSONArray) allObj.get("files");
                      if(!fileArray.toString().equals("[]")){
                         
                          long changed = 0,added = 0, deleted = 0;
                          for(int x=0; x<fileArray.size(); x++){
                               JSONObject fileObj = (JSONObject)fileArray.get(x);
                               changed += (long) fileObj.get("changes");
                               added += (long) fileObj.get("additions");
                               deleted += (long) fileObj.get("deletions");
                          }
                          /// Print something to the console.....
                          System.out.print("\t Changes:"+changed+", Added: "+added+", Deleted:"+deleted);
                          // ###############################################################################
                          
                          ///Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted
                          datas = new Object[] {date, "","","",
                            "" ,"",name+"/"+email+"/"+location+"/"+createdAt+"/"+updatedAt+"/"+public_repos+"/"+public_gists+"/"+followers+"/"+following+"/0_"+changed+"_"+added+"_"+deleted
                                   }; 
                      /// Now add the datas to the array list....
                      allobj.add(datas);// putting the object in to list...
                      }
                      
                      //############################################################### ***********
                   
                 //// We can print breaking line now . for the next commits..
                 System.out.println();
               
                 }/// *** End of JSon Object.....
               p++;//// Goto the next Page.......
          } /// ******** End of while loop ......
            
       WriteToCellNow(allobj,projectName,sheetName,number,sheet,toks);//Calling the WriteToCellNow method with the object...
       
       
    }
    
     /**
     * 
     * @param myURL
     * @return 
     */
    public  String callURL(String myURL) {
       // System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(60 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }

    /**
     * This method receive the object from the previous method and write it to excel file
     * @param allobj
     * @throws Exception 
     */
     private void WriteToCellNow(ArrayList<Object[]> allobj, String projectName,String sheetName, int number,XSSFSheet[] sheet,String[] toks) throws Exception {
        /// this keyword is used to reffer to the varriable which is not within this method
        ///but deceared as global, althougth it has less use here....
        ///Since no other variable is with the same meaning...
        
        this. workbook2 =  new XSSFWorkbook();
        openOldfile = new OpenFileName();
        
        ///Specifying the path for file
        String filePath = "/Users/john/Desktop/PROJECTS/JSON WORK/NEWExcel_work0.xlsx";
        FileOutputStream fileOut = new FileOutputStream(new File(filePath));
       // if(number > 0){
          this. workbook2 = openOldfile.readFileName(filePath);
       // }
        sheet[number]= workbook2.createSheet(sheetName);
        int rowid2 = sheet[number].getLastRowNum();
        int x;
        for(x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             rows = sheet[number].createRow(rowid2++);
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
         
         ///Now write the workbook to the file
        workbook2.write(fileOut);
        fileOut.close();
        
        ///Echo the feedback in the console...
        System.out.println( "Excell :Written successfully...");
        System.out.println( "PATH:\t\t\t"+filePath);
        
        String filePath2 = "/Users/john/Desktop/PROJECTS/JSON WORK/FINAL_WORKSS#1.xlsx";
        createExcel(allobj,number,filePath2,sheetName);
        System.out.println( "\n ALSO PATH:\t\t\t"+filePath2);
        
        
        /// Send the ArrayObject containing all data to the next class for further manipulation....
        CommitsInterval cInterval = new CommitsInterval();
        cInterval.useTagDatesInterval(projectName,sheetName,number,sheet,allobj,toks );
      
    }   //End of WriteToCellNow method

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
            sheet[number]= workbook.createSheet(sheetName);
            ///
        int rowid2 = sheet[number].getLastRowNum();
        int x;
        for(x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             rows = sheet[number].createRow(rowid2++);
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
