package io.ivndot.util;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {

	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;

	public RSAUtil() {
		// generate pair keys
		generateKeys();
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Generate the pair of RSA keys
	 * 
	 */
	private void generateKeys() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			// lenght of key
			generator.initialize(4096);

			KeyPair pair = generator.generateKeyPair();

			this.privateKey = pair.getPrivate();
			this.publicKey = pair.getPublic();

		} catch (Exception error) {
			System.out.println(error);
		}
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Get the private key
	 * 
	 * @return PrivateKey
	 */
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Get the public key
	 * 
	 * @return PublicKey
	 */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to regenerate the public key encoded in base64 saved in a file,
	 * returns the public key
	 * 
	 * @param base64PublicKey Public key string encoded in base64
	 * @return PublicKey
	 */
	public static PublicKey regeneratePublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to regenerate the private key encoded in base64 saved in a file,
	 * returns the private key
	 * 
	 * @param base64PrivateKey Private key string encoded in base64
	 * @return PrivateKey
	 */
	public static PrivateKey regeneratePrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(base64PrivateKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to encrypt text, returns the encrypted content encoded in base64
	 * 
	 * @param content   Text to be encrypted
	 * @param publicKey Base64 encoded public key
	 * @return String
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String content, String publicKey) throws BadPaddingException,
			IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance("RSA");
		// initialize the encryption
		cipher.init(Cipher.ENCRYPT_MODE, regeneratePublicKey(publicKey));
		// get the encrypted message
		byte[] encryptedContent = cipher.doFinal(content.getBytes());

		// encode the message to base64
		String encodedContent = Base64.getEncoder().encodeToString(encryptedContent);

		return encodedContent;
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to decrypt text, returns the original message
	 * 
	 * @param content    Text to be decrypted encoded in base64
	 * @param privateKey Base64 encoded private key
	 * @return String
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static String decrypt(String content, String privateKey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

		Cipher cipher = Cipher.getInstance("RSA");
		// initialize the decryption
		cipher.init(Cipher.DECRYPT_MODE, regeneratePrivateKey(privateKey));
		// decode text from base64
		byte[] decodedText = Base64.getDecoder().decode(content.getBytes());
		
		// get the original message
		String messg = new String(cipher.doFinal(decodedText));
		System.out.println("======ORIGINAL TEXT=======");
		System.out.println(messg);
		System.out.println("======ORIGINAL TEXT=======");
		return messg;
	}

}
