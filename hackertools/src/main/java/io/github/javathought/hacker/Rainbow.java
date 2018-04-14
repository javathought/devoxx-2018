package io.github.javathought.hacker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;

import static org.apache.commons.codec.digest.DigestUtils.sha1;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

public class Rainbow {

    public static final int PWD_MAX_LENGTH = 5;
    private static String carList =
            "abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "0123456789";

    public static void main(String[] args) throws IOException {
        computeRainbow();
    }


    private static void computeRainbow() throws IOException {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rainbow.txt"))) {

            byte[] clearText = new byte[PWD_MAX_LENGTH+1];

            for (int i = 0; i < PWD_MAX_LENGTH; i++) {

                computeIter(clearText, 0, PWD_MAX_LENGTH, writer);

            }
            writer.close();
        }
    }

    private static void computeIter(byte[] clearText, int i, int pwdMaxLength, BufferedWriter writer) throws IOException {
        for (int j = 0; j < 62; j++) {
            clearText[i] = (byte)carList.charAt(j);
            if (i + 1 < pwdMaxLength) {
                clearText[i + 1] = 0;
                computeIter(clearText, i + 1, pwdMaxLength, writer);
            } else {
                writer.write(sha1Hex(clearText));
                clearText[i+1] = (byte)'\n';
                writer.write(':');
                writer.write(new String(clearText));
            }

        }
    }


}
