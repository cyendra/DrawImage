package com.cyendra.drawimage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.cyendra.drawimage.image.ExImage;
import com.cyendra.drawimage.tool.Tool;
import com.cyendra.drawimage.tool.ToolFactory;

/**
 * 画图窗体
 * @author cyendra
 * @version 1.0
 */
public class ImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//业务逻辑
	private ImageService service = new ImageService();
	
	//初始化屏幕的尺寸
	private Dimension screenSize = service.getScreenSize();
	
	//设置默认画板
	private JPanel drawSpace = createDrawSpace();
	
	//设置缓冲图片
	private ExImage bufferedImage = new ExImage((int) screenSize.getWidth() / 2,
												(int) screenSize.getHeight() / 2,
												BufferedImage.TYPE_INT_RGB);
	
	//设置当前使用的工具
	private Tool tool = null;
	
	//设置画图对象
	Graphics g = bufferedImage.getGraphics();
	
	//颜色显示面板
	private JPanel currentColorPanel = null;
	
	//颜色选择器
	private JColorChooser colorChooser = getColorChooser();
	
	//菜单的事件监听器
	ActionListener menuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			service.menuDo(ImageFrame.this, e.getActionCommand());
		}
	};
	
	//默认JScrollPane
	private JScrollPane scroll = null;
	
	// 工具栏
	JPanel toolPanel = createToolPanel();
	
	// 颜色面板
	JPanel colorPanel = createToolPanel();
	
	/**
	 * 默认构造器
	 */
	public ImageFrame() {
		super();
		initialize();
	}

	/**
	 * 初始化 ImageFrame
	 */
	public void initialize() {
		this.setTitle("画图");
		service.initDrawSpace(this);
		tool = ToolFactory.getToolInstance(this, Tool.PENCIL_TOOL);
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};
		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);
		createMenuBar();
		scroll = new JScrollPane(drawSpace);
		ImageService.setViewport(scroll,drawSpace,bufferedImage.getWidth(),bufferedImage.getHeight());
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.SOUTH);
	}

	
	/**
	 * 获取颜色选择器
	 * @return JColorChooser
	 */
	private JColorChooser getColorChooser() {
		if (colorChooser == null) {
			colorChooser = new JColorChooser();
		}
		return colorChooser;
	}

	private void createMenuBar() {
		// TODO Auto-generated method stub
		
	}

	private JPanel createToolPanel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private JPanel createDrawSpace() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取画布
	 * @return JPanel
	 */
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}

	/**
	 * 设置图片
	 * @param bufferedImage MyImage
	 */
	public void setBufferedImage(ExImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * 获取图片 
	 * @return ExImage
	 */
	public ExImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	/**
	 * 获取JScroolPane
	 * @return JScrollPane
	 */
	public Object getScroll() {
		return this.scroll;
	}

	/**
	 * 获取颜色显示面板
	 * @return JPanel
	 */
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}
	
	/**
	 * 获取工具栏
	 * @return JPanel
	 */
	public JPanel getToolPanel() {
		return this.toolPanel;
	}
	
	/**
	 * 设置工具
	 * @param tool Tool
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	/**
	 * 获取工具
	 * @return Tool
	 */
	public Tool getTool() {
		return this.tool;
	}
	
	/**
	 * 获取颜色面板
	 * @return JPanel
	 */
	public JPanel getColorPanel() {
		return this.colorPanel;
	}
	

	/**
	 * 获取screenSize
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		return this.screenSize;
	}
	
}
