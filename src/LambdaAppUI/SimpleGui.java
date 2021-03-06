package LambdaAppUI;

import ApplicationModel.LambdaAppEngine;
import Util.StringResource;

import javax.swing.*;

public class SimpleGui implements LambdaView {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 350;
    private final LambdaAppEngine lambdaAppEngine;
    private final LambdaParser lambdaParser;
    private final JTextField inputExpr = new JTextField("Input Expression goes here",
            20);

    public SimpleGui(LambdaAppEngine lambdaAppEngine) {
        this.lambdaAppEngine = lambdaAppEngine;
        this.lambdaParser = new SimpleLambdaParser(this);
    }

    //view code
    public void display() {
        JFrame frame = new JFrame(StringResource.APP_TITLE);

        frame.setSize(WIDTH, HEIGHT);

        JPanel panel = new JPanel();
        addButtons(panel, frame);

        JPanel textPanel = new JPanel();
        textPanel.add(inputExpr);

        panel.add(textPanel);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void updateInputDisplay() {
        StringBuilder sb = new StringBuilder();
        lambdaParser.getTokens().forEach(token -> sb.append(token.toString()));

        inputExpr.setText(sb.toString());
    }

    @Override
    public void update() {

    }

    //Controller code

    private void addButtons(JPanel panel, JFrame frame) {
        JButton variableButton = new JButton(StringResource.VARIABLE);
        variableButton.addActionListener(e -> {
            String variable = getInputVariable("Please enter variable " +
                    "name", frame);
            if(variable.length() > 0){
                lambdaParser.add(new VariableToken(variable));
            }
        });
        panel.add(variableButton);

        JButton lambdaAbstrButton = new JButton(StringResource.LAMBDA);
        lambdaAbstrButton.addActionListener(e -> {
            String variable = getInputVariable("Please enter variable name " +
                    "for the lambda abstraction.", frame);
            if(variable.length() > 0) {
                lambdaParser.add(new AbstractionToken(variable));
            }
        });
        panel.add(lambdaAbstrButton);

        JButton backSpace = new JButton(StringResource.BACKSPACE);
        backSpace.addActionListener(e -> lambdaParser.removeLastInput());
        panel.add(backSpace);
    }

    private String getInputVariable(String message, JFrame frame) {
        String title = "LambdaVariable Input";
        String result;
        do{
            result = JOptionPane.showInputDialog(frame, message, title,
                    JOptionPane.PLAIN_MESSAGE);
            //In case no input or an error occurs
        } while(result == null);

        return result;
    }
}
