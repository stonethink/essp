package server.essp.issue.common.logic;

import server.framework.logic.*;
import java.util.List;
import java.util.ArrayList;
import c2s.dto.DtoUtil;
import java.util.HashMap;
import server.essp.issue.common.constant.Sort;
import java.util.Map;
import server.framework.common.BusinessException;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * 通用排序
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgSort extends AbstractBusinessLogic {
    public LgSort() {
    }

    public String updateSortInfo(String oldSortInfo, String sortItem) {
        Map sortInfoMap = null;
        String newSortInfo = "";
        try {
            /**
             * 检查原来是否有过排序操作
             */
            sortInfoMap = null;
            List sortList = null;
            if (oldSortInfo != null && !oldSortInfo.equals("")) {
                sortInfoMap = DtoUtil.decodeParam(oldSortInfo);
                sortList = (List) sortInfoMap.get("sortList");
            } else {
                sortInfoMap = new HashMap();
                sortList = new ArrayList();
                sortInfoMap.put("sortList", sortList);
            }
            if (sortItem != null && !sortItem.equals("")) {
                String sortKind = (String) sortInfoMap.get(sortItem);
                if (sortKind == null) {
                    sortInfoMap.put(sortItem, Sort.SORT_DESC);
                } else {
                    if (sortKind.equals(Sort.SORT_DESC)) {
                        sortInfoMap.put(sortItem, Sort.SORT_ASC);
                    } else {
                        sortInfoMap.put(sortItem, Sort.SORT_DESC);
                    }
                }
                sortList.remove(sortItem);
                sortList.add(0, sortItem);
            }
            newSortInfo = DtoUtil.encodeParam(sortInfoMap);
        } catch (Exception ex) {
            throw new BusinessException("LgSort", "sort error:" + ex.getMessage(),
                                        ex);
        }
        return newSortInfo;
    }

    public void sort(String sortInfo, List details) {
        if(sortInfo==null || details==null || details.size()<=1) {
            return;
        }
        Map sortInfoMap = null;
        try {
            /**
             * 检查原来是否有过排序操作
             */
            sortInfoMap = null;
            List sortList = null;
            if (sortInfo != null && !sortInfo.equals("")) {
                sortInfoMap = DtoUtil.decodeParam(sortInfo);
                sortList = (List) sortInfoMap.get("sortList");
            } else {
                sortInfoMap = new HashMap();
                sortList = new ArrayList();
                sortInfoMap.put("sortList", sortList);
            }
            if(sortList==null || sortList.size()==0) {
                return;
            }
            SortComparator comparator=new SortComparator(sortInfoMap);
            TreeSet treeSet=new TreeSet(comparator);
            for(int i=0;i<details.size();i++) {
                treeSet.add(details.get(i));
            }
            details.clear();
            for(Iterator it=treeSet.iterator();it.hasNext();) {
                Object obj=it.next();
                details.add(obj);
            }
        } catch (Exception ex) {
            throw new BusinessException("LgSort", "sort error:" + ex.getMessage(),
                                        ex);
        }
    }

    public static void main(String[] args) {
        LgSort lgsort = new LgSort();
    }
}

class SortComparator implements Comparator {
    private List sortList;
    private Map sortInfoMap;
    public SortComparator(Map sortInfoMap) {
        this.sortInfoMap=sortInfoMap;
        sortList = (List) sortInfoMap.get("sortList");
    }

    private void fillSpace(StringBuffer value1,StringBuffer value2) {
        int spaceSize=value1.length()-value2.length();
        if(spaceSize!=0) {
            if(spaceSize>0) {
                for(int i=0;i<spaceSize;i++) {
                    value2.append(" ");
                }
            } else {
                for(int i=0;i<spaceSize;i++) {
                    value1.append(" ");
                }
            }
        }
    }

    public int compare(Object o1, Object o2) {
        if(sortList.size()>0) {
            StringBuffer value1=new StringBuffer("");
            StringBuffer value2=new StringBuffer("");
            for(int i=0;i<sortList.size();i++) {
                String propertyName=(String)sortList.get(i);
                String sortKind=(String)sortInfoMap.get(propertyName);
                Object propertyValue1=null;
                Object propertyValue2=null;
                try {
                    propertyValue1 = DtoUtil.getProperty(o1, propertyName);
                } catch (Exception ex) {
                }
                try {
                    propertyValue2 = DtoUtil.getProperty(o2, propertyName);
                } catch (Exception ex) {
                }
                if(propertyValue1==null) {
                    propertyValue1="";
                } else {
                    propertyValue1=propertyValue1.toString();
                }
                if(propertyValue2==null) {
                    propertyValue2="";
                } else {
                    propertyValue2=propertyValue2.toString();
                }
                if(sortKind.equals(Sort.SORT_DESC)) {
                    value1.append(propertyValue1);
                    value2.append(propertyValue2);
                } else {
                    value1.append(propertyValue2);
                    value2.append(propertyValue1);
                }
                fillSpace(value1,value2);
                value1.append(" ");
                value2.append(" ");
            }
            String strValue1=value1.toString().toLowerCase();
            String strValue2=value2.toString().toLowerCase();
            int compareResult=strValue1.compareTo(strValue2);
            if(compareResult==0) {
                compareResult=o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
            }
            return compareResult;
        } else {
            return o1.toString().compareTo(o2.toString());
        }
    }
}
