package ch.uzh.ciclassifier.features.configuration;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfNotificationsTest {

    @Test
    public void disabledTest() throws Exception {
        String configuration = "notifications: false";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfNotifications feature = new NumberOfNotifications();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(0), feature.getSize());
    }

    @Test
    public void simpleEmailTest() throws Exception {
        String configuration = "notifications: true";
        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfNotifications feature = new NumberOfNotifications();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }

    @Test
    public void twoEmailTest() throws Exception {
        String configuration = "notifications:\n" +
                "  email:\n" +
                "  - recipients:\n" +
                "    - secure: encrypted string\n" +
                "    if: branch = master\n" +
                "    on_success: always\n" +
                "  - recipients:\n" +
                "      secure: encrypted string";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfNotifications feature = new NumberOfNotifications();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(2), feature.getSize());
    }

    @Test
    public void mixedTest() throws Exception {
        String configuration = "notifications:\n" +
                "  campfire: true\n" +
                "  email: false\n";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfNotifications feature = new NumberOfNotifications();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(1), feature.getSize());
    }

    @Test
    public void complexTest() throws Exception {
        String configuration = "notifications:\n" +
                "  campfire:\n" +
                "  - rooms:\n" +
                "    - secure: encrypted string\n" +
                "  - rooms:\n" +
                "      secure: encrypted string\n" +
                "  email:\n" +
                "  - recipients:\n" +
                "    - secure: encrypted string\n" +
                "    if: branch = master\n" +
                "    on_success: always\n" +
                "  - recipients:\n" +
                "      secure: encrypted string\n" +
                "  flowdock:\n" +
                "  - api_token:\n" +
                "      secure: encrypted string\n" +
                "    template:\n" +
                "    - string\n" +
                "    if: branch = master\n" +
                "  hipchat: true\n" +
                "  pushover: true\n" +
                "  slack:\n" +
                "  - channel1\n" +
                "  - channel2\n" +
                "  webhocks:\n" +
                "  - https://google.com\n" +
                "  - https://github.com\n";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);

        NumberOfNotifications feature = new NumberOfNotifications();
        feature.extract(evaluation);

        assertEquals(Integer.valueOf(11), feature.getSize());
    }
}