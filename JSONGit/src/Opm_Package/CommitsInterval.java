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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
   ArrayList < Object[]  > allobj2 = new ArrayList <Object[] > ();
    XSSFRow rows;
    int rowid = 0;
    XSSFWorkbook workbook2;
    XSSFSheet[] sheet;
    String projectName;
    Object[] datas = null;
    OpenFileName openOldfile;
    
    public void useTagDatesInterval(String projectName,String sheetName, int number, XSSFSheet[] sheet, ArrayList<Object[]> allobj,String[] toks) throws ParseException, org.json.simple.parser.ParseException, Exception {
        
            this.sheet = sheet;
            /// Writing the Headers of the excell documents..
             datas =    new Object[]{"Tag Date","PR Open",
                     "PR Closed","IS Open","IS Clossed","Forks","Watched","Project",
                     "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                                 };// end of assigning the header to the object..
             /// putting the header in to the arraylist..
             allobj2.add(datas);
        
        
        for(int x=0;x<allobj.size();x++){//Looping thru the array list to pick the objects...
             /// Getting the all the Objects in the arrayList...
             Object [] objectArr = allobj.get(x);
             Object tagDateObj = objectArr[1];
             ////Excell Header goes here....
            
             if(tagDateObj instanceof String){
                 //System.out.println(tagDateObj.toString());
                 
             }
             
             /** **************************************
              ** Getting the first and the last tag Date here
              **/
             Object [] firstObject = allobj.get((allobj.size()-1));
             Object [] lastObject = allobj.get(1);
             Object fDate = firstObject[0];
             Object lDate = lastObject[0];
             
             ArrayList<String> check = new ArrayList<>();
             
             /// Checking if Both First Date and the Last are all String.....
             if(fDate instanceof String && lDate instanceof String  ){
                 ///Preparing to add date by 7 after removing Z from the String
                 check.add(lDate.toString());
                 int i2 = 1,i =0,next = 0;
                 SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                 String date2 = fDate.toString();
                 
                 Date Ddate1 = s.parse(date2);
                 Date Ddate2 = s.parse(lDate.toString());
                 
                if(check.size() == 1){
                 RUN: do {
                     
                     //System.out.println(fDate.toString());
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                   
                     String subString = fDate.toString().substring(10, (fDate.toString().length() - 1));
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
                    
                     /// Now we can use the right variable names for the two dates interval
                     String date1 = dt2+""+subString+"Z";
                     date2 = dt22+""+subString+"Z";
                     
                     /// Now we assigns to the next method to get the commits within the two dates above....
                     
                     Ddate1 = s.parse(date2);
                     
        
                    /// We have to check whether the last date is reached!...
                    //Calendar cc1 = Calendar.getInstance();
                    //cc1.setTime(s.parse(date2));
                    //Calendar cc4 = Calendar.getInstance();
                    //cc4.setTime(s.parse(lDate.toString()));
                    if (Ddate2.compareTo(Ddate1) > 0) {
                         ///Calling the interval details...
                        datas =  getCommitsNow(projectName,date1,date2,i,toks);
                        /// Add to the List...
                        allobj2.add(datas);
                       }else{
                        next ++;
                        check.add(date2);
                        //System.out.print(check);
                        break RUN;
                        
                       } 
                    
                   
                     if(Ddate2.compareTo(Ddate1) < 0){
                        check.add(date2);
                        
                     }
                     i2 ++;
                     i ++;
                     
                     
                 
                     
                 }while( Ddate2.compareTo(Ddate1) > 0 && check.size() <= 1 ) ; 
                 
                 if(next > 0){
                     check.add(date2);
                     break;
                 }
                 }else if(check.size() >1){
                     break;
                 }
             }
             /** 
              ** End of Getting the first and the last tag Date here
              ******************************************************/
          }//End of for loop for arraylist of object....
        
        
        String filePath2 = "/Users/john/Desktop/PROJECTS/JSON WORK/FINAL_WORKSS#2.xlsx";
        createExcel(allobj2,number,filePath2,sheetName);
         
    }
             

    /**
     * 
     * @param projectName
     * @param date1
     * @param date2 
     */
    private Object[] getCommitsNow(String projectName, String date1, String date2, int i, String[] toks) throws org.json.simple.parser.ParseException {
        
            System.out.println(i+"\t\t\t : "+date1+"\t\t - "+date2);
            long changed = 0,added = 0, deleted = 0;
            this.projectName = projectName;
            ///..........................................
            String location = "";
            long public_repos = 0;
            long public_gists = 0;
            long followers = 0;
            long following = 0;
            String createdAt = "";
            String updatedAt = "";
            String login = "";
            String loginURL = "";
            JSONObject loginObj = null;
                     
            
            ArrayList<String> shaLists = new ArrayList<>();
            ArrayList<String> logList = new ArrayList<>();
            
            Set <String> loginSet = new LinkedHashSet<String>();
            Set <String> locationSet = new LinkedHashSet<String>();
            Set <String> nameSet = new LinkedHashSet<String>();
            Set <String> emailSet = new LinkedHashSet<String>();
            Set <String> createdSet = new LinkedHashSet<String>();
            Set <String> updatedSet = new LinkedHashSet<String>();
            
            
            ArrayList< List<String> > list = new ArrayList< List<String> >();
            JSONParser parser = new JSONParser();
    
            
            int p = 1; // Page number parameter
            //int i = 0; // Commit Counter
            int ct=0;
            int count =0;
            
            while (true){////loop thru the pagess....
        	if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                String jsonString = callURL("https://api.github.com/repos/"+projectName+"/commits?page="+p+"&per_page=100&since="+date1+"&until="+date2+"&access_token="+toks[ct++]);
                
                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]")){
                    /// Break out of the loop, when empty array is found!
                      	break;
	            }
                
                for (Object jsonObj : jsonArray) {
                      count ++;
                      
                      JSONObject jsonObject = (JSONObject) jsonObj; 
                      String shaa = (String) jsonObject.get("sha");
                      shaLists.add(shaa);/// Add to the List
                      JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                      JSONObject authorObj = (JSONObject) commitsObj.get("author");
                      ///..........................................
                      String name = (String) authorObj.get("name");
                      String email = (String) authorObj.get("email");
                      String date = (String) authorObj.get("date");
                      //......................................
                     
                      nameSet.add(name);
                      emailSet.add(email);
                       if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      }
                      /// Now we also need to get the Login Details,,the corresponding followes and following
                      JSONObject loginAuthorObj = (JSONObject) jsonObject.get("author");
                      /// Checking for null objects...
                      if(loginAuthorObj != null){
                          login = (String) loginAuthorObj.get("login");
                          /// Preventing dublicates using ses
                          loginSet.add(login);
                          logList.add(login);
                          
                          //**********************************************
                      }//......................................
                        if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      }
                      
                      //############################################################### ***********
                     if(count == jsonArray.size()){
                        
                    }
                  }/// *** End of for loop for JSon Object.....
	      p++;//// Goto the next Page.......
             }// ******* End of while loop....
            
            // create an iterator
            Iterator iterator = loginSet.iterator();
            Iterator name = nameSet.iterator();
            Iterator email = emailSet.iterator();
            
           
            
            
            List<String> lList = new ArrayList<>();
            ArrayList<String> nList = new ArrayList<>();
            ArrayList<String> eList = new ArrayList<>();
            ArrayList<String> locList = new ArrayList<>();
            
            
            
            
            while(iterator.hasNext()){
                lList.add((String) iterator.next());
            }
            while(name.hasNext()){
                nList.add((String) name.next());
            }
            while(email.hasNext()){
                eList.add((String) email.next());
            }
            
            /// This is where we shall store the list of all the comits with other details
            ArrayList<Long> commList = new ArrayList<>();
            ArrayList<Long> chaList = new ArrayList<>();
            ArrayList<Long> addList = new ArrayList<>();
            ArrayList<Long> delList = new ArrayList<>();
            ArrayList<Long> pubList = new ArrayList<>();
            ArrayList<Long> gisList = new ArrayList<>();
            ArrayList<Long> folList = new ArrayList<>();
            ArrayList<Long> fowList = new ArrayList<>();
            
            ///First initials all with 0 depending on number of users...
            
            for(int a=0; a<lList.size(); a++){
                long com=0;
                commList.add(com);
                chaList.add(com);
                addList.add(com);
                delList.add(com);
                pubList.add(com);
                gisList.add(com);
                folList.add(com);
                fowList.add(com);
                
            }
            if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
            // Getting the PR Open, PR Clossed IS Openned, Is Clossed, Forks, Stars
            String pulsURL =  callURL("https://api.github.com/repos/"+projectName+"/pulls?since="+date1+"&until="+date2+"&access_token="+toks[ct++]);
            String issuesURL =  callURL("https://api.github.com/repos/"+projectName+"/issues?since="+date1+"&until="+date2+"&access_token="+toks[ct++]);
                
            JSONArray pullsArray = (JSONArray)parser.parse(pulsURL);
            JSONArray issuesArray = (JSONArray)parser.parse(issuesURL);
                
                     long pl=0,pc=0,f=0,is=0,ic=0,s=0,w=0;
                     for (Object jsonObj : pullsArray) {
                         
                         JSONObject pullsObj = (JSONObject)jsonObj;
                         JSONObject headObj = (JSONObject)pullsObj.get("head");
                         JSONObject reposObj = (JSONObject) headObj.get("repo");
                         
                         if(reposObj != null){
                             // System.out.println(reposObj);
                             pl += (long) reposObj.get("open_issues");
                             f += (long) reposObj.get("forks");
                             w += (long) reposObj.get("watchers");
                         }
                        
                         
                     }
                     
                     for (Object jsonObj2 : issuesArray) {
                         JSONObject issuesObj = (JSONObject)jsonObj2;
                         String is_open = (String) issuesObj.get("state");
                         String is_close = (String) issuesObj.get("closed_at");
                         if(is_open.equals("open")){
                             is += 1;
                         }else{
                             is += 0;
                         }
                         
                         
                         
                     }
                      
            /// Now we need to loop through all the sha.....
            for(int x=0; x<shaLists.size(); x++){
                
                
                //All about pulls Stop here..
                
                for(int b=0; b<lList.size(); b++){/// Looping thru all the Shaa ......
                      String lg = lList.get(b);
                      if (ct == (toks.length-1) ){/// the the index for the tokens array...
                           ct = 0; //// go back to the first index......
                        }
                      loginURL = callURL("https://api.github.com/users/"+lg+"?access_token="+toks[ct++]);
                    
                      String commitsShaURL = callURL("https://api.github.com/repos/"+projectName+"/commits/"+shaLists.get(x)+"?access_token="+toks[ct++]);
                      JSONObject allObj = (JSONObject)parser.parse(commitsShaURL);
                      ////
                      JSONArray fileArray = (JSONArray) allObj.get("files");
                      if(!fileArray.toString().equals("[]")){
                          /// Now we also need to get the Login Details,,the corresponding followes and following
                          JSONObject logObj = (JSONObject) allObj.get("author");
                          List<String> comList = new ArrayList<>();
                         // String lgg = "###########";
                         
                          for(int a=0; a<fileArray.size(); a++){
                               JSONObject fileObj = (JSONObject)fileArray.get(a);
                               changed = (long) fileObj.get("changes");
                               added = (long) fileObj.get("additions");
                               deleted = (long) fileObj.get("deletions");
                               
                               /// Checking for null objects...
                               if(logObj != null){
                                    login = (String) logObj.get("login");
                                    if (ct == (toks.length-1)){/// the the index for the tokens array...
                                        ct = 0; //// go back to the first index......
                                    }
                                    
                                    loginObj = (JSONObject)parser.parse(loginURL);
                                    ///..........................................
                                    if(login.equals(lg)){
                                        
                                        
                                        int index = b;
                                        
                                        long get_cha = chaList.get(b) + (long) fileObj.get("changes");
                                        long get_add = addList.get(b) + (long) fileObj.get("additions");
                                        long get_del = delList.get(b) + (long) fileObj.get("deletions");
                                        
                                        ///To update specific element in the List we have to first remove that element
                                       
                                        chaList.remove(b);
                                        addList.remove(b);
                                        delList.remove(b);
                                        
                                        /// Now we have to put it back to there position
                                        
                                        chaList.add(b, get_cha);
                                        addList.add(b, get_add);
                                        delList.add(b, get_del);
                                        
                                    }
                                 
                                //**********************************************
                                }
                               
                          }
                          
                          if(logObj != null){
                                    login = (String) logObj.get("login");
                                    if(login.equals(lg)){
                                        long get_com = commList.get(b) + 1;
                                        long get_up = pubList.get(b) + (long) loginObj.get("public_repos");
                                        long get_gis = gisList.get(b) + (long) loginObj.get("public_gists");
                                        long get_fol = folList.get(b) + (long) loginObj.get("followers");
                                        long get_fow = fowList.get(b) + (long) loginObj.get("following");
                                        
                                        
                                        commList.remove(b);
                                        pubList.remove(b);
                                        gisList.remove(b);
                                        folList.remove(b);
                                        fowList.remove(b);
                                        
                                        commList.add(b, get_com);
                                        pubList.add(b, get_up);
                                        gisList.add(b, get_gis);
                                        folList.add(b, get_fol);
                                        fowList.add(b, get_fow);
                                        
                                        locationSet.add((String) loginObj.get("location"));
                                        createdSet.add((String) loginObj.get("created_at"));
                                        updatedSet.add((String) loginObj.get("updated_at"));
                                    }
                          }
                         
                      }
                    
                }
                    
                      
             }
            
            /// Split them here..
            
            ArrayList<String> locateList = new ArrayList<>();
            ArrayList<String> createdList = new ArrayList<>();
            ArrayList<String> updatedList = new ArrayList<>();
            
            Iterator locat = locationSet.iterator();
            Iterator create = createdSet.iterator();
            Iterator update = updatedSet.iterator();
            
            while(locat.hasNext()){
                locateList.add((String) locat.next());
            }
            while(create.hasNext()){
                createdList.add((String) create.next());
            }
            while(update.hasNext()){
                updatedList.add((String) update.next());
            }
            
            
            List<String> allList = new ArrayList<>();
            allList.add(date1+" - "+date2);
            allList.add(String.valueOf(pl));
            allList.add(String.valueOf(pc));
            allList.add(String.valueOf(is));
            allList.add(String.valueOf(ic));
            allList.add(String.valueOf(f));
            allList.add(String.valueOf(w));
            if(i == 0){
                allList.add(projectName);
            }else{
                allList.add("-");
            }
            
           if(shaLists.size() > 0){
               
               for (int z=0; z<lList.size(); z++){
                   allList.add(nList.get(z)+"/"+eList.get(z)+"/"+lList.get(z)+"/"+createdList.get(z)+"/"+updatedList.get(z)+"/"
                           +pubList.get(z)+"/"+gisList.get(z)+"/"+folList.get(z)+"/"+fowList.get(z)+"/" +commList.get(z)+"_"+chaList.get(z)+"_"
                           +addList.get(z)+"_"+delList.get(z));
                   
               }
               
               datas = new Object[allList.size()];
               datas = allList.toArray(datas);
               /// putting the bodies in to the arraylist..
               
             }
           datas = new Object[allList.size()];
           
           datas = allList.toArray(datas);
              
           
           System.out.print(shaLists+"\t\t pr_O: "+pl+", Closed: "+pc+" IS_OP:"+is+", IS_CL: "+ic+", FORK: "+f+", WATCH: "+w+" \t Login: "+loginSet+"\t name: "+nList+"\t email: "+eList+"\t Location: "+locList+" , Updated: "+pubList+",Gis: "+gisList+",Followers: "+folList+",Following: "+fowList+", Created_At: "+createdSet+", Updated: "+updatedSet+" \t Commits: "+commList+" ,  Changes:"+commList+", Added: "+chaList+", Deleted: "+addList);
           System.out.println("**************************");
           
            //System.out.println(datas);
            return datas;
            
    }
    
    /**
     * 
     * @param myURL
     * @return 
     */
    public  String callURL(String myURL) {
       //System.out.println("Requested URL:" + myURL);
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
