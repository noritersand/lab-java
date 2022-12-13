package jdk.statement;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java unlimited method arguments test case
 *
 * @author fixalot
 * @since 2018-04-03
 */
public class MethodArgumentsTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodArgumentsTest.class);

    public static void testMe(int... args) {
        for (int ele : args) {
            logger.debug("{}", ele);
        }
    }

    @Test
    public void test() {
        MethodArgumentsTest.testMe(1, 2, 3, 4, 5);
    }
}
