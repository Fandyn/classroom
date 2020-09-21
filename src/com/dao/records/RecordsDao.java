package com.dao.records;

import com.entry.Sellgoods;
import com.util.PageBean;

import java.util.List;

public interface RecordsDao {

   void selectAllRecords(PageBean page);

   void memberselectRecords(PageBean page);

   List<Sellgoods> sellOneAll(Integer id);
}
