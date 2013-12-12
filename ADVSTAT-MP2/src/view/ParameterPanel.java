package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Interval;
import model.Iteration;
import model.Model;
import model.polynomial.Polynomial;
import model.polynomial.Term;
import view.View.ViewAction;
import view.listeners.GraphListener;
import view.listeners.GraphListener.GraphParameters;
import view.util.ComponentFactory;
import view.util.Util;
import view.util.Size;
import view.util.Util.ErrorMessage;

public class ParameterPanel extends JPanel
{

    /**
     * constants
     */
    private static final long    serialVersionUID = 1L;
    public static final int      REGULA_FALSI     = 0;
    public static final int      SECANT           = 1;
    public static final int      UNKNOWN_METHOD   = -1;

    /* Error handling */
    private static final boolean SHOW_BORDER      = false;

    /* Parent class */
    private View                 view;

    /* Swing components */
    private JTextField           inputTerm, leftInterval, rightInterval, txtIteration;
    private JLabel               outputPolynomial, outputInterval;
    private JButton              btnAddTerm;
    private JButton              btnGraph, btnInterval, btnIteration, btnTable;
    private JButton              btnReset;

    private final ActionHandler  action           = new ActionHandler();

    /* Why the fuck is this here */
    private Model                model            = new Model();

    private boolean              hasError         = false;
    private ArrayList<Iteration> iterations;

    private JComboBox<String>    cmbxMethod;

    private Polynomial           currentPolynomial;
    private Interval             currentInterval;
    private int                  currentMethod;

    private GraphListener        graphListener;

    public ParameterPanel(View view)
    {
        this.view = view;
        /** Initialize the panel */
        setPreferredSize(new Dimension(400, 300));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createEtchedBorder());

        /** Initialize components */
        outputPolynomial = ComponentFactory.newLabel("");
        outputPolynomial.setBorder(BorderFactory.createTitledBorder("Current Polynomial"));
        outputPolynomial.setPreferredSize(Size.mediumWide);
        add(outputPolynomial);

        outputInterval = ComponentFactory.newLabel("");
        outputInterval.setBorder(BorderFactory.createTitledBorder("Current Intervals"));
        outputInterval.setPreferredSize(Size.mediumWide);
        add(outputInterval);

        inputTerm = ComponentFactory.newInput("e.g. 4 3 2 1", Size.Small);
        add(inputTerm);

        btnAddTerm = ComponentFactory.newButton("Add term", action.Handler(ParameterAction.ADD_TERM), Size.Small);
        btnAddTerm.setMnemonic(java.awt.event.KeyEvent.VK_A);
        add(btnAddTerm);

        btnReset = ComponentFactory.newButton("Reset", action.Handler(ParameterAction.RESET), Size.Small);
        btnReset.setMnemonic(java.awt.event.KeyEvent.VK_R);
        add(btnReset);

        btnGraph = ComponentFactory.newButton("Graph", action.Handler(ParameterAction.GRAPH), Size.Small);
        btnGraph.setMnemonic(java.awt.event.KeyEvent.VK_G);
        add(btnGraph);

        
        

        /** Initialize methods available */
        JLabel lblMethod = ComponentFactory.newLabel("Select a method");
        add(lblMethod);

        cmbxMethod = new JComboBox<String>();
        cmbxMethod.setPreferredSize(Size.Medium);
        cmbxMethod.addItem("Regula Falsi");
        cmbxMethod.addItem("Secant");
        cmbxMethod.addActionListener(action.Handler(ParameterAction.SET_METHOD));
        add(cmbxMethod);

        JLabel lblInterval = new JLabel("Interval :  [");
        lblInterval.setPreferredSize(new Dimension(70, 30));
        add(lblInterval);

        leftInterval = ComponentFactory.newInput("e.g. 2", Size.Petite);
        add(leftInterval);

        JLabel label = new JLabel(",");
        label.setPreferredSize(new Dimension(8, 30));
        add(label);

        rightInterval = ComponentFactory.newInput("e.g. 4", Size.Petite);
        add(rightInterval);

        JLabel label1 = new JLabel("]");
        label1.setPreferredSize(new Dimension(10, 30));
        add(label1);

        btnInterval = ComponentFactory.newButton("Add Interval", action.Handler(ParameterAction.ADD_INTERVAL), Size.Medium);                                               // 30));
        add(btnInterval);

        btnInterval.setMnemonic(java.awt.event.KeyEvent.VK_I);

        label1 = new JLabel("Iterations : ");
        label1.setPreferredSize(new Dimension(70, 30));
        add(label1);

        txtIteration = ComponentFactory.newInput("e.g. 4", Size.Petite);
        add(txtIteration);

        add(Box.createRigidArea(new Dimension(80, 30)));

        btnIteration = ComponentFactory.newButton("Add Iteration", action.Handler(ParameterAction.ADD_ITERATION), Size.Medium);
        add(btnIteration);

        add(Box.createRigidArea(new Dimension(210, 30)));

        btnTable = ComponentFactory.newButton("Generate Table", action.Handler(ParameterAction.TABLE), Size.Medium);
        add(btnTable);

        // add(Box.createRigidArea(new Dimension(80, 30)));

        if (SHOW_BORDER)
        {
            outputPolynomial.setBorder(BorderFactory.createEtchedBorder());

        }
    }

    public void setGraphListener(GraphListener listener)
    {
        graphListener = listener;
    }
    

    public int getIterations()
    {
        try{return Integer.parseInt(txtIteration.getText());}
        catch (Exception e){}return 0;
    }

    public int getCurrentMethod()
    {
        return currentMethod;
    }
    
    private enum ParameterAction
    {
        ADD_TERM, ADD_INTERVAL, RESET, ADD_ITERATION, GRAPH, TABLE, SET_METHOD
    }

    private class ActionHandler
    {

        public ActionListener Handler(ParameterAction e)
        {
            switch (e)
            {
            case ADD_INTERVAL:  return new AddInterval();
            case ADD_TERM:      return new AddTerm();
            case ADD_ITERATION: return new AddIteration();
            case GRAPH:         return new Graph();
            case RESET:         return new Reset();
            case TABLE:         return new Table();
            case SET_METHOD:    return new SetMethod();

            default:
                break;
            }

            return null;
        }

        public class SetMethod implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String string = cmbxMethod.getSelectedItem().toString();
                if (string.equalsIgnoreCase("Regula Falsi"))
                    currentMethod = REGULA_FALSI;
                if (string.equalsIgnoreCase("Secant"))
                    currentMethod = SECANT;
                currentMethod = UNKNOWN_METHOD;
            }

        }
        public class AddIteration implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (Util.isInputNumerical(txtIteration))
                    return;
                hasError = true;
                Util.Error(ErrorMessage.INPUT_NUMBERS_ONLY);
                
                
                // BUT WHAT IS THIS SUPPOSED TO DO?!
                // Mukhang error checking lang
            }

        }

        public class Graph implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                GraphParameters parameters = new GraphParameters(currentPolynomial);
                graphListener.GraphRequested(parameters);
                ActionEvent actionEvent = new ActionEvent(e.getSource(), e.getID(), e.getActionCommand());
                view.fireActionEvent(ViewAction.BACK, actionEvent);
            }

        }
        
        public class Table implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (hasError){
                    Util.Error(ErrorMessage.CANNOT_GENERATE_TABLE);
                    return;
                }
                if (currentInterval == null){
                    Util.Error(ErrorMessage.NO_INTERVALS_SET);
                    return;
                }
                
                
                double left = currentInterval.getLeftInterval();
                double right = currentInterval.getRightInterval();
                model.compute(left, right, getIterations(), getCurrentMethod());
                iterations = model.getIterations();
                view.InitializeTable(iterations, getCurrentMethod());
                
                ActionEvent actionEvent = new ActionEvent(e.getSource(), e.getID(), e.getActionCommand());
                view.fireActionEvent(ViewAction.NEXT, actionEvent);
            }
            
        }

        public final class AddInterval implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                /* Check for missing input errors */
                boolean leftHasInput, rightHasInput;
                leftHasInput = Util.hasInput(leftInterval);
                rightHasInput = Util.hasInput(rightInterval);
                if (leftHasInput && rightHasInput == false)
                {   
                    Util.Error("Missing input : " + (leftHasInput ? "Left interval" : "Right interval") );
                    hasError = true;
                    return;
                }
                
                /* Check for number format errors */
                boolean isFormatOkay = true;
                isFormatOkay = isFormatOkay && Util.isInputNumerical(leftInterval);
                isFormatOkay = isFormatOkay && Util.isInputNumerical(rightInterval);
                if (isFormatOkay == false)
                {   
                    Util.Error(ErrorMessage.INPUT_NUMBERS_ONLY);
                    hasError = true;
                    return;
                }
                
                try {
                    double left = Double.parseDouble(leftInterval.getText());
                    double right = Double.parseDouble(rightInterval.getText());
                    
                    GraphParameters.StartX = left;
                    GraphParameters.EndX = right;
                    
                    outputInterval.setText("[" + left + " ," + right + "]");
                    currentInterval = new Interval(left, right);
                }catch(NumberFormatException err){
                    Util.Error(ErrorMessage.INPUT_NUMBERS_ONLY);
                }
            }
        }

        public final class AddTerm implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                hasError = false;
             // Check if the input are numbers
                boolean inputNumerical = Util.isInputNumerical(inputTerm);
                if (inputNumerical == false)
                {
                    Util.Error(ErrorMessage.COEFFICIENT_FORMAT);
                    return;
                }

                // Check if the input are in pairs
                String inputText = inputTerm.getText();
                String[] array = inputText.split(" ");
                double[] input = new double[array.length];

                if (array.length % 2 == 1)
                {
                    Util.Error(ErrorMessage.INPUT_PAIRS_FORMAT);
                    return;
                }

                // Parse the values of the pairs
                int i = 0;
                while (i < array.length)
                    input[i] = Double.parseDouble(array[i++]);

                method.Polynomial.setPolynomial(input);
                

                // Set the current polynomial
                Term newTerm;
                double coefficient;
                if (currentPolynomial == null){
                    double[] initial = {};
                    currentPolynomial = new Polynomial(initial);
                }
                
                for (i = 0; i < array.length; i += 2)
                {
                    coefficient = input[i];
                    newTerm = new Term(coefficient, (int) input[i + 1]);
                    currentPolynomial = currentPolynomial.addTerm(newTerm);
                }
                
                outputPolynomial.setText(currentPolynomial.toString());
            }
        }

        public final class OldAddTerm implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e)
            {
                    Term newTerm = null;

                    inputTerm.setBackground(Color.white);

                    /** Parsing */
                    try
                    {
                        double coefficient = -1;
                        int exponent = -1;
                        boolean hasX = false;
                        boolean hasExponent = false;

                        String text = inputTerm.getText();
                        text = text.toLowerCase();

                        text = text.replace("-x", "-1x");
                        text = text.replace("+x", "+1x");

                        // for(2 chips)

                        text = text.substring(text.length() - 1).equalsIgnoreCase("x") ? text.replace("x", "x^1") : text;

                        hasExponent = text.contains("^");
                        hasX = text.contains("x");

                        if (text.contains("+") || text.contains("e") || text.contains("*") || text.contains("/"))
                            throw new Exception("Invalid!");

                        if (!hasExponent)
                        {
                            if (hasX)
                                exponent = 1;
                            else
                                exponent = 0;

                        }

                        if (!hasX && hasExponent)
                        {
                            String[] toks = null;
                            text = text.replace("^", "x");
                            toks = text.split("x");
                            text = text.replace("x", "");

                            int i = 1;
                            int val = Integer.parseInt(toks[0]);
                            while (i != Integer.parseInt(toks[1]))
                            {
                                val += val;
                                i++;
                            }
                            text = Integer.toString(val);
                            exponent = 0;
                        }

                        // Remove x and exponent, and split
                        String[] tokens = null;

                        text = text.replace("^", "");
                        text = text.trim();

                        tokens = text.split("x");

                        if (tokens.length == 0 || tokens[0].length() == 0 && hasX)
                            coefficient = 1;

                        if (exponent == -1)
                            exponent = Integer.parseInt(tokens[1]);
                        if (coefficient == -1)
                            coefficient = Double.parseDouble(tokens[0]);

                        newTerm = new Term(coefficient, exponent);

                        currentPolynomial = currentPolynomial.addTerm(newTerm);
                        outputPolynomial.setText(currentPolynomial.toString());
                    }
                    catch (Exception err)
                    {
                        inputTerm.setBackground(Color.pink);
                        String problem = "Please follow the format: 4x^2!";
                        Util.Error(problem);
                    }
                }
            }
        
        public final class Reset implements ActionListener {
            public void actionPerformed(ActionEvent e)
            {
                hasError = false;
                currentPolynomial = new Polynomial(new ArrayList<Term>());
                outputPolynomial.setText(currentPolynomial.toString());
                view.ResetGraph();
            }
        }

    }
}
