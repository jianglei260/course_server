package cn.edu.cuit.leijiang.couse.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by jianglei on 16/5/28.
 */
public class HttpRequest {
    private static HttpRequest httpRequest;
    private HttpClient httpClient = null;

    private HttpRequest() {
        HttpParams httpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(httpParams, true);

        httpClient = new DefaultHttpClient(httpParams);
    }

    public static HttpRequest getInstance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public synchronized String doGet(String url, String code) {
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), code);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public synchronized String doPost(String url, List<NameValuePair> params, String paramsCode, String resultCode) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity;
        try {
            httpEntity = new UrlEncodedFormEntity(params, paramsCode);
            httpPost.setEntity(httpEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse != null || httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), resultCode);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
