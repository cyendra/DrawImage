package com.cyendra.drawimage.action;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.cyendra.drawimage.ImageFrame;
import com.cyendra.drawimage.tool.AbstractTool;
import com.cyendra.drawimage.tool.Tool;
import com.cyendra.drawimage.tool.ToolFactory;

/**
 * 按键处理类
 * @author cyendra
 * @version 1.0
 */
public class ImageAction extends AbstractAction {
	
	private String name = "";
	private ImageFrame frame = null;
	private Color color = null;
	private Tool tool = null;
	private JPanel colorPanel = null;

	/**
	 * 构造器
	 * @param icon ImageIcon 图标
	 * @param name 名字
	 * @param frame ImageFrame
	 */
	public ImageAction(Color color, JPanel colorPanel) {
		// 调用父构造器
		super();
		this.color = color;
		this.colorPanel = colorPanel;
	}

	/**
	 * 构造器
	 * @param icon ImageIcon 图标
	 * @param name 名字
	 * @param frame ImageFrame
	 */
	public ImageAction(ImageIcon icon, String name, ImageFrame frame) {
		// 调用父构造器
		super("", icon);
		this.name = name;
		this.frame = frame;
	}
	
	/**
	 * 重写void actionPerformed( ActionEvent e )方法
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		tool = name != "" ? ToolFactory.getToolInstance(frame, name) : tool;// 设置tool
		if (tool != null) {
			frame.setTool(tool);// 设置正在使用的tool
		}
		if (color != null) {
			AbstractTool.color = color;// 设置正在使用的颜色
			colorPanel.setBackground(color);
		}
	}

}
