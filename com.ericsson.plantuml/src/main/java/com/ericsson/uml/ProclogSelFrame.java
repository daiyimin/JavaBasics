package com.ericsson.uml;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProclogSelFrame extends JFrame implements ActionListener{
    private JLabel label1 = new JLabel("File:");  
    private JTextField text1 = new JTextField();
    private JFileChooser jfc = new JFileChooser(); 
    private JButton browseButton = new JButton("Select");
    ColorTableModel model = null;  
    private JTable proclogIdTable = null;
    private JButton transButton = new JButton("ConvertUML");
    private JButton exitButton = new JButton("Exit");
    
	private Map<String, String> configs;
    List<Object> proclogIdList = new LinkedList<Object>();

    private void initFrame() {
        jfc.setDialogTitle("Select a proclog file");
        jfc.setCurrentDirectory(new File("c://"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Proclog file", "csv");
        jfc.setFileFilter(filter);
        
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
        setLocation(new Point((int) (lx / 2) - 225, (int) (ly / 2) - 200));
        setSize(450, 400);// set window size
        
		// Layout
        BorderLayout frameLayout = new BorderLayout();
        this.getContentPane().setLayout(frameLayout);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// File Select Panel
        JPanel proclogIdSelPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        proclogIdSelPanel.setLayout(borderLayout);

        browseButton.addActionListener(this); // 添加事件处理  
        proclogIdSelPanel.add(label1, BorderLayout.WEST);  
        proclogIdSelPanel.add(text1, BorderLayout.CENTER);  
        proclogIdSelPanel.add(browseButton, BorderLayout.EAST);  
        this.add(proclogIdSelPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        BorderLayout borderLayout1 = new BorderLayout();
        centerPanel.setLayout(borderLayout1);
        
        model = new ColorTableModel();
        model.setRowData(new ArrayList<Object>());
        proclogIdTable = new JTable(model);
        model.setTable(proclogIdTable);
        proclogIdTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(proclogIdTable);
        centerPanel.add(scrollPane, BorderLayout.NORTH);
        setVisible(true);
        
        this.add(scrollPane, BorderLayout.CENTER);
        
		JPanel buttonPanel = new JPanel();
        transButton.addActionListener(this);
        exitButton.addActionListener(this);
        buttonPanel.add(transButton, BorderLayout.CENTER);
        buttonPanel.add(exitButton, BorderLayout.EAST);

        this.add(buttonPanel, BorderLayout.SOUTH);
        
		this.setVisible(true);
		this.setResizable(true);
	}
    
    public ProclogSelFrame(Map<String, String> configs) {
		super(Constants.FRAME_TITLE);
    	this.configs = configs;
    	initFrame();
    }
    
	public ProclogSelFrame() {
		super(Constants.FRAME_TITLE);
    	initFrame();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
	        if (e.getSource().equals(browseButton)) {
	        	openFolder();
	        }  else if (e.getSource().equals(exitButton)) {
	        	closeWindow();
	        }  else if (e.getSource().equals(transButton)) {
	        	transformUML();
	        }	        
		} catch (IOException e1) {
		}

	}

    private void openFolder() throws IOException {
        int state = jfc.showOpenDialog(this);// 此句是打开文件选择器界面的触发语句  
        if (state == 1) {  
            return;  
        } else {  
            File f = jfc.getSelectedFile();// f为选择到的目录
            String file = f.getPath();
            text1.setText(file);
            
            ProclogReader reader = new ProclogReader(file);
            Set<String> proclogIds = reader.readProclogId();
            
            proclogIdList = new LinkedList<Object>();
            for (String proclogId : proclogIds) {
                proclogIdList.add(new Object[] {proclogId,  Boolean.FALSE});
            }
            model.setRowData(proclogIdList);
            model.fireTableChanged(null, 200);

        }
        
    }
    
    private void closeWindow() {
    	System.exit(0);
    }
    
    
    private void transformUML() throws IOException {
		String selectedLogId = "";
		int count = 0;
		for (Object proclogSel : proclogIdList) {
			Object[] sel = (Object[]) proclogSel;
			String rootProclogId = (String)sel[0];
			Boolean selected = (Boolean)sel[1]; 
			if (selected) {
				selectedLogId = rootProclogId;
				count++;
			}
		}
		if (count > 1) {
    		JOptionPane.showMessageDialog(this, "Only support one proclogId selection");
    		return;
		} else if (count == 0) {
    		JOptionPane.showMessageDialog(this, "Please select one proclogId");
    		return;
		}
		
		ProclogReader reader = new ProclogReader(text1.getText());
		reader.readProclog(selectedLogId);
		
		String uml = reader.getUML();
		System.out.println(uml);
		
		String pngfile = configs.get("outputdir") + "/" + selectedLogId + ".png";
		PlantUMLUtil.transfromStringToUML(uml, pngfile);
		
		Picture picture = new Picture(pngfile);
		picture.show();
    }
	
    public static void main(String[] args) {
    	new ProclogSelFrame();
    }

}
