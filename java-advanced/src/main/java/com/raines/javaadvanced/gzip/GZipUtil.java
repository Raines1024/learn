package com.raines.javaadvanced.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64.Encoder;

import java.util.Base64.Decoder;

/**
 *
 * gzip对字符串进行加解密和加解压
 * @author raines
 *
 */
@SuppressWarnings("restriction")
public class GZipUtil {

    private static Logger log = LoggerFactory.getLogger(GZipUtil.class);

    static Encoder encoder = Base64.getEncoder();
    static Decoder decoder = Base64.getDecoder();
    
    /**
     * 将字符串压缩后Base64
     * @param primStr 待加压加密函数
     * @return
     */
    public static String gzipString(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = null;
        GZIPOutputStream gout = null;
        try{
            out = new ByteArrayOutputStream();
            gout = new GZIPOutputStream(out);
            gout.write(primStr.getBytes(StandardCharsets.UTF_8));
            gout.flush();
        } catch (IOException e) {
            log.error("对字符串进行加压加密操作失败：", e);
            return null;
        } finally {
            if (gout != null) {
                try {
                    gout.close();
                } catch (IOException e) {
                    log.error("对字符串进行加压加密操作，关闭gzip操作流失败：", e);
                }
            }
        }
        // 需要放在out对象关闭之后，不然解压时会报错。
        return new String(encoder.encode(out.toByteArray()));
    }

    /**
     * 将压缩并Base64后的字符串进行解密解压
     * @param compressedStr 待解密解压字符串
     * @return
     */
    public static String ungzipString(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gin = null;
        String decompressed = null;
        try {
            byte[] compressed = decoder.decode(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            gin = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while((offset = gin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("对字符串进行解密解压操作失败：", e);
            decompressed = null;
        } finally {
            if (gin != null) {
                try {
                    gin.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭压缩流失败：", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭输入流失败：", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭输出流失败：", e);
                }
            }
        }
        return decompressed;
    }

    public static void main(String[] args) {
        System.out.println(gzipString("1234567"));
        System.out.println(ungzipString(gzipString("1234567")));
    }
}