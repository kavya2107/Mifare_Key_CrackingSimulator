import java.io.PrintWriter;
import java.util.Random;

public class CardGenerator {

    // Generate random HEX string
    public static String randomHex(int length) {
        String hex = "0123456789ABCDEF";
        Random r = new Random();
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < length; i++) {
            b.append(hex.charAt(r.nextInt(hex.length())));
        }
        return b.toString();
    }

    // Create fake dump file
    public static void generateCard() {
        try {
            PrintWriter writer = new PrintWriter("dump.mfd");

            for (int i = 0; i < 6; i++) {
                String keyA = randomHex(12);
                String data = randomHex(32);
                String keyB = randomHex(12);

                writer.println(keyA + data + keyB);
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
