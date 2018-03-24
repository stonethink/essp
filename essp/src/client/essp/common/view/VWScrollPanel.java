package client.essp.common.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import com.wits.util.TestPanel;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class VWScrollPanel{


    /*
    scrollPanel()���÷�����װpanel, ���ؽ��newPanel���й�����
    ==============================================================
    ||	����newPanel = scrollPanel( panel ), �����         	    ||
    ||	newPanel�ټ���һ��parentPanelʱ��parentPanel              ||
    ||	�Ĳ���Ӧ��ΪBorderLayout,���£�                            ||
    ||	parentPanel.setLayout( new BorderLayout() );            ||
    ||	JPanel newPanel = VWScrollPanel.scrollPanel(panel);     ||
    ||	parentPanel.add( newPanel, BorderLayout.CENTER );       ||
    ||	                                                        ||
    ||	-------------------------------------------     		||
    ||	| newPanel  					          |     		||
    ||  |                                         |    		    ||
    ||  | layout=BorderLayout                     |    		    ||
    ||	| newPanel.add( scrollPane, CENTER );     |     		||
    ||	| return newPanel;					      |     		||
    ||	|	-----------------------------         |     		||
    ||	|	|scrollPane			 		|         |     		||
    ||	|	|	------------------      |         |     		||
    ||	|	|	|panel	 	 	 |      |         |     		||
    ||	|	|	|                |      |         |     		||
    ||	|	|	|  layout = null |      |         |     		||
    ||	|	|	|                |      |         |     		||
    ||	|	|	|                |      |         |     		||
    ||	|	|	|                |      |         |     		||
    ||	|	|	|		 		 |      |         |     		||
    ||	|	|	------------------      |         |     		||
    ||  |   |                           |         |    		    ||
    ||  |   -----------------------------         |    		    ||
    ||  |                                         |    		    ||
    ||   -------------------------------------------    		||
    ||                                                  		||
    ==============================================================
    */
    public static VWJPanel addScroll( VWJPanel panel1 ){
        VWJPanel panel = panel1;
        if( panel == null ){
            panel = new VWJPanel();
        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().add( panel );

        VWJPanel newPanel = new VWJPanel();
//        newPanel.setLayout( new BorderLayout() ); //VWJPanelĬ��ΪBorderLayout
        newPanel.add( scrollPane, BorderLayout.CENTER  );

        return newPanel;
    }

    public static void main( String[] args ){
        int i = 600;
        VWJPanel panel = new VWJPanel();
        panel.setLayout( null );

        ImageIcon img = new ImageIcon( "crayons.jpg" );
//        ImageIcon img = new ImageIcon( "E:/essp/esspfw/classes/client/essp/common/view/crayons.jpg" );
        JLabel b = new JLabel( img ) ;
        b.setBounds(10,10,i,i);

        panel.add( b );
        panel.setPreferredSize( new Dimension(i,i) );
        panel.setMaximumSize( new Dimension(i,i) );
        panel.setMinimumSize( new Dimension(i,i) );
        panel.setBackground( Color.darkGray );
        panel.setBorder( BorderFactory.createLineBorder( Color.yellow ));

        //begin call scrollPanel()
        VWJPanel newPanel = VWScrollPanel.addScroll( panel );

        JPanel parentPanel = new JPanel();
        parentPanel.setLayout( new BorderLayout() );
        parentPanel.setBackground( Color.darkGray );
        parentPanel.add( newPanel, BorderLayout.CENTER  );


//        JFrame frame = new JFrame();
//        frame.setSize(1000, 600);
//
//        frame.getContentPane().add( parentPanel );
//        frame.pack();
//        frame.setVisible(true);

        TestPanel.show( parentPanel );

    }
}
