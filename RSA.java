import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi_n;
    private BigInteger k_pub[];
    private BigInteger k_priv[];


    public RSA(int qtdLetras){

        int maxByts = 15;

        p = BigInteger.valueOf(1283);
        q = BigInteger.valueOf(1289);

        if(p.toByteArray().length > maxByts || q.toByteArray().length > maxByts){
            throw new RuntimeException("Numero de bytes maior do que deveria ser");
        }

        n = p.multiply(q);
        phi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        k_pub = new BigInteger[]{BigInteger.valueOf(1291), n};
        k_priv = new BigInteger[]{getInversoMultiplicativo(k_pub[0],phi_n), n};
    }

    private BigInteger  getInversoMultiplicativo(BigInteger e, BigInteger phi_n ){
        e = e.mod(phi_n);
        BigInteger x = BigInteger.ONE;
        while(e.multiply(x).mod(phi_n).compareTo(BigInteger.ONE) != 0){
            x = x.add(BigInteger.ONE);
        }
        return x;
    }

    public String encriptar(String msg){
        String encripted = "";
        for(int i = 0; i < msg.length(); i++){
           Integer value = Integer.valueOf(msg.charAt(i));
           encripted +=  BigInteger.valueOf(value).pow(k_pub[0].intValue()).mod(n) + " ";
        }
        return encripted;
    }

    public String decriptar(String criptograma){
        String letras[] = criptograma.split(" ");
        String decript = "";
        for(String letraCriptografada : letras){
            Integer numb = Integer.valueOf(letraCriptografada);
            BigInteger result = BigInteger.valueOf(numb).pow(k_priv[0].intValue()).mod(n);
           decript += ((char) result.intValue());
        }

        return decript;
    }


    public BigInteger[] getK_pub() {
        return k_pub;
    }

    public BigInteger[] getK_priv() {
        return k_priv;
    }
}
