package ru.nsu.nmashkin.task131;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for finding a substring in a file.
 */
public class SubstringFinder {
    private static final int readLen = 4096;

    /**
     * Find indexes of a substring in a file.
     *
     * @param in input stream
     * @param substring target substring
     * @return ArrayList of indexes
     */
    public static List<Long> find(InputStream in, String substring) throws IOException {
        List<Long> result = new ArrayList<>();
        char[] target = substring.toCharArray();
        int[] shiftMap = new int[256 * 256];
        Arrays.fill(shiftMap, -1);
        for (int i = 0; i < target.length; i++) {
            shiftMap[target[i]] = i;
        }

        try (InputStreamReader inr = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            char[][] chunks = new char[2][readLen];
            int currChunk = 0;
            int[] chunkLens = new int[2];
            long chunkCount = 0;
            boolean chunkRequest = true;

            chunkLens[currChunk] = inr.read(chunks[currChunk]);
            while (true) {
                int i = 0;
                while (i < chunkLens[currChunk]) {
                    if (i + target.length > chunkLens[currChunk] && chunkRequest) {
                        chunkRequest = false;
                        chunkLens[currChunk ^ 1] = inr.read(chunks[currChunk ^ 1]);
                        if (chunkLens[currChunk ^ 1] == -1) {
                            break;
                        }
                    }

                    boolean gotcha = true;
                    for (int j = i + target.length - 1; j >= i; j--) {
                        int switchChunk = j >= readLen ? 1 : 0;

                        if (target[j - i] != chunks[currChunk ^ switchChunk][j % readLen]) {
                            i += j - i - shiftMap[chunks[currChunk ^ switchChunk][j % readLen]];
                            gotcha = false;
                            break;
                        }
                    }
                    if (gotcha) {
                        result.add(chunkCount * readLen + i);
                        i++;
                    }
                }
                if (chunkRequest) {
                    chunkLens[currChunk ^ 1] = inr.read(chunks[currChunk ^ 1]);
                }
                if (chunkLens[currChunk ^ 1] == -1) {
                    break;
                }

                chunkRequest = true;
                currChunk ^= 1;
                chunkCount++;
            }
        }

        return result;
    }
}