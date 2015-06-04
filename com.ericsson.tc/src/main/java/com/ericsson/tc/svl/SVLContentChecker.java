package com.ericsson.tc.svl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;

import com.ericsson.tc.ConfigTool;
import com.ericsson.tc.doc2400.Doc2400Bean;
import com.ericsson.tc.doc2400.Doc2400Parser;
import com.ericsson.tc.pdi.GETDOC;
import com.ericsson.tc.pdi.LSIS;
import com.ericsson.tc.util.pdf.PdfReader;

/**
 * SVLContentChecker checks the correctness of SVL
 * @author eyimdai
 *
 */
public class SVLContentChecker {
	private Logger logger = Logger.getLogger(SVLContentChecker.class);
	private SVLReader reader;
	
	public SVLContentChecker(SVLReader reader) {
		this.reader = reader;
	}
	
	public void checkSVL() {
		try {
			reader.readSVL();
			
			// iterate through each 3PP
			while (reader.hasNext()) {
				SVL3PPBean svl3ppBean = reader.next();
				Doc2400Bean doc2400Bean = generate2400Bean(svl3ppBean);
				
				SVLand2400comparator comparator = new SVLand2400comparator(svl3ppBean, doc2400Bean);
				if (!comparator.compare()) {
					logger.info("SVL and 2400 result don't match");
				} else {
					logger.info("SVL and 2400 result match");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	private Doc2400Bean generate2400Bean(SVL3PPBean svl3ppBean) throws IOException {
		// List the Information Structure of product in PRIM
		String[] prodNoWithRstate = svl3ppBean.getEricProdNo().split(" ");
		LSIS lsis = new LSIS(prodNoWithRstate[0], prodNoWithRstate[1]); 
		try {
			lsis.exec();
		} catch (IOException ex) {
			logger.error("Fail to list information structure of product.");
			throw ex;
		}
		
		// Download 2400 doc of the product
		GETDOC getdoc = new GETDOC(lsis.getChildNoE(), lsis.getChildLanguage(), lsis.getChildRevision());
		String doc2400Path = null;
		try {			
			getdoc.exec();
			doc2400Path = getdoc.getResultFilename();
		} catch (IOException ex) {
			logger.error("Fail to get 2400 doc.");
			throw ex;
		}
		
		Doc2400Bean doc2400Bean = null;
		if (doc2400Path != null) {
			PdfReader reader = new PdfReader(doc2400Path);
			Doc2400Parser parser = new Doc2400Parser(reader);
			parser.parse();
			doc2400Bean = parser.getDoc2400Bean();
		} else {
			logger.error("Fail to get 2400 doc path.");
		}
		
		return doc2400Bean;
	}
	
	public static void main(String...args) throws IOException {
		ConfigTool.loadConfig();
//		SVLReader reader = new SVLReader("src/test/java/com/ericsson/tc/poi/", "SVL.xls", SVLReader.PGN_Commercial );
		SVLReader reader = new SVLReader("src/test/java/com/ericsson/tc/poi/", "SVL.xls", SVLReader.PGN_FOSS );
		SVLContentChecker checker = new SVLContentChecker(reader);
		checker.checkSVL();
	}

}
