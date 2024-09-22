package utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/test/resources/test.properties"
})

public interface Props extends Config {
    Props props = ConfigFactory.create(Props.class);

    @Key("user")
    String user();

    @Key("password")
    String password();

    @Key("taskname")
    String taskname();

    @Key("taskkey")
    String taskkey();
}
