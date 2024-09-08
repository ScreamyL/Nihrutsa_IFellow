import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class RunTest {

    @BeforeAll
    public static void beforeAll(){
        System.out.println("beforeALL");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("afterAlladin");
    }

    @Test
    public void test1(){
        System.out.println("1");
        Assertions.assertEquals("CocoJumbo", "CocoJumbo");
    }

    @Test
    public void test2(){
        System.out.println("2");
        Assertions.assertEquals("CocoJumbo","CoconutCrispies");
    }

}
