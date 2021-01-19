package vrf;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 
 * @author avelas
 *
 */
public class Rsa_signature_primitives {

	/**
	 * RSASP1 (K, m)
	 * 
	 * Input:
	 * 
	 * K RSA private key, where K has one of the following forms: - a pair (n, d) -
	 * a quintuple (p, q, dP, dQ, qInv) and a (possibly empty) sequence of triplets
	 * (r_i, d_i, t_i), i = 3, ..., u m message representative, an integer between 0
	 * and n - 1
	 * 
	 * 
	 * Output:
	 * 
	 * s signature representative, an integer between 0 and n - 1
	 * 
	 * @param publickey - RSAPrivateKey
	 * @param signature - BigInteger
	 * @return BigInteger
	 */

	static BigInteger rSASP1(RSAPrivateKey K, BigInteger m) {

		/*
		 * 1. If the message representative m is not between 0 and n - 1, output
		 * "message representative out of range" and stop.
		 */

		if (m.compareTo(BigInteger.ZERO) == -1 || m.compareTo(K.getModulus().subtract(BigInteger.ONE)) == 1) {

			throw new IllegalArgumentException("Message representative out of range. Should be between 0 and "
					+ K.getModulus().subtract(BigInteger.ONE).toString() + "but is " + m.toString());

		}

		/*
		 * 2. The signature representative s is computed as follows.
		 * 
		 * a. If the first form (n, d) of K is used, let s = m^d mod n.
		 * 
		 * 3. Output s.
		 */
		return (BigInteger) m.modPow(K.getPrivateExponent(), K.getModulus());

	}

	/**
	 * RSAVP1 ((n, e), s)
	 * 
	 * Input:
	 * 
	 * (n, e) RSA public key
	 * 
	 * s signature representative, an integer between 0 and n - 1
	 * 
	 * Output:
	 * 
	 * m message representative, an integer between 0 and n - 1
	 * 
	 * @param K - RSAPublicKey
	 * @param s - BigInteger
	 * @return BigInteger
	 */
	static BigInteger rSAVP1(RSAPublicKey K, BigInteger s) {

		/*
		 * 1. If the signature representative s is not between 0 and n - 1, output
		 * "signature representative out of range" and stop.
		 */

		if (s.compareTo(BigInteger.ZERO) == -1 || s.compareTo(K.getModulus().subtract(BigInteger.ONE)) == 1) {
			throw new IllegalArgumentException("Message representative out of range. Should be between 0 and "
					+ K.getModulus().subtract(BigInteger.ONE).toString() + "but is " + s.toString());

		}

		/*
		 * 2. Let m = s^e mod n.
		 * 
		 * 3. Output s
		 */

		return (BigInteger) s.modPow(K.getPublicExponent(), K.getModulus());

	}

}
