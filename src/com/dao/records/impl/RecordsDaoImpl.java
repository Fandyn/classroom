package com.dao.records.impl;

import com.dao.records.RecordsDao;
import com.entry.Sellgoods;
import com.util.DBUtil;
import com.util.PageBean;
import com.util.TransBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordsDaoImpl implements RecordsDao {
    DBUtil dbUtil=new DBUtil();

    @Override
    public void selectAllRecords(PageBean page) {

        List<Map> dlist = null;
        String sql = "select count(*) from records where  1=1  ";
        String factor = "";
        if (page.getFactor() != null && page.getFactor().length > 0) {
            for (String str : page.getFactor()) {
                if (str != null && str != "") {
                    factor += " and  " + str;
                }
            }
        }


        List<List> list = null;
        try {
            list = dbUtil.queryList(sql + factor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        page.setRowCount(((Long) list.get(0).get(0)).intValue());//设置总行数
        page.setTotalPage();//计算总页数
        //----------查询共有多少条和多少页-----------
        //----------查询该条件下的数据集合并设置到pageBean的data中-----------

        sql = " select rid,rselldate,price,mid from records where  1=1  %s limit ?,? \n";

        factor = "";
        if (page.getFactor() != null && page.getFactor().length > 0) {
            for (String str : page.getFactor()) {
                if (str != null && str != "") {
                    factor += "  and  " + str;
                }
            }
        }
        sql = String.format(sql, factor);

        List<Map> maps;

        maps = dbUtil.executeQuery(sql, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        page.setData(maps);

    }


    @Override
    public void memberselectRecords(PageBean page) {

        List<Map> dlist = null;
        String sql = "select count(*) from records where  1=1  ";
        String factor = "";
        if (page.getFactor() != null && page.getFactor().length > 0) {
            for (String str : page.getFactor()) {
                if (str != null && str != "") {
                    factor += " and  " + str;
                }
            }
        }


        List<List> list = null;
        try {
            list = dbUtil.queryList(sql + factor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        page.setRowCount(((Long) list.get(0).get(0)).intValue());//设置总行数
        page.setTotalPage();//计算总页数
        //----------查询共有多少条和多少页-----------
        //----------查询该条件下的数据集合并设置到pageBean的data中-----------

        sql = " select rid,rselldate,price,records.mid,mname,mphone\n" +
                " from records,member \n" +
                " where records.mid=member.mid  %s limit ?,? \n";

        factor = "";
        if (page.getFactor() != null && page.getFactor().length > 0) {
            for (String str : page.getFactor()) {
                if (str != null && str != "") {
                    factor += "  and  " + str;
                }
            }
        }
        sql = String.format(sql, factor);

        List<Map> maps;

        maps = dbUtil.executeQuery(sql, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        page.setData(maps);

    }

    @Override
    public List<Sellgoods> sellOneAll(Integer id) {
        String sql="select sid,sname,snumber,snorms,sprice from sellgoods where rid=?";
        return TransBean.populate(Sellgoods.class, dbUtil.executeQuery(sql,id));
    }


}
