package org.example;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.suite.api.*;

@Slf4j
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource(".")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/demo-report.html")
public class RunCucumberTest {}
