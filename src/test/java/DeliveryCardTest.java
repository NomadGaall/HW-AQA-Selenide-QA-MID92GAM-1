import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private String dateGenerate(int addDays, String dateFormat) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(dateFormat));
    }

    @Test
    public void cardSuccessfullyBooked() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("УФА");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(dateGenerate(5, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79012345577");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!\n" + "Встреча успешно забронирована на " + dateGenerate(5, "dd.MM.yyyy"))).shouldBe(Condition.visible);
    }
}
