package utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/test/resources/test.properties"
})

public interface Props extends Config {
    Props props = ConfigFactory.create(Props.class);

    @Key("FILE_PATH")
    String file_path();

    @Key("potato_URI")
    String potato_uri();

    @Key("morty_URI")
    String morty_uri();

}