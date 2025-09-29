package ru.nsu.nmashkin.task131;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for finding a substring in a file.
 */
public class SubstringFinder {
    /**
     * Find indexes of a substring in a file.
     *
     * @param filename name of the file
     * @param substring target substring
     * @return ArrayList of indexes
     */
    public static List<Long> find(String filename, String substring) {
        List<Long> result = new ArrayList<>();

        try (InputStream in = new FileInputStream(filename)) {
            InputStreamReader inr = new InputStreamReader(in, StandardCharsets.UTF_8);
            char[] target = substring.toCharArray();
            char[] chunk = new char[4096];
            int chunkLen;
            long chunkCount = 0;

            while ((chunkLen = inr.read(chunk)) != -1) {
                int targetInd = 0;
                long potentialMatchInd = -1;
                for (int i = 0; i < chunkLen; i++) {
                    if (target[targetInd] == chunk[i]) {
                        if (potentialMatchInd == -1) {
                            potentialMatchInd = i + chunkCount * 4096;
                        }

                        targetInd++;

                        if (targetInd == target.length) {
                            result.add(potentialMatchInd);
                            targetInd = 0;
                            potentialMatchInd = -1;
                        }
                    } else {
                        targetInd = 0;
                        potentialMatchInd = -1;
                    }
                }
                chunkCount++;
            }
        } catch (IOException e) {
            throw new SubstringFinderException(e.getMessage());
        }

        return result;
    }
}
