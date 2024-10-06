package quiznew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class quizz {

    private JFrame frame;

    public static void main(String[] args) {
        // Use the event dispatch thread to build the UI for thread safety
        SwingUtilities.invokeLater(() -> new quizz().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Math Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        showWelcomePage();

        frame.setVisible(true);
    }

    private void showWelcomePage() {
        JLabel welcomeLabel = new JLabel("Welcome to the Math Quiz Game!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(welcomeLabel);

        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                startQuiz();
                frame.revalidate();
                frame.repaint();
            }
        });
        frame.add(startButton);
    }

    private void startQuiz() {
        int numQuestions = 5;
        int score = 0;

        for (int i = 1; i <= numQuestions; i++) {
            int num1 = (int) (Math.random() * 10) + 1;
            int num2 = (int) (Math.random() * 10) + 1;
            String operator = getRandomOperator();
            int correctAnswer = getCorrectAnswer(num1, num2, operator);

            String userAnswerString = JOptionPane.showInputDialog(
                    frame,
                    "Question " + i + ": " + num1 + " " + operator + " " + num2 + " = ?",
                    "Math Quiz",
                    JOptionPane.QUESTION_MESSAGE
            );

            try {
                int userAnswer = Integer.parseInt(userAnswerString);
                if (userAnswer == correctAnswer) {
                    score++;
                    JOptionPane.showMessageDialog(
                            frame,
                            "Correct!",
                            "Math Quiz",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Wrong! The correct answer is " + correctAnswer,
                            "Math Quiz",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid input. Please enter a number.",
                        "Math Quiz",
                        JOptionPane.ERROR_MESSAGE
                );
                i--; // Decrement i to repeat the same question
            }
        }

        displayScore(score, numQuestions);
        showWelcomePage(); // Show the welcome page after the quiz
    }

    private String getRandomOperator() {
        String[] operators = {"+", "-", "*"};
        return operators[(int) (Math.random() * operators.length)];
    }

    private int getCorrectAnswer(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private void displayScore(int score, int total) {
        JOptionPane.showMessageDialog(
                frame,
                "Your Score: " + score + "/" + total,
                "Math Quiz",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
