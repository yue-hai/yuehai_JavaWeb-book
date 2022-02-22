package com.yuehai.dao;

import com.yuehai.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 月海
 * @create 2021/12/28 16:27
 */
public abstract class BaseDao {

    // 使用 DBUtils 操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update() 方法用来执行：inser、update、delete 语句
     * @return 如果返回 -1 ，说明执行失败，返回其他表示影响的行数
     */
    public int update(String sql,Object ...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.update(conn,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 抛出捕获的异常，让其他方法也知道
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一个 JavaBean 的 sql 语句
     * @param type  返回的对象类型
     * @param sql   执行的 sql 语句
     * @param args  sql 语句对象的占位符
     * @param <T>   返回的类型的参数
     * @return
     */
    public <T> T queryForOne(Class<T> type,String sql,Object ...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 抛出捕获的异常，让其他方法也知道
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回多个 JavaBean 的 sql 语句
     * @param type  返回的对象类型
     * @param sql   执行的 sql 语句
     * @param args  sql 语句对象的占位符
     * @param <T>   返回的类型的参数
     * @return
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object ...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 抛出捕获的异常，让其他方法也知道
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一行一列的 sql 语句
     * @param sql   执行的 sql 语句
     * @param args  sql 语句对象的占位符
     * @return
     */
    public Object queryForSingleValue(String sql, Object ...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new ScalarHandler(),args);
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出捕获的异常，让其他方法也知道
            throw new RuntimeException(e);
        }
    }

}
