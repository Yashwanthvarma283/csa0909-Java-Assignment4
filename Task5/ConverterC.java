import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConverterC extends JFrame {
    JTextField amount = new JTextField(10);
    JComboBox<String> target = new JComboBox<String>(new String[]{"USD", "EUR"});
    JLabel result = new JLabel(" ");
    JButton convertButton = new JButton("Convert");

    public ConverterC() {
        super("Converter C");
        setLayout(new FlowLayout());
        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(convertButton);
        add(result);

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double value = Double.parseDouble(amount.getText());
                    double rate = 0.011;
                    if (target.getSelectedItem().equals("USD")) {
                        rate = 0.012;
                    }
                    result.setText(String.format("%.2f", value * rate));
                } catch (NumberFormatException ex) {
                    result.setText("Invalid amount");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        pack();
    }

    public static void main(String[] args) {
        new ConverterC().setVisible(true);
    }
}
