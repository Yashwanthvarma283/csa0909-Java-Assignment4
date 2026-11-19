import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPadWrong extends Frame {
    public DrawPadWrong() {
        addMouseListener(new MouseAdapter() {
            @Override public void mouseclicked(MouseEvent e) {
                System.out.println("Clicked");
            }
        });
        setSize(400, 300);
    }

    public static void main(String[] args) {
        new DrawPadWrong().setVisible(true);
    }
}
