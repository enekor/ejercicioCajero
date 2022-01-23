package encriptation;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {

    private static RSA rsa = null;
    private RSA(){}

    public static RSA getInstance(){
        if(rsa == null){
            rsa = new RSA();
        }
        return rsa;
    }
    /**
     * Leemos la clave pública de un fichero
     *
     * @param fichero fichero de clave pública
     * @return clave pública
     * @throws Exception
     */
    public PublicKey cargarClavePublicaRSA(String fichero) throws Exception {
        FileInputStream fis = new FileInputStream(fichero);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;
    }

    /**
     * Carga la Clave Privada desde fichero
     *
     * @param fichero fichero de clave privada
     * @return clave privada
     * @throws Exception
     */
    public PrivateKey cargarClavePrivadaRSA(String fichero) throws Exception {
        FileInputStream fis = new FileInputStream(fichero);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

    /**
     * Salva el par de claves (privada/pública) en un fichero
     *
     * @param key     clave
     * @param fichero fichero de claves
     * @throws Exception
     */
    public void salvarClavesRSA(Key key, String fichero) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fichero);
        fos.write(publicKeyBytes);
        fos.close();
    }

    /**
     * Crea las claves de cifrado RSA y las almacena en un fichero
     *
     * @param fichero
     */
    public void crearClavesRSA(String fichero) {
        try {
            // Generar el par de claves
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Se salva y recupera de fichero la clave publica
            this.salvarClavesRSA(publicKey, fichero + "-rsa-public.dat");
            this.salvarClavesRSA(privateKey, fichero + "-rsa-private.dat");
        } catch (Exception ex) {
            System.err.println("Error al crear clave RSA: " + ex.getLocalizedMessage());
        }
    }

    /**
     * Cifra un texto mediante RSA
     *
     * @param mensaje       mensaje a cifrar
     * @param publicKeyFile Fichero de la clave pública
     * @return cadena cifrada
     */
    public String cifrarRSA(String mensaje, String publicKeyFile) {
        try {
            PublicKey publicKey = this.cargarClavePublicaRSA(publicKeyFile);
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encriptado = rsa.doFinal(mensaje.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encriptado);
        } catch (Exception ex) {
            System.err.println("Error al cifrar con RSA: " + ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Descifra una cadena mediante RSA
     *
     * @param mensaje        mensaje a cifrar
     * @param privateKeyFile fichero de clave provada
     * @return mensje cifrado
     */
    public String descifrarRSA(String mensaje, String privateKeyFile) {
        try {
            PrivateKey privateKey = this.cargarClavePrivadaRSA(privateKeyFile);
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encriptado = Base64.getDecoder().decode(mensaje);
            byte[] desencriptado = rsa.doFinal(encriptado);
            return new String(desencriptado);
        } catch (Exception ex) {
            System.err.println("Error al descifrar con RSA: " + ex.getLocalizedMessage());
        }
        return null;
    }
}
