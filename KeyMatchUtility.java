/*
 * GSALogin.java
 *
 * Created on July 11, 2009, 11:13 PM
 * Author Aswath
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.*;



public class KeyMatchUtility {
    
    static final String LOGON_SITE = "GSA_IP";//Enter ip address of the GSA
    static final int LOGON_PORT = 8000;//Enter port number of the GSA
    
    public static void main(String[] args) throws Exception {
        
        
        try {
             HttpClient client = new HttpClient();
            client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT, "http");
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

            GetMethod authget = new GetMethod("/EnterpriseController");
	    //client.setTimeout(300000); 
            client.executeMethod(authget);
            System.out.println("  -   - "+client.toString());
            System.out.println("  -   - "+"Login form get: " + authget.getStatusLine().toString());
	    System.out.println("  -   - "+"  -   - ");
            // release any connection resources used by the method
            authget.releaseConnection();
            // See if we got any cookies
            CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
            org.apache.commons.httpclient.Cookie[] initcookies_1 = cookiespec.match(
                    LOGON_SITE, LOGON_PORT, "/", false, client.getState().getCookies());
            System.out.println("  -   - "+"Initial set of cookies:");
            if (initcookies_1.length == 0) {
                System.out.println("  -   - "+"None");
            } else {
                for (int i = 0; i < initcookies_1.length; i++) {
                    System.out.println("  -   - "+"- " + initcookies_1[i].toString());
                }
            }
            PostMethod authpost = new PostMethod("/EnterpriseController");
            // Prepare login parameters
            NameValuePair action = new NameValuePair("action", "/EnterpriseController");
            NameValuePair actionType = new NameValuePair("actionType", "authenticateUser");
            NameValuePair userid = new NameValuePair("userName", "userName");
            NameValuePair password = new NameValuePair("password", "password");
            authpost.setRequestBody(
                    new NameValuePair[]{action, actionType, userid, password});
            client.executeMethod(authpost);
            System.out.println("  -   - "+"Login form post: " + authpost.getStatusLine().toString());
            System.out.println("  -   - "+"Login form post: " + authpost.getURI() + ":---" + authpost.getParams());
            System.out.println("  -   -  -   - "+"Login form post: " + authpost.getResponseBodyAsString());
            // release any connection resources used by the method
            
            org.apache.commons.httpclient.Cookie[] logoncookies_1 = cookiespec.match(
                    LOGON_SITE, LOGON_PORT, "/", false, client.getState().getCookies());
            System.out.println("  -   - "+"Logon cookies:");
            if (logoncookies_1.length == 0) {
                System.out.println("  -   - "+"None");
            } else {
                for (int i = 0; i < logoncookies_1.length; i++) {
                    System.out.println("  -   - "+"- " + logoncookies_1[i].toString());
                }
            }
            // Usually a successful form-based login results in a redicrect to
            // another url
            int statuscode = authpost.getStatusCode();
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                    (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                    (statuscode == HttpStatus.SC_SEE_OTHER) ||
                    (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                Header header = authpost.getResponseHeader("location");
                if (header != null) {
                    String newuri = header.getValue();
                    if ((newuri == null) || (newuri.equals(""))) {
                        newuri = "/";
                    }
                    System.out.println("  -   - "+"Redirect target: " + newuri);
                    GetMethod redirect = new GetMethod(newuri);
                    client.executeMethod(redirect);
                    System.out.println("  -   - "+"Redirect: " + redirect.getStatusLine().toString());
                } else {
                    System.out.println("  -   - "+"Invalid redirect");
                    System.exit(1);
                }
            }
            System.out.println("  -   - "+"********************  End of Porcess 1 ********************");                                               
            
            authpost = new PostMethod("/EnterpriseController?actionType=frontKeymatchCreate");
            // Prepare login parameters
            NameValuePair action2 = new NameValuePair("action", "/EnterpriseController");
            NameValuePair actionType2 = new NameValuePair("actionType", "frontKeymatchCreate");
            
            
            
            authpost.setRequestBody(
                    new NameValuePair[]{action2, actionType2});
            client.executeMethod(authpost);
            System.out.println("<br /><br />"+"Login form post: " + authpost.getStatusLine().toString());
            System.out.println("<br /><br />"+"Login form post: " + authpost.getURI() + ":---" + authpost.getParams());
        //    System.out.println("  -   -  -   - "+authpost.getResponseBodyAsString());
            
            System.out.println("  -   - "+"********************  End of Process 2 ********************");
            
            authpost = new PostMethod("/EnterpriseController?actionType=frontKeymatchCreate");
            
            NameValuePair km_0_0 = new NameValuePair("km_0_0", "test");
            NameValuePair km_0_1 = new NameValuePair("km_0_1", "KeywordMatch");
            NameValuePair km_0_3 = new NameValuePair("km_0_3", "test");
            NameValuePair km_0_2 = new NameValuePair("km_0_2", "test.com");
            NameValuePair frontend = new NameValuePair("frontend", "default_frontend");
            NameValuePair frontKeymatchSave = new NameValuePair("frontKeymatchSave", "Save New Matches");
            NameValuePair startRow = new NameValuePair("startRow", "1");
            NameValuePair size = new NameValuePair("size", "10");
            NameValuePair search = new NameValuePair("search", "");
            authpost.setRequestBody(
                    new NameValuePair[]{km_0_0,km_0_1,km_0_3,km_0_2,frontend,frontKeymatchSave,startRow,size,search});
            client.executeMethod(authpost);
            System.out.println("<br /><br />"+"Login form post: " + authpost.getStatusLine().toString());
            System.out.println("<br /><br />"+"Login form post: " + authpost.getURI() + ":---" + authpost.getParams());
            System.out.println("  -   -  -   - "+authpost.getResponseBodyAsString());
            
            System.out.println("  -   - "+"********************  End of Process 3 ********************");
            
            authpost = new PostMethod("/EnterpriseController?actionType=logout");
            // Prepare login parameters
            NameValuePair action3 = new NameValuePair("action", "/EnterpriseController?actionType=logout");
            authpost.setRequestBody(
                    new NameValuePair[]{action3});
            client.executeMethod(authpost);
            System.out.println("  -   - "+"Login form post: " + authpost.getStatusLine().toString());
            System.out.println("  -   - "+"Login form post: " + authpost.getURI() + ":---" + authpost.getParams());
            System.out.println("  -   -  -   - "+authpost.getResponseBodyAsString());
            System.out.println("  -   - "+"********************  End of Process 4 ********************");
            authpost.releaseConnection();
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}

