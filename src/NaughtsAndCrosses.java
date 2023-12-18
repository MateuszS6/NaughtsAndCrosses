import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NaughtsAndCrosses implements ActionListener {
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel label;
    private JButton[] squares;
    private JButton clearButton;
    private JButton quitButton;
    private Color squareColour;
    private boolean xTurn;

    public NaughtsAndCrosses() {
        JFrame frame = new JFrame("Naughts and Crosses");
        frame.setIconImage(new ImageIcon("D:\\MY STUFF\\Saved Pictures\\Icons\\My Programs\\NaughtsAndCrosses.png").getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 680);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(mainPanel);

        clearButton.addActionListener(e -> restart());
        clearButton.setEnabled(false);
        quitButton.addActionListener(e -> System.exit(0));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        firstTurn();
    }

    private void createUIComponents() {
        gamePanel = new JPanel(new GridLayout(3, 3));
        squares = new JButton[9];
        squareColour = new Color(0xF2EECB);
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new JButton();
            squares[i].setBackground(squareColour);
            squares[i].setFont(new Font("MV Boli", Font.BOLD, 80));
            squares[i].setFocusable(false);
            squares[i].addActionListener(this);
            gamePanel.add(squares[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton square : squares)
            if (e.getSource() == square) if (square.getText().isEmpty()) {
                if (xTurn) {
                    square.setText("X");
                    square.setForeground(Color.RED);
                    xTurn = false;
                } else {
                    square.setText("O");
                    square.setForeground(Color.BLUE);
                    xTurn = true;
                }
                updateTextField();
                check("X");
                check("O");
                if (!clearButton.isEnabled()) clearButton.setEnabled(true);
            }
    }

    public void updateTextField() {
        if (xTurn) label.setText("X turn");
        else label.setText("O turn");
    }

    public void firstTurn() {
        xTurn = new Random().nextInt(2) == 0;
        updateTextField();
    }

    public void check(String player) {
        if (
                (squares[0].getText().equals(player)) &&
                (squares[1].getText().equals(player)) &&
                (squares[2].getText().equals(player))
        ) win(player, 0, 1, 2);
        else if (
                (squares[3].getText().equals(player)) &&
                (squares[4].getText().equals(player)) &&
                (squares[5].getText().equals(player))
        ) win(player, 3, 4, 5);
        else if (
                (squares[6].getText().equals(player)) &&
                (squares[7].getText().equals(player)) &&
                (squares[8].getText().equals(player))
        ) win(player, 6, 7, 8);
        else if (
                (squares[0].getText().equals(player)) &&
                (squares[3].getText().equals(player)) &&
                (squares[6].getText().equals(player))
        ) win(player, 0, 3, 6);
        else if (
                (squares[1].getText().equals(player)) &&
                (squares[4].getText().equals(player)) &&
                (squares[7].getText().equals(player))
        ) win(player, 1, 4, 7);
        else if (
                (squares[2].getText().equals(player)) &&
                (squares[5].getText().equals(player)) &&
                (squares[8].getText().equals(player))
        ) win(player, 2, 5, 8);
        else if (
                (squares[0].getText().equals(player)) &&
                (squares[4].getText().equals(player)) &&
                (squares[8].getText().equals(player))
        ) win(player, 0, 4, 8);
        else if (
                (squares[2].getText().equals(player)) &&
                (squares[4].getText().equals(player)) &&
                (squares[6].getText().equals(player))
        ) win(player, 2, 4, 6);
        else if (isFull()) draw();
    }

    public boolean isFull() {
        for (JButton square : squares) if (square.getText().isEmpty()) return false;
        return true;
    }

    public void win(String winner, int a, int b, int c) {
        Color colour = new Color(0xD7CF8D);
        squares[a].setBackground(colour);
        squares[b].setBackground(colour);
        squares[c].setBackground(colour);
        for (JButton square : squares) square.setEnabled(false);
        label.setText(winner + " wins!");
    }

    public void draw() {
        for (JButton button : squares) button.setEnabled(false);
        label.setText("Draw");
    }

    public void restart() {
        for (JButton square : squares) {
            square.setBackground(squareColour);
            square.setText("");
            square.setEnabled(true);
        }
        clearButton.setEnabled(false);
        firstTurn();
    }
}
