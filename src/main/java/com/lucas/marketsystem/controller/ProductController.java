package com.lucas.marketsystem.controller;

import com.lucas.marketsystem.Application;
import com.lucas.marketsystem.model.FornecedorModel;
import com.lucas.marketsystem.model.ProductCategory;
import com.lucas.marketsystem.model.ProductModel;
import com.lucas.marketsystem.repository.FornecedorRepository;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProductController {

    //Home Nodes
    @FXML
    private Accordion prodAccordion;
    @FXML
    private Accordion prodAccordionFornecedor;

    //Add fornecedor Nodes
    @FXML
    private TextField nomeFornecedor;
    @FXML
    private TextField logoFornecedor;
    @FXML
    private DatePicker inicioContratoFornecedor;
    @FXML
    private DatePicker finalContratoFornecedor;
    @FXML
    private TextArea descricaoFornecedor;
    @FXML
    Button btnSaveFornecedor;
    @FXML
    TextField txtIdFornecedor;
    @FXML
    TextField txtIsEditFornecedor;

    //Add product Nodes
    @FXML
    private TextField nomeProd;
    @FXML
    private TextField quantidadeProd;
    @FXML
    private TextField precoProd;
    @FXML
    private ChoiceBox fornecedorProd;
    @FXML
    private ChoiceBox productSelectBox;
    @FXML
    TextArea observacoesProd;
    @FXML
    Button btnSaveProd;
    @FXML
    TextField txtIsEditProd;
    @FXML
    TextField txtIdProd;

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
        if (products.isEmpty()){
            Alert alertNoProduct = new Alert(Alert.AlertType.INFORMATION, "Nenhum produto cadastrado", ButtonType.CLOSE);
            alertNoProduct.showAndWait();
        }
        prodAccordion.setMaxWidth(352);
        prodAccordion.getPanes().clear();

        for (ProductModel product:
             products) {
            VBox paneContent = new VBox();
            paneContent.setMaxHeight(190);
            paneContent.setMaxWidth(352);

            FlowPane paneBtn = new FlowPane();
            paneBtn.setMaxHeight(40);
            paneBtn.setMaxWidth(352);

            Button editBtn = new Button("Editar");
            editBtn.setId(product.getId().toString());
            editBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, loadEditProduct);

            Button deleteBtn = new Button("Deletar");
            deleteBtn.setId(product.getId().toString());
            deleteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, deleteProduct);



            paneContent.getChildren().add(new Label("Nome: " + product.getNome()));
            paneContent.getChildren().add(new Label("Observações: " + (product.getObservacoes() == null || product.getObservacoes().isBlank()  ? "N/A" : product.getObservacoes())));
            paneContent.getChildren().add(new Label("Preço unitário: " + product.getPreco_unidade()));
            paneContent.getChildren().add(new Label("Preço total: " + round(product.getPreco_unidade() * product.getQuantidade().doubleValue(),2)));
            paneContent.getChildren().add(new Label("Quantidade: " + product.getQuantidade()));
            paneContent.getChildren().add(new Label("Categoria: " + product.getCategoria()));
            paneContent.getChildren().add(new Label("Fornecedor: " + FornecedorRepository.getOne(product.getCod_fornecedor().toString()).getNome()));
            paneContent.getChildren().add(new Label(""));
            paneBtn.getChildren().add(editBtn);
            paneBtn.getChildren().add(deleteBtn);
            paneContent.getChildren().add(paneBtn);

            TitledPane paneBox = new TitledPane(product.getNome(), paneContent);
            prodAccordion.getPanes().add(paneBox);
        }
    }

    //Function for loading all the suppliers in the home
    @FXML
    public void loadFornecedores() throws Exception{
        List<FornecedorModel> fornecedores = FornecedorRepository.getAll();
        if (fornecedores.isEmpty()){
            Alert alertNoFornecedor = new Alert(Alert.AlertType.INFORMATION, "Nenhum fornecedor cadastrado", ButtonType.CLOSE);
            alertNoFornecedor.showAndWait();
        }
        prodAccordionFornecedor.setMaxWidth(352);
        prodAccordionFornecedor.getPanes().clear();

        for (FornecedorModel fornecedor:
                fornecedores) {
            VBox paneContent = new VBox();
            paneContent.setMaxHeight(190);
            paneContent.setMaxWidth(352);

            FlowPane paneBtn = new FlowPane();
            paneBtn.setMaxHeight(40);
            paneBtn.setMaxWidth(352);

            ImageView imageView = new ImageView();
            imageView.setImage(new Image((fornecedor.getLogo().equals("null")|| fornecedor.getLogo()==null || fornecedor.getLogo().isBlank() ? "https://images.assetsdelivery.com/compings_v2/yehorlisnyi/yehorlisnyi2104/yehorlisnyi210400016.jpg":fornecedor.getLogo())));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            Button editBtn = new Button("Editar");
            editBtn.setId(fornecedor.getId().toString());
            editBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, loadEditFornecedor);

            Button deleteBtn = new Button("Deletar");
            deleteBtn.setId(fornecedor.getId().toString());
            deleteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, deleteFornecedor);


            paneContent.getChildren().add(imageView);
            paneContent.getChildren().add(new Label(""));
            paneContent.getChildren().add(new Label("Nome: " + fornecedor.getNome()));
            paneContent.getChildren().add(new Label("Início contrato: " + fornecedor.getInicioContrato().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            paneContent.getChildren().add(new Label("Final contrato: " + fornecedor.getFinalContrato().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            paneContent.getChildren().add(new Label("Descrição: " + (fornecedor.getDescricao() == null || fornecedor.getDescricao().isBlank()  ? "N/A" : fornecedor.getDescricao())));
            paneContent.getChildren().add(new Label(""));
            paneBtn.getChildren().add(editBtn);
            paneBtn.getChildren().add(deleteBtn);
            paneContent.getChildren().add(paneBtn);

            TitledPane paneBox = new TitledPane(fornecedor.getNome(), paneContent);
            prodAccordionFornecedor.getPanes().add(paneBox);
        }
    }

    //Method for loading page for adding suppliers
    @FXML
    public void loadAddFornecedorPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-page-fornecedor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Adicionar Fornecedor");
        stage.setScene(scene);
        stage.show();
    }


    //Method for loading page for adding products
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
        ChoiceBox fornecedorProd = (ChoiceBox) scene.lookup("#fornecedorProd");
        fornecedorProd.getItems().addAll(FornecedorRepository.getAllNames());

        stage.show();
    }

    //method to add products
    @FXML
    public void addProduct() throws Exception {
        ProductModel prod = new ProductModel();
        Alert alertNullText = new Alert(Alert.AlertType.ERROR, "Apenas o campo de observações pode estar em branco", ButtonType.CLOSE);
        Alert alertQuantidadeText = new Alert(Alert.AlertType.ERROR, "O campo quantidade só pode ser um número", ButtonType.CLOSE);
        Alert alertPrecoText = new Alert(Alert.AlertType.ERROR, "O campo Preço unitário só pode ser um número", ButtonType.CLOSE);
        Alert alertSuccessful = new Alert(Alert.AlertType.NONE, "Produto salvo com sucesso", ButtonType.FINISH);
        Alert alertUnsuccessful = new Alert(Alert.AlertType.NONE, "Erro ao salvar produto", ButtonType.FINISH);

        if (this.nomeProd.getText().isBlank() || this.productSelectBox.equals(null) || this.fornecedorProd.equals(null) || this.precoProd.getText().isBlank() || this.quantidadeProd.getText().isBlank()){
            alertNullText.showAndWait();
            throw new Exception("Text fields not filled");
        }else{
            prod.setNome(this.nomeProd.getText());
            prod.setCategoria(this.productSelectBox.getSelectionModel().getSelectedItem().toString());
            prod.setCod_fornecedor(FornecedorRepository.getOneByName(this.fornecedorProd.getSelectionModel().getSelectedItem().toString()).getId());
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
        if (this.txtIsEditProd.getText().equals("EDIT")){
            prod.setId(new BigInteger(this.txtIdProd.getText()));
            if (ProductRepository.editProduct(prod)){
                alertSuccessful.showAndWait();
                Stage stage = (Stage) btnSaveProd.getScene().getWindow();
                stage.close();
            }else{
                alertUnsuccessful.showAndWait();
            }
        }else{
            if (ProductRepository.addNewProduct(prod)){
                alertSuccessful.showAndWait();
                Stage stage = (Stage) btnSaveProd.getScene().getWindow();
                stage.close();
            }else{
                alertUnsuccessful.showAndWait();
            }
        }
    }

    @FXML
    public void addFornecedor() throws Exception {
        FornecedorModel fornecedor = new FornecedorModel();
        Alert alertNullText = new Alert(Alert.AlertType.ERROR, "Apenas o campo de descrição e logo pode estar em branco", ButtonType.CLOSE);
        Alert alertSuccessful = new Alert(Alert.AlertType.NONE, "Fornecedor salvo com sucesso", ButtonType.FINISH);
        Alert alertUnsuccessful = new Alert(Alert.AlertType.NONE, "Erro ao salvar Fornecedor", ButtonType.FINISH);

        if (this.nomeFornecedor.getText().isBlank() || this.inicioContratoFornecedor.equals(null) || this.finalContratoFornecedor.equals(null)){
            alertNullText.showAndWait();
            throw new Exception("Text fields not filled");
        }else{
            fornecedor.setNome(this.nomeFornecedor.getText());
            fornecedor.setDescricao(this.descricaoFornecedor.getText());
            fornecedor.setLogo(this.logoFornecedor.getText());
            fornecedor.setInicioContrato(this.inicioContratoFornecedor.getValue());
            fornecedor.setFinalContrato(this.finalContratoFornecedor.getValue());

        }
        if (this.txtIsEditFornecedor.getText().equals("EDIT")){
            fornecedor.setId(new BigInteger(this.txtIdFornecedor.getText()));
            if (FornecedorRepository.editFornecedor(fornecedor)){
                alertSuccessful.showAndWait();
                Stage stage = (Stage) btnSaveFornecedor.getScene().getWindow();
                stage.close();
            }else{
                alertUnsuccessful.showAndWait();
            }
        }else{
            if (FornecedorRepository.addNewFornecedor(fornecedor)){
                alertSuccessful.showAndWait();
                Stage stage = (Stage) btnSaveFornecedor.getScene().getWindow();
                stage.close();
            }else{
                alertUnsuccessful.showAndWait();
            }
        }
    }

    //method to delete products
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

    //method to delete suppliers
    EventHandler<Event> deleteFornecedor = new EventHandler<Event>(){
        Alert alertSuccessful = new Alert(Alert.AlertType.NONE, "Fornecedor deletado com sucesso", ButtonType.FINISH);
        Alert alertUnsuccessful = new Alert(Alert.AlertType.ERROR, "Erro ao deletar fornecedor", ButtonType.FINISH);
        Alert alertDeleteAllProducts = new Alert(Alert.AlertType.CONFIRMATION, "Para deletar este fornecedor precisa deletar seus produtos, deseja deletar os produtos desse fornecedor?");
        Alert alertExistentProduct = new Alert(Alert.AlertType.ERROR, "Para deletar este fornecedor precisa deletar seus produtos", ButtonType.FINISH);
        @Override
        public void handle(Event event) {
            try{
                Button btn = (Button) event.getSource();
                List<ProductModel> producList = ProductRepository.getAllProductsByCodFornecedor(btn.getId());
                if (!producList.isEmpty()){
                    if (alertDeleteAllProducts.showAndWait().get() == ButtonType.OK) {
                        for (ProductModel product :
                                producList) {
                            ProductRepository.deleteProduct(product.getId().toString());
                        }
                        FornecedorRepository.deleteFornecedor(btn.getId());
                        alertSuccessful.showAndWait();
                    }else{
                        alertExistentProduct.showAndWait();
                    }
                }else{
                    FornecedorRepository.deleteFornecedor(btn.getId());
                    alertSuccessful.showAndWait();
                }
                loadFornecedores();
                loadProducts();
            }catch (Exception exc){
                alertUnsuccessful.showAndWait();
                exc.printStackTrace();
            }
        }
    };


    //method to edit products
    EventHandler<Event> loadEditProduct = new EventHandler<Event>(){
        @Override
        public void handle(Event e) {
            Button btn = (Button) e.getSource();
            String id = btn.getId();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-page.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Editar produto");
                stage.setScene(scene);

                ChoiceBox addProductSelectBox = (ChoiceBox) scene.lookup("#productSelectBox");
                TextField nomeProd = (TextField) scene.lookup("#nomeProd");
                ChoiceBox fornecedorProd = (ChoiceBox) scene.lookup("#fornecedorProd");
                TextArea observacoesProd = (TextArea) scene.lookup("#observacoesProd");
                TextField precoProd = (TextField) scene.lookup("#precoProd");
                TextField quantidadeProd = (TextField) scene.lookup("#quantidadeProd");
                TextField isEdit = (TextField) scene.lookup("#txtIsEditProd");
                TextField txtIdProd = (TextField) scene.lookup("#txtIdProd");

                ProductModel product = ProductRepository.getOne(id);

                addProductSelectBox.getItems().addAll(ProductCategory.CATEGORIAS);
                ChoiceBox fornecedorProdSelectBox = (ChoiceBox) scene.lookup("#fornecedorProd");
                fornecedorProdSelectBox.getItems().addAll(FornecedorRepository.getAllNames());

                nomeProd.setText(product.getNome());
                observacoesProd.setText(product.getObservacoes());
                precoProd.setText(product.getPreco_unidade().toString());
                quantidadeProd.setText(product.getQuantidade().toString());
                isEdit.setText("EDIT");
                txtIdProd.setText(id);

                for (Object categoria:addProductSelectBox.getItems()  ) {
                    if (categoria.equals(product.getCategoria())){
                        addProductSelectBox.getSelectionModel().select(categoria);
                        break;
                    }
                }

                for (Object fornecedor:fornecedorProd.getItems()  ) {
                    if (fornecedor.equals(FornecedorRepository.getOne(product.getCod_fornecedor().toString()).getNome())){
                        fornecedorProd.getSelectionModel().select(fornecedor);
                        break;
                    }
                }

                stage.show();

            }catch (Exception exc){
                exc.printStackTrace();
            }
        }
    };

    //method to edit products
    EventHandler<Event> loadEditFornecedor = new EventHandler<Event>(){
        @Override
        public void handle(Event e) {
            Button btn = (Button) e.getSource();
            String id = btn.getId();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-page-fornecedor.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Editar fornecedor");
                stage.setScene(scene);

                TextField nomeFornecedor = (TextField) scene.lookup("#nomeFornecedor");
                TextArea descricaoFornecedor = (TextArea) scene.lookup("#descricaoFornecedor");
                TextField logoFornecedor = (TextField) scene.lookup("#logoFornecedor");
                DatePicker finalContratoFornecedor = (DatePicker) scene.lookup("#finalContratoFornecedor");
                DatePicker inicioContratoFornecedor = (DatePicker) scene.lookup("#inicioContratoFornecedor");
                TextField txtIsEditFornecedor = (TextField) scene.lookup("#txtIsEditFornecedor");
                TextField txtIdFornecedor = (TextField) scene.lookup("#txtIdFornecedor");

                FornecedorModel fornecedor = FornecedorRepository.getOne(id);

                nomeFornecedor.setText(fornecedor.getNome());
                descricaoFornecedor.setText(fornecedor.getDescricao());
                logoFornecedor.setText(fornecedor.getLogo());
                finalContratoFornecedor.setValue(fornecedor.getFinalContrato());
                inicioContratoFornecedor.setValue(fornecedor.getInicioContrato());

                txtIsEditFornecedor.setText("EDIT");
                txtIdFornecedor.setText(id);

                stage.show();

            }catch (Exception exc){
                exc.printStackTrace();
            }
        }
    };

    @FXML
    public void loadFornecedorProductsData() throws Exception {
        this.loadFornecedores();
        this.loadProducts();
    }

}

