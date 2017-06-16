/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opm_Package;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author john
 */
public class New_CommitsInterval {
    
     Object[] datas = null;
    
     public Object[] getCommitsNow(String projectName, String date1, String date2, int i, String[] toks) throws org.json.simple.parser.ParseException {
        
            System.out.println(i+"\t\t\t : "+date1+"\t\t - "+date2);
            
            ///..........................................
            String location = "location";
            long public_repos = 0;
            long public_gists = 0;
            long followers = 0;
            long following = 0;
            String createdAt = "#######";
            String updatedAt = "#######";
            String login = "login######";
            String loginURL = "";
            JSONObject loginObj = null;
            long changed = 0,added = 0, deleted = 0;
                     
            
            ArrayList<String> shaLists = new ArrayList<>();
            ArrayList<String> logList = new ArrayList<>();
            
            Set <String> loginSet = new LinkedHashSet<String>();
            Set <String> locationSet = new LinkedHashSet<String>();
            Set <String> nameSet = new LinkedHashSet<String>();
            Set <String> emailSet = new LinkedHashSet<String>();
            Set <String> createdSet = new LinkedHashSet<String>();
            Set <String> updatedSet = new LinkedHashSet<String>();
            
            
            List<Long> cList = new ArrayList<>();
            List<Long> aList = new ArrayList<>();
            List<Long> dList = new ArrayList<>();
            
            
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
                      if (ct == (toks.length-1) ){/// the the index for the tokens array...
                            ct = 0; //// go back to the first index......
                       }
                      String commitsShaURL = callURL("https://api.github.com/repos/"+projectName+"/commits/"+shaa+"?access_token="+toks[ct++]);
                      JSONObject allObj = (JSONObject)parser.parse(commitsShaURL);
                      ////
                      JSONObject logObj = (JSONObject) allObj.get("author");
                      
                      JSONArray fileArray = (JSONArray) allObj.get("files");
                      long chh = 0,add = 0,del = 0;
                     if(!fileArray.toString().equals("[]")){
                          /// Now we also need to get the Login Details,,the corresponding followes and following
                          
                          List<String> comList = new ArrayList<>();
                         // String lgg = "###########";
                         
                          for(int a=0; a<fileArray.size(); a++){
                               JSONObject fileObj = (JSONObject)fileArray.get(a);
                               chh += (long) fileObj.get("changes");
                               add += (long) fileObj.get("additions");
                               del += (long) fileObj.get("deletions");
                               
                          }
                          
                          
                     }
                     cList.add(chh);
                     aList.add(add);
                     dList.add(del);
                      
                      
                      nameSet.add(name);
                      emailSet.add(email);
                       if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      }
                      /// Now we also need to get the Login Details,,the corresponding followes and following
                      JSONObject loginAuthorObj = (JSONObject) jsonObject.get("author");
                      /// Checking for null objects...
                      if(logObj != null){
                          login = (String) loginAuthorObj.get("login");
                          loginSet.add(login);
                          logList.add(login);
                          /// Preventing dublicates using see............
                          //**********************************************
                      }//......................................
                      
                      if(logObj == null){
                          loginSet.add("login######");
                          logList.add("login######");
                      }
                      
                      //logList.add(login);
                      
                        if (ct == (toks.length-1)){/// the the index for the tokens array...
                          ct = 0; //// go back to the first index......
                      } 
                      //############################################################### ***********
                     
                  }/// *** End of for loop for JSon Object.....
	      p++;//// Goto the next Page.......
             }// ******* End of while loop....
            
            // create an iterator
            Iterator iterator = loginSet.iterator();
            Iterator name = nameSet.iterator();
            Iterator email = emailSet.iterator();  ///
            
            List<String> lList = new ArrayList<>(); //// We 
            ArrayList<String> nList = new ArrayList<>();
            ArrayList<String> eList = new ArrayList<>();
            ArrayList<String> locList = new ArrayList<>();
            
//            
            
            
            while(iterator.hasNext()){
                String l = (String) iterator.next();
                //if(!l.equals("")){
                     lList.add(l);
                //}
               
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
            
            for(int cc=0; cc<shaLists.size(); cc++){
                
                for(int b=0; b<lList.size(); b++){
                   
                           if(logList.get(cc).equals(lList.get(b))){
                                long get_com = commList.get(b) + 1;
                                long get_cha = chaList.get(b) + cList.get(cc);
                                long get_add = addList.get(b) + aList.get(cc);
                                long get_del = delList.get(b) + dList.get(cc);
                                        
                                ///To update specific element in the List we have to first remove that element
                                commList.remove(b);       
                                chaList.remove(b);
                                addList.remove(b);
                                delList.remove(b);
                                        
                                        /// Now we have to put it back to there position
                                commList.add(b, get_com);        
                                chaList.add(b, get_cha);
                                addList.add(b, get_add);
                                delList.add(b, get_del);
                                        
                            }
                }
            }
            
            if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
            // Getting the PR Open, PR Clossed IS Openned, Is Clossed, Forks, Stars
            String pulsURL =  callURL("https://api.github.com/repos/"+projectName+"/pulls?since="+date1+"&until="+date2+"&access_token="+toks[ct++]);
            if (ct == (toks.length-1) ){/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
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
                     
                     
                   for(int b=0; b<lList.size(); b++){/// Looping thru all the Shaa ......
                       //System.out.println(lList.get(b));
                       long get_up = 0;
                       long get_gis = 0;
                       long get_fol = 0;
                       long get_fow = 0;
                       
                                
                       if(!lList.get(b).equals("login######")){
                           if (ct == (toks.length-1) ){/// the the index for the tokens array...
                               ct = 0; //// go back to the first index......
                            }
                            loginURL = callURL("https://api.github.com/users/"+lList.get(b)+"?access_token="+toks[ct++]);
                            loginObj = (JSONObject)parser.parse(loginURL);
                            
                            get_up = pubList.get(b) + (long) loginObj.get("public_repos");
                            get_gis = gisList.get(b) + (long) loginObj.get("public_gists");
                            get_fol = folList.get(b) + (long) loginObj.get("followers");
                            get_fow = fowList.get(b) + (long) loginObj.get("following");
                                        
                            location = (String) loginObj.get("location");
                            createdAt = (String) loginObj.get("created_at");
                            updatedAt = (String) loginObj.get("updated_at");
                            
                            pubList.set(b, get_up);
                            gisList.set(b, get_gis);
                            folList.set(b, get_fol);
                            fowList.set(b, get_fow);
                            
                            locationSet.add(location);
                            createdSet.add(createdAt);
                            updatedSet.add(updatedAt);
                            
                       }
                       if(lList.get(b).equals("login######")){
                            pubList.set(b, pubList.get(b)+get_up);
                            gisList.set(b, gisList.get(b)+get_gis);
                            folList.set(b, folList.get(b)+get_fol);
                            fowList.set(b, fowList.get(b)+get_fow);
                            
                            locationSet.add("location");
                            createdSet.add("created");
                            updatedSet.add("updated");
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
           
            ///System.out.println(nList+"\t"+eList+"\t"+lList+"\t"+createdList+"\t"+updatedList+"\t"+pubList+"\t"+gisList+"\t"+folList+"\t"+fowList+"\t"+commList+"\t"+chaList+"\t"+addList+"\t"+delList);
           
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
    
}
