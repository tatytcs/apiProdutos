package br.com.cotiinformatica.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ProdutoRepository {

	private ConnectionFactory connectionFactory = new ConnectionFactory();

	public void create(Produto produto, UUID categoriaId) {

		try {
			// abrindo uma conexão com o banco de dados
			var connection = connectionFactory.getConnection();
			var statement = connection
					.prepareStatement("insert into produto(id,nome,preco,quantidade,categoria_id)values (?,?,?,?,?)");
			statement.setObject(1, produto.getId());
			statement.setString(2, produto.getNome());
			statement.setDouble(3, produto.getPreco());
			statement.setInt(4, produto.getQuantidade());
			statement.setObject(5, categoriaId);
			statement.execute();

			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Produto produto, UUID categoriaId) {

		try {
			// abrindo uma conexão com o banco de dados
			var connection = connectionFactory.getConnection();
			var statement = connection.prepareStatement(
					"update produto set nome = ?, preco = ?, quantidade = ?, categoria_id = ? where id = ?");
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getPreco());
			statement.setInt(3, produto.getQuantidade());
			statement.setObject(4, categoriaId);
			statement.setObject(5, produto.getId());
			statement.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(UUID id) {

		try {
			// abrindo uma conexão com o banco de dados
			var connection = connectionFactory.getConnection();
			var statement = connection.prepareStatement("delete from produto where id = ?");
			statement.setObject(1, id);
			statement.execute();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public List<Produto> findAll(String nome) {
		try {
			var connection = connectionFactory.getConnection();

			var query = """
					SELECT
					p.id as idproduto, p.nome as nomeproduto,
					p.preco, p.quantidade,
					c.id as idcategoria, c.nome as nomecategoria
					FROM produto p
					INNER JOIN categoria c
					ON c.id = p.categoria_id
					WHERE p.nome ILIKE ?
					ORDER BY p.nome;
					""";
			var statement = connection.prepareStatement(query);
			statement.setString(1, "%" + nome + "%");
			var result = statement.executeQuery();
			var lista = new ArrayList<Produto>();
			while (result.next()) {
				var produto = new Produto(); // instanciando produto
				produto.setCategoria(new Categoria());
				// instanciando categoria
				produto.setId(UUID.fromString(result.getString("idproduto")));
				produto.setNome(result.getString("nomeproduto"));
				produto.setPreco(result.getDouble("preco"));
				produto.setQuantidade(result.getInt("quantidade"));
				produto.getCategoria().setId(UUID.fromString(result.getString("idcategoria")));
				produto.getCategoria().setNome(result.getString("nomecategoria"));
				lista.add(produto);
			}
			connection.close();
			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// Método para consultar 1 produto através do id
	public Produto findById(UUID id) {
		try {
			var connection = connectionFactory.getConnection();
			var query = """
					SELECT
					p.id as idproduto, p.nome as nomeproduto,
					p.preco, p.quantidade,
					c.id as idcategoria, c.nome as nomecategoria
					FROM produto p
					INNER JOIN categoria c
					ON c.id = p.categoria_id
					WHERE p.id = ?
					""";

			var statement = connection.prepareStatement(query);
			statement.setObject(1, id);
			var result = statement.executeQuery();
			Produto produto = null;
			if (result.next()) {
				produto = new Produto();
				// instanciando produto
				produto.setCategoria(new Categoria());
				// instanciando categoria
				produto.setId(UUID.fromString(result.getString("idproduto")));
				produto.setNome(result.getString("nomeproduto"));
				produto.setPreco(result.getDouble("preco"));
				produto.setQuantidade(result.getInt("quantidade"));
				produto.getCategoria().setId(UUID.fromString(result.getString("idcategoria")));
				produto.getCategoria().setNome(result.getString("nomecategoria"));

			}
			connection.close();
			return produto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
