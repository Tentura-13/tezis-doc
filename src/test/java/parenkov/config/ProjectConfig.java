package parenkov.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:config/remote.properties"
})
public interface ProjectConfig extends Config {

    @DefaultValue("chrome")
    String browser();

    @DefaultValue("96.0")
    String browserVersion();

    @DefaultValue("1920x1080")
    String browserSize();

    String remoteDriverUrl();

    String videoStorage();
}
