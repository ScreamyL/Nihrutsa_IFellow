package tests;


import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "hooks, steps")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "feature")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@Test")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm, json:target/cucumber.json")

public class RunCucumberTest {
}
