package com.ericsson.uml;

import java.awt.BorderLayout;  
import java.util.LinkedList;
import java.util.List;  
  
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTable;  
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;  
  
public class ColorTableModel extends AbstractTableModel {  
  
    private static final long serialVersionUID = 1L;  
      
    private List<Object> rowData;
    
    private JTable table;
  
    public void setTable(JTable table) {
		this.table = table;
	}

	public List<Object> getRowData() {  
        return rowData;  
    }  
  
    public void setRowData(List<Object> rowData) {  
        this.rowData = rowData;  
    }  
  
    String columnNames[] = { "RootProclogId", "Selected" };  
  
    public int getColumnCount() {  
        return columnNames.length;  
    }  
  
    public String getColumnName(int column) {  
        return columnNames[column];  
    }  
  
    public int getRowCount() {  
        return rowData.size();  
    }  
  
    public Object getValueAt(int row, int column) {  
        Object[] obj = (Object[]) rowData.get(row);  
        return obj[column];
    }  
  
    @SuppressWarnings("rawtypes")  
    public Class getColumnClass(int column) {  
        return (getValueAt(0, column).getClass());  
    }  
  
    public void setValueAt(Object value, int row, int column) {
    	
        Object[] obj = (Object[]) rowData.get(row);  
        obj[column] = value;
    }  
  
    public boolean isCellEditable(int row, int column) {  
        return (column == 1);  
    }  
    
	public void fireTableChanged(TableModelEvent e, int colWidth) {
		super.fireTableChanged(e);
        table.getColumnModel().getColumn(0).setPreferredWidth(colWidth);
	}

/*	public static void main(String[] args) {  
        JFrame frame = new JFrame("Hello");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        ColorTableModel model = new ColorTableModel();  
        List<Object> rowData = new LinkedList<Object>();  
        rowData.add(new Object[] { "1。。。。。。。。。。。。。。。。。。。。。。。", Boolean.TRUE });  
        rowData.add(new Object[] { "2", Boolean.TRUE });  
        rowData.add(new Object[] { "3", Boolean.FALSE });  
          
        model.setRowData(rowData);  
        JTable table = new JTable(model);  
        JScrollPane scrollPane = new JScrollPane(table);  
        frame.add(scrollPane, BorderLayout.CENTER);  
        frame.setSize(400, 150);  
        frame.setVisible(true);  
    }  
*/
}  