package com.yuehai.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 月海
 * @create 2021/12/28 15:36
 */
public class JDBCUtils {

    private static DruidDataSource dataSource;
    // ThreadLocal的作用：它可以解决多线程的数据安全问题。
    // 泛型内的数据为：当前线程关联的数据类型；即关联的数据为数据库链接
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    // 静态代码块
    static {
        try {
            Properties properties = new Properties();

            // 加载druid.properties配置文件
            // 使用类加载器，识别目录为当前工程的src目录下
            InputStream resource = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");

            // 读取文件中的键值对
            properties.load(resource);

            // 创建一个druid数据库连接池，并将读取的文件作为参数传入
            // 根据提供的DruidDataSourceFactory创建对应的DataSource对象
            // 创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池的链接
     */
    public static Connection getConnection(){

        // 从ThreadLocal对象中获取数据库链接
        Connection conn = conns.get();

        // 判断数据库链接是否为空
        if(conn == null){
            // 为空则从数据库链接池里取
            try {
                // 通过dbcp数据库连接池创建数据库连接对象
                conn = dataSource.getConnection();

                // 将数据库链接对象保存到ThreadLocal对象中，供后面的JDBC操作使用
                conns.set(conn);

                // 设置为手动管理事务
                conn.setAutoCommit(false);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // 返回创建完成后的数据库连接
        return conn;
    }

    /**
     * 提交事务，并关闭数据库连接、释放链接
     */
    public static void commitAndClose(){
        // 从ThreadLocal对象中获取数据库链接
        Connection conn = conns.get();

        // 判断数据库链接是否为空
        if(conn != null){
            // 数据库链接不为空
            try {
                // 提交事务
                conn.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    // 关闭数据库连接、释放链接
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // 一定要执行 remove 操作，否则就会出错（内存泄露）。
        // 因为 Tomcat 服务器底层使用了线程池技术
        conns.remove();
    }

    /**
     * 回滚事务，并关闭数据库连接池、释放链接
     */
    public static void rollbackAndClose(){
        // 从ThreadLocal对象中获取数据库链接
        Connection conn = conns.get();

        // 判断数据库链接是否为空
        if(conn != null){
            // 数据库链接不为空
            try {
                // 回滚事务
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    // 关闭数据库连接、释放链接
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // 一定要执行 remove 操作，否则就会出错（内存泄露）。
        // 因为 Tomcat 服务器底层使用了线程池技术
        conns.remove();
    }

}
