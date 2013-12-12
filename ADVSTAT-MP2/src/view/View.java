package view;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.bind.Marshaller.Listener;

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

    private static int         numericalAnalysisApproach = -1;

    public View()
    {
        super("Roots of Polynomials");
        setPreferredSize(new Dimension(450, 650));
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
        parameterPanel.setBorder(BorderFactory.createEtchedBorder());
        parameterPanel.setGraphListener(this);
        add(parameterPanel);

        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setPreferredSize(new Dimension(430, 400));

        graphPanel = new JPanel();
        graphPanel.setBorder(BorderFactory.createEtchedBorder());
        graphPanel.setPreferredSize(new Dimension(430, 310));

        tablePanel = new JPanel();
        // tablePanel.setBorder(BorderFactory.createEtchedBorder());
        tablePanel.setPreferredSize(new Dimension(430, 310));

        imgNext = new ImageIcon("icons/next.png");
        imgPrev = new ImageIcon("icons/prev.png");

        btnPanel = new JPanel();
        // btnPanel.setPreferredSize(new Dimension(200,50));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        // Initial creation of JTable
        if (parameterPanel.getCurrentMethod() == ParameterPanel.REGULA_FALSI)
            tblModel = setupTableRegula();
        else
            tblModel = setupTableSecant();

        tblInfo = new JTable(tblModel);

        scroll = new JScrollPane(tblInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(420, 300));

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
     * @param type
     *            - ParameterPanel.REGULA_FALSI or ParameterPanel.SECANT
     */
    public void InitializeTable(int type)
    {
        numericalAnalysisApproach = type;
        switch (type)
        {
        case ParameterPanel.REGULA_FALSI:
            tblModel = setupTableRegula();
            break;
        case ParameterPanel.SECANT:
            tblModel = setupTableSecant();
            break;
        default:
            Util.Error("Error: Invalid method of numerical analysis.");
            break;
        }
        tblInfo.setModel(tblModel);
    }

    public void resetGraph()
    {
        bottomPanel.remove(graphPanel);
        resetTable();
    }

    public void drawGraph(GraphParameters parameters, double begin, double end)
    {
        graphPanel.removeAll();
        if (parameters.polynomial.getTerms().size() == 0)
        {
            JOptionPane.showMessageDialog(this, "Invalid polynomial to graph.", "Invalid polynomial", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[] c = parameters.polynomial.getDoubles();
        graph = new PolynomialGraph(new PolynomialFunction2D(c), "Graph of f(x)", begin, end, parameters.polynomial.toString());
        graphPanel.add(graph.getChart());
        validate();
        // repaint();
    }

    private DefaultTableModel setupTableRegula()
    {
        return new DefaultTableModel(new Object[][] {}, new String[] { "x0", "x1", "x2", "y0", "y1", "y2" }

        )
        {

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] { Double.class, Double.class, Double.class, Double.class, Double.class, Double.class };

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
        return new DefaultTableModel(new Object[][] {}, new String[] { "x", "f(x)" }

        )
        {

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] { Double.class, Double.class };

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
        drawGraph(parameters, GraphParameters.StartX, GraphParameters.EndX);
    }

    public void setTable(ArrayList<Iteration> iterations, String type)
    {
        Double[][] rowEntries;
        InitializeTable(parameterPanel.getCurrentMethod());
        if (type.equalsIgnoreCase("Regula Falsi"))
        {
            rowEntries = new Double[iterations.size()][7];

            if (iterations.size() == 0)
                rowEntries = null;

            for (int i = 0; i < iterations.size(); i++)
            {
                Iteration x = iterations.get(i);
                rowEntries[i][0] = x.getX0();
                rowEntries[i][1] = x.getX1();
                rowEntries[i][2] = x.getX2();
                rowEntries[i][3] = x.getY0();
                rowEntries[i][4] = x.getY1();
                rowEntries[i][5] = x.getY2();

                tblModel.addRow(rowEntries[i]);
            }
        }
        else if (type.equalsIgnoreCase("Secant"))
        {
            rowEntries = new Double[iterations.size()][3];

            if (iterations.size() == 0)
                rowEntries = null;

            for (int i = 0; i < iterations.size(); i++)
            {
                Iteration x = iterations.get(i);
                rowEntries[i][0] = x.getX();
                rowEntries[i][1] = x.getY();

                tblModel.addRow(rowEntries[i]);
            }
        }
    }

    public void resetTable()
    {
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
