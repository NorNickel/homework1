package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: Check

        String result = "";

        try {
            final PluginInterface plugin = cls.getConstructor().newInstance();
            final Method method = cls.getDeclaredMethod("apply", String.class);
            result = (String) method.invoke(plugin, text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
