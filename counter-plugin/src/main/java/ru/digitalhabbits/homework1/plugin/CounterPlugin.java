package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: Check

        text = text.replaceAll("\\\\n", "\n").toLowerCase();

        int numberOfLines = text.split("\n").length;
        int numberOfWords = 0;
        int numberOfLetters = 0;

        Pattern pattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            numberOfWords++;
        }

        numberOfLetters += text.length();

        return numberOfLines + ";" + numberOfWords + ";" + numberOfLetters;

    }

}
