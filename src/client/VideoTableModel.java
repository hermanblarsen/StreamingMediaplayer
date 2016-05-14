package src.client;

import javax.swing.table.AbstractTableModel;

public class VideoTableModel extends AbstractTableModel {
	private String[] columnNames = 
			{"Title",
			"Duration",
			"Favourite",
			"Tags",
			"Last Watched",
			"Genre",
			"Rating"};
	private Object[][] tableData;// = new Object[100][columnNames.length];
	
	public VideoTableModel() {
		
	}
	
	public VideoTableModel(int numberOfVideoFiles) {
		this.tableData = new Object[numberOfVideoFiles][columnNames.length];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return tableData.length;
	}

	public String getColumnName(int columnIndex) {
		return this.columnNames[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.tableData[rowIndex][columnIndex];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.tableData[rowIndex][columnIndex] = aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
		fireTableDataChanged();
	}
	
	public void setTableDataSize(int numberOfVideos) {
		tableData = new Object[numberOfVideos][columnNames.length];
		fireTableDataChanged();
	}
}
