package com.dqr;

/**
 * @author Kushal Paudyal
 * www.icodejava.com
 *
 * This class can be used to Base64 encode or Base64 decode a file. It uses apache commons codec library.
 * The library used for testing the functionality was commons-codec-1.4.jar
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

import java.io.*;

public class FileCodecBase64 {

    private static final boolean IS_CHUNKED = false;

    public static void main(String args[]) throws Exception {

        String fileName;
        String fileExt;
        String baseName;

        if (args.length == 1) {
            fileName = args[0];
            baseName = FilenameUtils.getBaseName(fileName) + ".txt";
            fileExt = FilenameUtils.getExtension(fileName);
            /* Encode a file and write the encoded output to a text file. */
            encode(fileName, baseName, IS_CHUNKED);

            /* Decode a file and write the decoded file to file system */
            decode(baseName, baseName + "-decoded." + fileExt);
        }
        else {
            System.err.println("Missing file argument!");
        }
    }

    /**
     * This method converts the content of a source file into Base64 encoded data and saves that to a target file.
     * If isChunked parameter is set to true, there is a hard wrap of the output  encoded text.
     */
    private static void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

        byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

        writeByteArraysToFile(targetFile, base64EncodedData);
    }

    public static void decode(String sourceFile, String targetFile) throws Exception {

        byte[] decodedBytes = Base64.decodeBase64(loadFileAsBytesArray(sourceFile));

        writeByteArraysToFile(targetFile, decodedBytes);
    }

    /**
     * This method loads a file from file system and returns the byte array of the content.
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {

        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }

    /**
     * This method writes byte array content into a file.
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }
}