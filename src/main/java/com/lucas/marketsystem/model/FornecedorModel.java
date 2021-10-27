package com.lucas.marketsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorModel {
    private BigInteger id;
    private String nome;
    private LocalDate inicioContrato;
    private LocalDate finalContrato;
    private String descricao;
    private String logo;
}
