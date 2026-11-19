import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FeeWindow extends Frame {
    TextField regNo = new TextField(15);
    TextField amount = new TextField(15);
    Button pay = new Button("Pay");
    Button clear = new Button("Clear");
    Button receipt = new Button("Receipt");
    Label message = new Label("Enter the details and press Pay");
    FeeService service = new FeeService();

    class PayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = service.pay(regNo.getText(), amount.getText());
            message.setText(result);
        }
    }

    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            regNo.setText("");
            amount.setText("");
            message.setText("Cleared");
        }
    }

    class ReceiptListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            message.setText(service.lastReceipt);
        }
    }

    public FeeWindow() {
        super("Fee Payment");
        Panel form = new Panel(new GridLayout(2, 2, 5, 5));
        form.add(new Label("Register No:"));
        form.add(regNo);
        form.add(new Label("Amount:"));
        form.add(amount);

        Panel buttons = new Panel(new FlowLayout());
        buttons.add(pay);
        buttons.add(clear);
        buttons.add(receipt);

        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
        add(message, BorderLayout.SOUTH);

        pay.addActionListener(new PayListener());
        clear.addActionListener(new ClearListener());
        receipt.addActionListener(new ReceiptListener());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(350, 180);
    }

    public static void main(String[] args) {
        new FeeWindow().setVisible(true);
    }
}
