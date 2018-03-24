package server.essp.hrbase.synchronization.dao;

public interface INextKeyDao {
	
	/**
	 * 从ESSP_HBSEQ表中获取某张表的最大RID
	 * @param nextKeyName
	 * @return
	 */
	public Long getNextKey(String nextKeyName);
	
	/**
	 * 为某张表在ESSP_HBSEQ表中建立RID最大值记录
	 * @param nextKeyName
	 */
	public void createNewKey(String nextKeyName);

}
