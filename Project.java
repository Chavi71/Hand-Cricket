package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Project extends JFrame implements ActionListener {
    private int playerScore = 0;
    private int computerScore = 0;
    private int target;
    private int flag = 0;

    private JButton batButton;
    private JButton bowlButton;
    private JButton[] numberButtons;
    private List<Integer> userRunsList;
    private Random random;
    private boolean userBatted;

    private JLabel playerScoreLabel;
    private JLabel computerScoreLabel;

    public Project() {
        setTitle("Hand Cricket Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userRunsList = new ArrayList<>();
        random = new Random();

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel topPanel = new JPanel(new FlowLayout());
        batButton = new JButton("Bat");
        bowlButton = new JButton("Bowl");

        batButton.addActionListener(this);
        bowlButton.addActionListener(this);

        topPanel.add(batButton);
        topPanel.add(bowlButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        JPanel playerPanel = new JPanel(new FlowLayout());
        playerScoreLabel = new JLabel("Your Score: 0");
        playerPanel.add(playerScoreLabel);
        centerPanel.add(playerPanel);

        JPanel computerPanel = new JPanel(new FlowLayout());
        computerScoreLabel = new JLabel("Computer Score: 0");
        computerPanel.add(computerScoreLabel);
        centerPanel.add(computerPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        numberButtons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            numberButtons[i] = new JButton(Integer.toString(i + 1));
            numberButtons[i].addActionListener(this);
            bottomPanel.add(numberButtons[i]);
        }
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void startGame(String userChoice) {
        batButton.setEnabled(false);
        bowlButton.setEnabled(false);
        flag++;

        target = random.nextInt(51) + 50; // Random target between 50 and 100
        updateScores();
        userRunsList.clear();
        enableNumberButtons(true);
        userBatted = userChoice.equals("Bat");
    }

    private void playGame(int userRuns) {
        int computerRunsThisBall = random.nextInt(6) + 1;

        if (userBatted) {
            System.out.println("Computer bowls: " + computerRunsThisBall);
        } else {
            System.out.println("Computer bats: " + computerRunsThisBall);
        }

        if (userRuns == computerRunsThisBall) {
            if (userBatted) {
                JOptionPane.showMessageDialog(this, "Out! Your total runs: " + playerScore, "Out!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Out! Computer's total runs: " + computerScore, "Out!", JOptionPane.INFORMATION_MESSAGE);
            }

            enableNumberButtons(false);
            batButton.setEnabled(true);
            bowlButton.setEnabled(true);
            checkResult();
        } else {
            if (userBatted) {
                playerScore += userRuns;
                playerScoreLabel.setText("Your Score: " + playerScore);
                userRunsList.add(userRuns);
            } else {
                computerScore += computerRunsThisBall;
                computerScoreLabel.setText("Computer Score: " + computerScore);
            }
        }
    }

    private void updateScores() {
        playerScoreLabel.setText("Your Score: " + playerScore);
        computerScoreLabel.setText("Computer Score: " + computerScore);
    }

    private void checkResult() {
        if (flag == 2) {
            if (playerScore > computerScore) {
                JOptionPane.showMessageDialog(this, "You Win! Your Score: " + playerScore + ", Computer's Score: " + computerScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else if (playerScore < computerScore) {
                JOptionPane.showMessageDialog(this, "You Lose! Your Score: " + playerScore + ", Computer's Score: " + computerScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "It's a Tie! Your Score: " + playerScore + ", Computer's Score: " + computerScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void enableNumberButtons(boolean enable) {
        for (JButton button : numberButtons) {
            button.setEnabled(enable);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton == batButton) {
            startGame("Bat");
        } else if (clickedButton == bowlButton) {
            startGame("Bowl");
        } else if (Arrays.asList(numberButtons).contains(clickedButton)) {
            int userRuns = Integer.parseInt(clickedButton.getText());
            playGame(userRuns);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Project::new);
    }
}
