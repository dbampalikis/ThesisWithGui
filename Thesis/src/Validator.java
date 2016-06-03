import java.io.File;
import java.io.IOException;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.SAXException;
import com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory;

public class Validator {
	public static void main(String[] args) {
		String result = "Yes";
		try {
			validate("input2.xml", "schemaMarginal.rng");
		} catch (SAXException e) {
			result = "No";
			e.printStackTrace();
		} catch (IOException e) {
			result = "No";
			e.printStackTrace();
		}
		System.out.println(result);
	}
	public static void validate(String xmlFileName, String validationFileName) throws SAXException, IOException {
		(new XMLSyntaxSchemaFactory().newSchema(new File(validationFileName))).newValidator().validate(new StreamSource(new File(xmlFileName)));
	}
}
