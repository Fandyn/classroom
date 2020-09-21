package com.dao.medtype.impl;

import com.dao.medtype.MedTypeDao;
import com.entry.Medtype;
import com.util.DBUtil;
import com.util.PageBean;
import com.util.TransBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MedTypeImpl implements MedTypeDao {
    DBUtil dbUtil=new DBUtil();

    @Override
    public void selectAllMedType(PageBean page) {

            List<Map> dlist = null;

            String sql = "select count(*) from medtype where  1=1  ";
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



            sql = " select typeid,mtype from medtype where  1=1  %s limit ?,? ";

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
    public Medtype selectOneById(Integer id) {
        String sql="select typeid,mtype from medtype where typeid=?";
        List<Medtype> lists=TransBean.populate(Medtype.class,dbUtil.executeQuery(sql,id)) ;
        return lists.get(0);
    }

    @Override
    public int updateOne(Medtype medtype) {
        String sql="update medtype set mtype=?  where typeid=?";
        try {
            return dbUtil.executeUpdate(sql,medtype.getMtype(),medtype.getTypeid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return -1;
    }

    @Override
    public void insertOneType(Medtype medtype) {

        String sql="insert medtype values(null,?)";
        try {
            dbUtil.executeUpdate(sql,medtype.getMtype());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int deleteONeType(Integer id) {
        String sql="delete from medtype where  typeid=?";
        try {
            dbUtil.executeUpdate(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
