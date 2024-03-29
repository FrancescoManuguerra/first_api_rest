package com.example.first_project.services;

import com.example.first_project.model.Product;
import com.example.first_project.repository.ProductsRepository;
import com.example.first_project.repository.ProductsRepositoryImpl;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ProductServices")
public class ProductServicesImpl implements ProductServices {

    @Autowired
    private ProductsRepository productsRepository;

    //GET
    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return new ResponseEntity<List<Product>>(productsRepository.getAllProducts(),HttpStatus.OK);
        } catch (Exception e) {
            if(BadHttpRequest.class==e.getClass())return new ResponseEntity(HttpStatus.BAD_REQUEST);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product getProduct(String id) throws Exception {
        try{
            return productsRepository.getProduct(id);
        }catch(BadHttpRequest badHttpRequest){
         throw new BadHttpRequest();
        }catch (Exception e ){
            throw  new Exception();
        }
    }

    //POST
    @Override
    public ResponseEntity createProducts(List<Product> products) {

        System.out.println("service - create product ");
        try {
            return (productsRepository.createProducts(products));
        } catch (Exception e) {
            if(BadHttpRequest.class==e.getClass())return new ResponseEntity(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //PUT
    @Override
    public List<Product> updateProduct(List<Product> products)throws Exception {

        System.out.println("service - update product ");
        try {
            return productsRepository.updateProduct(products);
        } catch (Exception e) {
            if(BadHttpRequest.class==e.getClass())throw new BadHttpRequest();

            throw new Exception();
        }

    }

    //DELETE
    @Override
    public ResponseEntity deleteProduct(String id)  {
        System.out.println("service - delete product ");
        try {
            return productsRepository.deleteProduct(id);
        } catch (Exception e) {
            if(BadHttpRequest.class==e.getClass())return new ResponseEntity(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
