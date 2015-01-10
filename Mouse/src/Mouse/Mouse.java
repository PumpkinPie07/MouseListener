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
	JFrame frame = new JFrame("���к�ť��");
	JLabel status = new JLabel("�w�N��");
	JLabel label = new JLabel("���J��...");
	JButton exit = new JButton("�h�X�{��");
	Font label_font = new Font("�L�n������", Font.BOLD, 20);
	Font status_font = new Font("�L�n������", Font.BOLD, 15);
	// ----------
	//
	// �`�n�{��
	//
	// ----------
	// icon object
	private TrayIcon trayIcon;
	// SystemTray object
	private SystemTray tray = null;
	// ���o�ϥ�,�p�G�䤣���,�b�t�ΦC�W�|�O�ťժ�
	private final Image image = Toolkit.getDefaultToolkit().getImage(
			"image/tray.gif");

	// ���X�����
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
		// �`�n�{��
		//
		// ----------
		// �ˬdOS�O�_�䴩SystemTray
		if (SystemTray.isSupported()) {

			// �C��Java�{���u�঳�@��SystemTray����
			tray = SystemTray.getSystemTray();
			// �]�w���
			setMenu();

			// �]�wtrayIcon(�Ϥ�,�ƹ����W�h��Tip�T��,���)
			trayIcon = new TrayIcon(image, "���к�ť��", popup);

			// �]�w�ϥܦ۰��ܧ�ؤo
			trayIcon.setImageAutoSize(true);

			try {
				// ��trayIcon�[�Jtray��
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}

			// �]�w�ƥ�
			setEvent();
		} else {
			JOptionPane.showMessageDialog(null, "SystemTray not support!");
		}
	}

	private void setMenu() {
		// �[�J���
		historyItem = new MenuItem("����");
		optionItem = new MenuItem("�ﶵ");
		aboutItem = new MenuItem("���󥻵{��");
		exitItem = new MenuItem("���}");

		popup.add(historyItem);
		popup.add(optionItem);
		popup.add(aboutItem);
		// �[�J���j�u
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
		// �]�w�ƹ��ƥ��ť��
		MouseListener mouseListener = new MouseListener() {
			// Click
			public void mouseClicked(MouseEvent e) {
				// ������~���Tip�T��
				if (e.getButton() == MouseEvent.BUTTON1) {
					trayIcon.displayMessage("���к�ť��", "���к�ť�����b�I�����椤",
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

		// historyItem���s�ƥ�
		ActionListener historyListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		};
		// optionItem���s�ƥ�
		ActionListener optionListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Setting mouse = new Setting();
			}
		};

		// aboutItem���s�ƥ�
		ActionListener aboutListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "���к�ť�� v1.0\nBy �n�ʬ�");
			}
		};

		// aboutItem���s�ƥ�
		ActionListener exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		// �[�J�ƥ�
		historyItem.addActionListener(historyListener);
		optionItem.addActionListener(optionListener);
		aboutItem.addActionListener(aboutListener);
		exitItem.addActionListener(exitListener);
		trayIcon.addMouseListener(mouseListener);
	}

	private void hide() {
		ran = true;
		int static_keep_sec = keep_sec;
		status.setText("�ƹ������ʺ���" + static_keep_sec + "��F" + ",���õ{����...");
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
						status.setText("�ƹ������ʺ���" + keep_sec + "��F");
					} else {
						status.setText("�ƹ������ʺ���" + keep_min + "��" + keep_sec
								+ "��F");
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
			label.setText("�ثe���y�Ь�:" + p.x + "," + p.y);
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
