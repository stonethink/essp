package server.essp.hrbase.attributegroup.logic;

import java.util.List;

import db.essp.hrbase.HrbAttributeGroup;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgAttSelection extends AbstractESSPLogic {
	
	/**
	 * ��ȡָ��site�µ�hr����
	 * @param site String
	 * @return List<HrbAttributeGroup>
	 */
	public List listSiteHrAttributes(String site) {
		String sql = "select t.rid, t.code, t.description from hrb_attribute_group t "
			+ "where t.is_enable = '1' and t.site = '" + site + "'";
		return this.getDbAccessor().executeQueryToList(sql, HrbAttributeGroup.class);
	}
	
	/**
	 * ��ȡ������Ա����
	 * @return List<HrbAttributeGroup>
	 */
	public List listAttributes() {
		String sql = "select g.rid, g.attribute_rid, t.code, t.description from hrb_attribute t "
			+ "left join hrb_attribute_group g on t.rid = g.attribute_rid "
			+ "where t.is_enable = '1' and g.is_enable = '1'";
		return this.getDbAccessor().executeQueryToList(sql, HrbAttributeGroup.class);
	}

	/**
	 * ��ȡ��ȡ����HR�����Ƿ�Ϊ��ʽ
	 * @return List<HrbAttributeGroup>
	 */
	public List listFormals() {
		String sql = "select t.rid, t.is_formal from hrb_attribute_group t "
			+ "where t.is_enable = '1'";
		return this.getDbAccessor().executeQueryToList(sql, HrbAttributeGroup.class);
	}
}
