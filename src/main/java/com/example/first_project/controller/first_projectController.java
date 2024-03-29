package com.example.first_project.controller;


import com.example.first_project.model.Product;
import com.example.first_project.services.ProductServices;
import com.example.first_project.services.ProductServicesImpl;
import com.sun.scenario.effect.impl.prism.PrDrawable;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class first_projectController {

    @Autowired
    private ProductServices productServices;


    /*############################################# CRUD ###########################################################

      GET       --> 200 ok     il body contiene il/i products    500 Internal server error
      /products         --> riceve tutti i products inseriti nel database
      /products/{id}    --> riceve il prodotto con id={id}

      POST      --> 200 ok                                       500 Internal server error
      /products +body    --> scrive un product

      PUT       --> 200 ok
      /products +body    --> modifica n products

      DELETE    --> 200 ok                                       500 Internal server error
      /products/{id}    --> elimina il product con id={id}
    ##############################################################################################################*/

    //GET
    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getNota(@RequestHeader HttpHeaders headers){

        return productServices.getAllProducts();
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id ){

        try {
            return new ResponseEntity<Product>(productServices.getProduct(id),HttpStatus.OK);
        }catch(BadHttpRequest badHttpRequest){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //POST
    @PostMapping(value ="/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProduct(@RequestBody List<Product> products ){
        return productServices.createProducts(products);
    }

    //PUT
    @PutMapping(value="/products/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> updateProduct( @RequestBody List<Product> products){

        try {
            return new ResponseEntity<List<Product>>(productServices.updateProduct(products),HttpStatus.OK);

        }catch(BadHttpRequest badHttpRequest){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE
    @DeleteMapping(value="/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){

        return productServices.deleteProduct(id);
    }


}
