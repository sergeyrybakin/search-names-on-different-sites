import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;

public class FindSelenideTest {

    //This test works for one-page search sites.
    //You have to add some actions/locators for multi-page sites.
    //Perhaps, you have to change data file sites,txt for multi-page sites.
    @Test
    void findNamesOnSites() {
        String allSitesData = FileUtils.readStringFromFile("src/main/resources/sites.txt");
        String sites[] = allSitesData.split("\n");
        String allNames = FileUtils.readStringFromFile("src/main/resources/names.txt");

        for (String s : sites)
        {
            //locator[0] = URL; locator[1] = search field; locator[2] = grid of results
            String locator[] = s.split(";");
            Selenide.open(locator[0]); //open browser. Chrome is default.
            for (String name : allNames.split("\n")) {
                searchNameOnSiteAndTakeScreenShot(name, locator);
            }
        }
    }

    private void searchNameOnSiteAndTakeScreenShot(String name, String locator[]) {
        $(locator[1]).shouldBe(Condition.visible, Duration.ofSeconds(6));   //waiting of site loading
        $(locator[1]).val(name).submit();                                   //fill and submit search field
        $$(locator[2]).shouldHave(CollectionCondition.sizeGreaterThan(0));  //verify that something has been found
        if($$(locator[2]).size() > 0) {
            String screenShotFileName = "screenshots/" + locator[0] + "_" + name;
            screenshot(screenShotFileName);
        }
    }
}
