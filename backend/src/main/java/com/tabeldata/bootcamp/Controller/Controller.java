package com.tabeldata.bootcamp.Controller;


import com.tabeldata.bootcamp.dao.Productdao;
import com.tabeldata.bootcamp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequestAttributeListener;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private Productdao productdao;
    private Controller Pdao;

    @GetMapping
    public List<Product> list() {
      return productdao.list();
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try {
            Product data = this.productdao.findById(id);
            return ResponseEntity.ok(data);
        }catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping(value = "/show")
    public Product showData(
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "cat", required = true) String category,
            @RequestParam(name = "create", required = true) String Create_by,
            Integer id,
            BigDecimal price)
    {
        Product data = new Product();
        data.setId(id);
        data.setName(name);
        data.setCategory(category);
        data.setCreate_by(Create_by);
        data.setPrice(price);
        return data;
    }
    @PostMapping(value = "/input")
    public ResponseEntity<?> insertData(@Valid @RequestBody  Product data, BindingResult result) {
                Map<String, Object> hasil = new HashMap<>();

                if(result.hasErrors()){
                    hasil.put("status", result.getFieldErrors()  );
                    return ResponseEntity.badRequest().body(hasil);
                }else {
                    hasil.put("id", productdao.insertData(data));
                    hasil.put("status", "Simpan berhasil");
                    return ResponseEntity.ok(hasil);
                }
    }
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>>
    updateProduct(@RequestBody Product data) {
        Map<String, Object> hasil = new HashMap<>();
        productdao.updateProduct(data);
        hasil.put("id", 0);
        hasil.put("status", "Update Berhasil");
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        this.Pdao.delete(id);
        return ResponseEntity.ok().build();
    }


}

