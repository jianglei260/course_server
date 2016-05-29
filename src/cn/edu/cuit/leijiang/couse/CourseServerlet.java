package cn.edu.cuit.leijiang.couse;

import cn.edu.cuit.leijiang.couse.catcher.Catcher;
import cn.edu.cuit.leijiang.couse.moudle.CourseInfo;
import cn.edu.cuit.leijiang.couse.parser.CourseParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jianglei on 16/5/28.
 */
public class CourseServerlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CourseInfo courseInfo = getCourseInfo(req);
        Catcher catcher = new Catcher();
        String resultPage = catcher.getCoursePage(courseInfo);
        if (resultPage == null) {
            resp.sendError(400, "can't catch request course");
            return;
        }
        resp.getWriter().write(CourseParser.parse(resultPage));
    }

    private CourseInfo getCourseInfo(HttpServletRequest req) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setDepart((String) req.getParameter("depart"));
        courseInfo.setPro((String) req.getParameter("pro"));
        courseInfo.setGrade((String) req.getParameter("grade"));
        courseInfo.setClazz((String) req.getParameter("class"));
        return courseInfo;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CourseInfo courseInfo = getCourseInfo(req);
        Catcher catcher = new Catcher();
        String resultPage = catcher.getCoursePage(courseInfo);
        if (resultPage == null) {
            resp.sendError(400, "can't catch request course");
            return;
        }
        String result = new String(CourseParser.parse(resultPage).getBytes(), "iso-8859-1");
        resp.getWriter().write(result);
//        resp.getWriter().write(resp.getCharacterEncoding());
//        System.out.print( resp.getCharacterEncoding());
    }
}

