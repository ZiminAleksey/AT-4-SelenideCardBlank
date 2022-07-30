package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardBlankTestV1 {


    @BeforeEach
    void open() {
        Selenide.open("http://localhost:9999/");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void completedBlank() {
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] .input__control").click();
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(generateDate(7));
        $("[data-test-id=name] .input__control").setValue("Андрей Сан-Мартин");
        $("[data-test-id=phone] .input__control").setValue("+79991234564");
        $("[data-test-id=agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(7)));
    }
}
