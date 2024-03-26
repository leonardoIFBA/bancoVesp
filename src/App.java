import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class App {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello, Banco IFBA!");

    Conta c1 = new Conta();
    Fisica p1 = new Fisica();
    p1.setNome("leonardo");
    p1.setCpf("12312312378");
    c1.setNumero("123-4");
    c1.setCliente(p1);
    c1.setSaldo(0);

    c1.depositar(1000);
    System.out.println("O saldo atual de c1: " + c1.getSaldo());

    Conta c2 = new Conta();
    Juridica p2 = new Juridica();
    p2.setNome("Americanas LTDA");
    p2.setCnpj("1111111111111111");
    c2.setNumero("123-5");
    c2.setCliente(p2);
    c2.setSaldo(0);

    if (c1.transferir(c2, 1010)) {
      System.out.println("Transferencia realizada com sucesso.");
      System.out.println("O saldo atual de c1: " + c1.getSaldo());
    } else {
      System.out.println("Transferencia cancelada. Verirfique o saldo");
    }

    System.out.println("O saldo atual de " + c1.getCliente().getNome() + " é: " + c1.getSaldo());
    System.out.println("O saldo atual de " + c2.getCliente().getNome() + " é: " + c2.getSaldo());

    Conta cp1 = new Poupanca();
    cp1.setNumero("2212-3");
    cp1.setCliente(p1);
    cp1.setSaldo(0);

    cp1.depositar(1000);

    System.out.println("O rendimento da poupança foi: " + cp1.rendimento());

    Conta cc1 = new Corrente();
    cc1.setNumero("3321-1");
    cc1.setCliente(p1);
    cp1.setSaldo(0);

    cc1.depositar(1000);

    System.out.println("O rendimento da conta corrente foi: " + cc1.rendimento());

    // trabalhando a persistencia dos dados

    // Criando uma conexão com o BD
    // getConexao();
    System.out.println(listaTodos());
    //testando o inserir
    Fisica cliente = new Fisica();
    cliente.setCpf("00000");
    cliente.setNome("leonardo");
    Conta conta = new Conta();
    conta.setCliente(cliente);
    conta.setNumero("555-6");
    conta.setSaldo(1000000);
    conta.setLimite(100);
    inserir(conta);
    System.out.println(listaTodos());
    // testando atualizar
    conta.setCliente(p2);
    conta.setNumero("xxxx-6");
    conta.setSaldo(1000);
    conta.setLimite(0);
    atualizar(conta, "555-6");
    System.out.println(listaTodos());
    // testando o delete
    deletar(conta);
    System.out.println(listaTodos());

  }

  // este método retorna uma conexão com o banco de dados
  public static Connection getConexao() {
    Connection conexao = null;

    try {
      // Carregando o driver JDBC padrão
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Configurando a nossa conexão com um banco de dados//
      String url = "jdbc:mysql://10.28.0.35:3306/bancoifba"; // "jdbc:mysql://200.179.0.35:3306/bancoifba"; // caminho e
                                                             // nome do BD
      String username = "remoto"; // nome de um usuário de seu BD
      String password = "remoto"; // sua senha de acesso

      // O método getConnection retorna a conexão com o banco de dados e carrega na
      // variável CONEXAO
      conexao = DriverManager.getConnection(url, username, password);
      System.out.println("Conectado ao BD");

      return conexao; // o método getConexao retorna a conexão com o BD

    } catch (ClassNotFoundException e) { // Tratamento das excessões (erros)
      System.out.println("O driver expecificado nao foi encontrado.");
      return null;
    } catch (SQLException e) {
      // Não conseguindo se conectar ao banco
      System.out.println("Nao foi possivel conectar ao Banco de Dados.");
      return null;
    }

  }

  // este método retorna uma lista de contas cadastrados no banco de dados
  public static List<Conta> listaTodos() throws SQLException {

    // estabelece uma conexão com o banco de dados
    Connection conn = getConexao();

    List<Conta> contas = new ArrayList<>(); // inicializa uma lista de objetos tipo Conta
    try {
      String sql = "SELECT * FROM conta"; // consulta SQL a ser executada no banco de dados

      Statement stmt = conn.createStatement(); // Utilizando a conexão estabelecida cria o objeto Statement que vai
                                               // receber a consulta SQL

      // guarda no objeto ResutSet o resultado da consulta
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) { // percorre o resultSet e adiciona as contas retornadas na lista de contas
        Conta c = new Conta();
        c.setNumero(rs.getString("numero"));
        // c.setCliente(rs.getString("cliente"));
        c.setSaldo(rs.getDouble("saldo"));

        contas.add(c); // adiciona o conta na lista
      }

    } catch (SQLException ex) {
      System.out.println("Não conseguiu listar as contas do BD.");
    } finally {
      conn.close();
    }
    return contas;
  }

  // este método insere uma conta no banco de dados
  public static void inserir(Conta conta) throws SQLException {
    // estabelece uma conexão com o banco de dados
    Connection conn = getConexao();
    try {
      String sql = "INSERT INTO conta (numero, cliente, saldo) VALUES (?,?,?)"; //consulta SQL a ser executada no banco de dado.

      PreparedStatement ps = conn.prepareStatement(sql); // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      ps.setString(1, conta.getNumero()); // carrega o primeiro parametro
      ps.setString(2, conta.getCliente().getNome());  // carrega o segundo parametro
      ps.setDouble(3, conta.getSaldo());  // carrega o terceiro parametro
      int res = ps.executeUpdate();  //  executa a consulta SQL e retorna um valor inteiro
      if (res == 1) {
        System.out.println("Conta inserida com sucesso.");
      }
    } catch (SQLException ex) {
      System.out.println("Não conseguiu adicionar uma conta no BD.");
    } finally {
      conn.close();
    }
  }

  // este método altera uma conta no banco de dados
  public static void atualizar(Conta conta, String numero) throws SQLException {
    // estabelece uma conexão com o banco de dados
    Connection conn = getConexao();
    try {
      //consulta SQL a ser executada no banco de dados
      String sql = "UPDATE conta SET numero = ?, cliente = ?, saldo = ? WHERE numero = ?";

      // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, conta.getNumero());  // carrega o primeiro parametro
      ps.setString(2, conta.getCliente().getNome());  // carrega o segundo parametro
      ps.setDouble(3, conta.getSaldo());  // carrega o terceiro parametro
      ps.setString(4, numero);  // carrega o quarto parametro
      int res = ps.executeUpdate();  //  executa a consulta SQL e retorna um valor inteiro

      if (res == 1) {
        System.out.println("Conta atualizada com sucesso.");
      }
      System.out.println("Atualizou o cliente da conta.");
    } catch (SQLException ex) {
      System.out.println("Não conseguiu atualizar uma conta no BD.");
    } finally {
      conn.close();
    }
  }

  // este método exclui uma conta no banco de dados
  public static void deletar(Conta conta) throws SQLException {
    // estabelece uma conexão com o banco de dados
    Connection conn = getConexao();
    try {
      String atualizar = "DELETE FROM conta WHERE numero = ?"; //consulta SQL a ser executada no banco de dados

      // Cria um objeto Statement parametrizado e passa a string sql a ser executada
      PreparedStatement ps = conn.prepareStatement(atualizar);
      ps.setString(1, conta.getNumero());  // carrega o primeiro parametro
      int res = ps.executeUpdate(); //  executa a consulta SQL e retorna um valor inteiro

      if (res == 1) {
        System.out.println("Conta excluída com sucesso.");
      }

    } catch (SQLException ex) {
      System.out.println("Não conseguiu excluir uma conta no BD.");
    } finally {
      conn.close();
    }
  }

}
