package com.rikotsev.fin.grform.bus.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This serves as a wrapper around a CSV file. <br>
 * Through java nio utils and apache commons allows the read of CSV files
 * and provides utility methods for the downstream business logic
 *
 * @Author rikotsev
 */
public class CSVFile {

    private final Path filePath;

    /**
     * Initializes the wrapper class around an existing file
     * @param path the file
     */
    public CSVFile(final String path) {
        this(Paths.get(path));
    }

    /**
     * Initializes the wrapper class around an existing file
     * @param tempPath the file
     */
    public CSVFile(final Path tempPath) {
        if(Files.exists(tempPath) && Files.isReadable(tempPath) ) {
            this.filePath = tempPath;
        }
        else {
            final String exceptionMessage = String.format("File path:[ %s ] is not an existing and/or readable file!", tempPath.toString());
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    /**
     *
     * @return number of lines in the file
     * @throws IOException
     */
    public long numberOfLines() throws IOException {
        return Files.lines(filePath).count();
    }

    /**
     * The starting lines from which the file will be separated
     * into almost equal sections by number of lines
     *
     * @param sectionSize - the number of lines in a section
     * @return a collection of the starting line for each section with the provided size
     * @throws IOException
     */
    public Stack<Integer> startingLinesPool(final int sectionSize) throws IOException {

        final Stack<Integer> result = new Stack<>();
        final long numberOfLines = numberOfLines();

        //No lines in the file, nothing to calculate
        if(numberOfLines == 0) {
            return result;
        }

        final float numberOfPackets = Math.round(numberOfLines / sectionSize);

        // Start from 1 with the next sectionSize = 10 -> 1, 2, 3, 4, 5, 6, 7, 8, 9
        result.add(1);

        // Start from i = 1 - 10 with sectionSize = 10 -> 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
        // Start from i = 2 - 20 with sectionSize = 10 -> 20, 21, 22, 23, 24, 25, 26, 27, 28, 29
        // ...
        for(int i = 1; i <= (int) numberOfPackets; i++) {
            result.add(i * sectionSize);
        }

        return result;

    }

    /**
     * Gets a N number of lines from a file
     *
     * @param start - the starting line
     * @param sectionSize - the number of lines to collect
     * @return the lines collected
     * @throws IOException
     */
    public Set<String> lines(final int start, final int sectionSize) throws IOException  {

        final Set<String> result =  new HashSet<>();
        final Stream<String> lines = Files.lines(filePath);

        try {
            final int skip = start - 1;

            if(skip == 0) {
                // We take only n - 1 from the first line :)
                return lines.limit(sectionSize - 1).collect(Collectors.toSet());
            }

            return lines.skip(skip).limit(sectionSize).collect(Collectors.toSet());
        }
        finally {
            lines.close();
        }

    }

}
