import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    public Connection conn;
    public PreparedStatement pre;
    public ResultSet rs;
    public static Properties pro;
    //初始化连接
    static{
        init();
    }
    //数据库连接方法
    public static void init(){
        pro=new Properties();
        InputStream is=BaseDao.class.getClassLoader().getResourceAsStream("database.properties");
        try {
            pro.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //传送数据库连接字符串
    public static String getPro(String key){
        return pro.getProperty(key);
    }

    //调用数据库配置文件
    public Connection getConn(){
        //加载驱动类
        try {
            Class.forName(getPro("driver"));
            conn= DriverManager.getConnection(getPro("url"), getPro("user"), getPro("password"));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    //关闭数据库连接
    public void closeAll(){
        try {
            if (rs!=null) {
                rs.close();
            }
            if (pre!=null) {
                pre.close();
            }
            if (conn!=null) {
                conn.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //统一增删改
    public int update(String sql,Object[]params) throws SQLException{
        getConn();
        pre=conn.prepareStatement(sql);
        if (params!=null) {
            for (int i = 0; i < params.length; i++) {
                pre.setObject(i+1, params[i]);
            }
        }
        int result=pre.executeUpdate();
        return result;
    }

    //统一查询
    public ResultSet search(String sql,Object[]params) throws SQLException{
        getConn();
        pre=conn.prepareStatement(sql);
        if (params!=null) {
            for (int i = 0; i < params.length; i++) {
                pre.setObject(i+1, params[i]);
            }
        }
        rs=pre.executeQuery();
        return rs;
    }
}
