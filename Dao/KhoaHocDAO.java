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
import Entity.KhoaHoc;

/**
 *
 * @author Admin
 */
public class KhoaHocDAO {

    String INSERT_SQL = "INSERT INTO KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV) VALUES (?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?,MaNV=?,NgayTao=? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM HocVien WHERE MaKH = ? DELETE FROM KhoaHoc WHERE MaKH = ?\n";
    String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH=?";

    public void insert(KhoaHoc entity) {
        XJdbc.Update(INSERT_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), 
                entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV());
    }

    public void update(KhoaHoc entity) {
        XJdbc.Update(UPDATE_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(),
                entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao(), entity.getMaKH());
    }

    public void delete(String id) {
        XJdbc.Update(DELETE_SQL, id, id);
    }

    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public KhoaHoc selectById(int id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<KhoaHoc>();
        try {
            ResultSet rs = XJdbc.Query(sql, args);
            while (rs.next()) {
                KhoaHoc model = new KhoaHoc();
                model.setMaKH(rs.getInt("MaKH"));
                model.setMaCD(rs.getString("MaCD"));
                model.setHocPhi(rs.getDouble("HocPhi"));
                model.setThoiLuong(rs.getInt("ThoiLuong"));
                model.setNgayKG(rs.getDate("NgayKG"));
                model.setGhiChu(rs.getString("GhiChu"));
                model.setMaNV(rs.getString("MaNV"));
                model.setNgayTao(rs.getDate("NgayTao"));
                list.add(model);
            }
            rs.getStatement().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhoaHoc> selectByChuyenDe(String macd){
        String sql = "SELECT * FROM KhoaHoc WHERE MaCD=?";
        return this.selectBySql(sql, macd);
    }
    
    public List<Integer> selectYear(){
        String sql = "SELECT DISTINCT year(NgayKG) FROM KhoaHoc Order by Year(NgayKG) DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs =  XJdbc.Query(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
