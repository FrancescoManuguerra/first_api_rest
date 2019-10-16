package com.example.first_project.services;

import com.example.first_project.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.List;

public interface ProductServices {

    public ResponseEntity<List<Product>> getAllProducts() ;
    public Product getProduct(String id )throws Exception ;
    public ResponseEntity createProducts(List<Product> products);
    public List<Product> updateProduct(List<Product> products) throws Exception;
    public ResponseEntity deleteProduct(String id);

}
