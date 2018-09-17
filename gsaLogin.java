/*
 * gsaLogin.java
 *
 * Created on April 30, 2009, 12:34 AM
 *
 * Created by Aswath
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.*;



public class gsaLogin {
    
    static final String LOGON_SITE = "ipAddress";//Enter ip address of the GSA
    static final int LOGON_PORT = portNo;//Enter port number of the GSA
    
    public gsaLogin() {
        super();
    }
    public static void main(String[] args) throws Exception {
        
        
        try {
            HttpClient client = new HttpClient();
            client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT, "http");
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
                       
            // See if we got any cookies
            CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
            Cookie[] initcookies = cookiespec.match(
                    LOGON_SITE, LOGON_PORT, "/", false, client.getState().getCookies());
            System.out.println("Initial set of cookies:");
            if (initcookies.length == 0) {
                System.out.println("None");
            } else {
                for (int i = 0; i < initcookies.length; i++) {
                    System.out.println("- " + initcookies[i].toString());
                }
            }
            PostMethod authpost = new PostMethod("/EnterpriseController");
            // Prepare login parameters
            NameValuePair action = new NameValuePair("action", "/EnterpriseController");
            NameValuePair actionType = new NameValuePair("actionType", "authenticateUser");
            NameValuePair userid = new NameValuePair("userName", "UserName");//Enter username of the GSA
            NameValuePair password = new NameValuePair("password", "Password");//Enter password of the GSA
            authpost.setRequestBody(
                    new NameValuePair[]{action, actionType, userid, password});
            client.executeMethod(authpost);
            System.out.println("StatusLine: " + authpost.getStatusLine().toString());
            System.out.println("URI: " + authpost.getURI() + ":---" + authpost.getParams());
            System.out.println("Body: " + authpost.getResponseBodyAsString());
            System.out.println("Body: " + authpost.getResponseBody().toString());

            // release any connection resources used by the method
            authpost.releaseConnection();
            Cookie[] logoncookies = cookiespec.match(
                    LOGON_SITE, LOGON_PORT, "/", false, client.getState().getCookies());
            System.out.println("Logon cookies:");
            if (logoncookies.length == 0) {
                System.out.println("None");
            } else {
                for (int i = 0; i < logoncookies.length; i++) {
                    System.out.println("- " + logoncookies[i].toString());
                }
            }

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
                    System.out.println("Redirect target: " + newuri);
                    GetMethod redirect = new GetMethod(newuri);
                    client.executeMethod(redirect);
                    System.out.println("Redirect: " + redirect.getStatusLine().toString());
                } else {
                    System.out.println("Invalid redirect");
                    System.exit(1);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}
