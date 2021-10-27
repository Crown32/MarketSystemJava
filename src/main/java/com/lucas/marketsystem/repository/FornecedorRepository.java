package com.lucas.marketsystem.repository;

import com.lucas.marketsystem.model.FornecedorModel;
import com.lucas.marketsystem.model.ProductModel;

import java.math.BigInteger;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FornecedorRepository {

    public static List<FornecedorModel> getAll() {
        ResultSet resultSet = null;
        String query = "SELECT * FROM t_fornecedor";
        List<FornecedorModel> fornecedorList = new ArrayList<>();
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                FornecedorModel fornecedor = new FornecedorModel(new BigInteger(resultSet.getString("id")), resultSet.getString("nome"), LocalDate.parse(LocalDate.parse(resultSet.getString("inicio_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")) ,LocalDate.parse(LocalDate.parse(resultSet.getString("final_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")), resultSet.getString("descricao"), resultSet.getString("logo"));
                fornecedorList.add(fornecedor);
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return fornecedorList;
    }

    public static List<String> getAllNames() {
        ResultSet resultSet = null;
        String query = "SELECT nome FROM t_fornecedor";
        List<String> fornecedorNamesList = new ArrayList<>();
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String fornecedorName = resultSet.getString("nome");
                fornecedorNamesList.add(fornecedorName);
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return fornecedorNamesList;
    }


    public static FornecedorModel getOne(String id) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM t_fornecedor WHERE id="+id;
        FornecedorModel fornecedor = null;
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                fornecedor = new FornecedorModel(new BigInteger(resultSet.getString("id")), resultSet.getString("nome"), LocalDate.parse(LocalDate.parse(resultSet.getString("inicio_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")) ,LocalDate.parse(LocalDate.parse(resultSet.getString("final_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")), resultSet.getString("descricao"), resultSet.getString("logo"));
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return fornecedor;
    }

    public static FornecedorModel getOneByName(String name) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM t_fornecedor WHERE nome="+"'"+name+"'";
        FornecedorModel fornecedor = null;
        //Making the connection and query
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root", "mysql");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                fornecedor = new FornecedorModel(new BigInteger(resultSet.getString("id")), resultSet.getString("nome"), LocalDate.parse(LocalDate.parse(resultSet.getString("inicio_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")) ,LocalDate.parse(LocalDate.parse(resultSet.getString("final_contrato")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),DateTimeFormatter.ofPattern("dd-MM-yyyy")), resultSet.getString("descricao"), resultSet.getString("logo"));
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return fornecedor;
    }

    public static boolean deleteFornecedor(String id){
        String delete = "DELETE FROM t_fornecedor WHERE id="+id;
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

    public static boolean addNewFornecedor(FornecedorModel fornecedor) throws ParseException {
        String insert = String.format("INSERT INTO t_fornecedor (nome,inicio_contrato,final_contrato,descricao,logo) VALUES ('%s', '%s', '%s', '%s', '%s')",fornecedor.getNome(),fornecedor.getInicioContrato() ,fornecedor.getFinalContrato(),fornecedor.getDescricao(),fornecedor.getLogo());
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

    public static boolean editFornecedor(FornecedorModel fornecedor) throws ParseException {
        String insert = String.format("UPDATE t_fornecedor SET nome = '%s',inicio_contrato = '%s',final_contrato = '%s',descricao = '%s',logo = '%s' WHERE id = %s",fornecedor.getNome(),fornecedor.getInicioContrato() ,fornecedor.getFinalContrato(),fornecedor.getDescricao(),fornecedor.getLogo(),fornecedor.getId());
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

}
