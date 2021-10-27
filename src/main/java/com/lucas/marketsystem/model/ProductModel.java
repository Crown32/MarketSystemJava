package com.lucas.marketsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private String nome;
    private String observacoes;
    private BigInteger id;
    private Double preco_unidade;
    private BigInteger quantidade;
    private String categoria;
    private BigInteger cod_fornecedor;
}
