package cn.sign.utils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * 加密解密工具
 */
public class CryptUtils {

	private final static String DES = "DES";
	private final static String CHARSET = "UTF-8";

	/**
	 * 将字符串GZIP压缩后Base64
	 * @param string 待加压加密函数
	 * @return
	 */
	public static String gzipBase64Encode(String string){
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
			GZIPOutputStream gos = new GZIPOutputStream(os);
			gos.write(string.getBytes());
			gos.close();
			byte[] compressed = os.toByteArray();
			os.close();
			return Base64.getEncoder().encodeToString(compressed);
		} catch (IOException e) {
			throw new RuntimeException("对字符串进行加压加密操作失败：", e);
		}
	}


	/**
	 * GZIP压缩并Base64后的字符串进行解密解压
	 * @param textToDecode 待解密解压字符串
	 * @return
	 */
	public static String unGzipBase64Decoder(String textToDecode){
		try {
			byte[] compressed = Base64.getDecoder().decode(textToDecode);
			final int BUFFER_SIZE = 32;
			ByteArrayInputStream inputStream = new ByteArrayInputStream(compressed);
			GZIPInputStream gis  = new GZIPInputStream(inputStream, BUFFER_SIZE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] data = new byte[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = gis.read(data)) != -1) {
				baos.write(data, 0, bytesRead);
			}
			return baos.toString(CHARSET);
		}
		catch (Exception e) {
			throw new RuntimeException("对字符串进行解压解密操作，gzip操作流失败：", e);
		}
	}


	/**
	 * MD5 摘要计算(byte[]).
	 * @param src byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5(byte[] src) {
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			return alg.digest(src);
		} catch (Exception e) {
			throw new RuntimeException("MD5", e);
		}
	}

	/**
	 * MD5 摘要计算(String).
	 * @param src String
	 * @throws Exception
	 * @return String
	 */
	public static String md5(String src) {
		try {
			return byte2hex(md5(src.getBytes(CHARSET)));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5 decrypt", e);
		}
	}

	/**
	 * 加密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptDES(byte[] src, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		} catch (InvalidKeyException
			| NoSuchAlgorithmException
			| InvalidKeySpecException
			| NoSuchPaddingException
			| IllegalBlockSizeException
			| BadPaddingException e) {
			throw new RuntimeException("DES encrypt", e);
		}
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度[" + b.length + "]不是偶数");
        }
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 解密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decryptDES(byte[] src, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			// 现在，获取数据并解密
			// 正式执行解密操作
			return cipher.doFinal(src);
		} catch (InvalidKeyException
			| NoSuchAlgorithmException
			| InvalidKeySpecException
			| NoSuchPaddingException
			| IllegalBlockSizeException
			| BadPaddingException e) {
			throw new RuntimeException("DES decrypt", e);
		}
	}

	/**
	 * 密码解密
	 * @param data 密文
	 * @param key 密匙
	 * @return
	 * @throws Exception
	 */
	public final static String decryptPassword(String data, String key) {
		if (data != null) {
			try {
				return new String(decryptDES(hex2byte(data.getBytes(CHARSET)), key.getBytes(CHARSET)));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("DES decrypt", e);
			}
		}
		return null;
	}

	/**
	 * 密码加密
	 * @param password 明文
	 * @param key 密匙
	 * @return
	 * @throws Exception
	 */
	public final static String encryptPassword(String password, String key) {
		if (password != null) {
            try {
                return byte2hex(encryptDES(password.getBytes(CHARSET), key.getBytes(CHARSET)));
            } catch (Exception e) {
                throw new RuntimeException("DES encrypt", e);
            }
        }
		return null;
	}

	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
                sb.append("0").append(stmp);
            } else {
                sb.append(stmp);
            }
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 二行制转页面字符串
	 * @param b
	 * @return
	 */
	public static String byte2webhex(byte[] b) {
		return byte2hex(b, "%");
	}

	/**
	 * 二行制转字符串
	 * @param b
	 * @param elide 分隔符
	 * @return
	 */
	public static String byte2hex(byte[] b, String elide) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";
		elide = elide == null ? "" : elide;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
                sb.append(elide).append("0").append(stmp);
            } else {
                sb.append(elide).append(stmp);
            }
		}
		return sb.toString().toUpperCase();
	}

	public static String md5String(byte[] source) {
		return byte2hex(md5(source));
	}

	/**
	 * deflate base64解码解压
	 * @param input
	 * @return
	 * @throws IOException
	 */
    public static String unDeflateToBase64(byte[] input) throws IOException {
        input = Base64.getDecoder().decode(input);
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
        try {
            byte[] buff = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buff);
                baos.write(buff, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        inflater.end();
        byte[] output = baos.toByteArray();
        return new String(output, CHARSET);
    }

	/**
	 * deflate压缩base64编码
	 * @param data
	 * @return
	 * @throws IOException
	 */
    public static byte[] deflateToBase64(byte[] data) throws IOException {
        byte[] output;
        Deflater compress = new Deflater();
        compress.reset();
        compress.setInput(data);
        compress.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compress.finished()) {
                int i = compress.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            bos.close();
        }
        compress.end();
        return Base64.getEncoder().encode(output);
    }
}
