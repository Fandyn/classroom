package com;

import com.dao.medtype.MedTypeDao;
import com.dao.medtype.impl.MedTypeImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) throws SQLException {
        MedTypeDao medTypeDao=new MedTypeImpl();

        System.out.println(medTypeDao.selectOneById(1));

    }
}
