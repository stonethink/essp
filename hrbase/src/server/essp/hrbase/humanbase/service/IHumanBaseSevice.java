/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.service;

import java.util.List;

import db.essp.hrbase.HrbHumanBase;
import server.essp.hrbase.humanbase.form.AfHumanBase;
import server.essp.hrbase.humanbase.form.AfHumanBaseLog;
import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;

public interface IHumanBaseSevice {

    public List getHrBaseByCondition(AfHumanBaseQuery af);
    
    public List listAddingLog(String sites);
    
    public List listEditingLog(Long rid);

    public VbHumanBase loadHumanBase(Long rid);
    
    public VbHumanBaseLog loadHumanBaseLog(Long rid);
    
    public void addHumanBase(AfHumanBase af);
    
    public void updateHumanBase(AfHumanBase af);
    
    public void updateHumanBaseLog(AfHumanBaseLog af);
    
    public void deleteHumanBase(Long rid);
    
    public void cancelHumanBaseLog(Long rid);
    
    public List beanList2VbList(List<HrbHumanBase> beanList);
   
}
