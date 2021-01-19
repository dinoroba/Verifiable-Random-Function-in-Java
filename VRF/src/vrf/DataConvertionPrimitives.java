package vrf;

import java.math.BigInteger;

/**
 * 
 * @author avelas
 *
 */
public class DataConvertionPrimitives {

	/**
	 * I2OSP converts a nonnegative integer to an octet string of a specified
	 * length.
	 * 
	 * I2OSP (x, xLen)
	 * 
	 * Input:
	 * 
	 * x nonnegative integer to be converted
	 * 
	 * xLen intended length of the resulting octet string
	 * 
	 * Output:
	 * 
	 * X corresponding octet string of length xLen
	 * 
	 * @param i    -
	 * @param size
	 * @return
	 */
	static byte[] i2osp(BigInteger x, int xLen) {

		byte[] i2ospString = x.toByteArray();
		int i2ospString_length = i2ospString.length;

		int sign = 0;
		if(i2ospString[0] == 0) sign = 1;
		
		i2ospString_length -= sign;

		/*
		 * 1. If x >= 256^xLen, output "integer too large" and stop.
		 */

		if (i2ospString_length > xLen) {
			throw new IllegalArgumentException("Integer should not be larger than the specified size");
		}

		if (xLen < 1) {
			throw new IllegalArgumentException("Size of the octet should be at least 1 but is " + xLen);
		}

		/*
		 * 3. Let the octet X_i have the integer value x_(xLen-i) for 1 <= i <= xLen.
		 * Output the octet string X = X_1 X_2 ... X_xLen.
		 */

		byte[] paddedI2ospString = new byte[xLen];
		System.arraycopy(i2ospString, sign, paddedI2ospString, xLen - i2ospString_length, i2ospString_length);

		return paddedI2ospString;

	}

	/**
	 * * OS2IP converts an octet string to a nonnegative integer.
	 * 
	 * OS2IP (X)
	 * 
	 * Input: X octet string to be converted
	 * 
	 * Output: x corresponding nonnegative integer
	 * 
	 * @param o2sipString - byte[]
	 * @return - BigInteger
	 */

	static BigInteger os2ip(byte[] o2sipString) {

		/*
		 * 1. Let X_1 X_2 ... X_xLen be the octets of X from first to last, and let
		 * x_(xLen-i) be the integer value of the octet X_i for 1 <= i <= xLen.
		 * 
		 * 2. Let x = x_(xLen-1) 256^(xLen-1) + x_(xLen-2) 256^(xLen-2) + ... + x_1 256
		 * + x_0.
		 * 
		 * 3. Output x.
		 */

		return new BigInteger(1, o2sipString);
	}

}
