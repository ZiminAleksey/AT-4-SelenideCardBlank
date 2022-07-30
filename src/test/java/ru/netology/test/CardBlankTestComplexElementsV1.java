package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.SelectedText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardBlankTestComplexElementsV1 {

    @BeforeEach
    void open() {
        Selenide.open("http://localhost:9999/");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void selectionComplexElements() {
        $("[data-test-id=city] input").setValue("Ка");
        $$x("//div//span[@class='menu-item__control']").get(1).click();
        $("[data-test-id=date] .input__icon").click();
        LocalDate threeDays = LocalDate.now().plusDays(3);
        LocalDate inputWeek = LocalDate.now().plusDays(7);
        if (threeDays.getMonthValue() != inputWeek.getMonthValue()) {
            $("[data-step='1']").click();
        }
        $$("tr td").findBy(text(String.valueOf(inputWeek.getDayOfMonth()))).click();
        $("[data-test-id=name] .input__control").setValue("Андрей Сан-Мартин");
        $("[data-test-id=phone] .input__control").setValue("+79991234564");
        $("[data-test-id=agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(7)));
    }
}
