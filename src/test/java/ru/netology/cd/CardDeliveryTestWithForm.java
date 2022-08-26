package ru.netology.cd;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTestWithForm {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldOrderTheCardDeliveryEverythingOkDeliveryThroughSevenDays() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='city'] input").setValue("ка");
        $x("//*[contains(text(),'Казань')]").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.className("input__icon")).click();
        $x("//td[@data-day]//following-sibling::td/following-sibling::td").click();
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно забронирована на "
                        + $(".calendar__day_state_current").getText()));
    }

    @Test
    void shouldOrderTheCardDeliveryEverythingOkDeliveryToNextMonth() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='city'] input").setValue("ка");
        $x("//*[contains(text(),'Калининград')]").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.className("input__icon")).click();
        $(By.cssSelector("[data-step=\"1\"]")).click();
        $("[data-day]").click();
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно забронирована на "
                        + $(".calendar__day_state_current").getText()));
    }

}


