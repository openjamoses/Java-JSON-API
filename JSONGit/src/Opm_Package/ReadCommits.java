package Opm_Package;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
    XSSFSheet sheet;
    String projectName;
    
    public void readCommitsNow(String projectName) throws ParseException, Exception{
        
        ////Excell Header goes here....
         Object[] datas = null;
         this.projectName = projectName;
         /// Writing the Headers of the excell documents..
          datas =    new Object[]{"Tag Name","Tag Date","Months","PR Open",
                     "PR Closed","Stars","Forks","Project",
                     "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                                 };// end of assigning the header to the object..
          /// putting the header in to the arraylist..
          allobj.add(datas);
          ///#########################################################################
           int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int ct=0;
            String[] toks = {
                              };
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
                      /// Checking for null objects...
                      if(loginAuthorObj != null){
                          
                    
                      String login = (String) loginAuthorObj.get("login");
                      String loginURL = callURL("https://api.github.com/users/"+login+"?access_token="+toks[ct++]);
                      JSONObject loginObj = (JSONObject)parser.parse(loginURL);
                      ///..........................................
                      String location = (String) loginObj.get("location");
                      long public_repos = (long) loginObj.get("public_repos");
                      long public_gists = (long) loginObj.get("public_gists");
                      long followers = (long) loginObj.get("followers");
                      long following = (long) loginObj.get("following");
                      String createdAt = (String) loginObj.get("created_at");
                      String updatedAt = (String) loginObj.get("updated_at");
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
                          datas = new Object[] {login,date, "","","",
                            "" ,"",name+"/"+email+"/"+location+"/"+createdAt+"/"+updatedAt+"/"+public_repos+"/"+public_gists+"/"+followers+"/"+following+"/0_"+changed+"_"+added+"_"+deleted
                                   }; 
                      /// Now add the datas to the array list....
                      allobj.add(datas);// putting the object in to list...
                      
                
                      }
                      }
                      //#############################################################
                      //This is just for testing ... Otherwise it should be removed....
                      
                      //Should Remove upto here only................
                      //############################################################### **/
                   
                 //// We can print breaking line now . tor the next commits..
                 System.out.println();
               
                 }
               p++;//// Goto the next Page.......
          }
            
       WriteToCellNow(allobj,projectName);//Calling the WriteToCellNow method with the object...
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
     private void WriteToCellNow(ArrayList<Object[]> allobj, String projectName) throws Exception {
        /// this keyword is used to reffer to the varriable which is not within this method
        ///but deceared as global, althougth it has less use here....
        ///Since no other variable is with the same meaning...
        this. workbook2 =  new XSSFWorkbook();
        this.sheet= workbook2.createSheet("Statistics Assignment");
        ///Specifying the path for file
        String filePath = "/Users/john/Desktop/PROJECTS/JSON WORK/MyFirstWork.xlsx";
        FileOutputStream fileOut = new FileOutputStream(new File(filePath));
        
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
         
         ///Now write the workbook to the file
        workbook2.write(fileOut);
        fileOut.close();
        ///Echo the feedback in the console...
        System.out.println( "Excell :Written successfully...");
        System.out.println( "PATH:\t\t\t"+filePath);
        
        /// Send the ArrayObject containing all data to the next class for further manipulation....
        CommitsInterval cInterval = new CommitsInterval();
        cInterval.useTagDatesInterval(projectName,allobj );
      
    }   //End of WriteToCellNow method
}
