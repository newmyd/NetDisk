package database;

import java.sql.*;

public class Database {
    public static void set(String fileName, String password) throws SQLException {
        String str = "INSERT INTO file (fileName, password) VALUES (?, ?)";
        Connection con = Connect.getCon();
        PreparedStatement sql = con.prepareStatement(str); //预编译SQL，减少sql执行
        sql.setString(1, fileName);
        sql.setString(2, password);
        sql.execute();
//        con.close();
    }
    public static PairString get(int fileId) throws SQLException {
        PairString ans = new PairString();
        ans.init();
        String str = "SELECT * FROM file where fileId = ?";
        Connection con = Connect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        sql.setInt(1, fileId);
        ResultSet res = sql.executeQuery();
        while (res.next()) {
            ans.fileName = res.getString("fileName");
            ans.password = res.getString("password");
        }
//        con.close();
        return ans;
    }
    public static int getFileId() throws SQLException {
        int ans = 0;
        String str = "SELECT max(fileId) FROM file";
        Connection con = Connect.getCon();
        PreparedStatement sql = con.prepareStatement(str);
        ResultSet res = sql.executeQuery();
        while (res.next())
            ans = res.getInt("max(fileId)");
//        con.close();
        return ans;
    }
}