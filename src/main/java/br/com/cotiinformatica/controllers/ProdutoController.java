package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ProdutoRequestDto;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	@Operation(summary = "Serviço para cadastrar produto.")
	@PostMapping("cadastrar")
	// @RequestBody: utilizado para receber os dados do corpo da requisição
	// @Valid: utilizado para validar os dados do corpo da requisição
	public String cadastrar(@RequestBody @Valid ProdutoRequestDto request) {
		
		var produto = new Produto();
		
		// Capturando os dados da requisição e atribuindo ao objeto
		produto.setId(UUID.randomUUID());
		produto.setNome(request.getNome());
		produto.setPreco(request.getPreco());
		produto.setQuantidade(request.getQuantidade());
		
		var produtoRepository = new ProdutoRepository();
		produtoRepository.create(produto, request.getCategoriaId());
		
		// Retornando uma mensagem de sucesso
		return "Produto cadastrado com sucesso.";
		
	}
	
	@Operation(summary = "Serviço para atualizar produto.")
	@PutMapping("atualizar/{id}")
	public String atualizar(@PathVariable UUID id,@RequestBody @Valid ProdutoRequestDto request) {
        
		var produto = new Produto();
		// Capturando os dados da requisição e atribuindo ao objeto
		produto.setId(id);
		produto.setNome(request.getNome());
		produto.setPreco(request.getPreco());
		produto.setQuantidade(request.getQuantidade());
		
		var produtoRepository = new ProdutoRepository();
		produtoRepository.update(produto, request.getCategoriaId());
		
		return "Produto atualizado com sucesso.";		
		
	}
	
	@Operation(summary = "Serviço para excluir produto.")
	@DeleteMapping("excluir/{id}")
	public String excluir(@PathVariable UUID id) {

		var produtoRepository = new ProdutoRepository();
		produtoRepository.delete(id);
		
		return "Produto excluído com sucesso.";
	}
		

	@Operation(summary = "Serviço para consultar produtos.")
	@GetMapping("consultar/{nome}")
	public List<Produto> consultar(@PathVariable String nome) {
		
		var produtoRepository = new ProdutoRepository();
		return produtoRepository.findAll(nome);
    }
	}


