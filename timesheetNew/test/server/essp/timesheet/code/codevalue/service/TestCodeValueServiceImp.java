package server.essp.timesheet.code.codevalue.service;

import junit.framework.*;
import c2s.essp.timesheet.code.*;
import c2s.dto.*;
import java.util.*;

import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import db.essp.timesheet.TsCodeValue;

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
public class TestCodeValueServiceImp extends TestCase {
    private CodeValueServiceImp codeValueServiceImp = null;

    public TestCodeValueServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        codeValueServiceImp = new CodeValueServiceImp();
    }

    protected void tearDown() throws Exception {
        codeValueServiceImp = null;
        super.tearDown();
    }

    public void testDeleteBeanNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        DtoCodeValue dtoCodeValue = getTestDto(753, false);
        codeValueServiceImp.delete(dtoCodeValue);
    }

    public void testDeleteTreeNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        ITreeNode node = new DtoTreeNode(getTestDto(753, false));
        codeValueServiceImp.delete(node);
    }

    public void testGetJobCoeValueTreeNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        ITreeNode root = codeValueServiceImp.getJobCoeValueTree();
        assertNotNull("root not exist", root);
        assertEquals("children number", 3, root.getChildCount());
        DtoCodeValue expectedDto = getTestDto(1, true);
        DtoCodeValue dto = (DtoCodeValue) root.getDataBean();
        assertEquals("root description", expectedDto.getDescription(), dto.getDescription());
        assertEquals("root enable", expectedDto.getIsEnable(), dto.getIsEnable());
        assertEquals("root isLeaveType", expectedDto.getIsLeaveType(), dto.getIsLeaveType());
        assertEquals("root name", expectedDto.getName(), dto.getName());
        assertEquals("root parentRid", expectedDto.getParentRid(), dto.getParentRid());
        assertEquals("root rid", expectedDto.getRid(), dto.getRid());
        assertEquals("root seq", expectedDto.getSeq(), dto.getSeq());
    }

    public void testListLeaveCodeValueNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        List leaveList = codeValueServiceImp.listLeaveCodeValue();
        assertNotNull("leave list not null", leaveList);
        assertEquals("leave list size", 3, leaveList.size());
        DtoCodeValue expectedDto = getTestDto(456, true);
        DtoCodeValue dto = (DtoCodeValue) leaveList.get(0);
        assertEquals("root description", expectedDto.getDescription(), dto.getDescription());
        assertEquals("root enable", expectedDto.getIsEnable(), dto.getIsEnable());
        assertEquals("root isLeaveType", expectedDto.getIsLeaveType(), dto.getIsLeaveType());
        assertEquals("root name", expectedDto.getName(), dto.getName());
        assertEquals("root parentRid", expectedDto.getParentRid(), dto.getParentRid());
        assertEquals("root rid", expectedDto.getRid(), dto.getRid());
        assertEquals("root seq", expectedDto.getSeq(), dto.getSeq());

    }

    public void testMoveDownCodeValueNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        DtoCodeValue dtoCodeValue = getTestDto(456, true);
        codeValueServiceImp.moveDownCodeValue(dtoCodeValue);
    }

    public void testMoveUpCodeValueNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        DtoCodeValue dtoCodeValue = getTestDto(456, true);
        codeValueServiceImp.moveUpCodeValue(dtoCodeValue);
    }

    public void testSaveNormal() {
        codeValueServiceImp.setCodeValueDao(new TestCodeValueDaoImpNormal());
        DtoCodeValue dtoCodeValue = getTestDto(456, true);
        codeValueServiceImp.save(dtoCodeValue);
    }

    private class TestCodeValueDaoImpNormal implements ICodeValueDao {
        public List listLeaveCodeValue() {
            List list = new ArrayList();
            list.add(getTestBean(456, true));
            list.add(getTestBean(321, true));
            list.add(getTestBean(258, true));
            return list;
        }

        public TsCodeValue getRootJobCodeValue() {
            return getTestBean(1, true);
        }

        public List listJobCodeValueByParentRid(Long parentRid) {
            List list = new ArrayList();
            if(parentRid != 1) return list;

            list.add(getTestBean(9, false));
            list.add(getTestBean(10, false));
            list.add(getTestBean(11, false));
            return list;
        }

        public TsCodeValue getCodeValue(Long rid) {
            return getTestBean(rid.intValue(), false);
        }

        public void add(TsCodeValue tsCodeValue) {
        }

        public Long getJobCodeMaxSeq(Long parentRid) {
            return Long.valueOf(99);
        }

        public Long getLeaveCodeMaxSeq() {
            return Long.valueOf(33);
        }

        public void update(TsCodeValue tsCodeValue) {
        }

        public void delete(TsCodeValue tsCodeValue) {
        }

        public void setSeq(Long codeValueRid, Long seq) {
        }

        public TsCodeValue getUpSeqLeaveCodeValue(Long seq) {
            return getTestBean(seq.intValue(), false);
        }

        public TsCodeValue getUpSeqJobCodeValue(Long parentRid, Long seq) {
            return getTestBean(seq.intValue(), false);
        }

        public TsCodeValue getDownSeqLeaveCodeValue(Long seq) {
            return getTestBean(seq.intValue(), false);
        }

        public TsCodeValue getDownSeqJobCodeValue(Long parentRid, Long seq) {
            return getTestBean(seq.intValue(), false);
        }

        public void updateEnable(Long rid, Boolean isEnable) {
        }

        public List getLeaveCode() {
            return null;
        }

		public TsCodeValue isExistWorkCode(String workCode) {
			// TODO Auto-generated method stub
			return null;
		}

		public TsCodeValue getDownSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType) {
			return getTestBean(seq.intValue(), false);
		}

		public Long getJobCodeMaxSeq(Long parentRid, String isLeaveType) {
			return Long.valueOf(99);
		}

		public TsCodeValue getRootJobCodeValue(String isLeavelType) {
			return getTestBean(1, true);
		}

		public TsCodeValue getUpSeqJobCodeValue(Long parentRid, Long seq, String isLeaveType) {
			 return getTestBean(seq.intValue(), false);
		}

		public List listJobCodeValueByParentRid(Long parentRid, String isLeaveType) {
			List list = new ArrayList();
            if(parentRid != 1) return list;

            list.add(getTestBean(9, false));
            list.add(getTestBean(10, false));
            list.add(getTestBean(11, false));
            return list;
		}

		public List<TsCodeValue> getChildrenAfterSeq(Long parentRid, Long seq) {
			// TODO Auto-generated method stub
			return null;
		}

		public TsCodeValue findCodeValue(Long rid) {
			// TODO Auto-generated method stub
			return null;
		}

		public TsCodeValue findByTypeRidName(Long typeRid, String name) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    private static TsCodeValue getTestBean(int intRid, boolean isLeaveType) {
        Long rid = Long.valueOf(intRid);
        TsCodeValue bean = new TsCodeValue();
        bean.setRid(rid);
        bean.setDescription(rid.toString());
        bean.setIsEnable(isLeaveType);
        bean.setIsLeaveType(isLeaveType);
        bean.setName(rid.toString());
        bean.setParentRid(Long.valueOf(0));
        bean.setSeq(rid);
        return bean;
    }

    private static DtoCodeValue getTestDto(int intRid, boolean isLeaveType) {
        DtoCodeValue dto = new DtoCodeValue();
        TsCodeValue bean = getTestBean(intRid, isLeaveType);
        DtoUtil.copyBeanToBean(dto, bean);
        return dto;
    }
}
