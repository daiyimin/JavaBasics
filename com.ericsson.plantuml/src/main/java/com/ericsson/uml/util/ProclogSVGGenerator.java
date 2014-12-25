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
			int nextY = svg.addHyperLink(1, 15, "BACK", "file:///" + link);
			
			ProclogBean bean = svgMap.get(filename);
			
			if (filename.startsWith("req0")) { // northbound request
				nextY = svg.addText(1, nextY, bean.getFullrequest(), bean.getProtocol());
			} else if (filename.startsWith("res0")){ // northbound response
				nextY = svg.addText(1, nextY, bean.getFullresponse(), bean.getProtocol());
			} else { // southbound request & response
				nextY = svg.addH0(1, nextY, "SubLogId:");
				nextY = svg.addText(1, nextY, bean.getSubLogId(), Constants.LDAP_PROTOCOL); // add plain text, protocol is not important
				nextY = svg.addH0(1, nextY + 8, "FullRequest:");
				nextY = svg.addText(1, nextY, bean.getFullrequest(), bean.getProtocol());
				nextY = svg.addH0(1, nextY + 8, "FullResponse:");
				nextY = svg.addText(1, nextY, bean.getFullresponse(), bean.getProtocol());
				nextY = svg.addH0(1, nextY + 8, "ResponseCode:");
				nextY = svg.addText(1, nextY, bean.getResponseCode(), bean.getProtocol());
			}

			nextY = svg.addHyperLink(1, nextY + 8, "BACK", "file:///" + link);

			svg.addTail();
			svg.createSVG();
		}
	}
	
}
