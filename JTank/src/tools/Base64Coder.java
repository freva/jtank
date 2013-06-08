package tools;


public class Base64Coder {
	private static final char[] map1 = new char[64];
		static {
			int i=0;
			for (char c='A'; c<='Z'; c++) map1[i++] = c;
			for (char c='a'; c<='z'; c++) map1[i++] = c;
			for (char c='0'; c<='9'; c++) map1[i++] = c;
			map1[i++] = '+'; map1[i++] = '/';
		}
	
	private static final byte[] map2 = new byte[128];
		static {
			for (int i=0; i<map2.length; i++) map2[i] = -1;
			for (int i=0; i<64; i++) map2[map1[i]] = (byte)i; 
		}

	public static String encodeLines(byte[] in) {
		int iOff = 0, iLen = in.length, blockLen = 57, bufLen = ((iLen+2)/3)*4, ip=0;

		StringBuilder buf = new StringBuilder(bufLen);
		while(ip < iLen) {
			int l = Math.min(iLen-ip, blockLen);
			buf.append(encode(in, iOff+ip, l));
			ip += l;
		}
		return buf.toString(); 
	}
	
	public static char[] encode (byte[] in, int iOff, int iLen) {
		int oDataLen = (iLen*4+2)/3, oLen = ((iLen+2)/3)*4;
		char[] out = new char[oLen];
		int ip = iOff, iEnd = iOff + iLen, op = 0;
		
		while (ip < iEnd) {
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
			int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = map1[o0];
			out[op++] = map1[o1];
			out[op] = op < oDataLen ? map1[o2] : '='; op++;
			out[op] = op < oDataLen ? map1[o3] : '='; op++; }
		return out; 
	}
	
	public static byte[] decode(String s) {
		char[] in = s.toCharArray();
		int ip = 0, iEnd = in.length, op = 0;
		while (iEnd > 0 && in[iEnd-1] == '=') iEnd--;
		int oLen = (iEnd*3) / 4;
		byte[] out = new byte[oLen];

		while (ip < iEnd) {
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iEnd ? in[ip++] : 'A';
			int i3 = ip < iEnd ? in[ip++] : 'A';
			
			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
			int b0 = map2[i0], b1 = map2[i1], b2 = map2[i2], b3 = map2[i3];
			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
			
			int o0 = ( b0       <<2) | (b1>>>4);
			int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
			int o2 = ((b2 &   3)<<6) |  b3;
			out[op++] = (byte)o0;
			if(op<oLen) out[op++] = (byte)o1;
			if(op<oLen) out[op++] = (byte)o2; 
		}
		return out; 
	}
}