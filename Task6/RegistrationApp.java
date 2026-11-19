import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class RegistrationApp extends JFrame {
    String[] events = {"Singing", "Dance", "Drama", "Quiz"};
    RegistrationService service = new RegistrationService();

    JTextField regNo = new JTextField(10);
    JTextField name = new JTextField(10);
    JComboBox<String> eventBox = new JComboBox<String>(events);
    JButton registerButton = new JButton("Register");

    JComboBox<String> filterBox = new JComboBox<String>(
            new String[]{"All", "Singing", "Dance", "Drama", "Quiz"});
    JButton filterButton = new JButton("Filter");

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Register No", "Name", "Event"}, 0);
    JTable table = new JTable(model);
    JLabel status = new JLabel("Ready");

    class LoadWorker extends SwingWorker<ArrayList<Registration>, Void> {
        String eventName;

        LoadWorker(String eventName) {
            this.eventName = eventName;
        }

        protected ArrayList<Registration> doInBackground() throws Exception {
            return service.list(eventName);
        }

        protected void done() {
            try {
                ArrayList<Registration> list = get();
                model.setRowCount(0);
                for (int i = 0; i < list.size(); i++) {
                    Registration r = list.get(i);
                    model.addRow(new Object[]{r.regNo, r.name, r.eventName});
                }
                status.setText(list.size() + " registrations shown");
            } catch (Exception e) {
                status.setText("Could not load the data");
            }
        }
    }

    class RegisterWorker extends SwingWorker<String, Void> {
        String reg;
        String nm;
        String ev;

        RegisterWorker(String reg, String nm, String ev) {
            this.reg = reg;
            this.nm = nm;
            this.ev = ev;
        }

        protected String doInBackground() {
            return service.register(reg, nm, ev);
        }

        protected void done() {
            try {
                String message = get();
                status.setText(message);
                JOptionPane.showMessageDialog(RegistrationApp.this, message);
                registerButton.setEnabled(true);
                new LoadWorker(filterBox.getSelectedItem().toString()).execute();
            } catch (Exception e) {
                status.setText("Registration failed");
                registerButton.setEnabled(true);
            }
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            registerButton.setEnabled(false);
            status.setText("Saving...");
            new RegisterWorker(regNo.getText(), name.getText(),
                    eventBox.getSelectedItem().toString()).execute();
        }
    }

    class FilterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            status.setText("Loading...");
            new LoadWorker(filterBox.getSelectedItem().toString()).execute();
        }
    }

    class ExportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter fw = new FileWriter("registrations.txt");
                for (int i = 0; i < model.getRowCount(); i++) {
                    fw.write(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1) + ","
                            + model.getValueAt(i, 2) + "\n");
                }
                fw.close();
                status.setText("Exported to registrations.txt");
            } catch (IOException ex) {
                status.setText("Export failed");
            }
        }
    }

    class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            askAndClose();
        }
    }

    void askAndClose() {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to close?",
                "Exit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public RegistrationApp() {
        super("Event Registration");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem = new JMenuItem("Exit");
        exportItem.addActionListener(new ExportListener());
        exitItem.addActionListener(new ExitListener());
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Reg No"));
        form.add(regNo);
        form.add(new JLabel("Name"));
        form.add(name);
        form.add(eventBox);
        form.add(registerButton);

        JPanel filterRow = new JPanel(new FlowLayout());
        filterRow.add(new JLabel("Show"));
        filterRow.add(filterBox);
        filterRow.add(filterButton);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.NORTH);
        top.add(filterRow, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        registerButton.addActionListener(new RegisterListener());
        filterButton.addActionListener(new FilterListener());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                askAndClose();
            }
        });

        setSize(700, 420);
        new LoadWorker("All").execute();
    }

    public static void main(String[] args) {
        new RegistrationApp().setVisible(true);
    }
}
