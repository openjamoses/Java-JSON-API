package Opm_Package;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    ArrayList < Object[]  > allobj = new ArrayList <Object[] > ();
    XSSFRow rows;
    int rowid = 0;
    XSSFWorkbook workbook2;
    XSSFSheet sheet;
    String projectName;
    
    
    public void useTagDatesInterval(String projectName, ArrayList<Object[]> allobj) throws ParseException, org.json.simple.parser.ParseException {
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
                 int i2 = 1;
                 for(int i=0; i< (firstObject.length); i++){
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     String subString = fDate.toString().substring(10, fDate.toString().length()-1);
                     /// First Dates...
                     Calendar c = Calendar.getInstance();
                     c.setTime(sdf.parse(fDate.toString()));
                     c.add(Calendar.DATE, 7*i);  // number of days to add
                     String dt2 = sdf.format(c.getTime());  // dt is now the new date
                     /// Second Date
                     
                     Calendar c2 = Calendar.getInstance();
                     c2.setTime(sdf.parse(fDate.toString()));
                     c2.add(Calendar.DATE, 7*i2);  // number of days to add
                     String dt22 = sdf.format(c2.getTime());  // dt is now the new date
                     
                     //int increamented = Integer.parseInt(dt.substring(17,19)) + 1;
                     int increamented  = Integer.parseInt(subString.substring(subString.length()-2,subString.length())) + 1;///  index number 17 - 19 from "2016-11-13T10:31:35Z" is 35  increament the min by 1
                     String i_Z = increamented+"Z";
                     /// 
                     String sub = subString.substring(subString.length()-3,subString.length()-1);/// index number 17 - 19 from "2016-11-13T10:31:35Z" is 35
                     String replace_sub = subString.replace(sub, increamented+"");/// Replace the Last string which is the minutes..
        
                     String n_dt2 = dt22+""+replace_sub+"";// Concate to the date to make it full
                    
                     
                     System.out.println(fDate.toString()+"\t\t"+dt2+""+subString+"Z - "+dt22+""+subString+"Z");
                     
                     
                     /// Now we can use the right variable names for the two dates interval
                     String date1 = dt2+""+subString+"Z";
                     String date2 = dt22+""+subString+"Z";
                     
                    /// Now we assigns to the next method to get the commits within the two dates above....
                    getCommitsNow(projectName,date1,date2);
                     
                     i2 ++;
                 }
             }
             /** 
              ** End of Getting the first and the last tag Date here
              ******************************************************/
          }//End of for loop for arraylist of object....
         
    }

    /**
     * 
     * @param projectName
     * @param date1
     * @param date2 
     */
    private void getCommitsNow(String projectName, String date1, String date2) throws org.json.simple.parser.ParseException {
            this.projectName = projectName;
    
            ////Excell Header goes here....
            Object[] datas = null;
            /// Writing the Headers of the excell documents..
            datas =    new Object[]{"Tag Name","Tag Date","Months","PR Open",
                     "PR Closed","Stars","Forks","Project",
                     "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                                 };// end of assigning the header to the object..
            /// putting the header in to the arraylist..
            allobj.add(datas);
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int ct=0;
            String[] toks = {"d9fa524eb803729fff1a327ad670f5615fefb822",
                             "727b16f55df64b49c961fb25784bd760cd9fd986",
                              "8a124ae16017a27bff55dba50e349d34b9c6e37e"
                             
                             };
            int count =0;
            while (true){////loop thru the pagess....
        	if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                String jsonString = callURL("https://api.github.com/repos/"+projectName+"/commits?page="+p+"&per_page=100&since="+date1+"&until="+date2+"&access_token="+toks[ct++]);
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
                          datas = new Object[] {login,date, "","","",
                            "" ,"",name+"/"+email+"/"+location+"/"+createdAt+"/"+updatedAt+"/"+public_repos+"/"+public_gists+"/"+followers+"/"+following+"/0_"+changed+"_"+added+"_"+deleted
                                   }; 
                      /// Now add the datas to the array list....
                      allobj.add(datas);// putting the object in to list...
                      }
                      
                      //############################################################### ***********
                   
                     //// We can print breaking line now . for the next commits..
                    System.out.println();
                
                  }/// *** End of for loop for JSon Object.....
	      p++;//// Goto the next Page.......
           }// ******* End of while loop....
            
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
    
}
