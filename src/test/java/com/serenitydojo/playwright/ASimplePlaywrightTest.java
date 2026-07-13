package com.serenitydojo.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ASimplePlaywrightTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void setup(){
        //Create an environment
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @AfterEach
    void tearDown(){
        browser.close();
        playwright.close();
    }

    @Test
    void shouldShowThePageTitle() {
        // TODO: Write your first playwright test here

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();
        Assertions.assertTrue(title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeyword(){

        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("Pliers");
        //this is basically sort of xpath
        page.locator("button:has-text('Search')").click();

        //this will look for the element with card
        int matchingSearchResults = page.locator(".card").count();
        System.out.println(matchingSearchResults);

        Assertions.assertTrue(matchingSearchResults>0);

    }
}
