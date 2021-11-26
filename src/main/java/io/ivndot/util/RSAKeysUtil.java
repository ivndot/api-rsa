package io.ivndot.util;

import java.security.*;

public class RSAKeysUtil {

	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;

	public RSAKeysUtil() {
		//generate pair keys
		generateKeys();
	}

	/**
	 * Generate the pair of RSA keys
	 * 
	 * @return Pair of keys
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

	/**
	 * Get the private key
	 * 
	 * @return PrivateKey
	 */
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	/**
	 * Get the public key
	 * 
	 * @return PublicKey
	 */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

}
