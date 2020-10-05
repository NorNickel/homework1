package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        // TODO: Check

        final String currentDir = System.getProperty("user.dir");
        final String fileName = currentDir + "\\" + RESULT_DIR + "\\"
                + String.format(RESULT_FILE_PATTERN, pluginName);

        try(final FileWriter writer = new FileWriter(fileName, false)) {

            writer.write(text);
            writer.flush();

        } catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);

        if (!resultDir.exists()) {
            try {
                resultDir.mkdir();
            } catch(Exception e) {
                e.printStackTrace();
                System.exit(1); // bad way!
            }
        }

        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
