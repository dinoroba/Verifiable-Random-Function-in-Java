
package vrf;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
/**
 * 
 * @author avelas
 *
 */
public class Rsa_vrf {

	private MessageDigest digest;

	public Rsa_vrf(MessageDigest digest) {
		this.digest = digest;
	}

	/**
	 * RSAFDHVRF_prove(K, alpha_string)
	 * 
	 * Input:
	 * 
	 * K - RSA private key
	 * 
	 * alpha_string - VRF hash input, an octet string
	 * 
	 * Output:
	 * 
	 * pi_string - proof, an octet string of length k
	 * 
	 * @param privateKey  - RSAPrivateKey
	 * @param inputString - String
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] prove(RSAPrivateKey privateKey, byte[] alpha_string) throws NoSuchAlgorithmException {

		/*
		 * 1. one_string = 0x01 = I2OSP(1, 1), a single octet with value 1
		 */

		byte[] one_string = DataConvertionPrimitives.i2osp(BigInteger.ONE, 1);

		/*
		 * 2. EM = MGF1(one_string || I2OSP(k, 4) || I2OSP(n, k) || alpha_string, k - 1)
		 */

		MGF1 mgf1 = new MGF1(digest);

		int k = (privateKey.getModulus().bitLength() + 7) / 8;
		BigInteger n = privateKey.getModulus();

		byte[] em = mgf1
				.generateMask(Operations.concat(one_string, DataConvertionPrimitives.i2osp(BigInteger.valueOf(k), 4),
						DataConvertionPrimitives.i2osp(n, k), alpha_string), k - 1);

		/*
		 * 3. m = OS2IP(EM)
		 */

		BigInteger m = DataConvertionPrimitives.os2ip(em);

		/*
		 * 4. s = RSASP1(K, m)
		 */

		BigInteger s = Rsa_signature_primitives.rSASP1(privateKey, m);

		/*
		 * 5. pi_string = I2OSP(s, k)
		 */

		byte[] pi_string = DataConvertionPrimitives.i2osp(s, k);

		/*
		 * 6. Output pi_string
		 */

		return pi_string;

	}

	/**
	 * RSAFDHVRF_proof_to_hash(pi_string)
	 * 
	 * Input:
	 * 
	 * pi_string - proof, an octet string of length k
	 * 
	 * Output:
	 * 
	 * beta_string - VRF hash output, an octet string of length hLen
	 * 
	 * @param pi_string - byte[]
	 */
	public byte[] proof_to_hash(byte[] pi_string) {

		/*
		 * 1. two_string = 0x02 = I2OSP(2, 1), a single octet with value 2
		 */
		byte[] two_string = DataConvertionPrimitives.i2osp(BigInteger.valueOf(2), 1);

		/*
		 * 2. beta_string = Hash(two_string || pi_string)
		 */

		byte[] beta_string = digest.digest(Operations.concat(two_string, pi_string));

		/*
		 * 3. Output beta_string
		 */

		return beta_string;

	}

	public boolean verifying(RSAPublicKey publicKey, byte[] alpha_string, byte[] pi_string)
			throws NoSuchAlgorithmException {

		int k = (publicKey.getModulus().bitLength() + 7) / 8;
		BigInteger n = publicKey.getModulus();

		/*
		 * 1. s = OS2IP(pi_string)
		 */

		BigInteger s = DataConvertionPrimitives.os2ip(pi_string);

		/*
		 * 2. m = RSAVP1((n, e), s)
		 */

		BigInteger m = Rsa_signature_primitives.rSAVP1(publicKey, s);

		/*
		 * 3. EM = I2OSP(m, k - 1)
		 */

		byte[] em = DataConvertionPrimitives.i2osp(m, k - 1);

		/*
		 * one_string = 0x01 = I2OSP(1, 1), a single octet with value 1
		 */

		byte[] one_string = DataConvertionPrimitives.i2osp(BigInteger.ONE, 1);

		/*
		 * EM' = MGF1(one_string || I2OSP(k, 4) || I2OSP(n, k) || alpha_string, k - 1)
		 */

		MGF1 mgf1 = new MGF1(digest);

		byte[] em2 = mgf1
				.generateMask(Operations.concat(one_string, DataConvertionPrimitives.i2osp(BigInteger.valueOf(k), 4),
						DataConvertionPrimitives.i2osp(n, k), alpha_string), k - 1);

		return Arrays.equals(em, em2);
	}

}
