import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
//import java.util.Scanner;

public class Game extends JFrame {
    private int guess;
    private static int number;
    private int capLimit = 10;
    private int tryNumber = 1;
    private Container contentPane = getContentPane();
    private JPanel mainPanel = new JPanel();
    private JTextField guessInput = new JTextField();
    private JLabel message = new JLabel("");
    private JLabel answer = new JLabel("Answer");
    private JButton enter = new JButton("Enter");
    private JButton restart = new JButton("Restart");
    private Random random = new Random();

//    private Scanner scan = new Scanner(System.in);

    public Game() throws IOException {
        addWindowListener(new Terminator());
        setTitle("My Guessing Game!");
        setSize(600, 300); // default size is 0,0
        setLocation(10, 200); // default is 0,0 (top left corner)
        setResizable(false);

        //Create JPanels
        mainPanel.setBackground(Color.lightGray);

        //title image
        BufferedImage myPicture = ImageIO.read(new File("/Users/codygulley/IdeaProjects/GuessingGame/src/guess-the-number-game.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        mainPanel.add(picLabel);
        picLabel.setSize(picLabel.getPreferredSize());

        //Create JLabels
        JLabel guessLabel = new JLabel("Guess:");
        guessLabel.setSize(guessLabel.getPreferredSize());
        guessLabel.setLocation(50, 150);

        //Input box for guess
        guessInput.setLocation(100, 150);
        guessInput.setSize(new Dimension(100, 25));

        //enter button
        enter.setLocation(200, 140);
        enter.setSize(100, 50);
        enter.addActionListener(new EnterButtonClicked());

        //restart button (hidden until end of game)
        restart.setLocation(350, 140);
        restart.setSize(100, 50);
        restart.addActionListener(new RestartButtonClicked());


        //message label
        message.setText("<html>LET'S PLAY A GAME!<br>TRY TO GUESS THE NUMBER!</html>");
        message.setSize(new Dimension(200, 350));
        message.setLocation(50, 50);

        //answer jlabel (hidden unless you run out of tries
        answer.setSize(new Dimension(300, 350));
        answer.setLocation(50, 150);

        // Add Panel to ContentPane
        GroupLayout gl = new GroupLayout(mainPanel);
        mainPanel.setLayout(gl);
        contentPane.add(mainPanel);

        //add elements to JPanel
        mainPanel.add(guessLabel);
        mainPanel.add(guessInput);
        mainPanel.add(enter);
        mainPanel.add(message);
        mainPanel.add(restart);
        mainPanel.repaint();


        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                guessInput.requestFocus();
                restart.hide();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        Game app = new Game();
        JFrame f = new Game();
        f.show();
        app.run();
    }

    public void run() {
        generateRandom();
    }

    public void generateRandom() {
        int min = 0;
        int max = 11;
        number = random.nextInt((max - min) + min);
        System.out.println(number);
    }

//    public void guessANumber() {
//        System.out.println("Guess a number");
//      guess = scan.nextInt();
//        evaluateGuess();
//    }

    public void evaluateGuess() {
        if (tryNumber < capLimit) {
            if (guess > number) {
                System.out.println("Your too high!");
                System.out.println("Try again.");
                System.out.println("You have " + (capLimit - tryNumber) + " tries remaining.");
                message.setText("<html>Your too high!<br>Try again.<br>You have " + (capLimit - tryNumber) + " tries remaining.<br></html>");
                mainPanel.repaint();

                tryNumber++;
//            guessANumber();
            } else if (guess < number) {
                System.out.println("Your too low!");
                System.out.println("Try again.");
                System.out.println("You have " + (capLimit - tryNumber) + " tries remaining.");
                message.setText("<html>Your too low!<br>Try again.<br>You have " + (capLimit - tryNumber) + " tries remaining.</html>");
                mainPanel.repaint();

                tryNumber++;
//            guessANumber();
            } else {
                restart.show();
                enter.hide();
                mainPanel.setBackground(Color.green);
                System.out.println("YOU GOT IT!");
                System.out.println("You got it in " + (tryNumber) + " tries!");

                message.setText("<html>YOU GOT IT!<br>You got it in " + (tryNumber) + " tries!</html>");
                mainPanel.repaint();
            }
        }
        else {
            endGame();
        }
    }

    public void endGame() {
        restart.show();
        enter.hide();
        System.err.println("WRONG!");
        System.err.println("YOU RAN OUT OF TRIES!");
        System.out.println("The Answer was " + number);
        mainPanel.setBackground(Color.red);


        mainPanel.add(answer);
        mainPanel.repaint();
        message.setText("<html>WRONG!<br>YOU RAN OUT OF TRIES!</html>");
        answer.setText("The Answer was " + number);
        mainPanel.repaint();

//        scan.close();
    }

    public void restart(){
        restart.hide();
        enter.show();
        tryNumber = 1;
        message.setText("<html>TRY TO GUESS THE NUMBER!</html>");
        guessInput.setText("");
        answer.setText("");
        mainPanel.setBackground(Color.lightGray);
        generateRandom();
    }


    private class EnterButtonClicked implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guess = Integer.parseInt(guessInput.getText());
            evaluateGuess();
        }
    }


    private class RestartButtonClicked implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        restart();
        }
    }


    private class Terminator extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
