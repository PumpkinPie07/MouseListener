package Mouse;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
import java.util.Timer;
import java.util.TimerTask;

public class Mouse extends TimerTask implements ActionListener {
	int original_x = 0;
	int original_y = 0;
	int keep = 0;
	int keep_sec = 0;
	int keep_min = 0;
	int detec_sec = 20;
	int hide_sec = 3;
	boolean status_hide = false;
	boolean ran = false;
	JFrame frame = new JFrame("鼠標監聽器");
	JLabel status = new JLabel("已就緒");
	JLabel label = new JLabel("載入中...");
	JButton exit = new JButton("退出程式");
	Font label_font = new Font("微軟正黑體", Font.BOLD, 20);
	Font status_font = new Font("微軟正黑體", Font.BOLD, 15);
	// ----------
	//
	// 常駐程式
	//
	// ----------
	// icon object
	private TrayIcon trayIcon;
	// SystemTray object
	private SystemTray tray = null;
	// 取得圖示,如果找不到圖,在系統列上會是空白的
	private final Image image = Toolkit.getDefaultToolkit().getImage(
			"image/tray.gif");

	// 跳出式選單
	private PopupMenu popup = new PopupMenu();
	private MenuItem historyItem = null;
	private MenuItem optionItem = null;
	private MenuItem aboutItem = null;
	private MenuItem exitItem = null;

	public Mouse() {
		/*
		 * frame.add(label, "North"); frame.add(status);
		 * frame.add(exit,"South"); label.setFont(label_font);
		 * label.setHorizontalAlignment(SwingConstants.CENTER);
		 * status.setFont(status_font);
		 * status.setHorizontalAlignment(SwingConstants.CENTER);
		 * exit.addActionListener(this); frame.setLocationRelativeTo(null);
		 * frame.setSize(250, 125); frame.setLocation(1000, 823);
		 */
		// ----------
		//
		// 常駐程式
		//
		// ----------
		// 檢查OS是否支援SystemTray
		if (SystemTray.isSupported()) {

			// 每個Java程式只能有一個SystemTray實體
			tray = SystemTray.getSystemTray();
			// 設定選單
			setMenu();

			// 設定trayIcon(圖片,滑鼠指上去的Tip訊息,選單)
			trayIcon = new TrayIcon(image, "鼠標監聽器", popup);

			// 設定圖示自動變更尺寸
			trayIcon.setImageAutoSize(true);

			try {
				// 把trayIcon加入tray中
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}

			// 設定事件
			setEvent();
		} else {
			JOptionPane.showMessageDialog(null, "SystemTray not support!");
		}
	}

	private void setMenu() {
		// 加入選單
		historyItem = new MenuItem("紀錄");
		optionItem = new MenuItem("選項");
		aboutItem = new MenuItem("關於本程式");
		exitItem = new MenuItem("離開");

		popup.add(historyItem);
		popup.add(optionItem);
		popup.add(aboutItem);
		// 加入分隔線
		popup.addSeparator();
		popup.add(exitItem);
	}

	private void display() {
		frame.add(label, "North");
		frame.add(status);
		frame.add(exit, "South");
		label.setFont(label_font);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		status.setFont(status_font);
		status.setHorizontalAlignment(SwingConstants.CENTER);
		exit.addActionListener(exitListener);
		frame.setSize(270, 125);
		frame.setLocation(1000, 823);
		frame.setVisible(true);
	}

	private void setEvent() {
		// 設定滑鼠事件監聽器
		MouseListener mouseListener = new MouseListener() {
			// Click
			public void mouseClicked(MouseEvent e) {
				// 按左鍵才顯示Tip訊息
				if (e.getButton() == MouseEvent.BUTTON1) {
					trayIcon.displayMessage("鼠標監聽器", "鼠標監聽器正在背景執行中",
							TrayIcon.MessageType.INFO);
				}
			}

			// Enter
			public void mouseEntered(MouseEvent e) {
			}

			// Exit
			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};

		// historyItem按鈕事件
		ActionListener historyListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		};
		// optionItem按鈕事件
		ActionListener optionListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Setting mouse = new Setting();
			}
		};

		// aboutItem按鈕事件
		ActionListener aboutListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "鼠標監聽器 v1.0\nBy 南瓜派");
			}
		};

		// aboutItem按鈕事件
		ActionListener exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		// 加入事件
		historyItem.addActionListener(historyListener);
		optionItem.addActionListener(optionListener);
		aboutItem.addActionListener(aboutListener);
		exitItem.addActionListener(exitListener);
		trayIcon.addMouseListener(mouseListener);
	}

	private void hide() {
		ran = true;
		int static_keep_sec = keep_sec;
		status.setText("滑鼠未移動維持" + static_keep_sec + "秒了" + ",隱藏程式中...");
		Timer timer = new Timer();
		TimerTask hide = new TimerTask() {
			@Override
			public void run() {
				frame.setVisible(false);
				status_hide = false;
				ran = false;
			}
		};
		int hideMS = hide_sec * 1000;
		timer.schedule(hide, hideMS);
	}

	public static void main(String[] args) {
		Mouse mouse = new Mouse();
		mouse.getLocation();
	}

	public void getLocation() {
		while (true) {
			PointerInfo info = MouseInfo.getPointerInfo();
			Point p = info.getLocation();
			int x = p.x;
			int y = p.y;
			if (original_x == x && original_y == y) {
				keep += 1;
				keep_sec = keep % 60;
				keep_min = keep / 60;
				if (status_hide == false) {
					if (keep_min == 0) {
						status.setText("滑鼠未移動維持" + keep_sec + "秒了");
					} else {
						status.setText("滑鼠未移動維持" + keep_min + "分" + keep_sec
								+ "秒了");
					}
				}
				if (keep == detec_sec) {
					display();
				}
			} else {
				status_hide = true;
				keep = 0;
				if (ran == false) {
					hide();
				}
				original_x = x;
				original_y = y;
			}
			label.setText("目前的座標為:" + p.x + "," + p.y);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}

	ActionListener exitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);

		}
	};

	@Override
	public void run() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
