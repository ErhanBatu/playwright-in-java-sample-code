package com.serenitydojo.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightFormsTest {

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {

        @BeforeEach
        void openContactPage(Page page) {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Complete the form")
        @Test
        void completeForm(Page page) throws URISyntaxException {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailNameField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var uploadField = page.getByLabel("Attachment");

            firstNameField.fill("Erhan");
            lastNameField.fill("Batu");
            emailNameField.fill("erbatu@gmail.com");
            messageField.fill("Hello World!");
            subjectField.selectOption("Warranty");

            Path fileToUpload = Path.of(
                    ClassLoader.getSystemResource("data/sample-data.txt").toURI()
            );

            page.setInputFiles("#attachment", fileToUpload);


            assertThat(firstNameField).hasValue("Erhan");
            assertThat(lastNameField).hasValue("Batu");
            assertThat(subjectField).hasValue("warranty");

            String uploadFile = uploadField.inputValue();
            org.assertj.core.api.Assertions.assertThat(uploadFile).endsWith("sample-data.txt");



        }

        @DisplayName("Mandatory Fields")
        @ParameterizedTest
        @ValueSource(strings = {"First name", "Last name"})
        void mandatoryFields(String fieldName, Page page){
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailNameField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var sendButton = page.getByText("Send");

            firstNameField.fill("Erhan");
            lastNameField.fill("Batu");
            emailNameField.fill("erbatu@gmail.com");
            messageField.fill("Hello World!");
            subjectField.selectOption("Warranty");

            page.getByLabel(fieldName).clear();

            sendButton.click();

            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

            assertThat(errorMessage).isVisible();


        }
    }







}
