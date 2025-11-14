import java.io.*;
import java.util.*;

public class CardLoader {

    public static List<String> loadDump() {
        List<String> dump = new ArrayList<>();

        try {
            File f = new File("dump.mfd");

            if (!f.exists()) {
                CardGenerator.generateCard();
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                dump.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dump;
    }
}
