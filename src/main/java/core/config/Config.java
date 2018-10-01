package core.config;

import core.remote.SeleniumException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {

    private static final String FILE_NAME = "selenium.properties";

    final static Map<ConfigProperty, String> properties = new HashMap<>();

    public static void readConfigFile() {
        Properties properties = readPropertyFile();

        for (ConfigProperty configProperty : ConfigProperty.values()) {
            String propertyName = configProperty.getName();
            String value = properties.getProperty(propertyName);
            if (value == null && configProperty.isMandatory()) {
                throw new SeleniumException(String.format("Property \"%s\" was not found in %s file", propertyName, FILE_NAME));
            }
            setProperty(configProperty, value);
        }
    }

    private static Properties readPropertyFile() {
        Properties prop = new Properties();
        URL file = Config.class.getClassLoader().getResource(FILE_NAME);
        if (file == null) {
            throw new SeleniumException("Unable to find file " + FILE_NAME + " in the test resources");
        }

        try {
            prop.load(file.openStream());
        } catch (Exception e) {
            throw new SeleniumException("Unable to read " + FILE_NAME);
        }
        return prop;
    }

    public static void setProperty(ConfigProperty property, String value) {
        properties.put(property, value);
    }

    public static String getValue(ConfigProperty property) {
        String value = properties.get(property);
        if (value != null) {
            return value.trim();
        } else if (!property.isMandatory()) {
            return property.getDefaultValue();
        }
        throw new SeleniumException("Property " + property.name() + " is not found in the config file!");
    }
}