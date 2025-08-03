import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField textField;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";

    public Calculator() {
        setTitle("power Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        // TextField
        textField = new JTextField("0");
        textField.setFont(new Font("Verdana", Font.BOLD, 32));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(textField, BorderLayout.NORTH);

        
        String[] buttons = {
                "C", "←", "+/-", "Exit",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sqrt", "pow", "sin", "cos",
                "tan", "log"
        };

        JPanel buttonPanel = new JPanel(new GridLayout(7, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        for (String text : buttons) {
            JButton button = new JButton(text);
            styleButton(button, text);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void showSplashScreen() {
        JWindow splash = new JWindow();

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("power Calculator", SwingConstants.CENTER);
        label.setForeground(Color.black);
        label.setFont(new Font("Tahoma", Font.BOLD, 36));

        panel.add(label, BorderLayout.CENTER);

        splash.getContentPane().add(panel);
        splash.setSize(1000, 1000);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        
        try {
            Thread.sleep(2500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.setVisible(false);
        splash.dispose();
    }



    private void styleButton(JButton button, String text) {
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        button.setFocusPainted(false);

        if (text.matches("[0-9]") || text.equals(".")) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        } else if (text.equals("=")) {
            button.setBackground(new Color(0, 150, 0));
            button.setForeground(Color.WHITE);
        } else if (text.equals("C")) {
            button.setBackground(new Color(200, 50, 50));
            button.setForeground(Color.WHITE);
        } else if (text.equals("Exit")) {
            button.setBackground(new Color(150, 0, 0));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(220, 220, 220));
            button.setForeground(Color.BLACK);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        String currentText = textField.getText();

        try {
            switch (input) {
                
                case "0": case "1": case "2": case "3":
                case "4": case "5": case "6":
                case "7": case "8": case "9":
                case ".":
                    if (currentText.equals("0") || operator.equals("=")) {
                        textField.setText(input);
                        if (operator.equals("=")) operator = "";
                    } else {
                        textField.setText(currentText + input);
                    }
                    break;

                
                case "+": case "-": case "*": case "/": case "pow":
                    num1 = Double.parseDouble(textField.getText());
                    operator = input;
                    textField.setText("0");
                    break;

                case "=":
                    if (operator.isEmpty()) return;

                    num2 = Double.parseDouble(textField.getText());
                    calculate();
                    textField.setText(format(result));
                    num1 = result;
                    operator = "=";
                    break;

                
                case "+/-":
                    double val = Double.parseDouble(textField.getText());val *= -1;
                    textField.setText(format(val));
                    break;

                
                case "←":
                    if (currentText.length() > 1) {
                        textField.setText(currentText.substring(0, currentText.length() - 1));
                    } else {
                        textField.setText("0");
                    }
                    break;

                
                case "sqrt":
                    num1 = Double.parseDouble(textField.getText());
                    result = (num1 >= 0) ? Math.sqrt(num1) : Double.NaN;
                    textField.setText(Double.isNaN(result) ? "erorr" : format(result));
                    break;

                case "sin":
                    num1 = Math.toRadians(Double.parseDouble(textField.getText()));
                    result = Math.sin(num1);
                    textField.setText(format(result));
                    break;

                case "cos":
                    num1 = Math.toRadians(Double.parseDouble(textField.getText()));
                    result = Math.cos(num1);
                    textField.setText(format(result));
                    break;

                case "tan":
                    num1 = Math.toRadians(Double.parseDouble(textField.getText()));
                    result = Math.tan(num1);
                    textField.setText(format(result));
                    break;

                case "log":
                    num1 = Double.parseDouble(textField.getText());
                    result = (num1 > 0) ? Math.log10(num1) : Double.NaN;
                    textField.setText(Double.isNaN(result) ? "erorr" : format(result));
                    break;

               
                case "C":
                    textField.setText("0");
                    num1 = num2 = result = 0;
                    operator = "";
                    break;

                
                case "Exit":
                    System.exit(0);
                    break;
            }

        } catch (Exception ex) {
            textField.setText("erorr");
        }
    }

    private void calculate() {
        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    textField.setText("erorr");
                    result = 0;
                    return;
                } else {
                    result = num1 / num2;
                }
                break;
            case "pow":
                result = Math.pow(num1, num2);
                break;
            default:
                result = num2;
                break;
        }

        if (Double.isNaN(result) || Double.isInfinite(result)) {
            textField.setText("erorr");
        }
    }

    private String format(double num) {
        if (num == (long) num)
            return String.format("%d", (long) num);
        else
            return String.format("%.8f", num).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    public static void main(String[] args) {
        showSplashScreen();
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}

//power - alivhzd