package ru.netology.cd;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;


class CardDeliveryTest {

    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldOrderTheCardDeliveryEverythingOk() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + dayToDelivery));
    }

    @Test
    void shouldOrderTheCardDeliverySectionOneWrongCity() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Елабуга");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionOneTheCityNameContainsLatinSymbols() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("London");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionOneTheCityFieldIsEmpty() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionOneTheCityIsRealButNameContainsNumbers() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Свердловск-45");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionOneTheCityFieldContainsNumbers() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("45");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionTwoNumberDaysLessThanThree() {
        String dayToDelivery = date(1);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionTwoNumberDaysEqualZero() {
        String dayToDelivery = date(0);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionTwoNumberDaysLessThanZero() {
        String dayToDelivery = date(-1);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionTwoTheDateFieldIsEmpty() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Неверно введена дата"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionThreeTheClientNameContainsLatinSymbols() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Ivan Smith");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldOrderTheCardDeliverySectionThreeTheClientNameContainsHyphen() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов-Задунайский Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + dayToDelivery));
    }

    @Test
    void shouldOrderTheCardDeliverySectionThreeTheClientNameContainsSpecialSymbols() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов@ Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldOrderTheCardDeliverySectionThreeTheNameFieldIsEmpty() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionFourThePhoneNumberContainsEightInsteadOfPlusSeven() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("89012345678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldOrderTheCardDeliverySectionFourThePhoneNumberContainsFewerCharactersThanNecessary() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+7901234567");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldOrderTheCardDeliverySectionFourThePhoneNumberContainsSpecialSymbols() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012@45678");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldOrderTheCardDeliverySectionFourThePhoneNumberFieldIsEmpty() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldOrderTheCardDeliverySectionFiveWithoutCheckbox() {
        String dayToDelivery = date(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, (dayToDelivery));
        $("[data-test-id='name'] input").setValue("Иванов Петр");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}