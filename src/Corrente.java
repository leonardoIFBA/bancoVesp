public class Corrente extends Conta{

    @Override
    public double rendimento() {
        return getSaldo() * 0.0;
    }    
}
