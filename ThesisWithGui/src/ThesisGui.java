import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory;

import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;


public class ThesisGui {

	private JFrame frame;
	private TextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThesisGui window = new ThesisGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static boolean validation(String xmlFileName, String validationFileName) {
		try {
			validate(xmlFileName, validationFileName);
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			//e.printStackTrace();
		}
		return true;
	}
	
	public static void validate(String xmlFileName, String validationFileName) throws SAXException, IOException {
		(new XMLSyntaxSchemaFactory().newSchema(new File(validationFileName))).newValidator().validate(new StreamSource(new File(xmlFileName)));
	}

	/**
	 * Create the application.
	 */
	public ThesisGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		OpenFile of = new OpenFile();
		
		Button button = new Button("Validate!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validation(of.fileName.toString(), "schemaHealthy.rng")) {
					textField.setText("Healthy");
				}
				else if(validation(of.fileName.toString(), "firstSchematron.xsl")) {
					textField.setText("Schematron!");
				}
				else if(validation(of.fileName.toString(), "schemaMarginal.rng")) {
					textField.setText("Marginal");
				}
				else if(validation(of.fileName.toString(), "schemaSick.rng")) {
					textField.setText("Sick");
				}
				else {
					textField.setText("Out of described range or problematic input file");
				}
			}
		});

		textField = new TextField();
		textField.setBounds(70, 181, 289, 22);
		frame.getContentPane().add(textField);
		button.setBounds(161, 104, 103, 44);
		frame.getContentPane().add(button);
		
		JLabel label = new JLabel("Filename");
		label.setBounds(10, 11, 362, 14);
		frame.getContentPane().add(label);
		JButton button_1 = new JButton("...");
		button_1.setBounds(232, 63, 28, 23);
		frame.getContentPane().add(button_1);
		
		JLabel lblSelectfile = new JLabel("SelectFile");
		lblSelectfile.setBounds(161, 67, 46, 14);
		frame.getContentPane().add(lblSelectfile);
		
		
		//Function for calling the FileChooser after button 
		//click and getting back the name of the file
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					of.pick();
					if(of.fileName!=null){
						label.setText(of.fileName.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});	
	}
}
