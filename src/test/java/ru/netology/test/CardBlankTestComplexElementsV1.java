package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.SelectedText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardBlankTestComplexElementsV1 {

    @BeforeEach
    void open() {
        Selenide.open("http://localhost:9999/");
    }


    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, +7);
        return dateFormat.format(calendar.getTime());
    }

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        Calendar calendar = new GregorianCalendar();
        return dateFormat.format(calendar.getTime());
    }

    public String getDayWeekAdvance() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, +7);
        return dateFormat.format(calendar.getTime());
    }

    public int getDayOnMonth() {
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    @Test
    void selectionComplexElements() {
        $("[data-test-id=city] input").setValue("Ка");
        $$x("//div//span[@class='menu-item__control']").get(1).click();
        $("[data-test-id=date] .input__icon").click();
        int count = $$x("//tr//td").size();
        int endOfTheMonthWorWeek = getDayOnMonth() - 6;
        int borderIn3Days = getDayOnMonth() - 2;
        String day = getDayWeekAdvance();
        if ((Integer.parseInt(getCurrentDate())) >= endOfTheMonthWorWeek && (Integer.parseInt(getCurrentDate()) < borderIn3Days)) {
            $x("//div[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        for (int i = 0; i < count; i++) {
            String text = $$x("//tr//td").get(i).getText();
            if (text.equalsIgnoreCase(getDayWeekAdvance())) {
                $$x("//tr//td[contains(@class, 'weekend')] | //tr/td[@data-day] | //tr//td[contains(@class, 'off')]").get(Integer.parseInt(day) - 1).click();
            }
        }
        $("[data-test-id=name] .input__control").setValue("Андрей Сан-Мартин");
        $("[data-test-id=phone] .input__control").setValue("+79991234564");
        $("[data-test-id=agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + getDate()));
    }
}
