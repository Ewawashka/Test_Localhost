package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/test.properties"
})
public interface TestsProperties extends Config {
    @Config.Key("localhost.url")
    String localhostUrl();

    @Config.Key("localhostLoginPage.url")
    String localhostLoginPageUrl();

    @Config.Key("localhostProfilePage.url")
    String localhostProfilePageUrl();

    @Config.Key("localhostSingUpPage.url")
    String localhostSingUpPageUrl();
}
