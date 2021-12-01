package ui_test.firefox_browser.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui_test.chrome_browser.pages.LoginStaffPage;
import ui_test.chrome_browser.pages.MainStaffPage;
import ui_test.chrome_browser.pages.PlayersStaffPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests extends BaseTest {


    @Before
    public void setUp() {

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);
        loginPage = PageFactory.initElements(driver, LoginStaffPage.class);
        mainPage = PageFactory.initElements(driver, MainStaffPage.class);
        playersPage = PageFactory.initElements(driver, PlayersStaffPage.class);

    }

    //@Ignore
    @Test
    public void test1_authorization() {

        driver.get(STAFF_SITE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals("Casino", loginPage.getHeadingText());
        mainPage = loginPage.signIn(USER_LOGIN,USER_PASSWORD);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(true, mainPage.headerLogo.isDisplayed());
        Assert.assertEquals(true, mainPage.dashboardTickets.isDisplayed());
        Assert.assertEquals(true, mainPage.dashboardPlayersOnline.isDisplayed());
        Assert.assertEquals(true, mainPage.dashboardActiveSessions.isDisplayed());
        Assert.assertEquals(true, mainPage.dashboardWithdrawalRequests.isDisplayed());

        System.out.println("1. _Firefox_ User logged in successfully. Staff panel loaded successfully");

    }

    //@Ignore
    @Test
    public void test2_playerTableSorting() {

        driver.get(STAFF_SITE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals("Casino", loginPage.getHeadingText());
        mainPage = loginPage.signIn(USER_LOGIN,USER_PASSWORD);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        playersPage = mainPage.moveToPlayersPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        playersPage.choosePlayersPageSize(500);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(true, playersPage.playersTable.isDisplayed());

        System.out.println("2. _Firefox_ Players table loaded successfully and displayed");

    }

    //@Ignore
    @Test
    public void test3_playersTableAscendingSort() throws InterruptedException {

        driver.get(STAFF_SITE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals("Casino", loginPage.getHeadingText());
        mainPage = loginPage.signIn(USER_LOGIN,USER_PASSWORD);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        playersPage = mainPage.moveToPlayersPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(true, playersPage.playersTable.isDisplayed());

        // Check if ascending sort works correctly

        playersPage.choosePlayersPageSize(500);
        TimeUnit.SECONDS.sleep(15);
        playersPage.thRegistrationDate.click();
        TimeUnit.SECONDS.sleep(15);

        String firstDate = playersPage.firstRow.getText();
        String middleDate = playersPage.middleRow.getText();
        String lastDate = playersPage.lastRow.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date1 = LocalDate.parse(firstDate, formatter);
        LocalDate date2 = LocalDate.parse(middleDate, formatter);
        LocalDate date3 = LocalDate.parse(lastDate, formatter);

        Assert.assertEquals(true, date1.isBefore(date2) && date2.isBefore(date3));


        System.out.println("3. _Firefox_ Ascending sort works correctly");

    }


    @Test
    public void test4_playersTableDescendingSort() throws InterruptedException {

        driver.get(STAFF_SITE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals("Casino", loginPage.getHeadingText());
        mainPage = loginPage.signIn(USER_LOGIN,USER_PASSWORD);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        playersPage = mainPage.moveToPlayersPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertEquals(true, playersPage.playersTable.isDisplayed());

        // Check if descending sort works correctly

        playersPage.choosePlayersPageSize(500);
        TimeUnit.SECONDS.sleep(15);
        playersPage.thRegistrationDate.click();
        TimeUnit.SECONDS.sleep(15);
        playersPage.thRegistrationDate.click();
        TimeUnit.SECONDS.sleep(15);

        String firstDate = playersPage.firstRow.getText();
        String middleDate = playersPage.middleRow.getText();
        String lastDate = playersPage.lastRow.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date1 = LocalDate.parse(firstDate, formatter);
        LocalDate date2 = LocalDate.parse(middleDate, formatter);
        LocalDate date3 = LocalDate.parse(lastDate, formatter);

        Assert.assertEquals(true, date1.isAfter(date2) && date2.isAfter(date3));


        System.out.println("4. _Firefox_ Descending sort works correctly");

    }


    @After
    public void tearDown() {

        driver.quit();

    }
}