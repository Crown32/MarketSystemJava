package com.lucas.marketsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProductModel {
    private String nome;
    private String observacoes;
    private BigInteger id;
    private Double preco_unidade;
    private BigInteger quantidade;
    //When adding stuff use the ProductCategory final class
    private String categoria;
    private String fornecedor;
}
