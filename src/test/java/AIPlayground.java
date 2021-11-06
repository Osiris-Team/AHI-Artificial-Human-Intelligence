import com.osiris.ahi.organs.brain.Brain;
import com.osiris.ahi.organs.brain.Neuron;
import com.osiris.ahi.organs.brain.Signal;
import com.osiris.ahi.organs.brain.Worker;
import com.osiris.ahi.utils.UtilsTimeStopper;
import org.junit.jupiter.api.Test;

public class AIPlayground {

    @Test
    void test() throws Exception {
        // Utils
        UtilsTimeStopper timeStopper = new UtilsTimeStopper();

        // Create brain based on human biology:
        // 100 billion neurons | We use 0,1% which are 1 million neurons
        // 10000 connections per neuron | We use 0,1% which are 10 connections
        System.out.println("Creating brain...");
        int neuronsAmount = 1000000;
        int workersAmount = 5;
        timeStopper.start();
        Brain brain = new Brain(neuronsAmount, workersAmount);
        // 100 billion neurons are too much for memory. Save them to a file.
        // Wie sollte diese datei aussehen?
        // Welche sind die zeichen die am wenigsten platz verschwenden?
        timeStopper.stop();
        System.out.println("Created brain in " + timeStopper.getSeconds() + " seconds!");
    }
}
