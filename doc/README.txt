Tool: base64 encoder and decoder
Author: David Q. Romney
Date: 09/29/2017

The base64.jar is a simple utility that converts anyfile to its base64 representation.

Requirements: Installation of Java 8.x JRE or JDK.

The command-line syntax for base64 is as follows:

java -jar base64.jar [-f | --pathFile] <inputPathFile> [-e --encode | -d --decode]

Here is an example of how to encode a binary file, will use the base64.jar file, to a base64 text file. The output file name is the same as the input file with an added extension of .txt.

java -jar base64.jar --pathFile base64.jar --encode

To decode the same file base64.jar.txt, use the following command-line:

jar -jar base64.jar --pathFile base64.jar.txt --decode

The base64.jar was used a handy example. In the real world you may any binary file you wish, i.e. *.png, *.zip, etc.
