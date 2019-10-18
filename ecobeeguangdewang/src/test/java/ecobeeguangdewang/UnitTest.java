package ecobeeguangdewang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author guangdewang
 */
public class UnitTest {

    private static Logger logger = Logger.getLogger(UnitTest.class.getName());

    private String input = "\"John Doe\" \"Canada/Ontario/Toronto\" 1.5\n" +
        "\"Samanta Smith\" \"Canada/Ontario/London\" 3.7\n" +
        "\"Adam Xin\" \"Canada/British Columbia/Vancouver\" 2.110\n" +
        "\"Monica Taylor\" \"Canada/Ontario/Toronto\" 2.110\n" +
        "\"Alicia Yazzie\" \"US/Arizona/Phoenix\" 5.532\n" +
        "\"Mohammed Zadeh\" \"Canada/Ontario/Toronto\" 1.43\n" +
        "\n" +
        "\"John Doe\" \"Canada\"\n" +
        "\"John Doe\" \"Canada/Ontario\"\n" +
        "\"Alicia Yazzie\" \"US/Arizona\"";

    private String answers = "\"John Doe\" \"Canada\" 4\n" +
        "\"John Doe\" \"Canada/Ontario\" 5\n" +
        "\"Alicia Yazzie\" \"US/Arizona\" 10";

    public UnitTest() {
    }

    @Test
    public void test() {
        try {
            InputStream in = new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8));
            //let stdin read from input string
            System.setIn(in);

            ByteArrayOutputStream results = new ByteArrayOutputStream();
            //let stdout write to results string
            System.setOut(new PrintStream(results, true, "UTF-8"));

            //run OwnerRating class
            OwnerRating.main(null);

            in.read(); //read from input string
            in.close();

            results.flush();
            //get the output results
            String resultsString = results.toString("UTF-8");

            logger.log(Level.INFO, "== expected answers ===:\n" + answers);
            logger.log(Level.INFO, "== actual results =====:\n" + resultsString);

            assertEquals(resultsString.trim(), answers.trim());

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "error doing test:", ex);
        }
    }
}
