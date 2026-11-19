import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LibrarySearch extends Frame {
    Label title = new Label("Campus Library Search", Label.CENTER);
    TextField searchBox = new TextField(20);
    Choice category = new Choice();
    Button search = new Button("Search");
    TextArea results = new TextArea();
    Button prev = new Button("Prev");
    Button next = new Button("Next");
    Button reserve = new Button("Reserve");
    Label status = new Label("Ready");

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String word = searchBox.getText();
            if (word.trim().equals("")) {
                status.setText("Type something to search");
            } else {
                results.setText("Results for " + word + " in " + category.getSelectedItem()
                        + "\n1. Java Programming\n2. Data Structures\n3. Database Systems");
                status.setText("3 books found");
            }
        }
    }

    class PrevListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            status.setText("Previous page");
        }
    }

    class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            status.setText("Next page");
        }
    }

    class ReserveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            status.setText("Book reserved");
        }
    }

    public LibrarySearch() {
        super("Library");
        category.add("All");
        category.add("Title");
        category.add("Author");
        category.add("Subject");

        Panel searchRow = new Panel(new FlowLayout());
        searchRow.add(searchBox);
        searchRow.add(category);
        searchRow.add(search);

        Panel top = new Panel(new BorderLayout());
        top.add(title, BorderLayout.NORTH);
        top.add(searchRow, BorderLayout.CENTER);

        Panel buttonRow = new Panel(new FlowLayout());
        buttonRow.add(prev);
        buttonRow.add(next);
        buttonRow.add(reserve);

        Panel bottom = new Panel(new BorderLayout());
        bottom.add(buttonRow, BorderLayout.CENTER);
        bottom.add(status, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(results, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        search.addActionListener(new SearchListener());
        prev.addActionListener(new PrevListener());
        next.addActionListener(new NextListener());
        reserve.addActionListener(new ReserveListener());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(500, 400);
    }

    public static void main(String[] args) {
        new LibrarySearch().setVisible(true);
    }
}
