package com.generation.farmacia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	 /**
     * Aplica um desconto percentual em todos os produtos de uma categoria.
     * 
     * @param categoriaId ID da categoria
     * @param desconto Percentual de desconto (0 a 100)
     */
    public void aplicarDesconto(Long categoriaId, double desconto) {
        // Buscar todos os produtos da categoria
        List<Produto> produtos = produtoRepository.findAllByCategoriaId(categoriaId);

        // Aplicar o desconto a cada produto
        produtos.forEach(produto -> {
            BigDecimal percentualDesconto = BigDecimal.valueOf(desconto).divide(BigDecimal.valueOf(100));
            BigDecimal precoAtual = produto.getPreco();
            BigDecimal precoComDesconto = precoAtual.subtract(precoAtual.multiply(percentualDesconto));

            // Arredondar para 2 casas decimais
            precoComDesconto = precoComDesconto.setScale(2, RoundingMode.HALF_UP);

            produto.setPreco(precoComDesconto);
        });

        // Salvar os produtos atualizados
        produtoRepository.saveAll(produtos);
    }
}
