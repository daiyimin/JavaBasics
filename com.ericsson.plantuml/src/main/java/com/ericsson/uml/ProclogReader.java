package com.ericsson.uml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.opencsv.CSVReader;

public class ProclogReader {
	/**
	 * proclog file
	 */
	File file = null;
	/**
	 * csvIndex is used to get cell index by using cell name<br/>
	 * key is CSV heading cell name, like RootLogId<br/>
	 * value is its index in heading, like 1
	 */
	private Map<String, Integer> csvIndex = null;
	/**
	 * proclogMap saves all proclogs for the specified RootLogId key is subLogId<br/>
	 * (for northbound, use 0 as subLogId) value is the list of proclog which<br/>
	 * belong to that subLogId<br/>
	 * String[] in the list represents String array of one CSV line 
	 */
	private TreeMap<String, List<String[]>> proclogMap = null;
	
	/**
	 * svgList saves all southbound logs that will be saved as svg files<br/>
	 * keys is svg filename<br/>
	 * value is proclog bean<br/>
	 */
	private Map<String, ProclogBean> svgMap = null;

	public ProclogReader(String filename) {
		file = new File(filename);
	}

	public Map<String, Integer> getCSVIndex() {
		return csvIndex;
	}

	/**
	 * Get the cell value from a proclog by specifying cell name
	 * 
	 * @param line
	 *            proclog
	 * @param csvCellName
	 *            cell name
	 * @return cell value
	 */
	private String getCSVCellValue(String[] line, String csvCellName) {
		if (csvIndex == null)
			return null;
		return line[csvIndex.get(csvCellName)];
	}

	/**
	 * read root log id
	 * 
	 * @return
	 * @throws IOException
	 */
	public Set<String> readProclogId() throws IOException {
		csvIndex = new HashMap<String, Integer>();

		FileReader fReader = new FileReader(file);
		CSVReader csvReader = new CSVReader(fReader);
		// skip heading line
		String[] strs = csvReader.readNext();
		int colNum = strs.length;
		if (strs != null && colNum > 0) {
			// put CSV column names into csvIndex
			for (int i = 0; i < colNum; i++) {
				String str = strs[i];
				if (null != str && !str.equals("")) {
					csvIndex.put(strs[i], new Integer(i));
				}
			}
		}

		Set<String> proclogIdSet = new HashSet();
		List<String[]> list = csvReader.readAll();
		for (String[] line : list) {
			ProclogBean logbean = new ProclogBean(line, csvIndex);
			proclogIdSet.add(logbean.getRootLogId());
		}
		return proclogIdSet;
	}

	public TreeMap<String, List<String[]>> readProclog(String rootLogId)
			throws IOException {
		csvIndex = new HashMap<String, Integer>();
		proclogMap = new TreeMap<String, List<String[]>>(
				new SubLogIdComparator());

		FileReader fReader = new FileReader(file);
		CSVReader csvReader = new CSVReader(fReader);
		// read CSV file heading
		String[] strs = csvReader.readNext();
		int colNum = strs.length;
		if (strs != null && colNum > 0) {
			// put CSV column names into csvIndex
			for (int i = 0; i < colNum; i++) {
				String str = strs[i];
				if (null != str && !str.equals("")) {
					csvIndex.put(strs[i], new Integer(i));
				}
			}
		}

		// read proclog lines
		List<String[]> list = csvReader.readAll();
		for (String[] line : list) {
			ProclogBean logbean = new ProclogBean(line, csvIndex);
			if (line.length != colNum) {
				continue; // this is a invalid line?
			}
			if (!rootLogId.equals(logbean.getRootLogId())) {
				continue; // not the logId being searched for
			}

			String logType = logbean.getLogType();
			List<String[]> proclogs = null;
			if (Constants.NORTHBOUND.equals(logType)) {
				proclogs = new ArrayList<String[]>();
				proclogs.add(line);
				proclogMap.put("0", proclogs);
			} else {
				// assume that in proclog file, proclogs of same subLogId are
				// written by time sequence
				// so we put these logs into a list by their sequence in file
				String subLogId = logbean.getSubLogId();
				if (proclogMap.get(subLogId) == null) {
					proclogs = new ArrayList<String[]>();
				} else {
					proclogs = proclogMap.get(subLogId);
				}
				proclogs.add(line);
				proclogMap.put(subLogId, proclogs);
			}
		}
		fReader.close();
		return proclogMap;
	}

	class SubLogIdComparator implements Comparator {
		@Override
		public int compare(Object subLogId0, Object subLogId1) {
			if (subLogId0 == null || subLogId1 == null) {
				if (subLogId0 != null) {
					return 1;
				}
				if (subLogId1 != null) {
					return -1;
				}
				return 0;
			}

			String logId0 = (String) subLogId0;
			String logId1 = (String) subLogId1;

			// "0" represent northbound proclog, it should be the first one
			if ("0".equals(logId0)) {
				return -1;
			}
			if ("0".equals(logId1)) {
				return 1;
			}

			// remove "/" in subLogId
			int id0 = Integer.valueOf(logId0.replaceAll("/", ""));
			int id1 = Integer.valueOf(logId1.replaceAll("/", ""));
			if (id0 < id1) {
				return -1;
			} else if (id0 > id1) {
				return 1;
			} else {
				return 0;
			}
		}

	}
	
	public Map<String, ProclogBean> getSVGMap() {
		return svgMap;
	}

	/**
	 * Transform proclog map into plant UML string
	 * 
	 * @return
	 */
	public String getUML() {
		String uml = "@startuml\n";
		svgMap = new HashMap<String, ProclogBean>();

		Iterator<Entry<String, List<String[]>>> iter = proclogMap.entrySet()
				.iterator();

		// first proclog is northbound proclog
		List<String[]> proclogs = iter.next().getValue();
		String[] northLog = proclogs.get(0);
		ProclogBean northbean = new ProclogBean(northLog, csvIndex);
		svgMap.put("req0.svg", northbean);
		svgMap.put("res0.svg", northbean);

		uml += "hide footbox\n";
		uml += "actor CAS\n";
		// uml += "skinparam backgroundcolor AntiqueWhite\n";
		uml += "skinparam backgroundColor #EEEBDC\n";

		// uml += "skinparam classBackgroundColor Wheat|CornflowerBlue\n";
		uml += "skinparam sequence { \n" + "ArrowColor DeepSkyBlue \n"
				+ "ActorBorderColor DeepSkyBlue \n"
				+ "LifeLineBorderColor blue \n"
				+ "LifeLineBackgroundColor #A9DCDF\n"
				+ "ParticipantBorderColor DeepSkyBlue \n"
				+ "ParticipantBackgroundColor DodgerBlue \n"
				+ "ParticipantFontName Impact \n"
				+ " ParticipantFontSize 15 \n"
				+ "ParticipantFontColor #A9DCDF \n"
				+ "ActorBackgroundColor aqua \n"
				+ "ActorFontColor DeepSkyBlue \n" + "ActorFontSize 17 \n"
				+ "ActorFontName Aapex \n" + "}\n";

		uml += "title " + northbean.getOperation() + " "
				+ northbean.getTarget() + "\n";
		uml += "CAS -> PGNGN: [[file:///@SVGFILEPATH@/req0.svg]] " + northbean.getOperation() + "\n";

		int svgFileNum = 0;
		String svgFilename = null;
		List<String> svgContent = null;
		while (iter.hasNext()) {
			proclogs = iter.next().getValue();

			for (String[] proclog : proclogs) {
				ProclogBean logbean = new ProclogBean(proclog, csvIndex);
				if (Constants.SOUTHBOUND.equals(logbean.getLogType())) {
					// save information for create svg
					svgFileNum++;
					svgFilename = "req" + new Integer(svgFileNum).toString() + ".svg";
					svgMap.put(svgFilename, logbean);
					
					String request = null;
					if (logbean.getFullrequest().length() <= 40) {
						request = logbean.getFullrequest();
					} else {
						request = logbean.getFullrequest().substring(0, 40);
					}
					uml += "PGNGN -> " + logbean.getTarget() + ": [[file:///@SVGFILEPATH@/" +
							svgFilename + "]] " +
							request + "......\n";
					
					svgFilename = "res" + new Integer(svgFileNum).toString() + ".svg";
					svgMap.put(svgFilename, logbean);
					if ("SUCCESSFUL".equals(logbean.getStatus())) {
						uml += logbean.getTarget() + "-> PGNGN: "+ "[[file:///@SVGFILEPATH@/" + 
								svgFilename + "]] " + "Resp code "
								+ logbean.getResponseCode() + "\n";
					} else {
						uml += logbean.getTarget()
								+ "-[#red]> PGNGN: "+ "[[file:///@SVGFILEPATH@/" + 
								svgFilename + "]] " + "<font color=red><b> (Failed) Resp code "
								+ logbean.getResponseCode() + "\n";
						uml += "destroy " + logbean.getTarget() + "\n";
					}

					// uml += logbean.getTarget() + " -> PGNGN: " +
					// logbean.getOperation() + " response\n";
				} else {
					continue; // impossible?
				}
			}
		}
		if ("SUCCESSFUL".equals(northbean.getStatus())) {
			uml += "PGNGN -> CAS: [[file:///@SVGFILEPATH@/res0.svg]] " + northbean.getOperation() + " response\n";
		} else {
			uml += "PGNGN -[#red]> CAS:  [[file:///@SVGFILEPATH@/res0.svg]] <font color=red><b> (Failed)"
					+ northbean.getOperation() + " response\n";
		}

		// uml += "PGNGN -> CAS: " + northbean.getOperation() + " response\n";
		uml += "@enduml\n";
		return uml;
	}
}
