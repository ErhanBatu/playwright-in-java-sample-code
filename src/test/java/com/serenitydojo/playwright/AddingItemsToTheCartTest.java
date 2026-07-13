package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;


public class AddingItemsToTheCartTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void closeContext() {
        browserContext.close();
    }

    @AfterAll
    static void tearDown() {
        browser.close();
        playwright.close();
    }

    @DisplayName("Search for pliers")
    @Test
    void searchForPliers(){
        page.navigate("https://practicesoftwaretesting.com/");
//        page.locator("[placeholder='Search']").fill("pliers");
        page.getByPlaceholder("Search").fill("pliers");
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Search")).click();

        Locator products = page.locator(".card").filter(
                new Locator.FilterOptions()
                        .setHas(page.getByAltText("Pliers"))
                        .setHasNot(page.getByText("Out of stock"))
        );

        List<String> productNames = products.locator("h5").allTextContents();
        System.out.println(productNames);

    }














}
