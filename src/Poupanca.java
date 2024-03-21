public class Poupanca extends Conta {

    @Override
    public double rendimento() {
        
        return getSaldo() * 0.05;
    }    
}
