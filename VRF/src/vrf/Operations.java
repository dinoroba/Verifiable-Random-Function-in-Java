package vrf;

/**
 * 
 * @author avelas
 *
 */
public class Operations {

	/**
	 * Concatenate 4 byte arrays
	 * 
	 * @param a - byte[]
	 * @param b - byte[]
	 * @param c - byte[]
	 * @param d - byte[]
	 * @return byte[]
	 */
	static byte[] concat(byte[] a, byte[] b, byte[] c, byte[] d) {

		return concat(a, concat(b, concat(c, d)));

	}

	/**
	 * Concatenate 2 byte arrays
	 * 
	 * @param a - byte[]
	 * @param b - byte[]
	 * @return byte[]
	 */
	static byte[] concat(byte[] a, byte[] b) {
		byte[] output = new byte[a.length + b.length];

		System.arraycopy(a, 0, output, 0, a.length);
		System.arraycopy(b, 0, output, a.length, b.length);
		return output;
	}
	
	static boolean equals(byte[] a, byte[] b) {
		if(a == null || b == null) return false;
		
		if(a.equals(b)) return true;
		
		if(a.length != b.length) return false;
		
		for (int i = 0; i < a.length; i++) {
			if(a[i] != b[i]) return false;
		}
		return true;
	}

}
