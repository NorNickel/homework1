package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        // TODO: Check

        text = text.replaceAll("\\\\n", "\n").toLowerCase();

        Pattern pattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
        Matcher matcher = pattern.matcher(text);

        Map<String, Integer> frequencyMap = new HashMap<>();

        while (matcher.find()) {
            String word = text.substring(matcher.start(), matcher.end());
            Integer frequency = frequencyMap.get(word);
            frequencyMap.put(word, frequency == null ? 1 : ++frequency);
        }

        StringJoiner result = new StringJoiner("\n");

        frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(s -> result.add(s.getKey() + " " + s.getValue()));

        return result.toString();

    }
}
