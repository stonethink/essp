package server.essp.hrbase.synchronization.dao;

public interface INextKeyDao {
	
	/**
	 * ��ESSP_HBSEQ���л�ȡĳ�ű�����RID
	 * @param nextKeyName
	 * @return
	 */
	public Long getNextKey(String nextKeyName);
	
	/**
	 * Ϊĳ�ű���ESSP_HBSEQ���н���RID���ֵ��¼
	 * @param nextKeyName
	 */
	public void createNewKey(String nextKeyName);

}
