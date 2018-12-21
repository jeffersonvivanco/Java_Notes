package programming;

import models.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("JUnit Tutorial")
class JUnitTutorial {

    @Test
    @DisplayName("Standard Assertions")
    void standardAssertions(){
        assertEquals(2, 1+1, "Optional Message");
        assertTrue('a' < 'b', () -> "This should be true");
    }

    @Test
    @DisplayName("Grouped Assertions")
    void groupedAssertions(){
        Person p = new Person();
        p.setName("Jeff");
        p.setAge(24);
        assertAll("person", () -> assertEquals("Jeff", p.getName()),
                () -> assertEquals(24, p.getAge().intValue()));
    }

    @Test
    @DisplayName("Dependent Assertions")
    void dependentAssertions(){
        assertAll("properties",
                () -> {
                    Person zen = new Person();
                    zen.setName("Zen");
                    zen.setAge(20);
                    assertNotNull(zen.getName());
                    // executed only if the previous assertion is valid
                    assertAll("first name",
                            () -> assertEquals("Zen", zen.getName()));
                },
                () -> {
                    // Grouped assertion, so processed independently
                    Person jeff = new Person();
                    jeff.setAge(24);
                    jeff.setName("Jeff");
                    assertNotNull(jeff.getName());

                });
    }

    @Test
    @DisplayName("Exception Testing")
    void exceptionTesting(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("A message");
        });
        assertEquals("A message", exception.getMessage());
    }

    @Nested
    @DisplayName("Nested Test Class")
    class NestedTest {
        @Test
        @DisplayName("Testing NestedTest Class")
        void test(){
            assertEquals("Hello", "Hello");
        }
    }

    @RepeatedTest(value = 2, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeated Tests")
    void repeatedTesting(){
        int n = 1;
        assertEquals(2, n + 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    @DisplayName("Parameterized Tests")
    void parameterizedTesting(String candidate){
        assertTrue(isPalindrome(candidate));
    }

    boolean isPalindrome(String candidate){
        // Todo: write method
        return true;
    }

    @TestFactory
    @DisplayName("Dynamic Test with Exception")
    List<String> dynamicTestWithInvalidReturnType(){
        return Arrays.asList("Hello");
    }

    @TestFactory
    @DisplayName("Dynamic Test with Collection")
    Collection<DynamicTest> dynamicTestFromCollection(){
        return Arrays.asList(dynamicTest("1st dynamic test", () -> assertEquals(2, 1 + 1)),
                dynamicTest("2nd dynamic test", () -> assertTrue(true)));
    }

    @TestFactory
    Stream<DynamicTest> generateRandomNumberOfTests(){
        // Generates random positive integers between 0 and 100 until
        // a number evenly divisible by 7 is encountered.
        Iterator<Integer> inputGenerator = new Iterator<>() {

            Random random = new Random();
            int current;

            @Override
            public boolean hasNext() {
                current = random.nextInt(100);
                return current % 7 != 0;
            }

            @Override
            public Integer next() {
                return current;
            }
        };

        // Generates display names like: input:5, input:37, input:85, etc.
        Function<Integer, String> displayNameGenerator = (input) -> "input:" + input;

        // Executes tests based on the current input value.
        ThrowingConsumer<Integer> testExecutor = (input) -> assertTrue(input % 7 != 0);

        // Returns a stream of dynamic tests.
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }


}
