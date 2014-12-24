package com.ericsson.uml.util;

import com.ericsson.uml.Constants;
import com.ericsson.uml.ProclogBean;

import java.util.Map;

public class ProclogSVGGenerator extends SVGGenerator {

	public static void createSVGOfProclog(Map<String, ProclogBean> svgMap, String path, String link) throws Exception {
		for (String filename: svgMap.keySet()) {
			SVGGenerator svg = new SVGGenerator(path + "/" + filename);
			svg.setWidth(800);
			svg.setHeight(600); 
			svg.addHead();
			svg.addHyperLink(1, 15, "BACK", "file:///" + link);
			
			ProclogBean bean = svgMap.get(filename);
			if (filename.startsWith("req")) {
				svg.addText(1, 35, bean.getFullrequest(), bean.getProtocol());
			} else {
				svg.addText(1, 35, bean.getFullresponse(), bean.getProtocol());
			}
			svg.addTail();
			svg.createSVG();
		}
	}
	
}
