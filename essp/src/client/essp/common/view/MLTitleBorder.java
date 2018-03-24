package client.essp.common.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client.essp.common.view.ui.MessageUtil;

public class MLTitleBorder extends TitledBorder {

	public MLTitleBorder(String title) {
		super(MessageUtil.getMessage(title));
	}

	public void setTitle(String tile) {
		super.setTitle(MessageUtil.getMessage(title));
	}
}
