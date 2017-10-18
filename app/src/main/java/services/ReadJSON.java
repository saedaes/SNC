package services;

/**
 * Created by marco on 17/10/17.
 */

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ReadJSON {
    public static HttpClient HTTPCLIENT = new DefaultHttpClient();

    @SuppressWarnings("deprecation")
    public static String readJSON(String URL) {
        String json = "";
        try {
            /*HttpGet request = new HttpGet(URL);
            ResponseHandler<String> response = new BasicResponseHandler();
            json = HTTPCLIENT.execute(request, response);*/
            json = "[{'name':'SUPFD', 'description':'Topic to get notifications from SUPFD application'}, {'name':'ITSP','description':'To get notifications from ITSP application'}, {'name':'MST','description':'To get notifications from MST application'}, {'name':'TEST','description':'Testing notifications with different ids.'}]";
        } catch (Exception e) {
            json = "null";
        }
        return json;
    }
}
