import java.math.BigInteger;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        RSA rsa = new RSA(12);
        System.out.println("Chave publica ["+ rsa.getK_pub()[0] +","+ rsa.getK_pub()[1]+"] e privada gerada [" + rsa.getK_priv()[0]+","+rsa.getK_priv()[1]+"]");

        System.out.println("\n----------------------------------------------------");
        System.out.println("Teste: ");
        String criptograma = rsa.encriptar("ola, como voces vao?");
        System.out.println("Mensagem encriptada: " + criptograma);
        String decriptado = rsa.decriptar(criptograma);
        System.out.println("Valor obtido da decriptacao: " + decriptado);



        System.out.println("\n----------------------------------------------------");
        System.out.println("Utilizando o hacking para descobrir a chave privada");
        racking(rsa.getK_pub()[0], rsa.getK_pub()[1]);
    }


    public static void racking(BigInteger k_pub, BigInteger n){
        System.out.println("Chaves recebidas: ["+k_pub+","+n+"]");
        ArrayList<BigInteger> multiplos = new ArrayList<>();
        for(BigInteger i = n.subtract(BigInteger.ONE); i.compareTo(BigInteger.ONE) != 0; i=i.subtract(BigInteger.ONE) ){
            if(n.mod(i) == BigInteger.ZERO){
                if (!multiplos.contains(i) ){
                    multiplos.add(i);
                }
            }
        }

        System.out.println("Valores de P e Q encontrados:");
        multiplos.stream().forEach(System.out::println);

        BigInteger phi = multiplos.get(0).subtract(BigInteger.ONE).multiply(multiplos.get(1).subtract(BigInteger.ONE));
        System.out.println("Pegando o valor de PHI: " + phi);

        BigInteger inversoMultiplicativo = getInversoMultiplicativo(k_pub, phi);
        System.out.printf("Descobrindo a chave privada: " + inversoMultiplicativo);

    }

    public static BigInteger getInversoMultiplicativo(BigInteger e, BigInteger phi_n ){
        e = e.mod(phi_n);
        BigInteger x = BigInteger.ONE;
        while(e.multiply(x).mod(phi_n).compareTo(BigInteger.ONE) != 0){
            x = x.add(BigInteger.ONE);
        }
        return x;
    }
}
