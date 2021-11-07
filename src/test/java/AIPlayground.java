import com.osiris.ahi.Human;
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

        System.out.println("Creating new human...");
        timeStopper.start();
        Human peter = new Human();
        timeStopper.stop();
        System.out.println("Created human in " + timeStopper.getSeconds() + " seconds or "+timeStopper.getMillis()+"milliseconds!");
        Thread.sleep(30000);
    }
}
