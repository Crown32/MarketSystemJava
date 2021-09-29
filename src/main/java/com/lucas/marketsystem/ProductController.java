package com.lucas.marketsystem;

import com.lucas.marketsystem.model.ProductCategory;
import com.lucas.marketsystem.model.ProductModel;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.lucas.marketsystem.repository.ProductRepository;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ProductController {

    //Home Nodes
    @FXML
    private Accordion prodAccordion;

    //Add page Nodes
    @FXML
    private TextField nomeProd;
    @FXML
    private TextField quantidadeProd;
    @FXML
    private TextField precoProd;
    @FXML
    private TextField fornecedorProd;
    @FXML
    private ChoiceBox productSelectBox;
    @FXML
    TextArea observacoesProd;
    @FXML
    Button btnSaveProd;



    //Method for rounding decimal numbers
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //Function for loading all the products in the home
    @FXML
    public void loadProducts() throws Exception{
        ArrayList<ProductModel> products = ProductRepository.getAllProducts();
        prodAccordion.setMaxWidth(352);
        prodAccordion.getPanes().clear();

        for (ProductModel product:
             products) {
            FlowPane paneContent = new FlowPane();
            paneContent.setOrientation(Orientation.VERTICAL);
            paneContent.setMaxHeight(190);
            paneContent.setMaxWidth(352);

            FlowPane paneBtn = new FlowPane();
            paneBtn.setMaxHeight(40);
            paneBtn.setMaxWidth(352);

            Button editBtn = new Button("Editar");
            editBtn.setId(product.getId().toString());
            editBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, editProduct);

            Button deleteBtn = new Button("Deletar");
            deleteBtn.setId(product.getId().toString());
            deleteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, deleteProduct);



            paneContent.getChildren().add(new Label("Nome: " + product.getNome()));
            paneContent.getChildren().add(new Label("Observações: " + (product.getObservacoes() == null || product.getObservacoes().isBlank()  ? "N/A" : product.getObservacoes())));
            paneContent.getChildren().add(new Label("Preço unitário: " + product.getPreco_unidade()));
            paneContent.getChildren().add(new Label("Preço total: " + round(product.getPreco_unidade() * product.getQuantidade().doubleValue(),2)));
            paneContent.getChildren().add(new Label("Quantidade: " + product.getQuantidade()));
            paneContent.getChildren().add(new Label("Categoria: " + product.getCategoria()));
            paneContent.getChildren().add(new Label("Fornecedor: " + product.getFornecedor()));
            paneContent.getChildren().add(new Label(""));
            paneBtn.getChildren().add(editBtn);
            paneBtn.getChildren().add(deleteBtn);
            paneContent.getChildren().add(paneBtn);

            TitledPane paneBox = new TitledPane(product.getNome(), paneContent);
            prodAccordion.getPanes().add(paneBox);
        }
    }


    @FXML
    public void loadAddProductPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Adicionar produto");
        stage.setScene(scene);
        ChoiceBox addProductSelectBox = (ChoiceBox) scene.lookup("#productSelectBox");
        addProductSelectBox.getItems().addAll(ProductCategory.CATEGORIAS);
        stage.show();
    }

    @FXML
    public void addProduct() throws Exception {
        ProductModel prod = new ProductModel();
        Alert alertNullText = new Alert(Alert.AlertType.ERROR, "Apenas o campo de observações pode estar em branco", ButtonType.CLOSE);
        Alert alertQuantidadeText = new Alert(Alert.AlertType.ERROR, "O campo quantidade só pode ser um número", ButtonType.CLOSE);
        Alert alertPrecoText = new Alert(Alert.AlertType.ERROR, "O campo Preço unitário só pode ser um número", ButtonType.CLOSE);
        Alert alertSuccessful = new Alert(Alert.AlertType.NONE, "Produto salvo com sucesso", ButtonType.FINISH);
        Alert alertUnsuccessful = new Alert(Alert.AlertType.NONE, "Erro ao salvar produto", ButtonType.FINISH);

        //TODO: Também criar uma regra para trocar a , do double para . se o usuário colocar no preço ao invés de jogar o erro de texto no campo
        if (this.nomeProd.getText().isBlank() || this.productSelectBox.equals(null) || this.fornecedorProd.getText().isBlank() || this.precoProd.getText().isBlank() || this.quantidadeProd.getText().isBlank()){
            alertNullText.showAndWait();
            throw new Exception("Text fields not filled");
        }else{
            prod.setNome(this.nomeProd.getText());
            prod.setCategoria(this.productSelectBox.getSelectionModel().getSelectedItem().toString());
            prod.setFornecedor(this.fornecedorProd.getText());
            prod.setObservacoes(this.observacoesProd.getText());
            try {
                prod.setPreco_unidade(Double.valueOf(this.precoProd.getText()));
            }catch (Exception e){
                alertPrecoText.showAndWait();
                throw new Exception("Text in Number Field");
            }
            try{
                prod.setQuantidade(new BigInteger(this.quantidadeProd.getText()));
            }catch (Exception e) {
                alertQuantidadeText.showAndWait();
                throw new Exception("Text in number field");
            }
        }
        if (ProductRepository.addNewProduct(prod)){
            alertSuccessful.showAndWait();
            Stage stage = (Stage) btnSaveProd.getScene().getWindow();
            stage.close();
        }else{
            alertUnsuccessful.showAndWait();
        }
    }

    EventHandler<Event> deleteProduct = new EventHandler<Event>(){
        Alert alertSuccessful = new Alert(Alert.AlertType.NONE, "Produto deletado com sucesso", ButtonType.FINISH);
        Alert alertUnsuccessful = new Alert(Alert.AlertType.NONE, "Erro ao deletar produto", ButtonType.FINISH);
        @Override
        public void handle(Event event) {
            try{
                Button btn = (Button) event.getSource();
                ProductRepository.deleteProduct(btn.getId());
                loadProducts();
            }catch (Exception exc){
                alertUnsuccessful.showAndWait();
                exc.printStackTrace();
            }
            alertSuccessful.showAndWait();
        }
    };

    EventHandler<Event> editProduct = new EventHandler<Event>(){
        @Override
        public void handle(Event e) {
            Button btn = (Button) e.getSource();
            String id = btn.getId();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-page.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Adicionar produto");
                stage.setScene(scene);

                ChoiceBox addProductSelectBox = (ChoiceBox) scene.lookup("#productSelectBox");
                TextField nomeProd = (TextField) scene.lookup("#nomeProd");
                TextField fornecedorProd = (TextField) scene.lookup("#fornecedorProd");
                TextArea observacoesProd = (TextArea) scene.lookup("#observacoesProd");
                TextField precoProd = (TextField) scene.lookup("#precoProd");
                TextField quantidadeProd = (TextField) scene.lookup("#quantidadeProd");

                ProductModel product = ProductRepository.getOne(id);

                addProductSelectBox.getItems().addAll(ProductCategory.CATEGORIAS);

                //TODO: Fazer aperecer as caracteristicas do produto na página de edição (mesma de adição) e fazer a inserção deste objeto no banco

                stage.show();

            }catch (Exception exc){
                exc.printStackTrace();
            }
        }
    };
}

