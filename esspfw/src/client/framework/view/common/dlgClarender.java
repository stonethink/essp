package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import client.framework.view.jmscomp.*;

/**
 * <p>僞僀僩儖: 僇儗儞僟乕僟僀傾儘僌</p>
 * <p>愢柧: </p>
 * <p>挊嶌尃: Copyright (c) 2002</p>
 * <p>夛幮柤: </p>
 * @author 枹擖椡
 * @version 1.0
 */

public class dlgClarender extends JDialog {

	static final int min_Year = 1990; //丂庢傝埖偄嵟彫擭
	static final int max_Year = 2020; //丂庢傝埖偄嵟戝擭

	protected String psNewDate;
	protected String psOldDate;
	protected String psDATE_TYPE = ""; //庢摼偟偨jmsDATE偺DATE_TYPE
	int piYear;
	int piMonth;
	int piDay;

	protected boolean pbSelect;

	GregorianCalendar calendar = new GregorianCalendar();
	CalendarCellRenderer cCellRend = new CalendarCellRenderer();
	String weeks[] = new String[]{"Sun","01","02","03","04","05","06"};
	String days[][] = new String[6][7];

	JMsDisp DSP_YEAR = new JMsDisp();
	JMsDisp DSP_MONTH = new JMsDisp();
	JMsButton BTN_RETURN = new JMsButton();
	JMsButton BTN_NEXT = new JMsButton();
	JMsButton BTN_OK = new JMsButton();
	NoEditeModel neModel = new NoEditeModel(days, weeks);
	JScrollPane jScrollPane1 = new JScrollPane();
	JTable jTable1 = new JTable(neModel);
	JMsLabel jMsLabel1 = new JMsLabel();
	JMsLabel jMsLabel2 = new JMsLabel();
	JMsButton BTN_CANCEL = new JMsButton();
	JMsButton BTN_TODAY = new JMsButton();

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僐儞僗僩儔僋僞<BR>
	 *丂張棟柤丂丗  弶婜張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public dlgClarender(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			InitUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僐儞僗僩儔僋僞<BR>
	 *丂張棟柤丂丗丂弶婜張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public dlgClarender() {
		this(null, "", true);
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂弶婜張棟乵JBuilder愝掕乶<BR>
	 *丂張棟柤丂丗丂僐儞億乕僱儞僩愝掕<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle("");
		this.getContentPane().setLayout(null);
		DSP_MONTH.setToolTipText("");
		DSP_MONTH.setBackground(SystemColor.control);
		DSP_MONTH.setHorizontalAlignment(SwingConstants.RIGHT);
		DSP_MONTH.setFont(new java.awt.Font("Dialog", 0, 15));
		DSP_MONTH.setBounds(new Rectangle(96, 10, 30, 21));
		BTN_RETURN.setText("返回");
		BTN_RETURN.setBounds(new Rectangle(15, 41, 100, 30));
		BTN_RETURN.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				BTN_RETURN_keyPressed(e);
			}
		});
		BTN_RETURN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_RETURN_actionPerformed(e);
			}
		});
		BTN_NEXT.setNextFocusableComponent(BTN_OK);
		BTN_NEXT.setText("下月");
		BTN_NEXT.setBounds(new Rectangle(228, 41, 100, 30));
		BTN_NEXT.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				BTN_NEXT_keyPressed(e);
			}
		});
		BTN_NEXT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_NEXT_actionPerformed(e);
			}
		});
		BTN_OK.setText("确认");
		BTN_OK.setToolTipText("");
		BTN_OK.setBounds(new Rectangle(122, 246, 100, 30));
		BTN_OK.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				BTN_OK_keyPressed(e);
			}
		});
		BTN_OK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_OK_actionPerformed(e);
			}
		});
		jTable1.setCellSelectionEnabled(true);
		jTable1.setRowHeight(21);
		jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jTable1_mouseClicked(e);
			}
		});
		DSP_YEAR.setBackground(SystemColor.control);
		DSP_YEAR.setHorizontalAlignment(SwingConstants.RIGHT);
		DSP_YEAR.setFont(new java.awt.Font("Dialog", 0, 15));
		DSP_YEAR.setBounds(new Rectangle(15, 10, 55, 21));
		jMsLabel1.setText("年");
		jMsLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jMsLabel1.setFont(new java.awt.Font("Dialog", 0, 15));
		jMsLabel1.setBounds(new Rectangle(72, 13, 19, 21));
		jMsLabel2.setText("月");
		jMsLabel2.setFont(new java.awt.Font("Dialog", 0, 15));
		jMsLabel2.setBounds(new Rectangle(129, 13, 21, 21));
		BTN_CANCEL.setNextFocusableComponent(BTN_RETURN);
		BTN_CANCEL.setText("取消");
		BTN_CANCEL.setToolTipText("");
		BTN_CANCEL.setBounds(new Rectangle(228, 246, 100, 30));
		BTN_CANCEL.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				BTN_CANCEL_keyPressed(e);
			}
		});
		BTN_CANCEL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_CANCEL_actionPerformed(e);
			}
		});
		jScrollPane1.setBounds(new Rectangle(19, 82, 309, 152));
		jScrollPane1.setBackground(Color.white);
		BTN_TODAY.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BTN_TODAY_actionPerformed(e);
			}
		});
		BTN_TODAY.setBounds(new Rectangle(122, 41, 100, 30));
		BTN_TODAY.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				BTN_TODAY_keyPressed(e);
			}
		});
		BTN_TODAY.setToolTipText("");
		BTN_TODAY.setText("今日");
		this.getContentPane().add(BTN_RETURN, null);
		this.getContentPane().add(BTN_NEXT, null);
		this.getContentPane().add(BTN_TODAY, null);
		this.getContentPane().add(jScrollPane1, null);
		this.getContentPane().add(BTN_OK, null);
		this.getContentPane().add(BTN_CANCEL, null);
		this.getContentPane().add(DSP_MONTH, null);
		this.getContentPane().add(jMsLabel1, null);
		this.getContentPane().add(jMsLabel2, null);
		this.getContentPane().add(DSP_YEAR, null);
		jScrollPane1.getViewport().add(jTable1, null);
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂弶婜張棟乵儐乕僓愝掕乶<BR>
	 *丂張棟柤丂丗丂僐儞億乕僱儞僩愝掕<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void InitUser() throws Exception {

		///////////////////////////////////////
		//  Dialog偺僒僀僘偲僞僀僩儖傪愝掕
		///////////////////////////////////////
		setBounds(344,250,345,345);
		setTitle("僇儗儞僟乕");

		//////////////////////////////////////////////////////////////
		//  僥乕僽儖偺愝掕
		//  丂丒Cell偺昞帵忬懺偵CalendarCellRenderer(撪晹僋儔僗)傪搊榐
		//  丂丒僪儔僢僌偵傛傞cell偺僒僀僘曄峏傪嬛巭
		//  丂丒僪儔僢僌偵傛傞楍偺堏摦傪嬛巭
		//  丂丒cell偺慖戰儌乕僪傪SINGLE_SELECTION偵愝掕
		/////////////////////////////////////////////////////////////
		for(int i = 0; i < neModel.getColumnCount(); i++){
			jTable1.getColumnModel().getColumn(i).setCellRenderer(cCellRend);
			jTable1.getColumnModel().getColumn(i).setResizable(false);
		}
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		////////////////////////////////////
		//  僟僀傾儘僌傪夋柺偺拞墰偵攝抲
		////////////////////////////////////
		java.awt.Dimension d1 = this.getToolkit().getScreenSize();
		java.awt.Dimension d2 = this.getSize();
		this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
	}
	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂僟僀傾儘僌昞帵張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 * @param   jmsDate      :屇傃弌偟尦僐儞億乕僱儞僩
	 * @param   prm_Date     :慖戰擔晅
	 */
	public String showDialog(JMsDate jmsDate){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		psDATE_TYPE = jmsDate.getDataType();    //psDATE_TYPE偺庢摼
		cCellRend.setDateType( psDATE_TYPE );
		pbSelect = false;
		if( jmsDate.checkValue() == 0 ){
			psNewDate = jmsDate.getValueText();
                       //Modifed by yery on 2004/11/22
//                        if ( psNewDate.length() == 6 ) {
//                                 psNewDate = psNewDate + "01";
//                         } else if ( psNewDate.length() == 4) {
//                                 psNewDate = psNewDate + "0101";
//                         }

                       if(psDATE_TYPE.equals("YYYYMM")) {
                         psNewDate = psNewDate + "01";
                       } else if(psDATE_TYPE.equals("YYYY")) {
                         psNewDate = psNewDate + "0101";
                       } else if(psDATE_TYPE.equals("MM")) {
                         psNewDate = "2000"+psNewDate + "01";
                       } else if(psDATE_TYPE.equals("DD")) {
                         psNewDate = "2000"+"01" + psNewDate;
                       } else if(psDATE_TYPE.equals("MMDD")) {
                         psNewDate = "2000"+ psNewDate;
                       } else if(psDATE_TYPE.equals("YYMMDD")) {
                         psNewDate = "20"+ psNewDate;
                       } else if(psDATE_TYPE.equals("YYMM")) {
                         psNewDate = "20"+ psNewDate+"01";
                       }

		} else {
			psNewDate = "";
		}

		psOldDate = psNewDate;
		_setCalendar();
		this.setVisible( true );
                /**
                 * Add by yery on 2004/11/22
                 */
                if(psNewDate!=null && !psNewDate.equals("")) {
                  if (psDATE_TYPE.equals("YYYYMM")) {
                    psNewDate = psNewDate.substring(0, 6);
                  }
                  else if (psDATE_TYPE.equals("YYYY")) {
                    psNewDate = psNewDate.substring(0,4);
                  }
                  else if (psDATE_TYPE.equals("MM")) {
                    psNewDate = psNewDate.substring(4,6);
                  }
                  else if (psDATE_TYPE.equals("DD")) {
                    psNewDate = psNewDate.substring(6,8);
                  }
                  else if (psDATE_TYPE.equals("MMDD")) {
                    psNewDate = psNewDate.substring(4,8);
                  }
                  else if (psDATE_TYPE.equals("YYMMDD")) {
                    psNewDate = psNewDate.substring(2,8);
                  }
                  else if (psDATE_TYPE.equals("YYMM")) {
                    psNewDate = psNewDate.substring(2,6);
                  }
                }
		return psNewDate;
	}

	class wAdapter extends WindowAdapter {
		public void windowDeactivated( WindowEvent e ) {
			dlgClarender.this.setVisible( true );
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂僇儗儞僟乕昞帵擔晅愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	protected void _setCalendar(){
		int dayCount;
		int startWeek;
		int uru;
		int temp;
		int iAfterMonth;
		int[][] monthDays ={
			{31,29,31,30,31,30,31,31,30,31,30,31},
			{31,28,31,30,31,30,31,31,30,31,30,31}};
		/////////////////////////////////////////////////////////////////////
		//  屇傃弌偟傕偲偺僐儞億乕僱儞僩偐傜抣傪偲偭偰偄側偄応崌偼僔僗僥儉擔晅傪偲傞
		/////////////////////////////////////////////////////////////////////
		if(psNewDate.equals("")){
			piYear = calendar.get(GregorianCalendar.YEAR);
			piMonth = calendar.get(GregorianCalendar.MONTH) + 1;
			piDay = calendar.get(GregorianCalendar.DATE);
		}else{
			try {
				piYear = Integer.parseInt(psNewDate.substring(0,4));
				piMonth = Integer.parseInt(psNewDate.substring(4,6));
				piDay = Integer.parseInt(psNewDate.substring(6,8));
			} catch(NumberFormatException e) {
				System.out.println( "ERROR : " + e );
				dispose();
			}
		}
		////////////////////////////////////////
		// 崱寧偑壗梛擔偐傜巒傑偭偰偄傞偐傪挷傋傞
		// DAY_OF_WEEK 1乣7 > 擔乣搚
		////////////////////////////////////////
		GregorianCalendar thisMonth = new GregorianCalendar(piYear, piMonth - 1, 1);
		startWeek = thisMonth.get(GregorianCalendar.DAY_OF_WEEK) - 1;

		///////////////////////////
		// 偆傞偆擭偐偳偆偐偺妋擣
		///////////////////////////
		uru = calendar.isLeapYear(piYear)?0:1;

		DSP_YEAR.setText(Integer.toString(piYear));
		DSP_MONTH.setText(Integer.toString(piMonth));

		temp = startWeek-1;
		iAfterMonth = piMonth - 2;
		if( iAfterMonth < 0 ) {
			iAfterMonth = 11;
		}
		for(int i = 0; i < startWeek ; i++){
			neModel.setValueAt(Integer.toString(monthDays[uru][iAfterMonth] - temp), 0, i);
			temp--;
		}

		dayCount = 1;
		for(int i = 0; i < 6; i++){
			for(int j = startWeek; j < 7; j++){
				neModel.setValueAt(Integer.toString( dayCount ), i, j);
				if( ( dayCount == piDay ) && ( pbSelect == false) ){
					jTable1.setColumnSelectionInterval(j,j);
					jTable1.setRowSelectionInterval(i,i);
					pbSelect = true;
				}
				if(dayCount == monthDays[uru][piMonth - 1]){
					dayCount = 0;
				}
				dayCount++;
			}
			startWeek = 0;
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂慖戰擔晅庢摼張棟<BR>
	 *丂旛丂峫丂丗丂儐乕僓偑僇儗儞僟乕偱慖戰偟偰偄傞擔晅傪庢摼偡傞<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	private void  _selectDate(){
			int iColumn =  jTable1.getSelectedColumn();
			int iRow = jTable1.getSelectedRow();
			piDay = Integer.parseInt(String.valueOf(jTable1.getValueAt(iRow, iColumn)));

			///++**************************************************************
			/// 堷悢偱庢摼偟偨jmsDATE偺DATE_TYPE偑YYYYMMDD偺応崌捠忢偺僇儗儞僟乕
			/// YYYYMM偺偲偒偼偝偑旤僇儗儞僟乕俀侾埲崀偼梻寧傪庢摼
			///--**************************************************************
                        //removed by yery on 2004/12/22
//			if ( psDATE_TYPE.equals("YYYYMM") == true ) {
//				if(iRow >= 4){
//					if((piDay >= 21) || (piDay < 14)){
//						piMonth = piMonth + 1;
//						if(piMonth >= 13){
//							piMonth = 1;
//							piYear = piYear +1;
//							if(piYear < min_Year){
//								piYear = max_Year;
//							}
//						}
//					}
//				} else if(iRow >= 2){
//					if((piDay >= 21) || (piDay < 7)){
//						piMonth = piMonth + 1;
//						if(piMonth >= 13){
//							piMonth = 1;
//							piYear = piYear +1;
//							if(piYear < min_Year){
//								piYear = max_Year;
//							}
//						}
//					}
//				}
//			} else {
				if(iRow == 0){
					if(piDay > 7){
						piMonth = piMonth - 1;
						if(piMonth <= 0){
							piMonth = 12;
							piYear = piYear -1;
							if(piYear < min_Year){
								piYear = max_Year;
							}
						}
					}
				} else if(iRow >= 4) {
					if(piDay < 14){
						piMonth = piMonth + 1;
						if(piMonth >= 13){
							piMonth = 1;
							piYear = piYear + 1;
						}
					}
				}
//			}
		_setDate();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂擔晅僨乕僞嶌惉張棟<BR>
	 *丂旛丂峫丂丗丂擭丄寧丄擔傪傑偲傔偨擔晅僨乕僞傪嶌惉偡傞<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	private void _setDate(){
		psNewDate = "";
		psNewDate = psNewDate + ComDATA.zeroFormat(piYear, 4);
		psNewDate = psNewDate + ComDATA.zeroFormat(piMonth, 2);
		psNewDate = psNewDate + ComDATA.zeroFormat(piDay, 2);
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂悢抣僨乕僞僛儘杽傔張棟<BR>
	 *丂旛丂峫丂丗丂悢抣僨乕僞偑昁梫寘悢偵枮偨側偄応崌嵍偐傜僛儘杽傔偡傞<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 * @param   iValue      :悢抣僨乕僞
	 * @param   beam        :昁梫寘悢
	 * @return  sValue      :僛儘杽傔屻僨乕僞
	 */
	private String _zeroFormat(int iValue, int beam){
		String sValue = new String();
		int iLength;
		sValue = String.valueOf(iValue);
		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}
		return sValue;
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵慜寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_RETURN_actionPerformed(ActionEvent e) {
		piMonth = piMonth - 1;
		if(piMonth <= 0){
			piYear = piYear - 1;
			piMonth = 12;
			if(piYear < min_Year){
				piYear = max_Year;
			}
		}
		_setDate();
		_setCalendar();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵慜寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_RETURN_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == Event.ENTER ){
			piMonth = piMonth - 1;
			if(piMonth <= 0){
				piYear = piYear - 1;
				piMonth = 12;
				if(piYear < min_Year){
					piYear = max_Year;
				}
			}
			_setDate();
			_setCalendar();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵師寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_NEXT_actionPerformed(ActionEvent e) {
		piMonth = piMonth + 1;
		if(piMonth >= 13){
			piYear = piYear + 1;
			piMonth = 1;
			if(piYear > max_Year){
				piYear = min_Year;
			}
		}
		_setDate();
		_setCalendar();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵師寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_NEXT_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == Event.ENTER ){
		  piMonth = piMonth + 1;
			if(piMonth >= 13){
				piYear = piYear + 1;
				piMonth = 1;
				if(piYear > max_Year){
					piYear = min_Year;
				}
			}
			_setDate();
			_setCalendar();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵俷俲乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_OK_actionPerformed(ActionEvent e) {
		_selectDate();
		dispose();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵俷俲乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_OK_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == Event.ENTER ){
			_selectDate();
			dispose();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂擔晅僥乕僽儖僋儕僢僋張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void jTable1_mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2 ){
			_selectDate();
			dispose();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵僉儍儞僙儖乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	 void BTN_CANCEL_actionPerformed(ActionEvent e) {
			psNewDate = psOldDate;
			dispose();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵僉儍儞僙儖乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_CANCEL_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == Event.ENTER ){
			psNewDate = psOldDate;
			dispose();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵崱寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_TODAY_actionPerformed(ActionEvent e) {
		pbSelect = false;
		piYear = calendar.get(GregorianCalendar.YEAR);
		piMonth = calendar.get(GregorianCalendar.MONTH) + 1;
		piDay = calendar.get(GregorianCalendar.DATE);
		_setDate();
		_setCalendar();
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂乵崱寧乶儃僞儞墴壓張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/18丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void BTN_TODAY_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == Event.ENTER ){
			pbSelect = false;
			piYear = calendar.get(GregorianCalendar.YEAR);
			piMonth = calendar.get(GregorianCalendar.MONTH) + 1;
			piDay = calendar.get(GregorianCalendar.DATE);
			_setDate();
			_setCalendar();
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂僥乕僽儖儌僨儖愝掕<BR>
	 *丂旛丂峫丂丗丂撪晹僋儔僗<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	class NoEditeModel extends DefaultTableModel {
		public NoEditeModel(Object[][] object1, Object[] object2){
			super(object1, object2);
		}
		public boolean isCellEditable(int rowIndex,int colmunIndex) {
			return false;
		}
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂僥乕僽儖昞帵張棟愝掕<BR>
	 *丂旛丂峫丂丗丂撪晹僋儔僗<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/07/19丂徏塝丂庻旻丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	class CalendarCellRenderer extends DefaultTableCellRenderer {

		String psDateType = "";

		public Component getTableCellRendererComponent(JTable table, Object value,
								boolean isSelected, boolean isfocused, int row, int column){
			int iColumns[] = table.getSelectedColumns();
			int iDay = Integer.parseInt((String)table.getModel().getValueAt(row, column));
			Component defaultComponent = super.getTableCellRendererComponent(table,
												value, isSelected, isfocused, row, column);
			defaultComponent.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			switch(column){
				case 0:
					defaultComponent.setForeground(Color.red);
					break;
				case 6:
					defaultComponent.setForeground(Color.blue);
					break;
				default:
					defaultComponent.setForeground(Color.black);
					break;
			}
			if ( isSelected ) {
				if(iColumns.length > 1){
					_defaultCell(defaultComponent, iDay, row);
				} else {
					_selectedCell(defaultComponent);
				}
			} else {
				_defaultCell(defaultComponent, iDay, row);
			}
			return defaultComponent;
		}

		private void _selectedCell(Component defaultComponent){
			defaultComponent.setBackground(new Color(32, 32, 96));
			defaultComponent.setForeground(Color.yellow);
			defaultComponent.setFont(new Font("Dialog", Font.BOLD, 18));
		}

		private void _defaultCell(Component defaultComponent, int iDay, int row ){
			defaultComponent.setBackground( Color.white );
			defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 13));

			///++**************************************************************
			/// 堷悢偱庢摼偟偨jmsDATE偺DATE_TYPE偑YYYYMMDD偺応崌捠忢偺僇儗儞僟乕昞帵
			/// YYYYMM偺偲偒偼偝偑旤僇儗儞僟乕俀侾埲崀偼梻寧傪昞帵
			///--**************************************************************
			if( psDateType.equals("YYYYMM") == true ) {
				if(row == 5){
					if(( iDay <= 13 ) || ( iDay >= 30 )){
						defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
					}
				} else 	if(row == 4){
					if(( iDay <= 6 ) || ( iDay >= 22 )){
						defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
					}
				} else 	if(row >= 2){
					if( iDay >= 21) {
						defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
					}
				}
			} else {
				if(row == 0){
					if(iDay > 7){
						defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
					}
				} else if(row >= 4) {
					if(iDay < 14){
						defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
					}
				}
			}
		}

		public void setDateType( String prm_sDATE_TYPE ) {
			psDateType = prm_sDATE_TYPE;
		}
	}
}
