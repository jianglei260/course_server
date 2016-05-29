package cn.edu.cuit.leijiang.couse.catcher;

import cn.edu.cuit.leijiang.couse.moudle.CourseInfo;
import cn.edu.cuit.leijiang.couse.net.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianglei on 16/5/28.
 */
public class Catcher {
    private static final String loginUrl = "http://pkxt.cuit.edu.cn/showfunction.asp";
    private static final String checkUrl = "http://pkxt.cuit.edu.cn/showclasstb.asp";

    public String getCoursePage(CourseInfo courseInfo) {
        String result = "";
        String loginResult=doLogin();
        if (loginResult== null);
//            return null;
        result = doSubmit(courseInfo);
        return result;
    }

    public String doLogin() {
        HttpRequest httpRequest = HttpRequest.getInstance();
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair user = new BasicNameValuePair("user", "guest");
        NameValuePair passwd = new BasicNameValuePair("passwd", "guest");
        params.add(user);
        params.add(passwd);
        return httpRequest.doPost(loginUrl, params, "gb2312","GBK");
    }

    public String doSubmit(CourseInfo courseInfo) {
        HttpRequest httpRequest = HttpRequest.getInstance();
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair depart = new BasicNameValuePair("depart", courseInfo.getDepart());
        NameValuePair pro = new BasicNameValuePair("pro", courseInfo.getPro());
        NameValuePair grade = new BasicNameValuePair("grade", courseInfo.getGrade());
        NameValuePair clazz = new BasicNameValuePair("class", courseInfo.getClazz());
        NameValuePair submit = new BasicNameValuePair("submit", "查询");
        NameValuePair mode = new BasicNameValuePair("mode", "1");
        params.add(submit);
        params.add(mode);
        params.add(depart);
        params.add(pro);
        params.add(grade);
        params.add(clazz);
        String result = httpRequest.doPost(checkUrl, params, "gb2312","utf-8");
        if (result != null) {
            String classtbid = result.substring(result.indexOf("?id="), result.indexOf("&mode="));
            String classTbUrl = "http://pkxt.cuit.edu.cn/show_sub_table.asp" + classtbid + "&mode=1";
            String resultPage = httpRequest.doGet(classTbUrl, "GBK");
            return resultPage;
        }
        return null;
    }
}
