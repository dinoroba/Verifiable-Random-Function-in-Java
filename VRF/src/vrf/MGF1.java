package vrf;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author avelas
 *
 */
public class MGF1 {

	private final MessageDigest digest;

	public MGF1(MessageDigest digest) {
		this.digest = digest;
	}

	/**
	 * MGF1 is a Mask Generation Function based on a hash function.
	 *
	 * MGF1 (mgfSeed, maskLen)
	 * 
	 * Options: Hash hash function (hLen denotes the length in octets of the hash
	 * function output)
	 *
	 * Input: mgfSeed seed from which mask is generated, an octet string maskLen
	 * intended length in octets of the mask, at most 2^32 hLen
	 *
	 * Output: mask mask, an octet string of length maskLen
	 * 
	 * @param mgfSeed - byte[]
	 * @param maskLen - int
	 * @return - byte[]
	 * @throws NoSuchAlgorithmException
	 */

	public byte[] generateMask(byte[] mgfSeed, int maskLen) throws NoSuchAlgorithmException {

		/*
		 * 1. If maskLen > 2^32 hLen, output "mask too long" and stop.
		 */

		/*
		 * 2. Let T be the empty octet string.
		 */

		byte[] mask = new byte[0];

		/*
		 * 3. For counter from 0 to \ceil (maskLen / hLen) - 1, do the following:
		 * 
		 * A. Convert counter to an octet string C of length 4 octets (see Section 4.1):
		 * 
		 * C = I2OSP (counter, 4) .
		 * 
		 * B. Concatenate the hash of the seed mgfSeed and C to the octet string T:
		 * 
		 * T = T || Hash(mgfSeed || C) .
		 * 
		 */

		int counter = (maskLen + this.digest.getDigestLength() - 1) / this.digest.getDigestLength();

		for (int i = 0; i < counter; i++) {

			byte[] C = DataConvertionPrimitives.i2osp(BigInteger.valueOf(counter), 4);

			mask = Operations.concat(mask, digest.digest(Operations.concat(mgfSeed, C)));
		}

		/*
		 * 4. Output the leading maskLen octets of T as the octet string mask.
		 */

		byte[] output = new byte[maskLen];
		System.arraycopy(mask, 0, output, 0, output.length);
		return output;

	}

}
