package com.lucas.marketsystem.repository;

import com.lucas.marketsystem.model.ProductModel;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class ProductRepository {

    public static ArrayList<ProductModel> getAllProducts(){
        ResultSet resultSet = null;
        ArrayList<ProductModel> productList = new ArrayList<>();

        try{
            //Making the connection and query
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from table_estoque");

            //Placing inside the objects
            while (resultSet.next()){
                ProductModel product = new ProductModel(resultSet.getString("nome"),resultSet.getString("observacoes"), new BigInteger(resultSet.getString("id")),Double.valueOf(resultSet.getString("preco_unidade")),new BigInteger(resultSet.getString("quantidade")),resultSet.getString("categoria"),resultSet.getString("fornecedor"));
                productList.add(product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }

}
