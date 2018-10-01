package com;

import core.config.ConfigProperty;
import core.utils.MongoUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static core.config.Config.getValue;

public class MongoConnectionTest {

    private MongoUtils mongoUtil = new MongoUtils(
                    getValue(ConfigProperty.MONGO_HOST), 9081,
                    getValue(ConfigProperty.MONGO_DBNAME),
                    getValue(ConfigProperty.MONGO_USERNAME),
                    getValue(ConfigProperty.MONGO_PASSWORD));

    @Test()
    @Tag("LOCAL")
    @DisplayName("Check mongodb connection")
    public void checkMongoDbConnection() throws Exception {
            mongoUtil.getConnection();
    }

}
