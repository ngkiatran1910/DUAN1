/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import helper.XJdbc;
import helper.utilityHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getNguoiHoc() {
        String sql = "{call SP_THONGKENGUOIHOC}";  
         String[] cols = {"NAM","SOLUONG","DAUTIEN","CUOICUNG"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getBangDiem(Integer makh) {
        String sql = "{CALL SP_BANGDIEM(?)}";
        String[] cols = {"MaNH", "HoTen", "Diem"};
        return this.getListOfArray(sql, cols, makh);
    }

    public List<Object[]> getDiemTheoChuyenDe() {
        String sql = "{CALL SP_THONGKEDIEM}";
        String[] cols = {"TenCD","SOHV","THAPNHAT","CAONHAT","TRUNGBINH"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int nam) {
        String sql = "{CALL SP_THONGDOANHTHU(?)}";
        String[] cols = {"TenCD","SOKV","SOHV","DOANHTHU","THAPNHAT","CAONHAT","TRUNGBINH"};
        return this.getListOfArray(sql, cols,nam);
    }

    public List<Integer> selectYears() {
        List<Integer> list = new ArrayList<>();
        String sql = "select distinct year(NgayKG) as nam from KhoaHoc order by year(NgayKG) desc";
        try {
            ResultSet rs = XJdbc.Query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
