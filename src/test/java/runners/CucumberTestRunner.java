
package runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
// @RunWith(SerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "steps",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        monochrome = true
)
public class CucumberTestRunner {
}
