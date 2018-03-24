/*
 * 
 * CreatedDate: 2004-7-14
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import java.awt.Point;
import java.util.*;
import java.math.BigDecimal;
import java.text.*;

public class Thing {
    private int number;
    private Date date;
    private Point point;
    private BigDecimal id;

    public Thing() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public Point getPoint() {
        return point;
    }
    
    public void setPoint(Point point) {
        this.point = point;
    }
    
    public String toString() {
        String dateString = null;
        if (date != null) {
            dateString = DateFormat.getDateInstance().format(date);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[Thing number=");
        sb.append(number);
        sb.append(" date=");
        sb.append(dateString);
        sb.append(" point=");
        if (point != null) {
            sb.append(point.toString());
        } else {
            sb.append("null");
        }
        sb.append(" id=");
        sb.append(id);
        sb.append("]");
        return sb.toString();
    }
	/**
	 * <br>
	 * CreatedDate: 2004-7-14<br>
	 * creater: yerj
	 * @return
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * <br>
	 * CreatedDate: 2004-7-14<br>
	 * creater: yerj
	 * @param decimal
	 */
	public void setId(BigDecimal decimal) {
		id = decimal;
	}

}
