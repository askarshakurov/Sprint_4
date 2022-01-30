import io.qameta.allure.AllureLifecycle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Assert;
import io.qameta.allure.Allure;


@RunWith(Parameterized.class)
public class NameValidationsTest {
    private final String name;
    private final boolean expectedResult;
    private final String responseMessage;
    AllureLifecycle lifecycle = Allure.getLifecycle();

    public NameValidationsTest(String name, boolean expectedResult , String responseMessage) {
        this.name = name;
        this.expectedResult = expectedResult;
        this.responseMessage = responseMessage;
    }
    @Parameterized.Parameters
    public static Object[][] checkNameAndExpectedResult() {
        return new Object[][]{
                {"Тимоти Шаламе", true, "Валидное дефолтное Имя+Фамилия"},
                {"Timoti Shalame", true, "Валидное Имя+Фамилия на английском"},
                {"Тт", false, "Нельзя указать строку без Фамилии и меньше 3х симвлоов"},
                {"Тимоти Ш124а1359ма135ле", false, "Строка содержит более 13 символов"},
                {"Т Т", true, "В строке можно указать 3 символа в Имя+разделение+Фамилия"},
                {"", false, "Нельзя указать пустое значение"},
                {"Тимоти Шаламе1111", false, "Нельзя указать цифры в строке"},
                {"Тимоти Шаламе!", false, "Нельзя указать символы в строке"},
                {"Тимоти Шал Аме", false, "Нельзя указать два пробела в Имя+Фамилия"},
                {"ТимотиШаламе", false, "Не указан пробел между Имя+Фамилия"},
                {" ТимотиШаламе", false, "Нельзя указать пробел в началае Имени+Фамилии"},
                {" Тимоти Шаламе", false, "Нельзя указать 2 пробела в валидных Имени+Фамилии"},
                {"ТимотиШаламе ", false, "Пробел не должен быть после Имени+Фамилии"},
                {"Тимоти  Шаламе", false, "Нельзя указать больше 1го пробела между Имя+Фамилия"},
        };
    }
    @Test
    public void nameValidationsTest() {
        lifecycle.updateTestCase(testResult -> testResult.setName(responseMessage));

        Account account = new Account(name);
        Assert.assertEquals("Результат " + expectedResult + "для значения" + name, expectedResult, account.checkNameToEmboss());

    }
}