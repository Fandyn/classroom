package com.util;

import com.entry.PageEntity;

import java.util.List;
import java.util.Map;

public class PageUtil {
    public static PageEntity selectPage(StringBuffer stringBuffer, Map map) {
        DBUtil dbUtil = new DBUtil();
        //-------------------往上走都是基础sql-------------------
        int pageNow = BaseUtil.transObjectToInt(map.get("pageNow"));
        pageNow = pageNow <= 0 ? 1 : pageNow; //当前第几页:默认第一页
        int pageRows = BaseUtil.transObjectToInt(map.get("pageRows"));
        pageRows = pageRows <= 0 ? 4 : pageRows; //每页多少条：默认2条
        //在基础sql的基础上拼接上limit：作用是查询当前页数据的
        String sqlNow = stringBuffer.toString() + "   limit " + (pageNow - 1) * pageRows + "," + pageRows;
        List list = dbUtil.executeQuery(sqlNow);//查询当前页数据
        //总共多少条：
        String sqlCount = "select count(*) as rowscount from(" + stringBuffer.toString() + ")a";
        List<Map> sqlCountList = dbUtil.executeQuery(sqlCount);
        int rowsCount = 0;
        if (sqlCountList != null && sqlCountList.size() > 0) {
            rowsCount = BaseUtil.transObjectToInt(sqlCountList.get(0).get("rowscount"));
        }
        //计算总共页
        int pagesCount = 0;
        if (rowsCount % pageRows == 0) {
            pagesCount = rowsCount / pageRows;
        } else {
            pagesCount = rowsCount / pageRows + 1;
        }
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPageNow(pageNow);//当前第一页
        pageEntity.setPageRows(pageRows);//每页多少条
        pageEntity.setPagesCount(pagesCount);//总共多少页
        pageEntity.setRowsCount(rowsCount);//总共多少条
        pageEntity.setPageData(list);//当前页数据
        return pageEntity;
    }
}
