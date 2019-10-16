package com.example.first_project.repository;

import com.example.first_project.model.Product;
import org.springframework.http.ResponseEntity;

import javax.swing.text.EditorKit;
import java.util.List;

public interface ProductsRepository {

    public List<Product> getAllProducts() throws Exception;
    public Product getProduct(String id) throws Exception;
    public ResponseEntity createProducts(List<Product> products)throws Exception;
    public List<Product> updateProduct(List<Product> products) throws Exception;
    public ResponseEntity deleteProduct(String id) throws Exception;

}
