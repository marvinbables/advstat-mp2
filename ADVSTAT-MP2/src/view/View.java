package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

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
import javax.xml.bind.Marshaller.Listener;




import org.jfree.data.function.PolynomialFunction2D;

import view.listeners.GraphListener;
import view.listeners.MenuListener;


@SuppressWarnings("serial")
public class View extends JFrame implements GraphListener{
	private ParameterPanel parameterPanel;
	private JPanel graphPanel, btnPanel, tablePanel, buttomPanel;
	private PolynomialGraph graph;
	private JButton btnNext, btnPrev;
	private ImageIcon imgNext, imgPrev;
	private DefaultTableModel tblModel;
	private JTable tblInfo;
	private JScrollPane scroll;
	
	public View() {
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
		
		buttomPanel = new JPanel();
		buttomPanel.setBorder(BorderFactory.createEtchedBorder());
		buttomPanel.setPreferredSize(new Dimension(430, 400));
		
		
		graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createEtchedBorder());
		graphPanel.setPreferredSize(new Dimension(430, 310));
		
		tablePanel = new JPanel();
	//	tablePanel.setBorder(BorderFactory.createEtchedBorder());
		tablePanel.setPreferredSize(new Dimension(430, 310));
		
		imgNext = new ImageIcon("icons/next.png");
		imgPrev = new ImageIcon("icons/prev.png");
		
		btnPanel = new JPanel();
	//	btnPanel.setPreferredSize(new Dimension(200,50));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		
		tblModel = setupTable();
		tblInfo = new JTable(tblModel);
	//	tblInfo.setPreferredSize(new Dimension(400,370));
		
		scroll = new JScrollPane(tblInfo, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(420, 300));
	
		tablePanel.add(scroll);
		
		btnNext = parameterPanel.newButton(imgNext, parameterPanel);
		
		btnPrev = parameterPanel.newButton(imgPrev, parameterPanel);
		btnPrev.setEnabled(false);
		
		btnNext.setMnemonic(java.awt.event.KeyEvent.VK_RIGHT);
		btnPrev.setMnemonic(java.awt.event.KeyEvent.VK_LEFT);
		

		btnPanel.add(btnPrev);
		btnPanel.add(Box.createRigidArea(parameterPanel.getDimensions("small")));
		btnPanel.add(btnNext);
		
		add(btnPanel);
		
	//	add(graphPanel);
		buttomPanel.add(graphPanel);
		add(buttomPanel);
		
		pack(); 
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void nextButton(){
		
		buttomPanel.remove(graphPanel);
		buttomPanel.add(tablePanel);
		btnNext.setEnabled(false);
		btnPrev.setEnabled(true);
		validate();
	}
	public void prevButton(){
		
		buttomPanel.remove(tablePanel);
		buttomPanel.add(graphPanel);
		btnNext.setEnabled(true);
		btnPrev.setEnabled(false);
		validate();
	}
	public void resetGraph(){
		buttomPanel.remove(graphPanel);
	}
	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JButton getBtnPrev() {
		return btnPrev;
	}

	public void setBtnPrev(JButton btnPrev) {
		this.btnPrev = btnPrev;
	}

	public void drawGraph(GraphParameters parameters, double begin, double end){
		graphPanel.removeAll();
		if (parameters.polynomial.getTerms().size() == 0)
		{
			JOptionPane.showMessageDialog(this, "Invalid polynomial to graph.", "Invalid polynomial", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
 		double[] c = parameters.polynomial.getDoubles();
		graph = new PolynomialGraph(new PolynomialFunction2D(c), "Sample graph", begin, end);
		graphPanel.add(graph.getChart());
		validate();
	//	repaint();
	}
	private DefaultTableModel setupTable(){
		return new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"x0", "x1", "x2", "y0", "y1", "y2"
						}
				
			) {
		
			@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					Double.class, Double.class, Double.class, Double.class, Double.class, Double.class
				};
			@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			    public boolean isCellEditable(int row, int column) {
			        //all cells false
			        return false;
			    }
			};
	}
	
	public void addButtonActionListener(ActionListener l){
		btnNext.addActionListener(l);
		btnPrev.addActionListener(l);
	}
	@Override
	public void GraphRequested(GraphParameters parameters) {
		drawGraph(parameters, GraphParameters.StartX, GraphParameters.EndX);
	}
	
}
