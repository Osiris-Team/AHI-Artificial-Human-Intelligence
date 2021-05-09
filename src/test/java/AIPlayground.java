import com.osiris.ai.*;
import com.osiris.ai.utils.UtilsTimeStopper;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.util.List;

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
        System.out.println("Created brain in "+timeStopper.getSeconds()+" seconds!");

        for (Worker w :
                brain.getWorkers()) {
            w.actionsOnSignalDeathEvent.add(e -> {
                System.out.println(e.getTimestamp().toString()+" Signal death! Total connections: "+w.getTotalCountSynapses());
            });
        }

        for (Worker w :
                brain.getWorkers()) {
            w.executeRunnableAndReturnItsThread(() -> {
                Neuron n = w.getNeurons()[w.getStartIndex()];
                n.fireSignal(new Signal(), n.getStrongestSynapse());
                System.out.println("Fired Signal at index "+w.getStartIndex()+" Total Synapses: "+w.getTotalCountSynapses());
            });
        }
    }
}
