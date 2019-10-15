package com.example.first_project.repository;

import com.example.first_project.model.Product;
import javassist.tools.web.BadHttpRequest;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("productsRepository")
public class ProductsRepositoryImpl implements ProductsRepository {

    //get all product
    public List<Product> getAllProducts() throws Exception{
        List<Product> products= new ArrayList<Product>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from products ");

            while(rs.next()){
                 products.add(new Product(rs.getInt(1),rs.getInt(2), rs.getFloat(3),rs.getString(4),rs.getInt(5)));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println("Sono nella repository");
            System.out.println(e);
            throw new Exception();
        }
        return products;
    }

    //GET
    public Product getProduct(String id) throws Exception{
        Product product= new Product();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");
            PreparedStatement stmt=con.prepareStatement("select * from products where id=? ");
            stmt.setString(1,id);
            ResultSet rs=stmt.executeQuery();

            while(rs.next()){
                product= new Product(rs.getInt(1),rs.getInt(2), rs.getFloat(3),rs.getString(4),rs.getInt(5));
            }

            if(!rs.first())throw new BadHttpRequest();

        }catch(BadHttpRequest badHttpRequest){
            throw  new BadHttpRequest();
        }catch(Exception e){
            System.out.println("Sono nella repository");
            System.out.println(e);
            throw new Exception();
        }
        return product;
    }

    //POST
    public ResponseEntity createProducts(List<Product> products) throws Exception{
        try{
            //connesione al db
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");

            //controlliamo che non ci siano dati sbagliati nel body
            for(Product product: products){
                PreparedStatement preparedStatement= con.prepareStatement("SELECT id from products where id=?");
                preparedStatement.setString(1,Integer.toString(product.getId()));
                ResultSet rs= preparedStatement.executeQuery();
                if (rs.first())throw new BadHttpRequest();
            }

            //inserimento products
            for (Product product: products  ) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO products (quantity, unitPrice,name, type) VALUES (?,?,?,? )");
                stmt.setInt(1, product.getQuantity());
                stmt.setFloat(2, product.getUnitPrice());
                stmt.setString(3, product.getName());
                stmt.setInt(4, product.getType());
                stmt.executeUpdate();

            }

            System.out.println("repository - insert product -  ok ");
            con.close();
            return new ResponseEntity(HttpStatus.OK);

        }catch(BadHttpRequest badHttpRequest){
            throw new BadHttpRequest();
        }catch(Exception e){
            System.out.println("Sono nella repository");
            System.out.println(" repository - insert product - Internal server error");
            System.out.println(e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //PUT
    public ResponseEntity<List<Product>> updateProduct(List<Product> products) throws Exception{

        try{
            //connessione database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");

            //se qualche product non è presente nel db lancia una BadHttpRequest
            for(Product product: products){

                PreparedStatement stmt = con.prepareStatement("select id from products where id=? ");
                stmt.setInt(1,product.getId());
                ResultSet rs= stmt.executeQuery();
                if(!rs.first())throw new BadHttpRequest();
            }

            //aggiorna i products
            for (Product product : products) {

                PreparedStatement stmt = con.prepareStatement("UPDATE products SET quantity=? , unitPrice= ?, name=? , type=?  WHERE id=?  ");

                stmt.setInt(1, product.getQuantity());
                stmt.setFloat(2, product.getUnitPrice());
                stmt.setString(3, product.getName());
                stmt.setInt(4, product.getType());
                stmt.setInt(5, product.getId());
                stmt.executeUpdate();

                System.out.println("repository - put - before second statement -");
                //get updated product
                Statement stmt2 = con.createStatement();
                String query = "select * from products where id= " + product.getId();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    product.setId(rs.getInt(1));
                    product.setQuantity(rs.getInt(2));
                    product.setUnitPrice(rs.getFloat(3));
                    product.setName(rs.getString(4));
                    product.setType(rs.getInt(5));
                }
            }

            con.close();
            return new ResponseEntity<List<Product>>(products,HttpStatus.OK);

        }catch(BadHttpRequest badHttpRequest){
            throw new BadHttpRequest();

        }catch(Exception e){
            System.out.println(" repository - update product - Internal server error");
            System.out.println(e);
            throw new Exception();
        }
    }

    //DELETE
    public ResponseEntity deleteProduct(String id) throws Exception{

        try{
            //connessione al db
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");

            //controllo se il product esiste
            PreparedStatement preparedStatement=con.prepareStatement("select * from products where id=?");
            preparedStatement.setString(1,id);
            ResultSet rs= preparedStatement.executeQuery();
            if(!rs.first())throw new BadHttpRequest();

            PreparedStatement stmt=con.prepareStatement("DELETE FROM products WHERE id = ?");
            stmt.setString(1,id);
            stmt.executeUpdate();

                System.out.println("repository - delete product -  ok ");
                con.close();
                return new ResponseEntity(HttpStatus.OK);


            }catch(BadHttpRequest badHttpRequest){
                throw new BadHttpRequest();
            }catch(Exception e){
            System.out.println("Sono nella repository");
            System.out.println(" repository - delete product -  Internal server error");
            System.out.println(e);
            throw new Exception();
        }
        }


}





