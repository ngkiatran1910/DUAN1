/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import helper.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Entity.ChuyenDe;

/**
 *
 * @author Admin
 */
public class ChuyenDeDAO{

    String INSERT_SQL = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
    String DELETE_SQL = "DELETE FROM ChuyenDe WHERE MaCD=?";
    String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChuyenDe WHERE MaCD=?";

    public void insert(ChuyenDe entity) {
        XJdbc.Update(INSERT_SQL, entity.getMaCD(), entity.getTenCD(),
                entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
    }

    public void update(ChuyenDe entity) {
        XJdbc.Update(UPDATE_SQL, entity.getTenCD(),
                entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    }

//    @Override
//    public void delete(String id) {
//        XJdbc.Update(DELETE_SQL, id);
//    }
    public void delete(String MaCD) {
        String sql = "DELETE FROM HocVien WHERE MaKH IN (SELECT MaKH from KhoaHoc where MaCD = ?)\n"
                + "DELETE FROM KhoaHoc WHERE MaCD = ?\n"
                + "DELETE FROM ChuyenDe WHERE MaCD = ?";
        XJdbc.Update(sql, MaCD,MaCD,MaCD);
    }

    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<ChuyenDe>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                ChuyenDe model = new ChuyenDe();
                model.setMaCD(rs.getString("MaCD"));
                model.setHinh(rs.getString("Hinh"));
                model.setHocPhi(rs.getDouble("HocPhi"));
                model.setMoTa(rs.getString("MoTa"));
                model.setTenCD(rs.getString("TenCD"));
                model.setThoiLuong(rs.getInt("ThoiLuong"));

                list.add(model);
            }
            rs.getStatement().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
