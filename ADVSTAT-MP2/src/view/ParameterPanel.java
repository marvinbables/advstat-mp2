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
import view.listeners.GraphListener;
import view.listeners.GraphListener.GraphParameters;
import view.util.ComponentFactory;
import view.util.Util;
import view.util.Size;
import view.util.Util.ErrorMessage;

public class ParameterPanel extends JPanel implements ActionListener
{

    /**
	 * 
	 */
    private static final long    serialVersionUID = 1L;
    
    /* Parent class*/
    private View                 view;
    
    /* Swing components */
    private JTextField           inputTerm, leftInterval, rightInterval, txtIteration;
    private JLabel               outputPolynomial, outputInterval;
    private JButton              btnAddTerm;
    private JButton              btnGraph, btnInterval, btnIteration, btnTable;
    private JButton              btnReset;
    
    /* Why the fuck is this here */
    private Model                m                = new Model();
    
    
    private ArrayList<Iteration> iterations;
    
    /* Error handling */
    private static final boolean SHOW_BORDER      = false;
    private boolean              haveError        = false;
    
    private JComboBox<String>    cmbxMethod;

    private Polynomial           currentPolynomial;
    private Interval             currentInterval;
    private int                  selectedMethod;

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

        /** Initialize parameters */
        InitializeParameters();

        inputTerm = ComponentFactory.newInput("e.g. 4 3 2 1", Size.Small, this);
        add(inputTerm);

        btnAddTerm = ComponentFactory.newButton("Add term", this, Size.Small);
        add(btnAddTerm);

        btnReset = ComponentFactory.newButton("Reset", this, Size.Small);
        add(btnReset);

        btnGraph = ComponentFactory.newButton("Graph", this, Size.Small);
        add(btnGraph);

        btnReset.setMnemonic(java.awt.event.KeyEvent.VK_R);
        btnGraph.setMnemonic(java.awt.event.KeyEvent.VK_G);

        /** Initialize methods available */
        JLabel lblMethod = ComponentFactory.newLabel("Select a method");
        add(lblMethod);

        cmbxMethod = new JComboBox<String>();
        cmbxMethod.setPreferredSize(Size.Medium);
        cmbxMethod.addItem("Regula Falsi");
        cmbxMethod.addItem("Secant");
        cmbxMethod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectedMethod = cmbxMethod.getSelectedIndex();
            }
        });
        add(cmbxMethod);

        JLabel lblInterval = new JLabel("Interval :  [");
        lblInterval.setPreferredSize(new Dimension(70, 30));
        add(lblInterval);

        leftInterval = ComponentFactory.newInput("e.g. 2", Size.Petite, this);
        add(leftInterval);

        JLabel label = new JLabel(",");
        label.setPreferredSize(new Dimension(8, 30));
        add(label);

        rightInterval = ComponentFactory.newInput("e.g. 4", Size.Petite, this);
        add(rightInterval);

        JLabel label1 = new JLabel("]");
        label1.setPreferredSize(new Dimension(10, 30));
        add(label1);

        btnInterval = ComponentFactory.newButton("Add Interval", this, Size.Medium); // new
                                                                        // Dimension(120,
                                                                        // 30));
        add(btnInterval);

        btnInterval.setMnemonic(java.awt.event.KeyEvent.VK_I);

        label1 = new JLabel("Iterations : ");
        label1.setPreferredSize(new Dimension(70, 30));
        add(label1);

        txtIteration = ComponentFactory.newInput("e.g. 4", Size.Petite, this);
        add(txtIteration);

        add(Box.createRigidArea(new Dimension(80, 30)));

        btnIteration = ComponentFactory.newButton("Add Iteration", this, Size.Medium);
        add(btnIteration);

        add(Box.createRigidArea(new Dimension(210, 30)));

        btnTable = ComponentFactory.newButton("Generate Table", this, Size.Medium);
        add(btnTable);

        // add(Box.createRigidArea(new Dimension(80, 30)));

        if (SHOW_BORDER)
        {
            outputPolynomial.setBorder(BorderFactory.createEtchedBorder());

        }
    }

    private void InitializeParameters()
    {
        currentPolynomial = new Polynomial(new ArrayList<Term>());
        outputPolynomial.setText(currentPolynomial.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource();
        if (target.equals(btnAddTerm) || target.equals(inputTerm))
        {
            haveError = false;
            AddTerm2();
        }

        else if (target.equals(btnReset))
        {
            haveError = false;
            InitializeParameters();
            view.resetGraph();
        }

        else if (target.equals(btnGraph))
        {
            view.prevButton();
            GraphParameters parameters = new GraphParameters(currentPolynomial);
            graphListener.GraphRequested(parameters);
            validate();
            repaint();
        }
        else if (target.equals(view.getBtnNext()))
        {
            view.nextButton();
        }
        else if (target.equals(view.getBtnPrev()))
        {
            view.prevButton();
        }

        else if (target.equals(btnInterval) || target.equals(rightInterval) || target.equals(leftInterval))
        {
            haveError = false;
            AddInterval();

        }
        else if (target.equals(btnIteration) || target.equals(txtIteration))
        {
            haveError = false;
            AddIteration();
        }
        else if (target.equals(btnTable))
        {
            haveError = false;
            generateTable();
        }

    }

    private void generateTable()
    {
        System.out.println(outputPolynomial.getText());
        if (outputPolynomial.getText().equals(""))
            AddTerm2();
        if (outputInterval.getText().equals(""))
            AddInterval();
        AddIteration();
        if (!haveError)
        {
            m.compute(getInterval().getLeftInterval(), getInterval().getRightInterval(), getIterations(), getCurrentMethod());
            iterations = m.getIterations();
            view.resetTable();
            view.setTable(iterations, cmbxMethod.getSelectedItem().toString());
            view.nextButton();
        }

    }

    public boolean regulaIsSelected()
    {
        return cmbxMethod.getSelectedIndex() == 0;
    }

    private void AddIteration()
    {
        if (Util.isInputNumerical(txtIteration))
            return;
        haveError = true;
        Util.Error(ErrorMessage.INPUT_NUMBERS_ONLY);
    }

    private void AddInterval()
    {
        /* Check for missing input errors */
        boolean leftHasInput, rightHasInput;
        leftHasInput = Util.hasInput(leftInterval);
        rightHasInput = Util.hasInput(rightInterval);
        if (leftHasInput && rightHasInput == false)
        {   
            Util.Error("Missing input : " + (leftHasInput ? "Left interval" : "Right interval") );
            haveError = true;
            return;
        }
        
        /* Check for number format errors */
        boolean hasFormatError = false;
        hasFormatError = hasFormatError || Util.isInputNumerical(leftInterval);
        hasFormatError = hasFormatError || Util.isInputNumerical(rightInterval);
        if (hasFormatError)
        {   
            Util.Error("Please follow the format : Numbers only");
            haveError = true;
            return;
        }
        
        double left = Double.parseDouble(leftInterval.getText());
        double right = Double.parseDouble(rightInterval.getText());
        outputInterval.setText("[" + left + " ," + right + "]");
        currentInterval = new Interval(left, right);
        
    }

    private void AddTerm2()
    {
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
        outputPolynomial.setText(inputText);

        // Set the current polynomial
        Term newTerm;
        double coefficient;
        for (i = 0; i < array.length; i += 2)
        {
            coefficient = input[i];
            newTerm = new Term(coefficient, (int) input[i + 1]);
            currentPolynomial = currentPolynomial.addTerm(newTerm);
        }

    }

    private void AddTerm()
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



    public void setGraphListener(GraphListener listener)
    {
        graphListener = listener;
    }

    public Polynomial getPolynomial()
    {
        return currentPolynomial;
    }

    public Interval getInterval()
    {
        return currentInterval;
    }

    public int getIterations()
    {
        return Integer.parseInt(txtIteration.getText());
    }

    public int getCurrentMethod()
    {
        int type = 0;
        if (cmbxMethod.getSelectedItem().toString().equalsIgnoreCase("Regula Falsi"))
            type = 0;
        else if (cmbxMethod.getSelectedItem().toString().equalsIgnoreCase("Secant"))
            type = 1;

        return type;
    }

}
