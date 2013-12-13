package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import method.Method.Approach;
import model.Iteration;

import org.jfree.data.function.PolynomialFunction2D;

import view.listeners.GraphListener;
import view.listeners.MenuListener;
import view.util.ComponentFactory;
import view.util.Size;
import view.util.Util;

@SuppressWarnings("serial")
public class View extends JFrame implements GraphListener
{

    private ParameterPanel     parameterPanel;
    private JPanel             graphPanel, btnPanel, tablePanel, bottomPanel;
    private PolynomialGraph    graph;

    public final ActionHandler action                    = new ActionHandler();
    private ImageIcon          imgNext, imgPrev;
    private DefaultTableModel  tblModel;
    private JTable             tblInfo;
    private JScrollPane        scroll;
    private JButton            btnNext;
    private JButton            btnPrev;


    public View()
    {
        super("Roots of Polynomials");
        setPreferredSize(new Dimension(450, 730));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        /** Prepare menu */
        MenuListener menuListener = new MenuListener();
        menuListener.setGraphListener(this);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu settings = new JMenu("Settings");
        menuBar.add(settings);

        JMenuItem changeBounds = new JMenuItem("Change graph bounds");
        changeBounds.addActionListener(menuListener);
        settings.add(changeBounds);

        /** Initialize components */

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        parameterPanel = new ParameterPanel(this);
        parameterPanel.setGraphListener(this);
        add(parameterPanel);

        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setPreferredSize(new Dimension(430, 350));

        graphPanel = new JPanel();
        // graphPanel.setBorder(BorderFactory.createEtchedBorder());
        // graphPanel.setPreferredSize(new Dimension(430, 380));

        tablePanel = new JPanel();
        // tablePanel.setBorder(BorderFactory.createEtchedBorder());
        // tablePanel.setPreferredSize(new Dimension(430, 380));

        imgNext = new ImageIcon("icons/next.png");
        imgPrev = new ImageIcon("icons/prev.png");

        btnPanel = new JPanel();
        // btnPanel.setPreferredSize(new Dimension(200,50));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        // Initial creation of JTable
        if (parameterPanel.getApproach() == Approach.RegulaFalsi)
            tblModel = setupTableRegula();
        else if (parameterPanel.getApproach() == Approach.Secant)
            tblModel = setupTableSecant();

        tblInfo = new JTable(tblModel);

        scroll = new JScrollPane(tblInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(420, 320));

        tablePanel.add(scroll);

        btnNext = ComponentFactory.newButton(imgNext);
        btnNext.setMnemonic(java.awt.event.KeyEvent.VK_RIGHT);
        btnNext.addActionListener(action.Handle(ViewAction.NEXT));

        btnPrev = ComponentFactory.newButton(imgPrev);
        btnPrev.setEnabled(false);
        btnPrev.setMnemonic(java.awt.event.KeyEvent.VK_LEFT);
        btnPrev.addActionListener(action.Handle(ViewAction.BACK));

        btnPanel.add(btnPrev);
        btnPanel.add(Box.createRigidArea(Size.Small));
        btnPanel.add(btnNext);

        add(btnPanel);

        // add(graphPanel);
        bottomPanel.add(graphPanel);
        add(bottomPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Fires an action event from an external class.
     * Specifically, ParameterPanel is the one calling this method.
     * @param e
     * @param actionEvent
     */
    public void fireActionEvent(ViewAction e, ActionEvent actionEvent)
    {
        ActionListener[] actionListeners;
        switch (e)
        {
        case BACK:
            actionListeners = btnPrev.getActionListeners();
            for (ActionListener actionListener : actionListeners)
            {
                actionListener.actionPerformed(actionEvent);
            }
            break;
        case NEXT:
            actionListeners = btnNext.getActionListeners();
            for (ActionListener actionListener : actionListeners)
            {
                actionListener.actionPerformed(actionEvent);
            }
            break;
        }
    }

    /**
     * Initializes the table given the kind of approach for numerical analysis
     * 
     * @param approach
     *            - ParameterPanel.REGULA_FALSI or ParameterPanel.SECANT
     */
    public void InitializeTable(Approach approach)
    {
        switch (approach)
        {
        case RegulaFalsi:
            tblModel = setupTableRegula();
            break;
        case Secant:
            tblModel = setupTableSecant();
            break;
        default:
            Util.Error("Error: Invalid method of numerical analysis.");
            break;
        }
        tblInfo.setModel(tblModel);
    }

    private DefaultTableModel setupTableRegula()
    {
        return new DefaultTableModel(new Object[][] {}, new String[] {"iter", "x0", "x1", "x2", "y0", "y1", "y2" }

        )
        {

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] { String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class };

            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Class getColumnClass(int columnIndex)
            {
                return columnTypes[columnIndex];
            }

            public boolean isCellEditable(int row, int column)
            {
                // all cells false
                return false;
            }
        };
    }

    private DefaultTableModel setupTableSecant()
    {
        return new DefaultTableModel(new Object[][] {}, new String[] { "iter", "x", "f(x)" }

        )
        {

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] { String.class ,Double.class, Double.class };

            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Class getColumnClass(int columnIndex)
            {
                return columnTypes[columnIndex];
            }
            
            public boolean isCellEditable(int row, int column)
            {
                // all cells false
                return false;
            }
        };
    }

    @Override
    public void GraphRequested(GraphParameters parameters)
    {
        graphPanel.removeAll();

        if (parameters.polynomial == null || parameters.polynomial.getTerms().size() == 0)
        {
            Util.Error("Invalid polynomial to graph.");
            return;
        }

        graph = new PolynomialGraph(parameters);

        if (parameters.approach == null)
        {
            graphPanel.add(graph.getPolynomialChart());
            return;
        }

        switch (parameters.approach)
        {
        case Secant:
        case RegulaFalsi:
            graphPanel.add(graph.getScatterChart());
        default:
            break;
        }
    }

    public void InitializeTable(ArrayList<Iteration> iterations, Approach approach)
    {
        ResetTable();
        Object[][] rowEntries;
        InitializeTable(approach);
        switch (approach)
        {

        case RegulaFalsi:
            rowEntries = new Object[iterations.size()][7];

            if (iterations.size() == 0)
                rowEntries = null;

            for (int i = 0; i < iterations.size(); i++)
            {
                Iteration x = iterations.get(i);
                rowEntries[i][0] = new Integer(i + 1);
                rowEntries[i][1] = x.getX0();
                rowEntries[i][2] = x.getX1();
                rowEntries[i][3] = x.getX2();
                rowEntries[i][4] = x.getY0();
                rowEntries[i][5] = x.getY1();
                rowEntries[i][6] = x.getY2();

                tblModel.addRow(rowEntries[i]);
            }
            break;
        case Secant:
            rowEntries = new Object[iterations.size()][3];

            if (iterations.size() == 0)
                rowEntries = null;

            for (int i = 0; i < iterations.size(); i++)
            {
                Iteration x = iterations.get(i);
                rowEntries[i][0] = new Integer(i + 1);
                rowEntries[i][1] = x.getX();
                rowEntries[i][2] = x.getY();

                tblModel.addRow(rowEntries[i]);
            }
            break;
        case Bisection:
        case Newton:
        case Polynomial:
        default:
            break;
        }
    }

    /**
     * This method removes the graph and clears the table.
     */
    public void ResetGraph()
    {
        bottomPanel.remove(graphPanel);
        ResetTable();
    }

    /**
     * This method clears the table.
     */
    public void ResetTable()
    {
        if (tblModel != null)
            while (tblModel.getRowCount() > 0)
                tblModel.removeRow(0);
    }

    public enum ViewAction
    {
        BACK, NEXT
    }

    public class ActionHandler
    {

        public ActionListener Handle(ViewAction e)
        {
            switch (e)
            {
            case BACK:
                return new Back();
            case NEXT:
                return new Next();
            default:
                break;
            }
            return null;
        }

        public final class Back implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                bottomPanel.remove(tablePanel);
                bottomPanel.add(graphPanel);
                btnNext.setEnabled(true);
                btnPrev.setEnabled(false);
                validate();
                repaint();
            }

        }

        public final class Next implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                bottomPanel.remove(graphPanel);
                bottomPanel.add(tablePanel);
                btnNext.setEnabled(false);
                btnPrev.setEnabled(true);
                validate();
                repaint();
            }

        }
    }
}
