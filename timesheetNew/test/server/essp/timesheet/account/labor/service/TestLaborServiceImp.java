package server.essp.timesheet.account.labor.service;

import junit.framework.TestCase;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import java.util.List;
import db.essp.timesheet.TsLaborResource;
import java.util.ArrayList;
import c2s.essp.timesheet.labor.DtoLaborResource;
import server.framework.common.BusinessException;
import java.util.Map;
import java.util.HashMap;

import static org.easymock.EasyMock.*;
import c2s.dto.DtoUtil;

public class TestLaborServiceImp extends TestCase {

    private LaborServiceImp laborServiceImp;
    private ILaborDao laborDaoMock;
    private static Map<String, Object> dataMap;
static {
        dataMap = new HashMap<String,Object>();
        TsLaborResource tsLabor = new TsLaborResource();
        List list = new ArrayList();
        tsLabor.setRid(Long.valueOf(1));
        tsLabor.setLoginId("wh0607015");
        tsLabor.setName("wenhaizheng");
        tsLabor.setAccountRid(Long.valueOf(1));
        tsLabor.setStatus("In");
        tsLabor.setRm("wh0607015");
        tsLabor.setRemark("PG");
        dataMap.put("data", tsLabor);
        list.add(tsLabor);
        tsLabor = new TsLaborResource();
        tsLabor.setRid(Long.valueOf(2));
        tsLabor.setLoginId("wh0607016");
        tsLabor.setName("baohuitu");
        tsLabor.setAccountRid(Long.valueOf(1));
        tsLabor.setStatus("In");
        tsLabor.setRm("wh0607015");
        tsLabor.setRemark("PG");
        list.add(tsLabor);
        dataMap.put("dataList", list);

}
    protected void setUp() throws Exception {
        super.setUp();
        laborServiceImp = new LaborServiceImp();
        laborDaoMock = createMock(ILaborDao.class);

    }
     protected void tearDown() throws Exception {
         laborServiceImp = null;
         super.tearDown();
     }

     public void testListLabor_hasdata(){
         Long accountRid = Long.valueOf(1);
         expect(laborDaoMock.listLabor(accountRid))
                 .andReturn((List)dataMap.get("dataList"));
         replay(laborDaoMock);
         laborServiceImp.setLaborDao(laborDaoMock);
         List list = laborServiceImp.listLabor(accountRid);
         this.assertNotNull("result is null",list);
         DtoLaborResource dtoLabor = (DtoLaborResource)list.get(0);
         this.assertEquals("first login id is not wh0607015", "wh0607015", dtoLabor.getLoginId());
         this.assertEquals("", "wenhaizheng", dtoLabor.getName());
         dtoLabor = (DtoLaborResource)list.get(1);
         this.assertEquals("first login id is not wh0607015", "wh0607016", dtoLabor.getLoginId());
         this.assertEquals("", "baohuitu", dtoLabor.getName());

     }
     public void testAddLabor_success(){
         DtoLaborResource dtoLabor = new DtoLaborResource();
         dtoLabor.setLoginId("wh0607015");
         dtoLabor.setName("wenhaizheng");
         dtoLabor.setAccountRid(Long.valueOf(1));
         dtoLabor.setStatus("In");
         dtoLabor.setRm("wh0607015");
         dtoLabor.setRemark("PG");

         expect(laborDaoMock.loadByAccountIdLoginId(Long.valueOf(1), "wh0607015")).andReturn(null);
         TsLaborResource tsLabor = new TsLaborResource();

         laborDaoMock.addLabor((TsLaborResource)anyObject());

         expectLastCall().once();
         replay(laborDaoMock);
         laborServiceImp.setLaborDao(laborDaoMock);

         laborServiceImp.addLabor(dtoLabor);
     }
     public void testSaveLabor_success(){
         expect(laborDaoMock.loadLabor(Long.valueOf(1))).andReturn((TsLaborResource)dataMap.get("data"));
         laborDaoMock.saveLabor((TsLaborResource)dataMap.get("data"));
         expectLastCall().once();
         replay(laborDaoMock);
         laborServiceImp.setLaborDao(laborDaoMock);
         DtoLaborResource dtoLabor = new DtoLaborResource();
         dtoLabor.setRid(Long.valueOf(1));
         dtoLabor.setLoginId("wh0607015");
         dtoLabor.setName("wenhaizheng");
         dtoLabor.setAccountRid(Long.valueOf(1));
         dtoLabor.setStatus("In");
         dtoLabor.setRm("wh0607015");
         dtoLabor.setRemark("PG");
         laborServiceImp.saveLabor(dtoLabor);
     }
     public void testDeleteLabor_success(){
         laborDaoMock.deleteLabor((TsLaborResource)anyObject());
         expectLastCall().once();
         replay(laborDaoMock);
         laborServiceImp.setLaborDao(laborDaoMock);
         DtoLaborResource dtoLabor = new DtoLaborResource();
         dtoLabor.setRid(Long.valueOf(1));
         dtoLabor.setLoginId("wh0607015");
         dtoLabor.setName("wenhai.zheng");
         dtoLabor.setAccountRid(Long.valueOf(1));
         dtoLabor.setStatus("In");
         dtoLabor.setRm("wh0607015");
         dtoLabor.setRemark("PG");
         laborServiceImp.deleteLabor(dtoLabor);

     }
     public void testListLabor_nodata(){
         expect(laborDaoMock.listLabor(Long.valueOf(1)))
                .andReturn((List)dataMap.get(null));
         replay(laborDaoMock);

         laborServiceImp.setLaborDao(laborDaoMock);
         List list =  laborServiceImp.listLabor(Long.valueOf(1));
         this.assertNotNull("result is null",list);
         this.assertEquals("result's size is 0", 0, list.size());

     }
     public void testAddLabor_duplicate(){
         expect(laborDaoMock.loadByAccountIdLoginId(Long.valueOf(1),"wh0607015"))
                 .andReturn((TsLaborResource)dataMap.get("data"));
         replay(laborDaoMock);
         laborServiceImp.setLaborDao(laborDaoMock);
         DtoLaborResource dtoLabor = new DtoLaborResource();
         dtoLabor.setRid(Long.valueOf(1));
         dtoLabor.setLoginId("wh0607015");
         dtoLabor.setName("wenhaizheng");
         dtoLabor.setAccountRid(Long.valueOf(1));
         dtoLabor.setStatus("In");
         dtoLabor.setRm("wh0607015");
         dtoLabor.setRemark("PG");
         try{
             laborServiceImp.addLabor(dtoLabor);
         } catch(BusinessException e) {
             if(!"This person is already in this project".equals(e.getErrorMsg())){
                 fail("BusinessException should be thrown!");
             }
         }
     }

}
