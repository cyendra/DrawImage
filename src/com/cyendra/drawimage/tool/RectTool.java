package com.cyendra.drawimage.tool;

import java.awt.Graphics;

import com.cyendra.drawimage.ImageFrame;

/**
 * 矩形工具
 * @version  1.0
 * @author cyendra
 */
public class RectTool extends AbstractTool {
	private static Tool tool = null;

	private RectTool(ImageFrame frame) {
		super(frame);
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new RectTool(frame);
		}
		return tool;
	}

	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// 计算起点
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		// 画矩形
		g.drawRect(x, y, Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
}
