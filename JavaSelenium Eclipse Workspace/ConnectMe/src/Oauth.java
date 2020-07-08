
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

public class Oauth {

//    static final String USERNAME     = "xxxxxxxx@adt.com.practice";
//    static final String PASSWORD     = "xxxxxxxxdOYWyybG4StbKC6fewTGMcN6S";
//    static final String LOGINURL     = "https://adt--practice.cs50.my.salesforce.com";
//    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
//    static final String CLIENTID     = "3MVG9snqYUvtJB1OdYKXZIn..NM023QRf5UoANpmBrJq3bDgV_OFmGHCOyHIW1ubOGMLUJvPcdn_m5V34XJR1";
//    static final String CLIENTSECRET = "05138AAAC20EB9D33D1ED36421A3C85D8742BF02D988A6D8F833F001BD9D218B";

    static final String USERNAME     = "xxxxxxxx@adt.com.adthstage";
    static final String PASSWORD     = "xxxxxxxxi38HnGyA96Xi7ebN0imy1XijV";
    static final String LOGINURL     = "https://adt--adthstage.cs23.my.salesforce.com";
    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
//    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=assertion&assertion_type=urn%3Aoasis%3Anames%3Atc%3ASAML%3A2.0%3Aprofiles%3ASSO%3Abrowser&assertion=";
    static final String CLIENTID     = "3MVG90D5vR7Utjbqtq6jogC1Ae4CcT9KA6GQ87OZ8FrzOPkJzT65_T4f72YY9gzknDHGT_YxKBf8S_Ik2aDv2";
    static final String CLIENTSECRET = "889F0FA1077AB75B8A4F76F5E6EC68F9C69C5E6FD234B651A94C5BF960A606CD";

    public static void main(String[] args) {

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET+
                          "&username=" + USERNAME +
                          "&password=" + PASSWORD;

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            cpException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }

        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        JSONObject jsonObject = null;
        String loginAccessToken = null;
        String loginInstanceUrl = null;
        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        System.out.println(response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("  instance URL: "+loginInstanceUrl);
        System.out.println("  access token/session ID: "+loginAccessToken);

        // release connection
        httpPost.releaseConnection();
    }
}
