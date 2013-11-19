package com.cyendra.drawimage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cyendra.drawimage.file.ImageFileChooser;
import com.cyendra.drawimage.image.ExImage;

/**
 * 画图工具处理逻辑类(非工具)
 * @author cyendra
 * @version 1.0
 */
public class ImageService {
	
	private ImageFileChooser fileChooser = new ImageFileChooser();

	/**
	 * 获取屏幕的分辨率 
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}
	
	/**
	 * 初始化新 drawSpace
	 * @param frame ImageFrame
	 * @return void
	 */
	public void initDrawSpace(ImageFrame frame) {
		Graphics g = frame.getBufferedImage().getGraphics();// 获取画图对象
		Dimension d = frame.getDrawSpace().getPreferredSize();// 获取画布的大小
		int drawWidth = (int) d.getWidth();// 获取宽
		int drawHeight = (int) d.getHeight();// 获取高
		g.setColor(Color.WHITE);// 绘画区
		g.fillRect(0, 0, drawWidth, drawHeight);
	}
	
	/**
	 * 创建鼠标图形
	 * @param path String 图形路径
	 * @return Cursor
	 */
	public static Cursor createCursor(String path) {
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
				new Point(10, 10), "mycursor");
	}

	public static void setViewport(Object scroll, JPanel drawSpace, int x, int y) {
		// TODO Auto-generated method stub
		
	}


	public void menuDo(ImageFrame imageFrame, String actionCommand) {
		// TODO Auto-generated method stub
		
	}

	public static void setViewport(Object scroll, JFrame drawSpace, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * repaint
	 * 
	 */
	public void repaint(Graphics g, ExImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		// 设置非绘画区的颜色
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int) screenSize.getWidth() * 10, (int) screenSize.getHeight() * 10);
		// 设置拖动点的颜色
		g.setColor(Color.BLUE);
		g.fillRect(drawWidth, drawHeight, 4, 4);
		g.fillRect(drawWidth, (int) drawHeight / 2 - 2, 4, 4);
		g.fillRect((int) drawWidth / 2 - 2, drawHeight, 4, 4);
		// 把缓冲的图片绘画出来
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}

}
