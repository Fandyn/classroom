package com.entry;


import java.io.Serializable;

public class Medtype implements Serializable {

  private long typeid;
  private String mtype;



  public long getTypeid() {
    return typeid;
  }

  public void setTypeid(long typeid) {
    this.typeid = typeid;
  }


  public String getMtype() {
    return mtype;
  }

  public void setMtype(String mtype) {
    this.mtype = mtype;
  }



  @Override
  public String toString() {
    return "Medtype{" +
            "typeid=" + typeid +
            ", mtype='" + mtype + '\'' +
            '}';
  }
}
