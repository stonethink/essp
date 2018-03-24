package server.essp.timesheet.code.coderelation.service;

import junit.framework.*;
import c2s.dto.*;
import server.essp.timesheet.code.coderelation.dao.ICodeRelationDao;
import java.util.List;
import db.essp.timesheet.TsCodeRelation;
import java.util.ArrayList;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import c2s.essp.timesheet.code.DtoCodeValue;

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
public class TestCodeRelationServiceImp extends TestCase {
    private CodeRelationServiceImp codeRelationServiceImp = null;

    public TestCodeRelationServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        codeRelationServiceImp = new CodeRelationServiceImp();
    }

    protected void tearDown() throws Exception {
        codeRelationServiceImp = null;
        super.tearDown();
    }

    public void testGetRelationTree() {
        codeRelationServiceImp.setCodeRelationDao(new TestCodeRelationDaoImp());
        codeRelationServiceImp.setCodeValueService(new TestCodeValueServiceImp());
        ITreeNode root = codeRelationServiceImp.getRelationTree(null,false);
        assertNotNull("root is not null", root);
        assertEquals("root children size", 2, root.getChildCount());
        DtoCodeValue expectedDto = getTestDtoCodeValue(543);
        DtoCodeValue dto  = (DtoCodeValue) root.getChildAt(0).getDataBean();
        assertEquals("first child description", expectedDto.getDescription(), dto.getDescription());
        assertEquals("first child enable", Boolean.TRUE, dto.getIsEnable());
        assertEquals("first child isLeaveType", expectedDto.getIsLeaveType(), dto.getIsLeaveType());
        assertEquals("first child name", expectedDto.getName(), dto.getName());
        assertEquals("first child parentRid", expectedDto.getParentRid(), dto.getParentRid());
        assertEquals("first child rid", expectedDto.getRid(), dto.getRid());
        assertEquals("first child seq", expectedDto.getSeq(), dto.getSeq());
        DtoCodeValue dto2  = (DtoCodeValue) root.getChildAt(1).getDataBean();
        assertEquals("second child enable", Boolean.FALSE, dto2.getIsEnable());

    }

    public void testSaveRelation() {
        codeRelationServiceImp.setCodeRelationDao(new TestCodeRelationDaoImp());
        codeRelationServiceImp.setCodeValueService(new TestCodeValueServiceImp());
        DtoCodeValue dto = new DtoCodeValue();
        dto.setRid(Long.valueOf(667));
        dto.setIsEnable(true);
        ITreeNode root = new DtoTreeNode(dto);
        DtoCodeValue dto2 = new DtoCodeValue();
        dto2.setRid(Long.valueOf(735));
        dto2.setIsEnable(false);
        root.addChild(new DtoTreeNode(dto2));
        codeRelationServiceImp.saveRelation(Long.valueOf(852), root, false);
    }

    private class TestCodeRelationDaoImp implements ICodeRelationDao {
        public List listRelation(Long typeRid) {
            List list = new ArrayList();
            list.add(getTestBean(445));
            list.add(getTestBean(543));
            list.add(getTestBean(554));
            return list;
        }

        public void deleteRelations(Long typeRid) {
            assertEquals("delete relations type rid", Long.valueOf(852), typeRid);
        }

        public void add(TsCodeRelation relation) {
            assertNotNull("add relation not null", relation);
            assertNull("add relation rid is null", relation.getRid());
            assertEquals("add relation type rid", Long.valueOf(852), relation.getTypeRid());
            assertEquals("add relation value rid", Long.valueOf(667), relation.getValueRid());
        }

		public void deleteRelations(Long typeRid, String isLeaveType) {
			assertEquals("delete relations type rid", Long.valueOf(852), typeRid);
			
		}

		public List listRelation(Long typeRid, String isLeaveType) {
			List list = new ArrayList();
            list.add(getTestBean(445));
            list.add(getTestBean(543));
            list.add(getTestBean(554));
            return list;
		}
    }

    private class TestCodeValueServiceImp implements ICodeValueService {
        public List listLeaveCodeValue() {
            return null;
        }

        public ITreeNode getJobCoeValueTree() {
            ITreeNode node = new DtoTreeNode(getTestDtoCodeValue(445));
            node.addChild(new DtoTreeNode(getTestDtoCodeValue(543)));
            node.addChild(new DtoTreeNode(getTestDtoCodeValue(554)));
            node.addChild(new DtoTreeNode(getTestDtoCodeValue(333)));
            return node;
        }

        public void save(DtoCodeValue dtoCodeValue) {
        }

        public void moveUpCodeValue(DtoCodeValue dtoCodeValue) {
        }

        public void moveDownCodeValue(DtoCodeValue dtoCodeValue) {
        }

        public void delete(DtoCodeValue dtoCodeValue) {
        }

        public void delete(ITreeNode node) {
        }

        public void saveEnables(ITreeNode root) {
        }

		public DtoCodeValue getCodeValue(Long rid) {
			// TODO Auto-generated method stub
			return null;
		}

		public ITreeNode getLeaveCodeValueTree() {
			// TODO Auto-generated method stub
			return null;
		}

		public void moveLeftCodeValue(DtoCodeValue dtoCodeValue) {
			// TODO Auto-generated method stub
			
		}

		public void moveRightCodeValue(DtoCodeValue dtoCodeValue) {
			// TODO Auto-generated method stub
			
		}

    }

    private static TsCodeRelation getTestBean(int intRid) {
        TsCodeRelation bean = new TsCodeRelation();
        Long rid = Long.valueOf(intRid);
        bean.setRid(rid);
        bean.setTypeRid(rid);
        bean.setValueRid(rid);
        return bean;
    }

    private static DtoCodeValue getTestDtoCodeValue(int intRid) {
        DtoCodeValue dto  = new DtoCodeValue();
        Long rid = Long.valueOf(intRid);
        dto.setDescription(rid.toString());
        dto.setIsEnable(intRid%2 == 1);
        dto.setIsLeaveType(false);
        dto.setName(rid.toString());
        dto.setParentRid(rid - 1);
        dto.setRid(rid);
        dto.setSeq(rid);
        return dto;
    }

}
