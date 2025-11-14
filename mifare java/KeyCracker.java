
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KeyCracker {

    public static String crack(String target) {

        List<String> keys = Arrays.asList(
                "000000000000",
                "A0A1A2A3A4A5",
                "FFFFFFFFFFFF",
                "010203040506",
                "112233445566",
                "1234567890AB",
                target     // correct key shuffled
        );

        Collections.shuffle(keys);

        StringBuilder log = new StringBuilder();
        log.append("🔐 Brute Force Log:\n\n");

        for (String k : keys) {
            log.append("Trying key: ").append(k).append("\n");
            if (k.equals(target)) {
                log.append("\n✅ Cracked Key: ").append(k).append("\n");
                break;
            }
        }

        return log.toString();
    }
}
