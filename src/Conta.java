public class Conta {
    private String numero;
    private Cliente cliente;
    private double saldo;
    private double limite;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    boolean sacar(double valor){
        if (this.saldo >= valor){
            this.saldo -= valor;
            return true;
        }else{
            return false;
        }
    }

    void depositar(double valor){
        this.saldo += valor;
    }

    boolean transferir(Conta destino, double valor){
        if (sacar(valor)){
            destino.depositar(valor);
            return true;
        }else{
            return false;
        }
    }

    public double rendimento(){
        return 0;
    }

    @Override
    public String toString() {
        return "Conta [numero=" + numero + ", cliente=" + cliente + ", saldo=" + saldo + "]";
    }


}
