package com.entry;


import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Date;
import java.time.LocalDateTime;

public class Records {

  private int rid;

  private Date rselldate;

  private double price;
  private int mid;


  public int getRid() {
    return rid;
  }

  public void setRid(int rid) {
    this.rid = rid;
  }

  public Date getRselldate() {
    return rselldate;
  }

  public void setRselldate(Date rselldate) {
    this.rselldate = rselldate;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public int getMid() {
    return mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  @Override
  public String toString() {
    return "Records{" +
            "rid=" + rid +
            ", rselldate=" + rselldate +
            ", price=" + price +
            ", mid=" + mid +
            '}';
  }
}
