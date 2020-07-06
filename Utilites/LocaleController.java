package Utilites;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocaleController {
    public static Locale rus;
    public static Locale eng;
    public static Locale port;
    public static Locale ltu;
    private static HashSet<Localizeable> toLocalize = new HashSet<>();

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("words", Locale.getDefault(), new UTF8Control());
    }

    public static void regist(Localizeable localized) {
        toLocalize.add(localized);
    }

    public static void changeLocale(Locale locale) {
        try {
            Locale.setDefault(locale);
            for (Localizeable localized : toLocalize) {
                localized.createLocale();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Locale getLocal(String name) {
        switch (name) {
            case ("Russian"):
                return rus;
            case ("English"):
                return eng;
            case ("Portugal"):
                return port;
            case ("Lithuanian"):
                return ltu;
        }
        return null;
    }

    public LocaleController() {
        rus = new Locale("ru", "RU");
        eng = new Locale("en", "GB");
        port = new Locale("pt", "PT");
        ltu = new Locale("lt", "LT");
        Locale.setDefault(eng);
    }


    public static class UTF8Control extends ResourceBundle.Control {
        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            // The below is a copy of the default implementation.
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}
