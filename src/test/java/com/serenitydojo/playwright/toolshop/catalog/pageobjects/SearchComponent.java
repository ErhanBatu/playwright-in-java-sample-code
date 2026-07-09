package com.serenitydojo.playwright.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchComponent {
    private final Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void searchBy(String keyword) {
        // The site's search endpoint is now invoked with the HTTP QUERY method, which sends
        // the keyword in the request body rather than as a "?q=" query-string parameter, so we
        // match on the endpoint URL alone rather than a keyword-specific query string.
        page.waitForResponse("**/products/search**", () -> {
            page.getByPlaceholder("Search").fill(keyword);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
        waitForResultsToRender();
    }

    public void clearSearch() {
        page.waitForResponse("**/products**", () -> {
            page.getByTestId("search-reset").click();
        });
        waitForResultsToRender();
    }

    // The matching network response can arrive slightly before the SPA finishes re-rendering
    // the results, so give Angular's change detection a brief moment to update the DOM before
    // it is read. Waiting for network-idle is not viable here as the site has continuous
    // background network activity (analytics, bot-challenge beacons) that never goes quiet.
    private void waitForResultsToRender() {
        page.waitForTimeout(500);
    }
}