package com.dqr;

/**
 * @author Kushal Paudyal
 * www.icodejava.com
 * <p>
 * This class can be used to Base64 encode or Base64 decode a file. It uses apache commons codec library.
 * The library used for testing the functionality was commons-codec-1.4.jar
 */

import jargs.gnu.CmdLineParser;
import lombok.extern.java.Log;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.logging.Level;

@Log
public class FileCodecBase64 {

    private static final boolean IS_CHUNKED = false;

    public static void main(String args[]) throws Exception {

        String fileName;
        String fileNameBase64;
        String baseName;

        Arguments arguments = commandLineProcessor(args);

        if (arguments.getPathFile() != null) {

            fileName = arguments.getPathFile();
            if (arguments.getEncode() == Boolean.TRUE) {
                /* Encode a file and write the encoded output to a text file. */
                fileNameBase64 = fileName + ".txt";
                encode(fileName, fileNameBase64, arguments.getChunkMode());
            }
            if (arguments.getDecode() == Boolean.TRUE) {
                /* Decode a file and write the decoded file to file system */
                baseName = fileName + arguments.getOutputExt();
                decode(fileName, baseName);
            }

        } else {
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

    public static Arguments commandLineProcessor(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        Arguments arguments = new Arguments();

//        CmdLineParser.Option consoleOpt = parser.addBooleanOption('c', "console");
        CmdLineParser.Option helpOpt = parser.addBooleanOption('h', "help");
        CmdLineParser.Option verboseOpt = parser.addBooleanOption('v', "verbose");
        CmdLineParser.Option chunkModeOpt = parser.addBooleanOption('c', "chunkMode");
        CmdLineParser.Option encodeOpt = parser.addBooleanOption('e', "encode");
        CmdLineParser.Option decodeOpt = parser.addBooleanOption('d', "decode");
        CmdLineParser.Option outDirOpt = parser.addStringOption('o', "outDir");
        CmdLineParser.Option pathFileOpt = parser.addStringOption('f', "pathFile");
        CmdLineParser.Option outputExtOpt = parser.addStringOption('x', "outputExt");

        try {
            parser.parse(args);
        } catch (CmdLineParser.OptionException e) {
            usage();
            System.exit(1);
        }

        Boolean encode = (Boolean) parser.getOptionValue(encodeOpt);
        if (encode != null && encode.booleanValue()) {
            log.setLevel(Level.FINEST);
            log.info("Setting to encode file.");
            arguments.setEncode(encode);
        }

        Boolean decode = (Boolean) parser.getOptionValue(decodeOpt);
        if (decode != null && decode.booleanValue()) {
            log.setLevel(Level.FINEST);
            log.info("Setting to decode file.");
            arguments.setDecode(decode);
        }

        Boolean chunkMode = (Boolean) parser.getOptionValue(chunkModeOpt);
        if (chunkMode != null && chunkMode.booleanValue()) {
            log.info("Setting to chunk mode file.");
            arguments.setChunkMode(chunkMode);
        }

        if ((encode != null && encode) && (decode != null && decode)) {
            usage();
            System.exit(0);
        }

        String pathFile = (String) parser.getOptionValue(pathFileOpt);
        if (pathFile == null) {
            usage();
            System.exit(0);
        } else {
            arguments.setPathFile(pathFile);
        }

        String outDir = (String) parser.getOptionValue(outDirOpt);
        if (outDir == null) {
            outDir = "./";
        } else {
            arguments.setOutDir(outDir);
        }

        String outputExt = (String) parser.getOptionValue(outputExtOpt);
        if (outputExt == null) {
            outputExt = ".decoded";
        } else {
            arguments.setOutputExt(outputExt);
        }

        Boolean help = (Boolean) parser.getOptionValue(helpOpt);
        if (help != null && help.booleanValue()) {
            usage();
            System.exit(0);
        }

        Boolean verbose = (Boolean) parser.getOptionValue(verboseOpt);
        if (verbose != null && verbose.booleanValue()) {
            log.setLevel(Level.FINEST);
            log.info("Setting LogLevel to DEBUG.");
        } else {
            log.setLevel(Level.INFO);
            log.info("Setting LogLevel to INFO.");
        }

        return arguments;
    }

    private static void usage() {
        System.out.println("Use:");
        System.out.println("\tjava -jar base64.jar [-f | --pathFile] <inputPathFile> [-e --encode | -d --decode]\n");
        System.out.println("\t\tex. java -jar base64.jar --pathFile base64.jar --encode\t~ creates a file base64.jar.txt");
    }
}