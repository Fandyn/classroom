package com.dao.medtype;

import com.entry.Medtype;
import com.util.PageBean;

public interface MedTypeDao {
//    显示所有 加分页
    void selectAllMedType(PageBean page);

//    按id查一个
    Medtype selectOneById(Integer id);

//    修改
    int updateOne(Medtype medtype);

//    添加一个
    void  insertOneType(Medtype medtype);

//    s删除
    int deleteONeType(Integer id);
}
