package com.srihari92.tamilkeyboard;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.srihari92.tamilkeyboard.util.ClipBoardUtil;
import com.srihari92.tamilkeyboard.util.DBUtil;
import com.srihari92.tamilkeyboard.util.Util;

public class TamilKeyboard {

	private JFrame mainWindow;
	private JPanel mainPanel,wordPopup=null,dynamicChar,consonentWord,normalKeys;
	private Util util;
	private DBUtil  dbutil;
	int height=300,width=1000;
	int fontSize=10;
	static String popupWord="";
	
	public static void main(String[] args) {
		TamilKeyboard window=new TamilKeyboard();
		window.showGui();
	}
	public void popClr(){
		popupWord="";
		wordPopup.removeAll();
		wordPopup.repaint();
		wordPopup.revalidate();
		mainWindow.validate();	
	}
	public TamilKeyboard(){
		util=new Util();
		dbutil=new DBUtil();
		prepareGuiWindow();
	}

	/**
	 * initialize the gui components
	 */
	private void prepareGuiWindow() {
		//naming the frame
		mainWindow=new JFrame("Tamil Virtaul Keyboard");

		//setting the size of the frame

		//setting the layout of the frame
		mainWindow.setLayout(new GridLayout(3, 1));

		//adding window listenr to close the app
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				dbutil.close();
				System.exit(0);
			}        
		}); 

		//adding necessary components

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6,1));

		//Dynamic
		wordPopup=new JPanel();
		wordPopup.setLayout(new GridLayout(1,8,1,1));

		JPanel independentWord=new JPanel();
		independentWord.setLayout(new GridLayout(1,15));

		consonentWord=new JPanel();
		consonentWord.setLayout(new GridLayout(2,25));

		//Dynamic
		dynamicChar=new JPanel();
		dynamicChar.setLayout(new GridLayout(1,20));

		JPanel specialWord=new JPanel();
		specialWord.setLayout(new GridLayout(2,20));

		normalKeys=new JPanel();
		normalKeys.setLayout(new GridLayout(1,11));



		//independent
		for(String temp:dbutil.getIndependentCharacters()){
			JButton jb=new JButton("\n"+temp);

			jb.setFont(new Font("Latha",
					Font.PLAIN , fontSize));
			//	jb.setPreferredSize(new Dimension(30,30));
			//jb.setMinimumSize(new Dimension(20,30));
			jb.setBackground(Color.GRAY);
			jb.setForeground(Color.WHITE);
			jb.setOpaque(true);

			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {

					ClipBoardUtil.copy(temp);
					try {
						ClipBoardUtil.paste();
					} catch (AWTException e) {

						e.printStackTrace();
					}
					popupWord+=temp;
					updatePop();
				}

			});
			independentWord.add(jb);
		}






		//special characters
		for(String temp:dbutil.getSpecialCharacters()){
			JButton jb=new JButton(temp);
			jb.setFont(new Font("Latha",
					Font.PLAIN , fontSize));
			//  	jb.setPreferredSize(new Dimension(30,30));
			//	jb.setMinimumSize(new Dimension(20,30));

			//jb.setToolTipText(temp);
			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					ClipBoardUtil.copy(temp);
					try {
						ClipBoardUtil.paste();
					} catch (AWTException e) {

						e.printStackTrace();
					}
					popClr();

				}

			});


			specialWord.add(jb);

		}



		//normal




		mainPanel.add(wordPopup);

		mainPanel.add(consonentWord);
		mainPanel.add(dynamicChar);
		mainPanel.add(independentWord);
		mainPanel.add(specialWord);
		mainPanel.add(normalKeys);

		mainWindow.setContentPane(mainPanel);
		// final settings for frame
		mainWindow.setFocusableWindowState(false);
		mainWindow.toFront();
		Point p=util.getBottomCenterLocation(mainWindow.getGraphicsConfiguration(),width,height);
		if(p!=null){
			mainWindow.setLocationByPlatform(false);
			mainWindow.setLocation(p);
		}

		mainWindow.setAlwaysOnTop(true);
		//mainWindow.setResizable(false);
		//mainWindow.setVisible(true); 
	}

	private void showGui(){
		//mainWindow.pack();
		JButton firstBtn = null;
		boolean status=true;
		try {
			normalKeyBinding();
		} catch (AWTException e1) {

			e1.printStackTrace();
		}

		//consonent
		for(String tmp:dbutil.getConsonentCharacters()){
			String temp[]=tmp.split(",");
			JButton jb=new JButton(temp[0]);
			if(status){firstBtn=jb;status=false;}
			jb.setFont(new Font("Latha",
					Font.PLAIN , fontSize));
			jb.setBackground(Color.DARK_GRAY);
			jb.setForeground(Color.WHITE);
			jb.setOpaque(true);
			//jb.setPreferredSize(new Dimension(30,30));
			//jb.setMinimumSize(new Dimension(20,30));

			jb.setToolTipText(temp[0]);
			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dynamicChar.removeAll();
					for(String temp:dbutil.getDynamicCharacters(Integer.parseInt(temp[1]))){
						JButton jb1=new JButton(temp);

						jb1.setFont(new Font("Latha",
								Font.PLAIN , fontSize));
						jb1.setBackground(Color.GRAY);
						jb1.setForeground(Color.WHITE);
						jb1.setOpaque(true);
						//	jb.setPreferredSize(new Dimension(30,30));
						//jb.setMinimumSize(new Dimension(20,30));
						jb1.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent arg0) {
								ClipBoardUtil.copy(temp);
								try {
									ClipBoardUtil.paste();
								} catch (AWTException e) {
									e.printStackTrace();
								}
								popupWord+=temp;
								updatePop();
							}
						});
						/*JPanel jp=new JPanel(new CardLayout());
				       jp.add(jb);*/
						dynamicChar.add(jb1);
					}

					dynamicChar.revalidate();
					mainWindow.validate();
					// dynamicChar.repaint();
				}
			});
			consonentWord.add(jb);
		}
		mainPanel.setBackground(Color.BLACK);
		mainWindow.setSize(width,height);
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);
		if(firstBtn!=null)
			firstBtn.doClick();
	}
	private void normalKeyBinding() throws AWTException {

		JButton atBtn,perBtn,excBtn,queBtn,RsBtn,spcBtn,dotBtn,cmaBtn,qtBtn,DqtBtn,andBtn;
		Robot robot = new Robot();
		//Button declaration 

		atBtn=new JButton("@");
		perBtn=new JButton("%");
		excBtn=new JButton("!");
		queBtn=new JButton("?");
		RsBtn=new JButton("\u20B9");
		spcBtn=new JButton("SPACE");
		dotBtn=new JButton(".");
		cmaBtn=new JButton(",");
		qtBtn=new JButton("'");
		DqtBtn=new JButton("\"");
		andBtn=new JButton("&");


		atBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();

			}
		});

		perBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();
			}
		});
		excBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();
			}
		});
		queBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SLASH);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();
			}
		});

		RsBtn.setFont(new Font("Arial",
				Font.PLAIN , fontSize));
		RsBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClipBoardUtil.copy("\u20B9");
				try {
					ClipBoardUtil.paste();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				popClr();
			}
		});
		spcBtn.setPreferredSize(new Dimension(40,40));
		spcBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				robot.keyPress(KeyEvent.VK_SPACE );
				robot.keyRelease(KeyEvent.VK_SPACE );
				popClr();
			}
		});
		dotBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				robot.keyPress(KeyEvent.VK_PERIOD );
				robot.keyRelease(KeyEvent.VK_PERIOD );
				popClr();
			}
		});
		cmaBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				robot.keyPress(KeyEvent.VK_COMMA );
				robot.keyRelease(KeyEvent.VK_COMMA);
				popClr();
			}
		});
		qtBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				robot.keyPress(KeyEvent.VK_QUOTE);
				robot.keyRelease(KeyEvent.VK_QUOTE );
				popClr();
			}
		});
		DqtBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_QUOTE);
				robot.keyRelease(KeyEvent.VK_QUOTE );
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();
			}
		});
		andBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				popClr();
			}
		});


		normalKeys.add(atBtn);
		normalKeys.add(perBtn);
		normalKeys.add(excBtn);
		normalKeys.add(queBtn);
		normalKeys.add(RsBtn);
		normalKeys.add(spcBtn);
		normalKeys.add(dotBtn);
		normalKeys.add(cmaBtn);
		normalKeys.add(qtBtn);
		normalKeys.add(DqtBtn);
		normalKeys.add(andBtn);

	}

	public void updatePop(){
		dbutil.getPops(popupWord);

		wordPopup.removeAll();
		List<String> str=dbutil.getPops(popupWord);
		if(str!=null){
			for(String temp1:str){
				String[] strAry=temp1.split(",");
				String temp=strAry[0];
				JButton jb1=new JButton(temp);

				jb1.setFont(new Font("Latha",
						Font.PLAIN , fontSize));
				jb1.setBackground(Color.GRAY);
				jb1.setForeground(Color.WHITE);
				jb1.setOpaque(true);

				jb1.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(temp.indexOf(popupWord)>=0 && temp.length()>temp.indexOf(popupWord)+popupWord.length()){

							String est=temp.substring(temp.indexOf(popupWord)+popupWord.length());
							ClipBoardUtil.copy(est+" ");
							try {
								ClipBoardUtil.paste();
							} catch (AWTException e) {
								e.printStackTrace();
							}
							dbutil.updateHit(Integer.parseInt(strAry[1]));
						}
						popClr();
					}
				});
				/*JPanel jp=new JPanel(new CardLayout());
	       jp.add(jb);*/
				wordPopup.add(jb1);
			}
		}
		else
			wordPopup.repaint();
		wordPopup.revalidate();
		mainWindow.validate();
		// dynamicChar.repaint();

	}
}

