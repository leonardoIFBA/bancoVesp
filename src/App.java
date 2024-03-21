import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
    getConexao();
      

  }

  public static Connection getConexao() {
    Connection conexao = null;

    try {
      // Carregando o JDBC Driver padrão
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Configurando a nossa conexão com um banco de dados//
      String url = "jdbc:mysql://200.128.9.179:3306/bancoifba"; //"jdbc:mysql://10.28.0.35:3306/bancoifba"; // caminho e nome do BD
      String username = "remoto"; // nome de um usuário de seu BD
      String password = "remoto"; // sua senha de acesso

      conexao = DriverManager.getConnection(url, username, password);
      System.out.println("Conectado ao BD");

      return conexao;

    } catch (ClassNotFoundException e) { // Driver não encontrado
      System.out.println("O driver expecificado nao foi encontrado.");
      return null;
    } catch (SQLException e) {
      // Não conseguindo se conectar ao banco
      System.out.println("Nao foi possivel conectar ao Banco de Dados.");
      return null;
    }

  }


}
