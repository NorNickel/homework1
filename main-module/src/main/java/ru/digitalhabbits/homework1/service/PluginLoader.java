package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.slf4j.LoggerFactory.getLogger;


public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: Check

        List<Class<? extends PluginInterface>> result = new ArrayList<>();

        // get all .jar files from pluginDir
        final File pluginDir = new File(pluginDirName);
        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith("." + PLUGIN_EXT));

        if (jars == null) {
            logger.error("Problem with plugin loading! No files in directory '\\"
                    + pluginDirName + "' or directory does not exist");
            jars = new File[0];
        }

        for (int i = 0; i < jars.length; i++) {
            try {

                final JarFile jarFile = new JarFile(jars[i]);
                final Enumeration<JarEntry> e = jarFile.entries();

                final URL jarURL = jars[i].toURI().toURL();
                final URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});

                // search for class name and load it
                while (e.hasMoreElements()) {
                    final JarEntry je = e.nextElement();

                    if (je.isDirectory() || !je.getName().endsWith(".class")
                            || !je.getName().replace('/', '.').startsWith(PACKAGE_TO_SCAN)) {
                        continue;
                    }

                    String className = je.getName().substring(0, je.getName().length() - 6);  // -6 because of .class
                    className = className.replace('/', '.');

                    final Class clazz = classLoader.loadClass(className);

                    // checking that the class is an interface implementation
                    if (PluginInterface.class.isAssignableFrom(clazz)) {
                        result.add((Class<? extends PluginInterface>) clazz);
                        logger.info("class " + className + " has loaded");
                        break;
                    }

                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        return result;
    }
}
