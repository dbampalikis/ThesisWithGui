import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

public class SchemaCreation {

	private JFrame frmRelaxng;
	private JTextArea resultText;
	private List<JTextField> valueText = new ArrayList<JTextField>();
	private List<JTextField> minText = new ArrayList<JTextField>();
	private List<JTextField> maxText = new ArrayList<JTextField>();
	private ButtonGroup gender = new ButtonGroup();
	private JRadioButton maleRadioButton = new JRadioButton("Male");
	private JRadioButton femaleRadioButton = new JRadioButton("Female");
	private JRadioButton otherRadioButton = new JRadioButton("Other");
	
	private int y = 35, x = 10, wv = 155, h = 20, wm = 41;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchemaCreation window = new SchemaCreation();
					window.frmRelaxng.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchemaCreation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmRelaxng = new JFrame();
		frmRelaxng.setTitle("RelaxNG");
		frmRelaxng.setBounds(100, 100, 493, 615);
		frmRelaxng.setSize(500, 600);
		frmRelaxng.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRelaxng.getContentPane().setLayout(null);
				
		JButton addFieldButton = new JButton("Add Field");
		addFieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addField();
			}

			private void addField() {
				y += 30;
				valueText.add(new JTextField());
				int index = valueText.size()-1;
				valueText.get(index).setBounds(x, y, wv, h);
				frmRelaxng.getContentPane().add(valueText.get(index));
				valueText.get(index).setColumns(10);
				
				minText.add(new JTextField());
				minText.get(index).setBounds(x+165, y, wm, h);
				frmRelaxng.getContentPane().add(minText.get(index));
				minText.get(index).setColumns(10);
				
				maxText.add(new JTextField());
				maxText.get(index).setBounds(x+215, y, wm, h);
				frmRelaxng.getContentPane().add(maxText.get(index));
				maxText.get(index).setColumns(10);

				frmRelaxng.pack();
				frmRelaxng.setSize(500, 600);
			}
		});
		addFieldButton.setBounds(335, 158, 89, 23);
		frmRelaxng.getContentPane().add(addFieldButton);
		
		
		valueText.add(new JTextField());
		valueText.get(0).setBounds(x, y, wv, h);
		frmRelaxng.getContentPane().add(valueText.get(0));
		valueText.get(0).setColumns(10);
		
		minText.add(new JTextField());
		minText.get(0).setBounds(x+165, y, wm, h);
		frmRelaxng.getContentPane().add(minText.get(0));
		minText.get(0).setColumns(10);
		
		maxText.add(new JTextField());
		maxText.get(0).setBounds(x+215, y, wm, h);
		frmRelaxng.getContentPane().add(maxText.get(0));
		maxText.get(0).setColumns(10);
		
		Label fieldLabel = new Label("Field");
		fieldLabel.setBounds(10, 10, 62, 22);
		frmRelaxng.getContentPane().add(fieldLabel);
		
		JLabel minLabel = new JLabel("Min");
		minLabel.setBounds(175, 10, 46, 14);
		frmRelaxng.getContentPane().add(minLabel);
		
		JLabel maxLabel = new JLabel("Max");
		maxLabel.setBounds(226, 10, 46, 14);
		frmRelaxng.getContentPane().add(maxLabel);
		
		JButton doneButton = new JButton("Done!");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFieldButton.setEnabled(false);
				healthySchema();
				marginalSchema();
				sickSchema();
			}
			
			private void healthySchema() {
				initializeRelaxNG();
				createRelaxNG(0);
				saveRelaxNG("schema.rng");
				
			}
			
			private void marginalSchema() {
				initializeRelaxNG();
				createRelaxNG(5);
				saveRelaxNG("schema2M.rng");
			}
			
			private void sickSchema() {
				initializeRelaxNG();
				createRelaxNG(10);
				saveRelaxNG("schema3S.rng");
				
			}

			private void initializeRelaxNG() {
				resultText.setText("<element name=\"patientData\" xmlns=\"http://relaxng.org/ns/structure/1.0\"\r\n");
				resultText.append("datatypeLibrary=\"http://www.w3.org/2001/XMLSchema-datatypes\">\r\n");
				if(maleRadioButton.isSelected()) {
					resultText.append("\t<element name = \"gender\">male</element>\r\n");
				}				
				else if(maleRadioButton.isSelected()) {
					resultText.append("\t<element name = \"gender\">female</element>\r\n");
				}
				else if(otherRadioButton.isSelected()) {
					resultText.append("\t<element name = \"gender\">other</element>\r\n");
				}
			}

			private void createRelaxNG(float percentage) {
				for (int i = 0; i < valueText.size(); i++) {
					float min = Float.parseFloat(minText.get(i).getText().toString());
					min = min - (percentage/100)*min;
					float max = Float.parseFloat(maxText.get(i).getText().toString());
					max = max + (percentage/100)*max;
					addField(valueText.get(i), min, max);
				}
			}

			private void addField(JTextField valueText, float minText, float maxText) {
				resultText.append("\t<element name = \""+valueText.getText()+"\">\r\n");
				resultText.append("\t\t<data type = \"float\">\r\n");
				resultText.append("\t\t\t<param name = \"minInclusive\">"+minText+"</param>\r\n");
				resultText.append("\t\t\t<param name = \"maxInclusive\">"+maxText+"</param>\r\n");
				resultText.append("\t\t</data>\r\n\t</element>\r\n");
			}
			
			private void saveRelaxNG(String filename) {
				resultText.append("</element>\r\n");
				try {
					PrintWriter out = new PrintWriter("C:\\Users\\obscure\\JavaRepository\\ThesisWithGui\\"+filename);
					out.println(resultText.getText().toString());
					out.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					resultText.setText("fail");
					e1.printStackTrace();
				}
			}
		});
		
		doneButton.setBounds(335, 192, 89, 23);
		frmRelaxng.getContentPane().add(doneButton);
		
		resultText = new JTextArea();
		resultText.setWrapStyleWord(true);
		resultText.setEditable(false);
		resultText.setBounds(10, 342, 457, 191);
		frmRelaxng.getContentPane().add(resultText);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(335, 10, 46, 14);
		frmRelaxng.getContentPane().add(lblGender);
		
		maleRadioButton.setBounds(335, 31, 109, 23);
		frmRelaxng.getContentPane().add(maleRadioButton);
		
		femaleRadioButton.setBounds(335, 57, 109, 23);
		frmRelaxng.getContentPane().add(femaleRadioButton);
		
		otherRadioButton.setBounds(335, 83, 109, 23);
		frmRelaxng.getContentPane().add(otherRadioButton);
		
		gender.add(maleRadioButton);
		gender.add(femaleRadioButton);
		gender.add(otherRadioButton);
	}
}
