package dao;

import database.Database;
import entity.SanPham;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SanPhamDAO extends DAO<SanPham, String> {

    String INSERT_SQL = "INSERT INTO SanPham (MaSP, TenSP, MaLoai, GiaSP, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE SanPham SET TenSP=?, MaLoai=?, GiaSP=?, Hinh=?, MoTa=? WHERE MaSP=?";
    String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP=?";
    String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSP=?";

    @Override
    public void insert(SanPham entity) {
        Database.executeUpdate(INSERT_SQL,
                entity.getMaSP(),
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getGiaSP(),
                entity.getHinh(),
                entity.getMoTa());
    }

    @Override
    public void update(SanPham entity) {
        Database.executeUpdate(UPDATE_SQL,
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getGiaSP(),
                entity.getHinh(),
                entity.getMoTa(),
                entity.getMaSP());
    }

    @Override
    public void delete(String id) {
        Database.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<SanPham> selectBySQL(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = Database.executeQuery(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MaSP"));
                entity.setTenSP(rs.getString("TenSP"));
                entity.setMaLoai(rs.getString("MaLoai"));
                entity.setGiaSP(rs.getInt("GiaSP"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMoTa(rs.getString("MoTa"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
