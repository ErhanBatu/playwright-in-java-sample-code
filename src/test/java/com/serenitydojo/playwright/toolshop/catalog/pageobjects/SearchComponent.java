package com.serenitydojo.playwright.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchComponent {
    private final Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void searchBy(String keyword) {
        page.waitForResponse(response ->
                response.url().endsWith("/products/search")
                        && "QUERY".equalsIgnoreCase(
                        response.request().method()
                )
                        && response.status() == 200,
                () -> {
            page.getByPlaceholder("Search").fill(keyword);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
    }

    public void clearSearch() {
        page.waitForResponse("**/products**", () -> {
            page.getByTestId("search-reset").click();
        });
    }

    public void filterBy(String filterName) {

        page.waitForResponse(
                response ->
                        response.url().endsWith("/products")
                                && "QUERY".equalsIgnoreCase(
                                response.request().method()
                        )
                                && response.status() == 200,
                () -> page.getByLabel(filterName).check()
        );
    }

    public void sortBy(String sortFilter) {
        page.waitForResponse(response ->
                response.url().endsWith("/products")
                        && "QUERY".equalsIgnoreCase(
                        response.request().method()
                )
                        && response.status() == 200, () -> {
            page.getByTestId("sort").selectOption(sortFilter);
        });
    }
}