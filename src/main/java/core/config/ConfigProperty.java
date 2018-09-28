package core.config;

public enum ConfigProperty {
    SELENIUM_GRID("selenium.grid", false, "false"),
    SELENIUM_GRID_PROJECT("selenium.grid.project", false),
    SELENIUM_GRID_VIDEO("selenium.grid.video", false),
    WEBDRIVER_URL("webdriver.url", true),

    TEST_USER_NAME("test.user.name", false),
    TEST_USER_PWD("test.user.pwd", false),

    BROWSER("browser", true),
    BROWSER_RESOLUTION("browser.resolution", false, "1280x800"),
    HOME_PAGE("browser.home.page", false, "about:blank"),
    GLOBAL_TIMEOUT("global.timeout.seconds", false, "60"),
    CUSTOM_TIMEOUT("custom.timeout.seconds", false, "30"),

    MAX_RETRIES("max.retries", false, "50"),
    RETRY_TIMEOUT("retry.timeout", false, "5000"),

    MONGO_HOST("mongodb.host", false),
    MONGO_PORT("mongodb.port", false),
    MONGO_DBNAME("mongodb.name", false),
    MONGO_USERNAME("mongodb.user.name", false),
    MONGO_PASSWORD("mongodb.user.password", false);

    private String name;
    private boolean mandatory;
    private String defaultValue;

    ConfigProperty(String name, boolean mandatory) {
        this.name = name;
        this.mandatory = mandatory;
    }

    ConfigProperty(String name, boolean mandatory, String defaultValue) {
        this(name, mandatory);
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}