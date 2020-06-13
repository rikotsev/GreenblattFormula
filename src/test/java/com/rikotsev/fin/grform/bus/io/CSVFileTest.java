package com.rikotsev.fin.grform.bus.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.Stack;

public class CSVFileTest {

    final Logger logger = LoggerFactory.getLogger(CSVFileTest.class);

    @Test
    public void initializationTest() {
        final CSVFile csvFile;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CSVFile("no_such_directory"));
    }

    @Test
    public void startingLinesPool() {

        String path = "";

        try {
            path = Paths.get(getClass().getClassLoader().getResource("test.csv").toURI()).toString();
            logger.info("path = " + path);
        }
        catch(URISyntaxException e) {
            logger.debug(e.getMessage());
            Assertions.fail();
        }

        final CSVFile file = new CSVFile(path);

        try {
            final Stack<Integer> result = file.startingLinesPool(3);

            //Iterator<Integer> it = result.iterator();

            //while(it.hasNext()) {
            //    logger.info(String.format("it value = %d", it.next()));
            //}

            Assertions.assertEquals(6, result.pop());
            Assertions.assertEquals(3, result.pop());
            Assertions.assertEquals(1, result.pop());
        }
        catch(final IOException e) {
            logger.debug(e.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void lines() {

        String path = "";

        try {
            path = Paths.get(getClass().getClassLoader().getResource("test.csv").toURI()).toString();
            logger.info("path = " + path);
        }
        catch(URISyntaxException e) {
            logger.debug(e.getMessage());
            Assertions.fail();
        }

        final CSVFile file = new CSVFile(path);

        try {
            final int sectionSize = 3;
            final Stack<Integer> result = file.startingLinesPool(sectionSize);

            final int start1 = result.pop();
            final Set<String> lines1 = file.lines(start1, sectionSize);

            Assertions.assertTrue(lines1.contains("row7,row7"));
            Assertions.assertTrue(lines1.contains("row6,row6"));
            Assertions.assertTrue(lines1.size() == 2);

            final int start2 = result.pop();
            final Set<String> lines2 = file.lines(start2, sectionSize);

            Assertions.assertTrue(lines2.contains("row5,row5"));
            Assertions.assertTrue(lines2.contains("row4,row4"));
            Assertions.assertTrue(lines2.contains("row3,row3"));
            Assertions.assertTrue(lines2.size() == 3);

            final int start3 = result.pop();
            final Set<String> lines3 = file.lines(start3, sectionSize);

            Assertions.assertTrue(lines3.contains("row1,row1"));
            Assertions.assertTrue(lines3.contains("row2,row2"));
            Assertions.assertTrue(lines3.size() == 2);
        }
        catch(final IOException e) {
            logger.debug(e.getMessage());
            Assertions.fail();
        }

    }

    @Test
    public void emptyFileStartingLines() {
        //TODO
    }

    @Test
    public void emptyFileLines() {
        //TODO
    }

}
