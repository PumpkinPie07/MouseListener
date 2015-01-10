package Mouse;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Setting {
	JFrame setting_frame = new JFrame("設置");
	Label AutoPopupLabel = new Label("自動跳出視窗時間：");
	Label AutoHideLabel = new Label("自動關閉視窗時間：");
	Label SecLabel_1 = new Label("秒");
	Label SecLabel_2 = new Label("秒");
	TextField AutoPopupSecTfield = new TextField();
	TextField AutoHideSecTfield = new TextField();
	Button OkButton = new Button("確定");
	Button CancelButton = new Button("取消");
	Button ResetButton = new Button("恢復預設值");
	private int att[][];
	private ArrayList<JComponent> GUIComponent;

	public Setting() {
		setting_frame.setLayout(new GridBagLayout());
		setting_frame.setSize(250, 125);
		setting_frame.setLocation(1000, 823);
		setting_frame.setVisible(true);
		int fill[] = { GridBagConstraints.BOTH, GridBagConstraints.VERTICAL,
				GridBagConstraints.HORIZONTAL, GridBagConstraints.NONE };
		int anchor[] = { GridBagConstraints.CENTER, GridBagConstraints.EAST,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.SOUTH,
				GridBagConstraints.SOUTHWEST, GridBagConstraints.WEST,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NORTH,
				GridBagConstraints.NORTHEAST };
		int a[][] = { { 0, 0, 2, 1, 0, 0, fill[3], anchor[5] },
				{ 0, 1, 2, 1, 0, 0, fill[3], anchor[5] },
				{ 2, 0, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 2, 1, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 3, 0, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 3, 1, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 2, 2, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 3, 2, 1, 1, 0, 0, fill[3], anchor[5] },
				{ 1, 2, 2, 1, 0, 0, fill[3], anchor[5] } };
		att = a;
		GUIComponent = new ArrayList<JComponent>(12);
		addComponent_Label(AutoPopupLabel, 0);
		addComponent_Label(AutoHideLabel, 1);
		addComponent_TextField(AutoPopupSecTfield, 2);
		addComponent_TextField(AutoHideSecTfield, 3);
		addComponent_Label(SecLabel_1, 4);
		addComponent_Label(SecLabel_2, 5);
		addComponent_Button(OkButton, 6);
		addComponent_Button(CancelButton, 7);
		addComponent_Button(ResetButton, 8);
	}

	private void addComponent_Label(Label j, int i) {
		GridBagConstraints c = new GridBagConstraints();
		int a[] = att[i];
		c.gridx = a[0];
		c.gridy = a[1];
		c.gridwidth = a[2];
		c.gridheight = a[3];
		c.weightx = a[4];
		c.weighty = a[5];
		c.fill = a[6];
		c.anchor = a[7];
		setting_frame.add(j, c);
	}

	private void addComponent_TextField(TextField j, int i) {
		GridBagConstraints c = new GridBagConstraints();
		int a[] = att[i];
		c.gridx = a[0];
		c.gridy = a[1];
		c.gridwidth = a[2];
		c.gridheight = a[3];
		c.weightx = a[4];
		c.weighty = a[5];
		c.fill = a[6];
		c.anchor = a[7];
		setting_frame.add(j, c);
	}

	private void addComponent_Button(Button j, int i) {
		GridBagConstraints c = new GridBagConstraints();
		int a[] = att[i];
		c.gridx = a[0];
		c.gridy = a[1];
		c.gridwidth = a[2];
		c.gridheight = a[3];
		c.weightx = a[4];
		c.weighty = a[5];
		c.fill = a[6];
		c.anchor = a[7];
		setting_frame.add(j, c);
	}
}
