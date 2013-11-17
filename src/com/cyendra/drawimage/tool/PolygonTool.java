package com.cyendra.drawimage.tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.cyendra.drawimage.ImageFrame;

/**
 * 多边形工具
 * @version  1.0
 * @author cyendra
 */
public class PolygonTool extends AbstractTool {
	private int firstX = -1;
	private int firstY = -1;
	private int lastX = -1;
	private int lastY = -1;

	private static Tool tool = null;

	private PolygonTool(ImageFrame frame) {
		super(frame);
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new PolygonTool(frame);
		}
		return tool;
	}

	public void mouseReleased(MouseEvent e) {
		int pressX = getPressX();
		int pressY = getPressY();
		// 调用父方法画直线
		super.mouseReleased(e);
		// 设置第一个与最后一个坐标
		if (firstX == -1) {
			firstX = pressX;
			firstY = pressY;
		}
		lastX = e.getX();
		lastY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		Graphics g = getFrame().getBufferedImage().getGraphics();
		if (e.getClickCount() == 2 && firstX > 0 && e.getX() > 0
				&& e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			g.setColor(AbstractTool.color);
			g.drawImage(getFrame().getBufferedImage(), 0, 0,
					AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			draw(g, 0, 0, firstX, firstY);
			draw(g, 0, 0, lastX, lastY);
			setPressX(-1);
			setPressY(-1);
			firstX = -1;
			firstY = -1;
			lastX = -1;
			lastY = -1;
			getFrame().getDrawSpace().repaint();
		}
	}

	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// 计算起点
		int x = lastX > 0 ? lastX : getPressX();
		int y = lastY > 0 ? lastY : getPressY();
		// 画椭圆
		g.drawLine(x, y, x2, y2);
	}
}
