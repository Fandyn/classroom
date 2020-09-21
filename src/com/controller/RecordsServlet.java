package com.controller;

import com.alibaba.fastjson.JSON;
import com.dao.medtype.MedTypeDao;
import com.dao.medtype.impl.MedTypeImpl;
import com.dao.records.RecordsDao;
import com.dao.records.impl.RecordsDaoImpl;
import com.entry.Medtype;
import com.entry.Sellgoods;
import com.util.PageBean;
import com.util.TransBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/records/*")
public class RecordsServlet extends HttpServlet {
    RecordsDao recordsDao=new RecordsDaoImpl();
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = uri.substring(uri.lastIndexOf("/") + 1);
        String target = null;
        switch (method) {
            case "selectRecords": target = selectRecords(request, response);break;
            case "memberselectRecords": target = memberselectRecords(request, response);break;
            case "xiangxi": target = xiangxi(request, response);break;
//            药品类型
            case "showtype": target = showtype(request, response);break;
            case "findOneType": target = findOneType(request, response);break;
            case "updateOne": target = updateOne(request, response);break;
            case "insertOne": target = insertOne(request, response);break;
            case "deleteOne": target = deleteOne(request, response);break;
        }
        if (target != null) {
            request.getRequestDispatcher(target).forward(request, response);
        }


    }

    private String deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String rid=request.getParameter("id");

        System.out.println(rid);
        MedTypeDao medTypeDao=new MedTypeImpl();

        medTypeDao.deleteONeType(Integer.parseInt(rid));

        return "/jsp/type/ShowType.jsp";
    }

    private String insertOne(HttpServletRequest request, HttpServletResponse response) {
        MedTypeDao medTypeDao=new MedTypeImpl();
        Medtype medtype=new Medtype();
        TransBean.populate(medtype, request.getParameterMap());
        System.out.println(medtype);
        medTypeDao.insertOneType(medtype);
        return null;

    }

    private String updateOne(HttpServletRequest request, HttpServletResponse response) {
        MedTypeDao medTypeDao=new MedTypeImpl();
        Medtype medtype=new Medtype();
        TransBean.populate(medtype, request.getParameterMap());

        medTypeDao.updateOne(medtype);
        return null;
    }

    private String findOneType(HttpServletRequest request, HttpServletResponse response) {
        String rid=request.getParameter("id");
        MedTypeDao medTypeDao=new MedTypeImpl();
         Medtype medtype = medTypeDao.selectOneById(Integer.parseInt(rid));
        String jsonStr = JSON.toJSONString(medtype);
        output(response, jsonStr);
        return null;
    }

    private String showtype(HttpServletRequest request, HttpServletResponse response) {
        PageBean pageBean = new PageBean();
        MedTypeDao medTypeDao=new MedTypeImpl();
        try {
            TransBean.populate(pageBean, request.getParameterMap());
            medTypeDao.selectAllMedType(pageBean);
            String jsonStr = JSON.toJSONString(pageBean);
            output(response, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String xiangxi(HttpServletRequest request, HttpServletResponse response) {
        String rid=request.getParameter("rid");
        RecordsDao recordsDao=new RecordsDaoImpl();
        List<Sellgoods> sellgoods= recordsDao.sellOneAll(Integer.parseInt(rid));
        String jsonStr = JSON.toJSONString(sellgoods);
        output(response, jsonStr);
        return null;

    }

    private String memberselectRecords(HttpServletRequest request, HttpServletResponse response) {
        PageBean pageBean = new PageBean();
        try {
            TransBean.populate(pageBean, request.getParameterMap());
            recordsDao.memberselectRecords(pageBean);
            String jsonStr = JSON.toJSONString(pageBean);
            output(response, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String selectRecords(HttpServletRequest request, HttpServletResponse response) {
        PageBean pageBean = new PageBean();

        try {
            TransBean.populate(pageBean, request.getParameterMap());
            recordsDao.selectAllRecords(pageBean);
            String jsonStr = JSON.toJSONString(pageBean);
            output(response, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void output(HttpServletResponse response, String jsonString) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(jsonString);
        out.close();
    }

}
