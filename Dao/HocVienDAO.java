package Dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import helper.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Entity.HocVien;

/**
 *
 * @author Admin
 */
public class HocVienDAO {

    String INSERT_SQL = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
    String UPDATE_SQL = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
    String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV=?";
    String SELECT_ALL_SQL = "SELECT * FROM HocVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV=?";

    public void insert(HocVien entity) {
        XJdbc.Update(INSERT_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    public void update(HocVien entity) {
        XJdbc.Update(UPDATE_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem(), entity.getMaHV());
    }

    public void delete(int id) {
        XJdbc.Update(DELETE_SQL, id);
    }

    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public HocVien selectById(int id) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<HocVien>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                HocVien model = new HocVien();
                model.setMaHV(rs.getInt("MaHV"));
                model.setMaKH(rs.getInt("MaKH"));
                model.setMaNH(rs.getString("MaNH"));
                model.setDiem(rs.getDouble("Diem"));
                list.add(model);
            }
            rs.getStatement().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<HocVien> selectByKhoaHoc(int makh){
        String sql = "SELECT * FROM HocVien WHERE MaKH=?";
        return this.selectBySql(sql, makh);
    }

}
