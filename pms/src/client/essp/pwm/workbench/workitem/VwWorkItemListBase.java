
package client.essp.pwm.workbench.workitem;

import client.essp.common.view.VWTableWorkArea;
import java.util.HashMap;
import java.util.Map;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import com.wits.util.StringUtil;
import c2s.essp.pwm.workbench.DtoKey;
import java.util.Date;
import com.wits.util.comDate;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.pwm.workbench.DtoPwWkitemPeriod;
import java.util.Set;
import java.util.*;

/**
 * <p>Title: 为VwWorkItemListBase访问数据库提供缓冲机制</p>
 */
public class VwWorkItemListBase extends VWTableWorkArea {
    private Map wkitemMap = new HashMap();

    protected ReturnInfo accessData(InputInfo inputInfo) {
        String funId = StringUtil.nvl(inputInfo.getFunId());

        //select
        if (funId.equals(DtoKey.SELECT) == true) {

            return select(inputInfo);
        } else if (funId.equals(DtoKey.RELOAD) == true) {

            return reload(inputInfo);
        } else if (funId.equals(DtoKey.UPDATE) == true) {

            return update(inputInfo);
        } else if (funId.equals(DtoKey.SAVE_TEMP_DATA) == true) {

            return saveTempDate(inputInfo);
        } else if (funId.equals(DtoKey.CUSTOM) == true) {

            return custom(inputInfo);
        } else if (funId.equals(DtoKey.DEL_CUSTOM) == true) {

            return delCustom(inputInfo);
        } else {
            return super.accessData(inputInfo);
        }
    }

    private ReturnInfo delCustom(InputInfo inputInfo) {
        ReturnInfo returnInfo = super.accessData(inputInfo);
        Calendar c = Calendar.getInstance();
        if( returnInfo.isError() == false ){
            List dateList = (List)returnInfo.getReturnObj(DtoKey.DUPLICATE_DATE_LIST);
            for (Iterator iter = dateList.iterator(); iter.hasNext(); ) {
                int[] item = (int[]) iter.next();
                c.set(item[0], item[1],item[2]);
                String sDate = dateToString(c.getTime());
                putToCache(sDate, null);
            }
        }

        return returnInfo;
    }

    private ReturnInfo custom(InputInfo inputInfo) {
        Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);
        DtoPwWkitemPeriod dto = (DtoPwWkitemPeriod)inputInfo.getInputObj(DtoKey.DTO);
        if (dto != null) {

            //"Custom"功能使缓冲区中selectedDate之后的数据都失效
            //因为往selectedDate之后的每个日期中可能新添了日报
            Set keySet = this.wkitemMap.keySet();
            for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
                String sDate = (String) iter.next();
                if( isDateBigger( sDate, selectedDate ) == true ){

                    //清空该日期的数据
                    putToCache(sDate, null);
                }
            }
        }

        return super.accessData(inputInfo);
    }

    private ReturnInfo saveTempDate(InputInfo inputInfo) {
        Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);
        List wkitemList = (List) inputInfo.getInputObj(DtoKey.WKITEM_LIST);
        if (wkitemList != null
            && wkitemList.size() > 0
            && selectedDate != null) {
            String sDate = dateToString(selectedDate);

            putToCache(sDate, wkitemList);
        }

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setReturnObj(DtoKey.WKITEM_LIST, wkitemList);
        returnInfo.setError(false);
        return returnInfo;
    }

    private ReturnInfo update(InputInfo inputInfo) {
        Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);
        if (selectedDate != null) {
            String sDate = dateToString(selectedDate);

            //access database
            ReturnInfo returnInfo = super.accessData(inputInfo);
            if (returnInfo.isError() == false) {

                //put to cache
                List wkitemList = (List) returnInfo.getReturnObj(DtoKey.WKITEM_LIST);
                if (wkitemList == null) {
                    wkitemList = new ArrayList();
                }
                putToCache(sDate, wkitemList);
            }
            return returnInfo;

        } else {

            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, new ArrayList());
            returnInfo.setError(false);
            return returnInfo;
        }
    }

    private ReturnInfo reload(InputInfo inputInfo) {
        Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);
        if (selectedDate != null) {
            String sDate = dateToString(selectedDate);

            //access database
            ReturnInfo returnInfo = super.accessData(inputInfo);
            if (returnInfo.isError() == false) {

                //put to cache
                List wkitemList = (List) returnInfo.getReturnObj(DtoKey.WKITEM_LIST);
                if (wkitemList == null) {
                    wkitemList = new ArrayList();
                }
                putToCache(sDate, wkitemList);
            }
            return returnInfo;

        } else {

            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, new ArrayList());
            returnInfo.setError(false);
            return returnInfo;
        }

    }

    private ReturnInfo select(InputInfo inputInfo) {
        Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);
        if (selectedDate != null) {
            String sDate = dateToString(selectedDate);
            List wkitemList = getFromCache(sDate);
            if (wkitemList != null) {

                //find in the cache
                ReturnInfo returnInfo = new ReturnInfo();
                returnInfo.setReturnObj(DtoKey.WKITEM_LIST, wkitemList);
                returnInfo.setError(false);
                return returnInfo;
            } else {

                //access database
                ReturnInfo returnInfo = super.accessData(inputInfo);
                if (returnInfo.isError() == false) {

                    //put to cache
                    wkitemList = (List) returnInfo.getReturnObj(DtoKey.WKITEM_LIST);
                    if (wkitemList == null) {
                        wkitemList = new ArrayList();
                    }
                    putToCache(sDate, wkitemList);
                }
                return returnInfo;
            }

        } else {

            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, new ArrayList());
            returnInfo.setError(false);
            return returnInfo;
        }
    }

    private void putToCache(String sDate, List wkitemList) {
        wkitemMap.put(sDate, wkitemList);
    }

    private List getFromCache(String sDate) {
        return (List) wkitemMap.get(sDate);
    }

    private String dateToString(Date date) {
        return comDate.dateToString(date, "yyyyMMdd");
    }

    private boolean isDateBigger(String sLeft, Date right ) {
        Date left = comDate.toDate(sLeft, "yyyyMMdd");

        int y1= left.getYear();
        int m1= left.getMonth();
        int d1 = left.getDate();

        int y2= right.getYear();
        int m2= right.getMonth();
        int d2 = right.getDate();

        long n1 = y1 * 1024 + m1 * 512 + d1;
        long n2 = y2 * 1024 + m2 * 512 + d2;
        if( ( n1 ) > (  n2  ) ){
            return true;
        }else{
            return false;
        }
    }
}
