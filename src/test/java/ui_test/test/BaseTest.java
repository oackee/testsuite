package ui_test.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui_test.pages.LoginStaffPage;
import ui_test.pages.MainStaffPage;
import ui_test.pages.PlayersStaffPage;

public class BaseTest {

    // Basic URL's
    protected final String STAFF_SITE_URL = "http://test-app.d6.dev.devcaz.com/admin/login";

    // Test admin user credentials
    protected final String USER_LOGIN = "admin1";
    protected final String USER_PASSWORD = "[9k<k8^z!+$$GkuP";

    // Page object and driver variables
    protected static WebDriver driver;
    protected static LoginStaffPage loginPage;
    protected static MainStaffPage mainPage;
    protected static PlayersStaffPage playersPage;
    protected static WebDriverWait wait;


}
