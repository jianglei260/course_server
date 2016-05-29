package cn.edu.cuit.leijiang.couse.parser;

import cn.edu.cuit.leijiang.couse.moudle.CourseItem;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianglei on 16/5/28.
 */
public class CourseParser {
    public static synchronized String parse(String resultPage) {
        int id=0;
        Document doc = Jsoup.parse(resultPage);
        Elements trs = doc.select("tr");
        int startWeek, endWeek;
        List<CourseItem> courseItems = new ArrayList<>();
        for (int i = 3; i < trs.size() - 2; i++) {
            Element tr = trs.get(i);
            Elements tds = tr.select("td");
            for (int j = 1; j <= tds.size(); j++) {
                Element td = tds.get(j - 1);
                CourseItem courseItem = new CourseItem();
                ++id;
                courseItem.setId(id);
                String value = td.toString();
                value = value.substring(value.indexOf(">") + 1, value.indexOf("</td>"));
                if (value.contains("&nbsp;")) {

                } else if (value.contains("节课")) {

                } else {
                    while (value.contains("<br />")) {
                        if (value.substring(0, value.indexOf("<br />")).contains("单")) {
                            courseItem.setFlag(1);
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else if (value.substring(0, value.indexOf("<br />")).contains("双")) {
                            courseItem.setFlag(2);
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else {
                            courseItem.setFlag(0);
                        }
                        courseItem.setName(value.substring(0, value.indexOf("<br />")));
                        courseItem.setHour(String.valueOf(i - 2));
                        courseItem.setWeekday(j - 1);
                        value = value.substring(value.indexOf("<br />") + 6, value.length());

                        String time = value.substring(0, value.indexOf("<br />"));
                        time = time.replace(" ", "");
                        startWeek = Integer.parseInt(time.substring(0, time.indexOf("--")));
                        endWeek = Integer.parseInt(time.substring(time.indexOf("--") + 2, time.indexOf("周")));
                        courseItem.setSatrtWeek(startWeek);
                        courseItem.setEndWeek(endWeek);

                        value = value.substring(value.indexOf("<br />") + 6, value.length());
                        courseItem.setTeacher(value.substring(0, value.indexOf("<br />")));
                        value = value.substring(value.indexOf("<br />") + 6, value.length());
                        if (value.contains("<br />")) {
                            courseItem.setClassRoom(value.substring(0, value.indexOf("<br />")));
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else {
                            courseItem.setClassRoom(value.substring(0, value.length()));
                        }
                        courseItems.add(courseItem);
                    }

                }

            }
        }
        return JSON.toJSONString(courseItems);
    }
}
