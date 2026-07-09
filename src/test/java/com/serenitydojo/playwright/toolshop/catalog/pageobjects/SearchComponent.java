package com.serenitydojo.playwright.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchComponent {
    private final Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void searchBy(String keyword) {
        // The search request is now sent as an HTTP QUERY request with the
        // keyword in the JSON body (e.g. {"q":"tape"}) rather than as a
        // "?q=" query string on the URL, so we match on the request body
        // instead of the URL suffix.
        page.waitForResponse(
                response -> response.url().endsWith("/products/search")
                        && response.request().postData() != null
                        && response.request().postData().contains("\"" + keyword + "\""),
                () -> {
                    page.getByPlaceholder("Search").fill(keyword);
                    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
                });
    }

    public void clearSearch() {
        // Match only the product listing API call, not the "/products/search"
        // call or the "/assets/img/products/..." image requests that also
        // contain "products" in their URL.
        page.waitForResponse(
                response -> response.url().endsWith("/products") && !response.url().contains("/assets/"),
                () -> page.getByTestId("search-reset").click());
    }
}