package com.controleestoque.api_estoque.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.dto.VendaRequest;
import com.controleestoque.api_estoque.dto.ItemVendaRequest;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Venda registrarVenda(VendaRequest req) {
        
        Cliente cliente = clienteRepository.findById(req.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Cliente não encontrado"));

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDate.now().toString());
        venda.setValorTotal(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemVendaRequest itemReq : req.getItensVenda()) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                            "Produto não encontrado: " + itemReq.getProdutoId()));

            Estoque estoque = estoqueRepository.findById(produto.getId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                            "Estoque não encontrado para " + produto.getId()));

            if (itemReq.getQuantidade() == null || itemReq.getQuantidade() <= 0) {
                throw new ResponseStatusException(BAD_REQUEST, "Quantidade inválida para " + produto.getId());
            }

            if (estoque.getQuantidade() < itemReq.getQuantidade()) {

                throw new ResponseStatusException(BAD_REQUEST,
                        "Estoque insuficiente para " + produto.getNome() + " (id=" + produto.getId() + ")");
                
            }

            estoque.setQuantidade(estoque.getQuantidade() - itemReq.getQuantidade());
            estoqueRepository.save(estoque);

            BigDecimal precoUnit = produto.getPreco();
            BigDecimal itemTotal = precoUnit.multiply(BigDecimal.valueOf(itemReq.getQuantidade()));
            total = total.add(itemTotal);

            ItensVenda item = new ItensVenda();
            item.setProduto(produto);
            item.setQuantidade(itemReq.getQuantidade());
            item.setPrecoUnitario(precoUnit);
            item.setVenda(venda);

            venda.getItensVenda().add(item);
        }

        venda.setValorTotal(total);

        Venda saved = vendaRepository.save(venda);

        return saved;
    }
}