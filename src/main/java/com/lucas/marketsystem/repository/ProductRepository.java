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
        String query = "select * from t_produtos";
        try{
            //Making the connection and query
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            //Placing inside the objects
            while (resultSet.next()){
                ProductModel product = new ProductModel(resultSet.getString("nome"),resultSet.getString("observacoes"), new BigInteger(resultSet.getString("id")),Double.valueOf(resultSet.getString("preco_unidade")),new BigInteger(resultSet.getString("quantidade")),resultSet.getString("categoria"),new BigInteger(resultSet.getString("cod_fornecedor")));
                productList.add(product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    public static ArrayList<ProductModel> getAllProductsByCodFornecedor(String codFornecedor){
        ResultSet resultSet = null;
        ArrayList<ProductModel> productList = new ArrayList<>();
        String query = "select * from t_produtos WHERE cod_fornecedor = "+codFornecedor;
        try{
            //Making the connection and query
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            //Placing inside the objects
            while (resultSet.next()){
                ProductModel product = new ProductModel(resultSet.getString("nome"),resultSet.getString("observacoes"), new BigInteger(resultSet.getString("id")),Double.valueOf(resultSet.getString("preco_unidade")),new BigInteger(resultSet.getString("quantidade")),resultSet.getString("categoria"),new BigInteger(resultSet.getString("cod_fornecedor")));
                productList.add(product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    public static boolean addNewProduct(ProductModel prod){
        String insert = String.format("INSERT INTO t_produtos (nome,quantidade,preco_unidade,categoria,observacoes,cod_fornecedor) VALUES (\"%s\", %s, %s, \"%s\", \"%s\", \"%s\")",prod.getNome(),prod.getQuantidade(),prod.getPreco_unidade(),prod.getCategoria(),prod.getObservacoes(),prod.getCod_fornecedor());
        try{
            //Making the connection and saving object
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            statement.executeUpdate(insert);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean editProduct(ProductModel prod){
        String insert = String.format("UPDATE t_produtos SET nome = \"%s\",quantidade = %s,preco_unidade = %s,categoria = \"%s\",observacoes = \"%s\",cod_fornecedor = \"%s\" WHERE id = %s",prod.getNome(),prod.getQuantidade(),prod.getPreco_unidade(),prod.getCategoria(),prod.getObservacoes(),prod.getCod_fornecedor(),prod.getId());
        try{
            //Making the connection and saving object
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            statement.executeUpdate(insert);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public static boolean deleteProduct(String id){
        String delete = "DELETE FROM t_produtos WHERE id="+id;
        try{
            //Making the connection and saving object
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ProductModel getOne(String id) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM t_produtos WHERE id="+id;
        ProductModel product = null;
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                product = new ProductModel(resultSet.getString("nome"),resultSet.getString("observacoes"), new BigInteger(resultSet.getString("id")),Double.valueOf(resultSet.getString("preco_unidade")),new BigInteger(resultSet.getString("quantidade")),resultSet.getString("categoria"),new BigInteger(resultSet.getString("cod_fornecedor")));
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return product;
    }

    public static ProductModel getOneByName(String name) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM t_produtos WHERE nome="+"'"+name+"'";
        ProductModel product = null;
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                product = new ProductModel(resultSet.getString("nome"),resultSet.getString("observacoes"), new BigInteger(resultSet.getString("id")),Double.valueOf(resultSet.getString("preco_unidade")),new BigInteger(resultSet.getString("quantidade")),resultSet.getString("categoria"),new BigInteger(resultSet.getString("cod_fornecedor")));
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return product;
    }

}
