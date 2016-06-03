import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenFile {

	public File fileName;
	
	public void pick() throws Exception{
	
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		File directory = new File("C:\\Users\\obscure\\JavaRepository\\ThesisWithGui\\");
		//File directory = new File("user.dir");

		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(directory);
		
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile();
        }
		else{
			// Decide what will be done when no file is chosen
		}
		
	}
}
