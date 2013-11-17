package com.cyendra.drawimage.tool;

import java.awt.Graphics;

import com.cyendra.drawimage.ImageFrame;

/**
 * 直线工具
 * @version  1.0
 * @author cyendra
 */
public class LineTool extends AbstractTool {
	private static Tool tool = null;

	private LineTool(ImageFrame frame) {
		super(frame);
		// super.setShape( new LineShape() );
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new LineTool(frame);
		}
		return tool;
	}

	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
}
