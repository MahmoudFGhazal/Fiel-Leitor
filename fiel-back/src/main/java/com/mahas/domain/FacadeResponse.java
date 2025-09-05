package com.mahas.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacadeResponse {
    private List<DomainEntity> entities;
    private DomainEntity entity;

    private Integer errorType;
    private TypeResponse typeResponse = TypeResponse.OK;
    private String message;

     /* private Map<String, String> errorDetails; // Detalhes de validação/erros específicos
    private LocalDateTime timestamp;          // Quando a resposta foi gerada
    private String requestId;                 // ID para rastreamento da requisição

    // Para paginação (se aplicável) */
}