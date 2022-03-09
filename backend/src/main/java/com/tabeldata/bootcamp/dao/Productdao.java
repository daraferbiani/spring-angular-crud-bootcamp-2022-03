package com.tabeldata.bootcamp.dao;


import com.tabeldata.bootcamp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

@Repository
public class Productdao {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Product> list(){
        String sql = "select * from products";
        return this.jdbcTemplate.query(sql,new RowMapperInner());

    }

    public Product findById(Integer id) {
        String sql = "select * from products where id = :kode";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("kode", id);
        return this.jdbcTemplate.queryForObject(sql, map, new RowMapperInner());

    }

    public Integer insertData(Product data) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO products(id, name, price, category, create_date, create_by)" +
                "VALUES (:kode, :name, :price, :category, :create_date, :create_by)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("kode", data.getId());
        map.addValue("name", data.getName());
        map.addValue("price", data.getPrice());
        map.addValue("category", data.getCategory());
        map.addValue("create_date", data.getCreate_date());
        map.addValue("create_by", data.getCreate_by());
        this.jdbcTemplate.update(sql, map, keyHolder);
        return (Integer) keyHolder.getKeys().get("kode");
    }
    public void updateProduct(Product data) {
        String sql = "update products set name = :name, create_by = :create_by  where id = :kode";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("kode", data.getId());
        map.addValue("name", data.getName());
        map.addValue("price", data.getPrice());
        map.addValue("category", data.getCategory());
        map.addValue("create_date", data.getCreate_date());
        map.addValue("create_by", data.getCreate_by());
        this.jdbcTemplate.update(sql, map);
    }
    public void delete(Integer id) {
        String sql = "delete from products where id = :kode";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("kode", id);
        this.jdbcTemplate.update(sql, map);
    }
    public class RowMapperInner implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product data = new Product();
            data.setId(rs.getInt("id"));
            data.setName(rs.getString("name"));
            data.setPrice(rs.getBigDecimal("price"));
            data.setCategory(rs.getString("category"));
            Date Create_date = rs.getDate("create_date");
            data.setCreate_date(Create_date.toLocalDate());
            data.setCreate_by(rs.getString("create_by"));
            return data;
        }
    }

}


