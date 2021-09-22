package com.lucas.marketsystem;

import com.lucas.marketsystem.model.ProductModel;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import com.lucas.marketsystem.repository.ProductRepository;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductController {

    @FXML
    private Accordion prodAccordion;
    @FXML
    private ScrollPane prodScroll;
    private Pane paneContent = new Pane(new Label("teste"));

    //Function for loading all the products in the home
    //TODO: Precisa atribuir os atributos dos produtos a cada painel do acordeão e adicionar métodos de editar, salvar e excluir
    @FXML
    public void loadProducts(){
        ArrayList<ProductModel> products = ProductRepository.getAllProducts();
        prodAccordion.setMinWidth(210);

        for (ProductModel product:
             products) {
            TitledPane paneBox = new TitledPane(product.getNome(), paneContent);
            prodAccordion.getPanes().add(paneBox);
        }
    }

    //Method called in the view start
    public void initialize(){
        this.loadProducts();
    }

}