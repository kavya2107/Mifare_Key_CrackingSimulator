import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CardViewer extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private java.util.List<String> cardDump = new ArrayList<>();

    public CardViewer() {
        setTitle("MIFARE Classic Key Cracking Simulator");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Sector", "Key A", "Data", "Key B"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton genBtn = new JButton("Generate Fake Card");
        JButton loadBtn = new JButton("Load Card Dump");
        JButton crackBtn = new JButton("Crack Key");

        panel.add(genBtn);
        panel.add(loadBtn);
        panel.add(crackBtn);

        add(panel, BorderLayout.SOUTH);

        genBtn.addActionListener(e -> {
            CardGenerator.generateCard();
            JOptionPane.showMessageDialog(this, "Fake card generated: dump.mfd");
        });

        loadBtn.addActionListener(e -> loadCard());
        crackBtn.addActionListener(e -> crackKey());

        setVisible(true);
    }

    private void loadCard() {
        try {
            cardDump = CardLoader.loadDump();
            model.setRowCount(0);
            int sector = 0;
            for (String line : cardDump) {
                String keyA = line.substring(0, 12);
                String data = line.substring(12, 44);
                String keyB = line.substring(44);
                model.addRow(new Object[]{sector, keyA, data, keyB});
                sector++;
            }
            JOptionPane.showMessageDialog(this, "Dump loaded successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading dump: " + ex.getMessage());
        }
    }

    private void crackKey() {
        if (cardDump == null || cardDump.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Load dump first!");
            return;
        }
        String sec = JOptionPane.showInputDialog("Sector (0-5):");
        if (sec == null) return;
        int sector;
        try {
            sector = Integer.parseInt(sec);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid sector number.");
            return;
        }
        if (sector < 0 || sector >= cardDump.size()) {
            JOptionPane.showMessageDialog(this, "Sector out of range.");
            return;
        }
        String type = JOptionPane.showInputDialog("Key A or B?");
        if (type == null) return;
        type = type.toUpperCase();
        String line = cardDump.get(sector);
        String targetKey = type.equals("A") ? line.substring(0, 12) : line.substring(44);
        String log = KeyCracker.crack(targetKey);
        JTextArea text = new JTextArea(log);
        text.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(text),
                "Brute Force Simulation", JOptionPane.INFORMATION_MESSAGE);
    }
}
