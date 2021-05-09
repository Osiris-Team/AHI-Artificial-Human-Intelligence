import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NeuronsFile {

    @Test
    void testReadAndWrite() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/src/test/test.neurons");
        if (file.exists()) file.delete();
        file.createNewFile();
        for (byte b :
                System.lineSeparator().getBytes()) {
            System.out.println("Line separator in bytes:" + b);
        }

        try (OutputStream out = new FileOutputStream(file)) {
            // Idea is that each line represents one neuron
            // and the line contains the other neurons it is connected to

        }
    }
}
