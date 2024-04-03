import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaService {
    
  // este método insere um objeto conta no banco de dados
  public static void inserir(Conta conta) {
    // consulta SQL a ser executada no banco de dado.
    String sql = "INSERT INTO conta (numero, cliente, saldo) VALUES (?,?,?)";

    // estabelece uma conexão com o banco de dados
    try (Connection conexao = App.getConexao()) {
      PreparedStatement ps = conexao.prepareStatement(sql); // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      ps.setString(1, conta.getNumero()); // carrega o primeiro parametro
      ps.setString(2, conta.getCliente().getNome()); // carrega o segundo parametro
      ps.setDouble(3, conta.getSaldo()); // carrega o terceiro parametro
      int res = ps.executeUpdate(); // executa a consulta SQL e retorna um valor inteiro
      if (res > 0) {
        System.out.println("Conta inserida com sucesso.");
      }
    } catch (SQLException ex) {
      System.out.println("Não conseguiu adicionar uma conta no BD.");
      ex.printStackTrace(); // Mostra a linha exata onde houve o erro
    }
  }

  // este método Atualiza um objeto conta no banco de dados
  public static void atualizar(Conta conta, String numero) {
    // consulta SQL a ser executada no banco de dados
    String sql = "UPDATE conta SET numero = ?, cliente = ?, saldo = ? WHERE numero = ?";
    
    // estabelece uma conexão com o banco de dados
    try (Connection conexao = App.getConexao()) {
      PreparedStatement ps = conexao.prepareStatement(sql); // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      ps.setString(1, conta.getNumero()); // carrega o primeiro parametro
      ps.setString(2, conta.getCliente().getNome()); // carrega o segundo parametro
      ps.setDouble(3, conta.getSaldo()); // carrega o terceiro parametro
      ps.setString(4, numero); // carrega o quarto parametro
      int res = ps.executeUpdate(); // executa a consulta SQL e retorna um valor inteiro
      if (res > 0) {
        System.out.println("Conta atualizada com sucesso.");
      }
    } catch (SQLException ex) {
      System.out.println("Não conseguiu atualizar uma conta no BD.");
      ex.printStackTrace(); // Mostra a linha exata onde houve o erro
    }
  }

  // este método deleta um objeto conta no banco de dados
  public static void deletar(Conta conta) {
    // consulta SQL a ser executada no banco de dados
    String sql = "DELETE FROM conta WHERE numero = ?"; 
    // estabelece uma conexão com o banco de dados
    try (Connection conexao = App.getConexao()) {
      // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      PreparedStatement ps = conexao.prepareStatement(sql);
      ps.setString(1, conta.getNumero()); // carrega o primeiro parametro
      int res = ps.executeUpdate(); // executa a consulta SQL e retorna um valor inteiro
      if (res > 0) {
        System.out.println("Conta excluída com sucesso.");
      }
    } catch (SQLException ex) {
      System.out.println("Não conseguiu excluir uma conta no BD.");
      ex.printStackTrace(); // Mostra a linha exata onde houve o erro
    }
  }

  // este método lista as contas cadastradas no banco de dados
  public static List<Conta> listaTodos() {
        String sql = "SELECT * FROM conta";
        List<Conta> contas = new ArrayList<>(); //lista de contas vazia para guardar o resultado da consulta no BD

        try (Connection conexao = App.getConexao()) {
            Statement stmt = conexao.createStatement(); //Cria o objeto Statement pra receber a consulta SQL 
            // guarda no objeto o resultado da consulta SQL
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Conta c = new Conta();
                c.setNumero(rs.getString("numero"));
                //c.setCliente(FisicaService.BuscarPorNome(rs.getString("cliente")));
                c.setSaldo(rs.getDouble("saldo"));

                contas.add(c); //Adiciona o objeto conta na lista de contas
            }

        } catch (SQLException e) {
            System.out.println("Não conseguiu listar as contas do BD.");
            e.printStackTrace(); //mostra a linha exata onde houve o erro
        }
        return contas;
    }
}
