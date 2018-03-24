package server.essp.timesheet.preference.service;

import java.util.List;

import junit.framework.*;
import server.framework.common.*;
import c2s.essp.timesheet.preference.*;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import db.essp.timesheet.TsParameter;

/**
 * <p>Title: PreferenceServiceImp Test Class</p>
 *
 * <p>Description: Test PreferenceServiceImp</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TestPreferenceServiceImp extends TestCase {
    private PreferenceServiceImp preferenceServiceImp = null;

    public TestPreferenceServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        preferenceServiceImp = new PreferenceServiceImp();
    }

    protected void tearDown() throws Exception {
        preferenceServiceImp = null;
        super.tearDown();
    }

    public void testGetLoadPreferenceNormal() throws BusinessException {
        preferenceServiceImp.setPreferenceDao(new TestPreferenceDaoImpNormal());
        DtoPreference dto = preferenceServiceImp.getLoadPreference();
        this.assertNotNull("return dto not null", dto);
        this.assertEquals("CompleteTaskHrsFlag", Boolean.FALSE, dto.getCompleteTaskHrsFlag());
        this.assertEquals("FutureTsCnt", Long.valueOf(200), dto.getFutureTsCnt());
        this.assertEquals("FutureTsHrsFlag", Boolean.FALSE, dto.getFutureTsHrsFlag());
        this.assertEquals("HrDecimalCnt", Long.valueOf(300), dto.getHrDecimalCnt());
        this.assertEquals("NostartTaskHrsFlag", Boolean.TRUE, dto.getNostartTaskHrsFlag());
        this.assertEquals("PastTsCnt", Long.valueOf(400), dto.getPastTsCnt());
        this.assertEquals("PostendTaskHrsFlag", Boolean.TRUE, dto.getPostendTaskHrsFlag());
        this.assertEquals("FutureTsHrsFlag", Boolean.TRUE, dto.getPrestartTaskHrsFlag());
    }

    public void testSavePreferenceNormal() throws BusinessException {
        preferenceServiceImp.setPreferenceDao(new TestPreferenceDaoImpNormal());
        DtoPreference prefer = new DtoPreference();
        prefer.setCompleteTaskHrsFlag(false);
        prefer.setFutureTsCnt(Long.valueOf(200));
        prefer.setFutureTsHrsFlag(false);
        prefer.setHrDecimalCnt(Long.valueOf(300));
        prefer.setNostartTaskHrsFlag(true);
        prefer.setPastTsCnt(Long.valueOf(400));
        prefer.setPostendTaskHrsFlag(true);
        prefer.setPrestartTaskHrsFlag(true);
        preferenceServiceImp.savePreference(prefer);
    }

    public void testGetLoadPreferenceNull() throws BusinessException {
        preferenceServiceImp.setPreferenceDao(new TestPreferenceDaoImpNull());
        DtoPreference dto = preferenceServiceImp.getLoadPreference();
        this.assertNotNull("return dto not null", dto);
        this.assertNull("CompleteTaskHrsFlag", dto.getCompleteTaskHrsFlag());
        this.assertNull("FutureTsCnt", dto.getFutureTsCnt());
        this.assertNull("FutureTsHrsFlag", dto.getFutureTsHrsFlag());
        this.assertNull("HrDecimalCnt", dto.getHrDecimalCnt());
        this.assertNull("NostartTaskHrsFlag", dto.getNostartTaskHrsFlag());
        this.assertNull("PastTsCnt", dto.getPastTsCnt());
        this.assertNull("PostendTaskHrsFlag", dto.getPostendTaskHrsFlag());
        this.assertNull("FutureTsHrsFlag", dto.getPrestartTaskHrsFlag());
    }

    public void testSavePreferenceNull() throws BusinessException {
        preferenceServiceImp.setPreferenceDao(new TestPreferenceDaoImpNull());
        DtoPreference prefer = null;
        preferenceServiceImp.savePreference(prefer);
    }


    private class TestPreferenceDaoImpNormal implements IPreferenceDao {
        public TsParameter loadPreference() {
            TsParameter param = new TsParameter();
            param.setCompleteTaskHrsFlag(false);
            param.setFutureTsCnt(Long.valueOf(200));
            param.setFutureTsHrsFlag(false);
            param.setHrDecimalCnt(Long.valueOf(300));
            param.setNostartTaskHrsFlag(true);
            param.setPastTsCnt(Long.valueOf(400));
            param.setPostendTaskHrsFlag(true);
            param.setPrestartTaskHrsFlag(true);
            return param;
        }

        public void saveOrUpdatePreference(TsParameter tsParameter) {
            assertNotNull("input tsParameter not null", tsParameter);
            assertEquals("input CompleteTaskHrsFlag", Boolean.FALSE,
                              tsParameter.getCompleteTaskHrsFlag());
            assertEquals("input FutureTsCnt", Long.valueOf(200), tsParameter.getFutureTsCnt());
            assertEquals("input FutureTsHrsFlag", Boolean.FALSE, tsParameter.getFutureTsHrsFlag());
            assertEquals("input HrDecimalCnt", Long.valueOf(300), tsParameter.getHrDecimalCnt());
            assertEquals("input NostartTaskHrsFlag", Boolean.TRUE,
                              tsParameter.getNostartTaskHrsFlag());
            assertEquals("input PastTsCnt", Long.valueOf(400), tsParameter.getPastTsCnt());
            assertEquals("input PostendTaskHrsFlag", Boolean.TRUE,
                              tsParameter.getPostendTaskHrsFlag());
            assertEquals("input FutureTsHrsFlag", Boolean.TRUE,
                              tsParameter.getPrestartTaskHrsFlag());
        }

		public TsParameter loadPreferenceBySite(String site) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<String> listSitesInTsHumanbase() {
			// TODO Auto-generated method stub
			return null;
		}

		public TsParameter loadPreferenceByLoginId(String loginId) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    private class TestPreferenceDaoImpNull implements IPreferenceDao {
        public TsParameter loadPreference() {
            return null;
        }

        public void saveOrUpdatePreference(TsParameter tsParameter) {
            assertNotNull("input tsParameter not null", tsParameter);
            assertNull("input CompleteTaskHrsFlag", tsParameter.getCompleteTaskHrsFlag());
            assertNull("input FutureTsCnt", tsParameter.getFutureTsCnt());
            assertNull("input FutureTsHrsFlag", tsParameter.getFutureTsHrsFlag());
            assertNull("input HrDecimalCnt", tsParameter.getHrDecimalCnt());
            assertNull("input NostartTaskHrsFlag", tsParameter.getNostartTaskHrsFlag());
            assertNull("input PastTsCnt", tsParameter.getPastTsCnt());
            assertNull("input PostendTaskHrsFlag", tsParameter.getPostendTaskHrsFlag());
            assertNull("input FutureTsHrsFlag", tsParameter.getPrestartTaskHrsFlag());

        }

		public TsParameter loadPreferenceBySite(String site) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<String> listSitesInTsHumanbase() {
			// TODO Auto-generated method stub
			return null;
		}

		public TsParameter loadPreferenceByLoginId(String loginId) {
			// TODO Auto-generated method stub
			return null;
		}

    }


}
