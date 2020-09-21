package com.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DBUtil {
    private static String url;
    private static String username;
    private static String password;

    static {
        //1. 加载驱动
        try {
            InputStream is = DBUtil.class.getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(is);
            url = (String) properties.get("db.url");
            username = (String) properties.get("db.user");
            password = (String) properties.get("db.pwd");
            String classname = (String) properties.get("db.className");
            Class.forName(classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    /**
     * ...忽略，如果传递的参数是多个：
     * 将第一个参数值赋值给sql 后续所有的参数值封装直接赋值给objs
     *
     * @param sql
     * @param objs
     * @return
     */
    public List<Map> executeQuery(String sql, Object... objs) {
        //用户名  密码：
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Map> list = new ArrayList();
        try {
            conn = this.getConnection();
            stm = conn.prepareStatement(sql);
            if (objs != null && objs.length > 0) {
                for (int i = 1; i <= objs.length; i++) {
                    stm.setObject(i, objs[i - 1]);
                }
            }
            rs = stm.executeQuery();//rs是结果集
            //存储的是：结果集合的结构：有几列 每一列的列名  数据类型等：空的二位表格的结构
            ResultSetMetaData metaData = rs.getMetaData();
            int lie = metaData.getColumnCount();
            //循环的行
            while (rs.next()) {
                Map map = new HashMap();//每一行是一个Map
                //循环的列
                for (int i = 1; i <= lie; i++) {
                    //每一列是Map的一个键值对:列名作为key  列植作为值
                    map.put(metaData.getColumnLabel(i).toLowerCase(), rs.getObject(i));
                    // System.out.print(metaData.getColumnClassName(i)+":"+metaData.getColumnName(i)+":"+rs.getObject(i)+"\t");
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(conn, stm, rs);
        }
        return list;
    }


    public  List<List> queryList(String sql, Object... objects) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<List> lists = new ArrayList<>();

        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            addSet(preparedStatement, objects);
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                List list = new ArrayList();
                for (int i = 1; i <= count; i++) {
                    list.add(resultSet.getObject(i));
                }
                lists.add(list);
            }
        } finally {
            this.close(connection, preparedStatement,resultSet);
        }

        return lists;
    }
    public  void addSet(PreparedStatement preparedStatement, Object... objects) throws SQLException {
        if (objects.length == 0) return;
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
    }

    /*
     *   并不是所有新增都需要知道主键：
     *       账号：学生ID--- 学生
     *       学生姓名  年龄  账号 密码
     *       学生id=insert into 学生
     *       insert into 账号(学生id)
     * */
    public int executeInsertForKey(String sql, Object... objs) {
        //用户名  密码：
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //数组长度  ？个数一定必须一致  一一对应进行赋值的
            if (objs != null && objs.length > 0) {
                for (int i = 1; i <= objs.length; i++) {
                    stm.setObject(i, objs[i - 1]);
                }
            }
            stm.executeUpdate();

            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(conn, stm, rs);
        }
        return -1;
    }

    /**
     * 新增  修改  删除
     */
    public int executeUpdate(String sql, Object... objs) throws SQLException {
        //用户名  密码：
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = this.getConnection();
            stm = conn.prepareStatement(sql);
            //数组长度  ？个数一定必须一致  一一对应进行赋值的
            if (objs != null && objs.length > 0) {
                for (int i = 1; i <= objs.length; i++) {
                    stm.setObject(i, objs[i - 1]);
                }
            }
            return stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.close(conn, stm, null);
        }
    }

    public void close(Connection conn, Statement stm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
