package cn.edu.cuit.leijiang.couse.test;

import cn.edu.cuit.leijiang.couse.net.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianglei on 16/5/29.
 */
public class Test {
    public static void main(String[] args) {
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair depart = new BasicNameValuePair("depart", "计算机学院");
        NameValuePair pro = new BasicNameValuePair("pro", "数字媒体技术本科");
        NameValuePair grade = new BasicNameValuePair("grade", "2013级");
        NameValuePair clazz = new BasicNameValuePair("class", "01班");
        params.add(depart);
        params.add(pro);
        params.add(grade);
        params.add(clazz);
        HttpRequest request = HttpRequest.getInstance();
        String result = request.doPost("http://192.168.1.209:8080/serverlet/course", params, "utf-8", "utf-8");

        System.out.print(result);
    }
}
