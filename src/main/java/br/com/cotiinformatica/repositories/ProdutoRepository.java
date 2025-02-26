package br.com.cotiinformatica.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ProdutoRepository {
	
	private ConnectionFactory connectionFactory = new ConnectionFactory();
	
	public void create(Produto produto, UUID categoriaId) {
		
		try {
			//abrindo uma conexão com o banco de dados
			var connection = connectionFactory.getConnection();
			var statement =connection.prepareStatement("insert into produto(id,nome,preco,quantidade,categoria_id)values (?,?,?,?,?)");
			statement.setObject(1, produto.getId());
			statement.setString(2, produto.getNome());
			statement.setDouble(3, produto.getPreco());
			statement.setInt(4, produto.getQuantidade());
			statement.setObject(5, categoriaId);
			statement.execute();
		
			connection.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		public void update(Produto produto,UUID categoriaId) {
			
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
				// abrindo uma conexão com o banco de dados
				var connection = connectionFactory.getConnection();
				//ilike : ignorar maiúsculas e minúsculas
				var statement = connection.prepareStatement("select * from produto where nome ilike ? order by nome");
				statement.setString(1, "%" + nome + "%");
				//executando a consulta no banco de dados e obtendo os registros retornados
				var result = statement.executeQuery();
				
				List<Produto> lista = new ArrayList<Produto>();
				
				//
				while (result.next()) {
					Produto produto = new Produto();
					//recuperando os valores dos campos do registro e atribuindo ao objeto produto
					produto.setId(UUID.fromString(result.getString("id")));
					produto.setNome(result.getString("nome"));
					produto.setPreco(result.getDouble("preco"));
					produto.setQuantidade(result.getInt("quantidade"));

					lista.add(produto);
				}
				connection.close();
				return lista;
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}			
			
		}

}
