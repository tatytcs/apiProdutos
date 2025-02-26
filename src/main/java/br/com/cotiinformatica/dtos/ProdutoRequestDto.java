package br.com.cotiinformatica.dtos;

import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequestDto {
	
	//Nome não pode ser nulo e nem vazio
	@NotBlank(message = "Por favor, informe o nome do produto.")	
	@Size(min =8 , max = 100, message = "Por favor, informe um nome com no mínimo 8 e no máximo 100 caracteres.")
	private String nome;
	
	@NotNull(message = "Por favor, informe o preço do produto.")
	@DecimalMin(value = "0.01", message = "Por favor, informe um preço com valor mínimo de '0.01'.")
	@Digits(integer = 10, fraction = 2, message = "Por favor, informe um preço com no máximo 5 dígitos e 2 decimais.")
	private Double preco;
	
	@Min(value = 1, message = "Por favor, informe a quantidade do produto com valor mínimo de '1'.")
	@NotNull(message = "Por favor, informe a quantidade do produto.")
	private Integer quantidade;
	
	@NotNull(message = "Por favor, informe a categoria do produto.")
	private UUID categoriaId;

}
