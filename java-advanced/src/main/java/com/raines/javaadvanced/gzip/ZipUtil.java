package com.raines.javaadvanced.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64.Encoder;

import java.util.Base64.Decoder;




/**
 *
 * zip对字符串进行加解密和加解压
 * @author raines
 *
 */
@SuppressWarnings("restriction")
public class ZipUtil {

    private static Logger log = LoggerFactory.getLogger(ZipUtil.class);


    static Encoder encoder = Base64.getEncoder();
    static Decoder decoder = Base64.getDecoder();

    /**
     * 将字符串压缩后Base64
     * @param primStr 待加压加密函数
     * @return
     */
    public static String zipString(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        try{
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(primStr.getBytes(StandardCharsets.UTF_8));
            zout.closeEntry();
            return new String(encoder.encode(out.toByteArray()));
        } catch (IOException e) {
            log.error("对字符串进行加压加密操作失败：", e);
            return null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                    log.error("对字符串进行加压加密操作，关闭zip操作流失败：", e);
                }
            }
        }
    }

    /**
     * 将压缩并Base64后的字符串进行解密解压
     * @param compressedStr 待解密解压字符串
     * @return
     */
    public static final String unzipString(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed = null;
        try {
            byte[] compressed = decoder.decode(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("对字符串进行解密解压操作失败：", e);
            decompressed = null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
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
        System.out.println(zipString("1234567").length());
    }
}