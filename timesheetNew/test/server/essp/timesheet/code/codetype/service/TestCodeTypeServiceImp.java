package server.essp.timesheet.code.codetype.service;

import junit.framework.*;
import java.util.*;

import c2s.essp.timesheet.code.*;
import server.essp.timesheet.code.codetype.dao.ICodeTypeDao;
import db.essp.timesheet.TsCodeType;
import c2s.dto.DtoUtil;
import c2s.dto.DtoComboItem;
import server.framework.common.BusinessException;
import c2s.dto.IDto;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TestCodeTypeServiceImp extends TestCase {
    private CodeTypeServiceImp codeTypeServiceImp = null;

    public TestCodeTypeServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        codeTypeServiceImp = new CodeTypeServiceImp();
    }

    protected void tearDown() throws Exception {
        codeTypeServiceImp = null;
        super.tearDown();
    }

    public void testListCodeTypeDtoComboItemNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        Vector vct = codeTypeServiceImp.ListCodeTypeDtoComboItem(false);
        assertEquals("Normal listComboItem size", 2, vct.size());
        DtoComboItem dto = (DtoComboItem) vct.get(0);
        assertEquals("Normal listComboItem itemName", "123", dto.getItemName());
        assertEquals("Normal listComboItem itemValue", Long.valueOf(123), dto.getItemValue());
    }

    public void testDeleteCodeTypeNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(222));
        codeTypeServiceImp.deleteCodeType(dtoCodeType);
    }

    public void testListCodeTypeNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        List list = codeTypeServiceImp.listCodeType(false);
        assertEquals("Normal listComboItem size", 3, list.size());
        DtoCodeType dto = (DtoCodeType) list.get(1);
        DtoCodeType expectedDto = getTestDto(Long.valueOf(321));
        assertEquals("Normal listCodeType getDescription", expectedDto.getDescription() ,dto.getDescription());
        assertEquals("Normal listCodeType getIsEnable", expectedDto.getIsEnable() ,dto.getIsEnable());
        assertEquals("Normal listCodeType getName", expectedDto.getName() ,dto.getName());
        assertEquals("Normal listCodeType getRid", expectedDto.getRid() ,dto.getRid());
        assertEquals("Normal listCodeType getSeq", expectedDto.getSeq() ,dto.getSeq());
    }

    public void testMoveDownCodeTypeNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(456));
        codeTypeServiceImp.moveDownCodeType(dtoCodeType);
    }

    public void testMoveUpCodeTypeNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(654));
        codeTypeServiceImp.moveUpCodeType(dtoCodeType);
    }

    public void testSaveCodeTypeNormal() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNormal());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(997));
        codeTypeServiceImp.saveCodeType(dtoCodeType);
    }

    public void testListCodeTypeDtoComboItemNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        Vector vct = codeTypeServiceImp.ListCodeTypeDtoComboItem(false);
        assertEquals("Null listComboItem size", 0, vct.size());
    }

    public void testDeleteCodeTypeNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(222));
        codeTypeServiceImp.deleteCodeType(dtoCodeType);
    }

    public void testListCodeTypeNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        List list = codeTypeServiceImp.listCodeType(false);
        assertEquals("Null listComboItem size", 0, list.size());
    }

    public void testMoveDownCodeTypeNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(456));
        try {
            codeTypeServiceImp.moveDownCodeType(dtoCodeType);
        } catch (BusinessException e) {
            assertEquals("Downmost Move Down ErrorCode", "error.logic.common.isDownmost", e.getErrorCode());
            assertEquals("Downmost Move Down ErrorMsg", "This code type is downmost", e.getErrorMsg());
        }
    }

    public void testMoveUpCodeTypeNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(654));
        try {
            codeTypeServiceImp.moveUpCodeType(dtoCodeType);
        } catch (BusinessException e) {
            assertEquals("Uppermost Move Up ErrorCode", "error.logic.common.isUppermost", e.getErrorCode());
            assertEquals("Uppermost Move Up ErrorMsg", "This code type is uppermost", e.getErrorMsg());
        }

    }

    public void testSaveCodeTypeNull() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpNull());
        DtoCodeType dtoCodeType = getTestDto(Long.valueOf(997));
        codeTypeServiceImp.saveCodeType(dtoCodeType);
    }

    public void testSaveCodeTypeAdd() {
        codeTypeServiceImp.setCodeTypeDao(new TestCodeTypeDaoImpAdd());
        DtoCodeType dtoCodeType = new DtoCodeType();
        dtoCodeType.setDescription("ddddd");
        dtoCodeType.setIsEnable(true);
        dtoCodeType.setName("testName");
        dtoCodeType.setOp(IDto.OP_INSERT);
        codeTypeServiceImp.saveCodeType(dtoCodeType);
    }

    private class TestCodeTypeDaoImpNormal implements ICodeTypeDao {
        public List listCodeType() {
            List list = new ArrayList();
            list.add(getTestBean(Long.valueOf(123)));
            list.add(getTestBean(Long.valueOf(321)));
            list.add(getTestBean(Long.valueOf(312)));
            return list;
        }

        public void add(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(codeType.getRid());
            assertEquals("Normal testSave getDescription", expectedBean.getDescription(), codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable(), codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName(), codeType.getName());
            assertNull("Normal testSave getRid", codeType.getRid());
            assertEquals("Normal testSave getSeq", Long.valueOf(getMaxSeq() + 1) ,codeType.getSeq());
        }

        public void update(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(codeType.getRid());
            assertEquals("Normal testSave getDescription", expectedBean.getDescription() ,codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable() ,codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName() ,codeType.getName());
            assertEquals("Normal testSave getRid", expectedBean.getRid() ,codeType.getRid());
        }


        public Long getMaxSeq() {
            return Long.valueOf(999);
        }

        public void delete(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(Long.valueOf(222));
            assertEquals("Normal testSave getDescription", expectedBean.getDescription() ,codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable() ,codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName() ,codeType.getName());
            assertEquals("Normal testSave getRid", expectedBean.getRid() ,codeType.getRid());
            assertEquals("Normal testSave getSeq", expectedBean.getSeq() ,codeType.getSeq());
        }

        public TsCodeType getUpSeqCodeType(Long seq) {
            return getTestBean(seq - 1);
        }

        public TsCodeType getDownSeqCodeType(Long seq) {
            return getTestBean(seq + 1);
        }

        public void setSeq(Long rid, Long seq) {
        }

        public List getCodeTypeList(String accountId) {
            return null;
        }

        public TsCodeType getCodeType(Long rid) {
            return null;
        }

		public List listCodeType(String isLeaveType) {
			 List list = new ArrayList();
	            list.add(getTestBean(Long.valueOf(123)));
	            list.add(getTestBean(Long.valueOf(321)));
	            list.add(getTestBean(Long.valueOf(312)));
	            return list;
		}

    }

    private class TestCodeTypeDaoImpNull implements ICodeTypeDao {
        public List listCodeType() {
            List list = new ArrayList();
            return list;
        }

        public void add(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(codeType.getRid());
            assertEquals("Normal testSave getDescription", expectedBean.getDescription(), codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable(), codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName(), codeType.getName());
            assertNull("Normal testSave getRid", codeType.getRid());
            assertEquals("Normal testSave getSeq", Long.valueOf(getMaxSeq() + 1) ,codeType.getSeq());
        }

        public void update(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(codeType.getRid());
            assertEquals("Normal testSave getDescription", expectedBean.getDescription() ,codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable() ,codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName() ,codeType.getName());
            assertEquals("Normal testSave getRid", expectedBean.getRid() ,codeType.getRid());
        }

        public void setSeq(Long rid, Long seq) {
        }

        public Long getMaxSeq() {
            return Long.valueOf(1);
        }

        public void delete(TsCodeType codeType) {
        }

        public TsCodeType getUpSeqCodeType(Long seq) {
            return null;
        }

        public TsCodeType getDownSeqCodeType(Long seq) {
            return null;
        }

        public List getCodeTypeList(String accountId) {
            return null;
        }

        public TsCodeType getCodeType(Long rid) {
            return null;
        }

		public List listCodeType(String isLeaveType) {
			List list = new ArrayList();
            return list;
		}
    }

    private class TestCodeTypeDaoImpAdd implements ICodeTypeDao {
        public List listCodeType() {
            List list = new ArrayList();
            return list;
        }

        public void add(TsCodeType codeType) {
            assertEquals("Normal testSave getDescription", "ddddd" ,codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", Boolean.TRUE ,codeType.getIsEnable());
            assertEquals("Normal testSave getName", "testName" ,codeType.getName());
            assertNull("Normal testSave getRid is null" ,codeType.getRid());
            assertEquals("Normal testSave getSeq", Long.valueOf(getMaxSeq() + 1) ,codeType.getSeq());
        }

        public void update(TsCodeType codeType) {
            TsCodeType expectedBean = getTestBean(codeType.getRid());
            assertEquals("Normal testSave getDescription", expectedBean.getDescription() ,codeType.getDescription());
            assertEquals("Normal testSave getIsEnable", expectedBean.getIsEnable() ,codeType.getIsEnable());
            assertEquals("Normal testSave getName", expectedBean.getName() ,codeType.getName());
            assertEquals("Normal testSave getRid", expectedBean.getRid() ,codeType.getRid());
        }

        public void setSeq(Long rid, Long seq) {
        }

        public Long getMaxSeq() {
            return Long.valueOf(97);
        }

        public void delete(TsCodeType codeType) {
        }

        public TsCodeType getUpSeqCodeType(Long seq) {
            return null;
        }

        public TsCodeType getDownSeqCodeType(Long seq) {
            return null;
        }

        public List getCodeTypeList(String accountId) {
            return null;
        }

        public TsCodeType getCodeType(Long rid) {
            return null;
        }

		public List listCodeType(String isLeaveType) {
			return null;
		}
    }



    private static TsCodeType getTestBean(Long id) {
        TsCodeType bean = new TsCodeType();
        bean.setRid(id);
        bean.setDescription(id.toString());
        bean.setIsEnable((id%2 == 1));
        bean.setName(id.toString());
        bean.setSeq(id);
        return bean;
    }

    private static DtoCodeType getTestDto(Long id) {
        DtoCodeType dto = new DtoCodeType();
        DtoUtil.copyBeanToBean(dto, getTestBean(id));
        return dto;
    }

}
