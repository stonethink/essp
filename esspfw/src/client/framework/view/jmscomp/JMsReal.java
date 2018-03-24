package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JTextField;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import client.framework.view.common.*;

/**
 * <p>僞僀僩儖: JMsComp </p>
 * <p>愢柧: Javax.Swing宲彸偺僆儕僕僫儖僐儞億乕僱儞僩孮</p>
 * <p>挊嶌尃: milestone Copyright (c) 2002</p>
 * <p>夛幮柤: 儅僀儖僗僩乕儞姅幃夛幮</p>
 * @author 枹擖椡
 * @version 1.0
 */

public class JMsReal extends JTextField {

	/**		擖椡暥帤敾掕梡	*/
	public int         _iInputCharType;

	/**		僼傿乕儖僪僄儔乕敾掕梡	*/
	private String		_sField_Error   = null;

	/**		setEnabled偱偺嵟廔梫媮抣	*/
	private boolean		_bEnabled_Save;

	/**		僾儘僥僋僩丒僋儕傾丒僼儔僌	*/
        private boolean		_bProtectClearFlag;
        private boolean canNegative = false; //add by xh


	BorderLayout borderLayout1 = new BorderLayout();
	private int maxInputIntegerDigit=8;
	private int MaxInputDecimalDigit=0;

	public JMsReal() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			initBeanUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂弶婜壔<BR>
	 *丂張棟柤丂丗丂弶婜抣愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/31丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setDisabledTextColor(Color.black);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		this.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				this_actionPerformed(e);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				this_keyPressed(e);
			}
		});
		this.setLayout(borderLayout1);
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂弶婜壔<BR>
	 *丂張棟柤丂丗丂儐乕僓弶婜抣愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/31丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	private void initBeanUser() throws Exception {

		//++****************************
		//	弶婜抣愝掕
		//--****************************
		this._sField_Error	= "";
		_sField_Error	= "";
//		setKey( false );
//		setProtectClear( false );

        this.setFont(DefaultComp.REAL_FONT);

        //++****************************
		//	暥帤忣曬愝掕
		//--****************************
//		setAutoIME( true );
//		setInputStyle ( defComponent.INPUT_STYLE );
		setFont( DefaultComp.NUMBER_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	僾儘僷僥傿弶婜抣
		//--****************************
		this.setMaxInputIntegerDigit( DefaultComp.REAL_MAX_INTEGER_DIGIT );
		this.setMaxInputDecimalDigit( DefaultComp.REAL_MAX_DECIMAL_DIGIT );
		this.setValue( 0 );

		//++****************************
		//	攚宨怓愝掕
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	偦偺懠愝掕
		//--****************************
//		setSelectedInFocus( true );
		setEnabled( true );
//		setModified( false );

	}



        /**
         *<BR>
         *丂僞僀僾丂丗丂僀儀儞僩<BR>
         *丂張棟柤丂丗丂僉乕墴壓帪偺張棟<BR>
         *丂旛丂峫丂丗丂Enter僉乕墴壓偱僼傿乕儖僪堏摦<BR>
         *<BR>
         *丂曄峏棜楌<BR>
         *<BR>
         *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
         *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
         *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
         *   00.01   2004/11/30  Yery        Enter僀儀儞僩偺張棟傪嶍彍
         *<BR>
         */
	protected void this_keyPressed(KeyEvent e) {
                //偙偙偺ENTER僀儀儞僩偱栤戣傪敪惗偡傞偺偱丄嶍彍偡傞偼偢偱偡丅comFORM.setEnterOrder()傪嶲徠偔偩偝偄
		//Enter僉乕墴壓偺応崌TAB僉乕墴壓偵僀儀儞僩傪曄峏
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂僼僅乕僇僗帪偺張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/31丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	void this_focusGained(FocusEvent e) {
		String    sOldStr;

		//++****************************
		//	擖椡晄壜帪丄僼傿乕儖僪堏摦
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

		//++****************************
		//	擖椡曄姺乮仺捈愙擖椡乯
		//--****************************
		getInputContext().setCharacterSubsets( null );

		//++****************************
		//	攚宨怓愝掕
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	昞帵撪梕曐懚
		//--****************************
		sOldStr    = this.getText();
		/*setDocument(new InputDocument( 100,//','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
									   5,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));

	*/
       InputDocument document = new InputDocument(100, //','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
                                                  5,
                                                  getMaxInputIntegerDigit(),
                                                  getMaxInputDecimalDigit());

       document.setCanNegative(this.canNegative());
       setDocument(document);


     //++****************************
		//	昞帵撪梕暅妶
		//--****************************
//System.out.println( " removeNonNumeric( sOldStr ) : " +  removeNonNumeric( sOldStr ) );
		this.setText( removeNonNumeric( sOldStr ) );

		//++****************************
		//	攚宨怓愝掕
		//--****************************
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

		//++****************************
		//	慡慖戰忬懺
		//--****************************
		this.selectAll();

	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僀儀儞僩<BR>
	 *丂張棟柤丂丗丂儘僗偲僼僅乕僇僗帪偺張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void this_focusLost(FocusEvent e) {
		String  sStr1;
		String  sStr2;
		double  dvalue;

		//++****************************
		//	攚宨怓愝掕
		//--****************************
		_setBackgroundColor();

		if ( getText().trim().equals( "-" ) == false ) {
		   if ( getText().trim().equals( "." ) == false ) {


				StringBuffer sbuff = new StringBuffer();
				FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

				this.setText2( getText() );
		   }
		}
		//++****************************
		//	慖戰忬懺傪夝彍
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);

	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僾儘僷僥傿[ setText ]: set<BR>
	 *丂張棟柤丂丗丂僾儘僷僥傿[ setText ]偺愝掕儊僜僢僪<BR>
	 *丂旛丂峫丂丗丂setText偺僆乕僶乕儔僀僪娭悢<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setText (String sStr ) {

		/*setDocument(new InputDocument( 100,//','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
									   5,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));
	*/
       InputDocument document = new InputDocument(100, //','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
                                                  5,
                                                  getMaxInputIntegerDigit(),
                                                  getMaxInputDecimalDigit());

       document.setCanNegative(this.canNegative());
       setDocument(document);

       super.setText(sStr);
  }



	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僾儘僷僥傿[ setText ]: set<BR>
	 *丂張棟柤丂丗丂僾儘僷僥傿[ setText ]偺愝掕儊僜僢僪<BR>
	 *丂旛丂峫丂丗丂setText偺僆乕僶乕儔僀僪娭悢<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setText2 (String sStr ) {

		/*setDocument(new InputDocument( 100,//','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
									   0,
									   getMaxInputIntegerDigit(),
									   getMaxInputDecimalDigit() ));
                */
               InputDocument document = new InputDocument( 100,//','偑偮偄偨応崌偵丄偳偺偔傜偄憹偊傞偐傢偐傜側偄偺偱100偵僙僢僩
                                                                           0,
                                                                           getMaxInputIntegerDigit(),
                                                                           getMaxInputDecimalDigit() );
              document.setCanNegative(this.canNegative());
              setDocument(document);
		super.setText( fromatFractionDigits( sStr ) );
  }

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂暥帤楍憖嶌張棟<BR>
	 *丂旛丂峫丂丗丂悢帤媦傃乽.乿埲奜傪暥帤楍偐傜奜偡<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	private String removeNonNumeric( String oldStr ){
		StringBuffer newStr = new StringBuffer();

		boolean bFrg  = false;
		char char2;

		for( int i=0 ; i<oldStr.length() ; i++ ){
			char chr = oldStr.charAt(i);
			//if( Character.isDigit( chr ) ){ 偙傟偱偼慡妏偺悢帤傕捠偟偰偟傑偆
			if( ( '0' <= chr && chr <= '9' ) || chr == '.' || chr == '-'  ){
				try {
					newStr.append( chr );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

        //add: 防止下面情况出现"-"的情况
        if( newStr.equals( "-" ) == true ){
            newStr = new StringBuffer("");
        }

		return( newStr.toString() );
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂暥帤楍悢抣楍曄峏憖嶌<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public String fromatFractionDigits (String prm_sStr ) {
		String  sStr1;
		String  sStr2;
		double  dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( prm_sStr.equals( "" ) == false ) {

			//dvalue  = Double.parseDouble( prm_sStr ); replaced by yery on 2004/12/22
                        dvalue  = Double.parseDouble( prm_sStr.replaceAll(",","") );
			DecimalFormat df = new DecimalFormat();

			df.setMinimumFractionDigits( getMaxInputDecimalDigit() );
			df.format( dvalue, sbuff, fpos );

			return sbuff.toString();
		} else {
			return "";
		}

	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂攚宨怓愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void _setBackgroundColor(
	) {
		if ( isEnabled() == true ) {
			setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
		} else {
			setBackground( DefaultComp.BACKGROUND_COLOR_DISABLED );
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僾儘僷僥傿[ Field_Error ]: get<BR>
	 *丂張棟柤丂丗丂僾儘僷僥傿[ Field_Error ]偺庢摼儊僜僢僪<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public String getField_Error () {
		//++******************************
		//	栠傝抣愝掕
		//--******************************
		return this._sField_Error;
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僾儘僷僥傿[ Field_Error ]: set<BR>
	 *丂張棟柤丂丗丂僾儘僷僥傿[ Field_Error ]偺愝掕儊僜僢僪<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	擖椡抣傪曐懚
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	暥帤怓愝掕
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	擖椡壜斲愝掕
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	僾儘僥僋僩帪偺僋儕傾張棟
			//--********************************
			if ( _bProtectClearFlag == true ) {
				setText( "" );
			}

			//++********************************
			//	擖椡晄壜偵愝掕
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	嵟廔setEnabled愝掕梫媮抣偵愝掕
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂僾儘僷僥傿[ Enabled ]: set<BR>
	 *丂張棟柤丂丗丂僾儘僷僥傿[ Enabled ]偺愝掕儊僜僢僪<BR>
	 *丂旛丂峫丂丗丂setEnabled偺僆乕僶乕儔僀僪娭悢<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setEnabled(
		boolean		prm_bValue
	) {

		//++****************************
		//	梫媮抣曐懚
		//--****************************
		_bEnabled_Save	= prm_bValue;

		//++**************************************
		//	擖椡晄壜偺応崌丄張棟僉儍儞僙儖
		//--**************************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	擖椡晄壜愝掕
		//--****************************
		super.setEnabled( prm_bValue );
		_setBackgroundColor();

	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂僄儔乕忬懺愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setErrorField(
		boolean		prm_bError
	) {
		//++********************************
		//	僾儘僥僋僩忬懺偺応崌丄張棟側偟
		//--********************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++********************************
		//	僄儔乕忬懺愝掕
		//--********************************
		if ( prm_bError == true ) {
			setField_Error( DefaultComp.FIELD_ERROR );
		} else {
			setField_Error( "" );
		}
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂僄儔乕忬懺愝掕張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void clearText(
	) {
		//++********************************
		//	昞帵抣傪僋儕傾偡傞
		//--********************************
		this.setText( "" );
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂昞帵僨乕僞庢摼張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public double getValue(
	) {
		double	dDoub;
		Double	dTemp;
		String  sStr;

		sStr	= getText();

		//擖椡偑柍偐偭偨応崌丄0傪曉偡
		if ( sStr.equals( "" ) == true ) {
			dTemp	= Double.valueOf( "0" );
		} else {
			sStr    = removeNonNumeric( sStr );
			dTemp	= Double.valueOf( sStr );
		}
		dDoub = dTemp.doubleValue();
		return dDoub;
	}


	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕娭悢<BR>
	 *丂張棟柤丂丗丂昞帵僨乕僞庢摼張棟<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 */
	public void setValue(
		double	prm_dDoub
	) {
		String  sStr;

		sStr	= Double.toString( prm_dDoub);

/*        //擖椡偑柍偐偭偨応崌丄0傪曉偡
		if ( sStr.equals( "" ) == true ) {
			dNum	= Double.valueOf( "0" );
		} else {
			sStr    = removeNonNumeric( sStr );
			dNum	= Double.valueOf( sStr );
		}
		return dNum;
*/
		this.setText2( sStr );
	}

	/**
	 *<BR>
	 *丂僞僀僾丂丗丂儐乕僓乕掕媊<BR>
	 *丂張棟柤丂丗丂擖椡敾掕<BR>
	 *丂旛丂峫丂丗丂<BR>
	 *<BR>
	 *丂曄峏棜楌<BR>
	 *<BR>
	 *丂丂Version丂丂擔丂晅丂丂丂峏怴幰丂丂丂丂丂僐儊儞僩<BR>
	 *丂亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅亅<BR>
	 *丂丂00.00丂丂2002/05/30丂曮棃丂岾峅丂丂怴婯嶌惉<BR>
	 *<BR>
	 * @return 	0:OK 1:枹擖椡  <0:僄儔乕
	 */
	public int checkValue(
	) {
		String		sStr;
		long		lNum;

		sStr	= getText();

		//++****************************
		//	null敾掕
		//--****************************
		if ( sStr == null ) {
			return 1;
		}

		//++****************************
		//	枹擖椡敾掕敾掕
		//--****************************
		if ( sStr.trim().length() == 0 ) {
			return 1;
		}

		//++****************************
		//	悢抣敾掕
		//--****************************
		try {
//			lNum	= getValue();
			return 0;
		} catch ( Exception clsExcept ) {
			return -1;
		}
	}
	public void setMaxInputIntegerDigit(int maxInputIntegerDigit) {
		this.maxInputIntegerDigit = maxInputIntegerDigit;
	}
	public int getMaxInputIntegerDigit() {
		return maxInputIntegerDigit;
	}
	public int getMaxInputDecimalDigit() {
		return MaxInputDecimalDigit;
	}
	public void setMaxInputDecimalDigit(int MaxInputDecimalDigit) {
		this.MaxInputDecimalDigit = MaxInputDecimalDigit;
	}

	void this_actionPerformed(ActionEvent e) {

        }

        public boolean canNegative() {
            return this.canNegative;
        }

        public void setCanNegative(boolean canNegative) {
            this.canNegative = canNegative;
        }
}
