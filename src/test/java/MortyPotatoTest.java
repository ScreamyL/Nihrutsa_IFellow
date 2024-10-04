import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.MortySteps;
import steps.PotatoSteps;

import static utils.Props.props;


public class MortyPotatoTest {

    private final MortySteps mortySteps = new MortySteps(props.morty_uri());
    private final PotatoSteps potatoSteps = new PotatoSteps(props.potato_uri());


    @Test
    @DisplayName("Тест переименования картошки")
    public void mortyTest() {
        potatoSteps.performPotatoTest();
    }

    @Test
    @DisplayName("Проверка данных последнего персонажа, появлявшегося с Морти")
    public void potatoTest() {
        mortySteps.testLastCharacterWithMorty();
    }

}
