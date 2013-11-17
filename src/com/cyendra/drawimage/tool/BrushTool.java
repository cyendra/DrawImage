package com.cyendra.drawimage.tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.cyendra.drawimage.ImageFrame;

/**
 * 刷子工具
 * @version  1.0
 * @author cyendra
 */
public class BrushTool extends AbstractTool {

	private static Tool tool = null;

	private BrushTool(ImageFrame frame) {
		super(frame, "img/brushcursor.gif");
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new BrushTool(frame);
		}
		return tool;
	}

	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		int x = 0;
		int y = 0;
		// 画笔大小
		int size = 4;
		if (getPressX() > 0 && getPressY() > 0
				&& e.getX() < AbstractTool.drawWidth
				&& e.getY() < AbstractTool.drawHeight) {
			g.setColor(AbstractTool.color);
			x = e.getX() - getPressX() > 0 ? getPressX() : e.getX();
			y = e.getY() - getPressY() > 0 ? getPressY() : e.getY();
			g.fillRect(x - size, y - size, Math.abs(e.getX() - getPressX())
					+ size, Math.abs(e.getY() - getPressY()) + size);
			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}
}
