package client.framework.view.vwcomp;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import client.framework.view.common.ComDATA;

/**
 * <p>タイトル: カレンダーダイアログ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class VWJCalendar extends JDialog {
    static final java.awt.Font DSP_FONT = new java.awt.Font("simsun", 0, 15);
    static final java.awt.Font BUTTON_FONT = new java.awt.Font("simsun", 0, 15);
    static final int min_Year = 1990; //　取り扱い最小年
    static final int max_Year = 2020; //　取り扱い最大年

    protected String psNewDate;
    protected String psOldDate;
    protected String psDATE_TYPE = ""; //取得したjmsDATEのDATE_TYPE
    int piYear;
    int piMonth;
    int piDay;

    protected boolean pbSelect;

    GregorianCalendar calendar = new GregorianCalendar();
    CalendarCellRenderer cCellRend = new CalendarCellRenderer();
    String weeks[] = new String[] {
                     "rsid.common.sun", "rsid.common.mon", 
                     "rsid.common.tue", "rsid.common.wed",
                     "rsid.common.thu", "rsid.common.fri", 
                     "rsid.common.sat"};
    String days[][] = new String[6][7];

    VWJDisp DSP_YEAR = new VWJDisp();
    VWJDisp DSP_MONTH = new VWJDisp();
    VWJButton BTN_RETURN = new VWJButton();
    VWJButton BTN_NEXT = new VWJButton();
    VWJButton BTN_OK = new VWJButton();
    NoEditeModel neModel = new NoEditeModel(days, weeks);
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable(neModel);
    VWJLabel jMsLabel1 = new VWJLabel();
    VWJLabel jMsLabel2 = new VWJLabel();
    VWJButton BTN_CANCEL = new VWJButton();
    VWJButton BTN_TODAY = new VWJButton();

    /**
     *<BR>
     *　タイプ　：　コンストラクタ<BR>
     *　処理名　：  初期処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    public VWJCalendar(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            InitUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *<BR>
     *　タイプ　：　コンストラクタ<BR>
     *　処理名　：　初期処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    public VWJCalendar() {
        this(null, "", true);
    }

    /**
     *<BR>
     *　タイプ　：　初期処理［JBuilder設定］<BR>
     *　処理名　：　コンポーネント設定<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void jbInit() throws Exception {
        this.setResizable(false);
        this.setTitle("");
        this.getContentPane().setLayout(null);
        DSP_MONTH.setToolTipText("");
        DSP_MONTH.setBackground(SystemColor.control);
        DSP_MONTH.setHorizontalAlignment(SwingConstants.RIGHT);
        DSP_MONTH.setFont(DSP_FONT);
        DSP_MONTH.setBounds(new Rectangle(96, 10, 30, 21));
        BTN_RETURN.setText("rsid.common.ultimo");
        BTN_RETURN.setFont(BUTTON_FONT);
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
        BTN_NEXT.setText("rsid.common.proximo");
        BTN_NEXT.setFont(BUTTON_FONT);
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
        BTN_OK.setText("rsid.common.ok");
        BTN_OK.setFont(BUTTON_FONT);
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
        DSP_YEAR.setFont(DSP_FONT);
        DSP_YEAR.setBounds(new Rectangle(15, 10, 55, 21));
        jMsLabel1.setText("rsid.common.year");
        jMsLabel1.setToolTipText("");
        jMsLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jMsLabel1.setFont(DSP_FONT);
        jMsLabel1.setBounds(new Rectangle(72, 13, 19, 21));
        jMsLabel2.setText("rsid.common.month");
        jMsLabel2.setFont(DSP_FONT);
        jMsLabel2.setBounds(new Rectangle(129, 13, 21, 21));
        BTN_CANCEL.setNextFocusableComponent(BTN_RETURN);
        BTN_CANCEL.setText("rsid.common.cancel");
        BTN_CANCEL.setFont(BUTTON_FONT);
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
        BTN_TODAY.setText("rsid.common.today");
        BTN_TODAY.setFont(BUTTON_FONT);
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
     *　タイプ　：　初期処理［ユーザ設定］<BR>
     *　処理名　：　コンポーネント設定<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void InitUser() throws Exception {

        ///////////////////////////////////////
        //  Dialogのサイズとタイトルを設定
        ///////////////////////////////////////
        setBounds(344, 250, 345, 345);
        setTitle("Calendar");

        //////////////////////////////////////////////////////////////
        //  テーブルの設定
        //  　・Cellの表示状態にCalendarCellRenderer(内部クラス)を登録
        //  　・ドラッグによるcellのサイズ変更を禁止
        //  　・ドラッグによる列の移動を禁止
        //  　・cellの選択モードをSINGLE_SELECTIONに設定
        /////////////////////////////////////////////////////////////
        for (int i = 0; i < neModel.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(cCellRend);
            jTable1.getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getSelectionModel().setSelectionMode(ListSelectionModel.
            SINGLE_SELECTION);

        ////////////////////////////////////
        //  ダイアログを画面の中央に配置
        ////////////////////////////////////
        java.awt.Dimension d1 = this.getToolkit().getScreenSize();
        java.awt.Dimension d2 = this.getSize();
        this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　ダイアログ表示処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     * @param   jmsDate      :呼び出し元コンポーネント
     * @param   prm_Date     :選択日付
     */
    public String showDialog(VWJDate jmsDate) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        psDATE_TYPE = jmsDate.getDataType(); //psDATE_TYPEの取得
        cCellRend.setDateType(psDATE_TYPE);
        pbSelect = false;
        if (jmsDate.checkValue() == 0) {
            psNewDate = jmsDate.getValueText();
            //Modifed by yery on 2004/11/22
//                        if ( psNewDate.length() == 6 ) {
//                                 psNewDate = psNewDate + "01";
//                         } else if ( psNewDate.length() == 4) {
//                                 psNewDate = psNewDate + "0101";
//                         }

            if (psDATE_TYPE.equals("YYYYMM")) {
                psNewDate = psNewDate + "01";
            } else if (psDATE_TYPE.equals("YYYY")) {
                psNewDate = psNewDate + "0101";
            } else if (psDATE_TYPE.equals("MM")) {
                psNewDate = "2000" + psNewDate + "01";
            } else if (psDATE_TYPE.equals("DD")) {
                psNewDate = "2000" + "01" + psNewDate;
            } else if (psDATE_TYPE.equals("MMDD")) {
                psNewDate = "2000" + psNewDate;
            } else if (psDATE_TYPE.equals("YYMMDD")) {
                psNewDate = "20" + psNewDate;
            } else if (psDATE_TYPE.equals("YYMM")) {
                psNewDate = "20" + psNewDate + "01";
            }

        } else {
            psNewDate = "";
        }

        psOldDate = psNewDate;
        _setCalendar();
        this.setVisible(true);
        /**
         * Add by yery on 2004/11/22
         */
        if (psNewDate != null && !psNewDate.equals("")) {
            if (psDATE_TYPE.equals("YYYYMM")) {
                psNewDate = psNewDate.substring(0, 6);
            } else if (psDATE_TYPE.equals("YYYY")) {
                psNewDate = psNewDate.substring(0, 4);
            } else if (psDATE_TYPE.equals("MM")) {
                psNewDate = psNewDate.substring(4, 6);
            } else if (psDATE_TYPE.equals("DD")) {
                psNewDate = psNewDate.substring(6, 8);
            } else if (psDATE_TYPE.equals("MMDD")) {
                psNewDate = psNewDate.substring(4, 8);
            } else if (psDATE_TYPE.equals("YYMMDD")) {
                psNewDate = psNewDate.substring(2, 8);
            } else if (psDATE_TYPE.equals("YYMM")) {
                psNewDate = psNewDate.substring(2, 6);
            }
        }
        return psNewDate;
    }

    class wAdapter extends WindowAdapter {
        public void windowDeactivated(WindowEvent e) {
            VWJCalendar.this.setVisible(true);
        }
    }


    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　カレンダー表示日付設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    protected void _setCalendar() {
        int dayCount;
        int startWeek;
        int uru;
        int temp;
        int iAfterMonth;
        int[][] monthDays = {
                            {
                            31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
                            , {
                            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
        };
        /////////////////////////////////////////////////////////////////////
        //  呼び出しもとのコンポーネントから値をとっていない場合はシステム日付をとる
        /////////////////////////////////////////////////////////////////////
        if (psNewDate.equals("")) {
            piYear = calendar.get(GregorianCalendar.YEAR);
            piMonth = calendar.get(GregorianCalendar.MONTH) + 1;
            piDay = calendar.get(GregorianCalendar.DATE);
        } else {
            try {
                piYear = Integer.parseInt(psNewDate.substring(0, 4));
                piMonth = Integer.parseInt(psNewDate.substring(4, 6));
                piDay = Integer.parseInt(psNewDate.substring(6, 8));
            } catch (NumberFormatException e) {
                System.out.println("ERROR : " + e);
                dispose();
            }
        }
        ////////////////////////////////////////
        // 今月が何曜日から始まっているかを調べる
        // DAY_OF_WEEK 1～7 > 日～土
        ////////////////////////////////////////
        GregorianCalendar thisMonth = new GregorianCalendar(piYear, piMonth - 1, 1);
        startWeek = thisMonth.get(GregorianCalendar.DAY_OF_WEEK) - 1;

        ///////////////////////////
        // うるう年かどうかの確認
        ///////////////////////////
        uru = calendar.isLeapYear(piYear) ? 0 : 1;

        DSP_YEAR.setText(Integer.toString(piYear));
        DSP_MONTH.setText(Integer.toString(piMonth));

        temp = startWeek - 1;
        iAfterMonth = piMonth - 2;
        if (iAfterMonth < 0) {
            iAfterMonth = 11;
        }
        for (int i = 0; i < startWeek; i++) {
            neModel.setValueAt(Integer.toString(monthDays[uru][iAfterMonth] - temp),
                               0, i);
            temp--;
        }

        dayCount = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = startWeek; j < 7; j++) {
                neModel.setValueAt(Integer.toString(dayCount), i, j);
                if ((dayCount == piDay) && (pbSelect == false)) {
                    jTable1.setColumnSelectionInterval(j, j);
                    jTable1.setRowSelectionInterval(i, i);
                    pbSelect = true;
                }
                if (dayCount == monthDays[uru][piMonth - 1]) {
                    dayCount = 0;
                }
                dayCount++;
            }
            startWeek = 0;
        }
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　選択日付取得処理<BR>
     *　備　考　：　ユーザがカレンダーで選択している日付を取得する<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    private void _selectDate() {
        int iColumn = jTable1.getSelectedColumn();
        int iRow = jTable1.getSelectedRow();
        piDay = Integer.parseInt(String.valueOf(jTable1.getValueAt(iRow, iColumn)));

        ///++**************************************************************
        /// 引数で取得したjmsDATEのDATE_TYPEがYYYYMMDDの場合通常のカレンダー
        /// YYYYMMのときはさが美カレンダー２１以降は翌月を取得
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
        if (iRow == 0) {
            if (piDay > 7) {
                piMonth = piMonth - 1;
                if (piMonth <= 0) {
                    piMonth = 12;
                    piYear = piYear - 1;
                    if (piYear < min_Year) {
                        piYear = max_Year;
                    }
                }
            }
        } else if (iRow >= 4) {
            if (piDay < 14) {
                piMonth = piMonth + 1;
                if (piMonth >= 13) {
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
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　日付データ作成処理<BR>
     *　備　考　：　年、月、日をまとめた日付データを作成する<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    private void _setDate() {
        psNewDate = "";
        psNewDate = psNewDate + ComDATA.zeroFormat(piYear, 4);
        psNewDate = psNewDate + ComDATA.zeroFormat(piMonth, 2);
        psNewDate = psNewDate + ComDATA.zeroFormat(piDay, 2);
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　数値データゼロ埋め処理<BR>
     *　備　考　：　数値データが必要桁数に満たない場合左からゼロ埋めする<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     * @param   iValue      :数値データ
     * @param   beam        :必要桁数
     * @return  sValue      :ゼロ埋め後データ
     */
    private String _zeroFormat(int iValue, int beam) {
        String sValue = new String();
        int iLength;
        sValue = String.valueOf(iValue);
        iLength = sValue.length();
        for (int i = iLength; i < beam; i++) {
            sValue = 0 + sValue;
        }
        return sValue;
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［前月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_RETURN_actionPerformed(ActionEvent e) {
        piMonth = piMonth - 1;
        if (piMonth <= 0) {
            piYear = piYear - 1;
            piMonth = 12;
            if (piYear < min_Year) {
                piYear = max_Year;
            }
        }
        _setDate();
        _setCalendar();
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［前月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_RETURN_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Event.ENTER) {
            piMonth = piMonth - 1;
            if (piMonth <= 0) {
                piYear = piYear - 1;
                piMonth = 12;
                if (piYear < min_Year) {
                    piYear = max_Year;
                }
            }
            _setDate();
            _setCalendar();
        }
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［次月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_NEXT_actionPerformed(ActionEvent e) {
        piMonth = piMonth + 1;
        if (piMonth >= 13) {
            piYear = piYear + 1;
            piMonth = 1;
            if (piYear > max_Year) {
                piYear = min_Year;
            }
        }
        _setDate();
        _setCalendar();
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［次月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_NEXT_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Event.ENTER) {
            piMonth = piMonth + 1;
            if (piMonth >= 13) {
                piYear = piYear + 1;
                piMonth = 1;
                if (piYear > max_Year) {
                    piYear = min_Year;
                }
            }
            _setDate();
            _setCalendar();
        }
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［ＯＫ］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_OK_actionPerformed(ActionEvent e) {
        _selectDate();
        dispose();
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［ＯＫ］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_OK_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Event.ENTER) {
            _selectDate();
            dispose();
        }
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　日付テーブルクリック処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void jTable1_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            _selectDate();
            dispose();
        }
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［キャンセル］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_CANCEL_actionPerformed(ActionEvent e) {
        psNewDate = psOldDate;
        dispose();
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［キャンセル］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_CANCEL_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Event.ENTER) {
            psNewDate = psOldDate;
            dispose();
        }
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　［今月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
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
     *　タイプ　：　イベント<BR>
     *　処理名　：　［今月］ボタン押下処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/18　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    void BTN_TODAY_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Event.ENTER) {
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
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　テーブルモデル設定<BR>
     *　備　考　：　内部クラス<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    class NoEditeModel extends DefaultTableModel {
        public NoEditeModel(Object[][] object1, Object[] object2) {
            super(object1, object2);
        }

        public boolean isCellEditable(int rowIndex, int colmunIndex) {
            return false;
        }
    }


    /**
     *<BR>
     *　タイプ　：　ユーザー定義<BR>
     *　処理名　：　テーブル表示処理設定<BR>
     *　備　考　：　内部クラス<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/07/19　松浦　寿彦　　新規作成<BR>
     *<BR>
     */
    class CalendarCellRenderer extends DefaultTableCellRenderer {

        String psDateType = "";

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean isfocused, int row, int column) {
            int iColumns[] = table.getSelectedColumns();
            int iDay = Integer.parseInt((String) table.getModel().getValueAt(row,
                column));
            Component defaultComponent = super.getTableCellRendererComponent(table,
                value, isSelected, isfocused, row, column);
            defaultComponent.setComponentOrientation(ComponentOrientation.
                RIGHT_TO_LEFT);
            switch (column) {
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
            if (isSelected) {
                if (iColumns.length > 1) {
                    _defaultCell(defaultComponent, iDay, row);
                } else {
                    _selectedCell(defaultComponent);
                }
            } else {
                _defaultCell(defaultComponent, iDay, row);
            }
            return defaultComponent;
        }

        private void _selectedCell(Component defaultComponent) {
            defaultComponent.setBackground(new Color(32, 32, 96));
            defaultComponent.setForeground(Color.yellow);
            defaultComponent.setFont(new Font("Dialog", Font.BOLD, 18));
        }

        private void _defaultCell(Component defaultComponent, int iDay, int row) {
            defaultComponent.setBackground(Color.white);
            defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 13));

            ///++**************************************************************
            /// 引数で取得したjmsDATEのDATE_TYPEがYYYYMMDDの場合通常のカレンダー表示
            /// YYYYMMのときはさが美カレンダー２１以降は翌月を表示
            ///--**************************************************************
            if (psDateType.equals("YYYYMM") == true) {
                if (row == 5) {
                    if ((iDay <= 13) || (iDay >= 30)) {
                        defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
                    }
                } else if (row == 4) {
                    if ((iDay <= 6) || (iDay >= 22)) {
                        defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
                    }
                } else if (row >= 2) {
                    if (iDay >= 21) {
                        defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
                    }
                }
            } else {
                if (row == 0) {
                    if (iDay > 7) {
                        defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
                    }
                } else if (row >= 4) {
                    if (iDay < 14) {
                        defaultComponent.setFont(new Font("Dialog", Font.PLAIN, 9));
                    }
                }
            }
        }

        public void setDateType(String prm_sDATE_TYPE) {
            psDateType = prm_sDATE_TYPE;
        }
    }

}
