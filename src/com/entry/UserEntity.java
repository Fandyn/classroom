package com.entry;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class UserEntity implements Serializable, HttpSessionBindingListener, HttpSessionActivationListener {
    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("UserEntity属性被放入");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("UserEntity属性被移除");
    }

    /**
     * 钝化：服务器停止的时候会将这些实现了HttpSessionActivationListener接口的对象（Sesission）
     * 将对象放入到一个缓存文件中，session.ser--session内容持久化，tomcat：session.ser
     */
    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("UserEntity即将被钝化");
    }

    /**
     * 项目启动的时候：工程会自动去去读取这个文件，将session还原。
     */
    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("UserEntity已经被活化");

    }

    private String name;

    public UserEntity(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    private String pwd;

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
