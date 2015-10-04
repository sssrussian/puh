package puh;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
//
import javax.net.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class prog extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	//метод для GET запроса
	private String makeGet(){
		String rez = "?";
		
		for( int i=0;i<10;i++){
			rez+= ("tov"+(i+1)+"=");
			
			int kol;
			
			try {
				String str = table.getValueAt(i, 3).toString().trim();
				kol = Integer.parseInt(str);
			}
			catch (Exception e){
				kol = 0;
			}
			
			rez+=""+kol+"&";
		}
		
		return rez;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prog frame = new prog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public prog() {
		setResizable(false);
		setTitle("\u041F\u043E\u0441\u0442\u0443\u043F\u043B\u0435\u043D\u0438\u0435 \u0442\u043E\u0432\u0430\u0440\u0430");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 534, 265);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "\u041C\u0451\u0434", null, "10"},
				{"2", "\u041F\u0440\u043E\u043F\u043E\u043B\u0438\u0441", null, "20"},
				{"3", "\u0412\u043E\u0441\u043A", null, "30"},
				{"4", "\u041F\u044B\u043B\u044C\u0446\u0430", null, "40"},
				{"5", "\u041F\u0435\u0440\u0433\u0430", null, null},
				{"6", "\u041C\u043E\u043B\u043E\u0447\u043A\u043E \u043C\u0430\u0442", null, null},
				{"7", "\u041C\u043E\u043B\u043E\u0447\u043A\u043E \u0442\u0440\u0443\u0442", null, null},
				{"8", "\u042F\u0434", null, null},
				{"9", "\u041E\u0433\u043D\u0451\u0432\u043A\u0430", null, null},
				{"10", "\u041F\u043E\u0434\u043C\u043E\u0440", null, "90"},
			},
			new String[] {
				"\u2116", "\u041D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435 \u0442\u043E\u0432\u0430\u0440\u0430", "\u041E\u0441\u0442\u0430\u0442\u043E\u043A \u043D\u0430 \u0441\u043A\u043B\u0430\u0434\u0435", "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0441\u0442\u0443\u043F\u043B\u0435\u043D\u0438\u044F"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setMinWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setMinWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMinWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setMinWidth(150);
		
		JButton buttonOk = new JButton("\u0412\u044B\u043F\u043E\u043B\u043D\u0438\u0442\u044C");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = "http://puh/tovar.php"+makeGet();
				boolean flag = false;
				try {
					URL url = new URL(str);
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.connect();
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String inputLine = in.readLine().trim();
					if(inputLine.indexOf('~') >= 0) {
						flag = true;
						String[] mass = inputLine.split("~");
						for(int i=0;i<mass.length;i++) {
							table.setValueAt(mass[i], i, 2);
						}
					}
					in.close();
					conn.disconnect();
					conn = null;
				}
				catch (Exception e) {}
				if (flag == false){
					JOptionPane.showMessageDialog(
							null,
							"Возможно интернет не подключен.",
							"Ошибка отправки данных.",
							0);
				}
			}
		});
		buttonOk.setBounds(445, 276, 89, 23);
		contentPane.add(buttonOk);
	}
}
