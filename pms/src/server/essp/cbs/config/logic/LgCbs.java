package server.essp.cbs.config.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.DtoCbs;
import c2s.essp.cbs.DtoSubject;
import db.essp.cbs.SysSubject;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import oracle.sql.BLOB;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgCbs extends AbstractESSPLogic {
    /**
     * 新增CBS，整个CBS科目树作为一个对象写入Blob字段
     * @param cbsDto DtoCbs
     */
    public void addCbsDefine(DtoCbs cbsDto){
        if(cbsDto == null || cbsDto.getCbsType() == null || cbsDto.getCbsRoot() == null){
            throw new BusinessException("CBS_CFG_001","CBS type and subject root can not be null!");
        }
        try {
            Session s = this.getDbAccessor().getSession();
            SysSubject cbs = new SysSubject();
            cbs.setCbsType(cbsDto.getCbsType());
            this.getDbAccessor().assignedRid(cbs);
            byte[] buffer = new byte[1024];
            cbs.setCbsDefine(Hibernate.createBlob(buffer));
            s.save(cbs);
            s.flush();
            s.refresh(cbs, LockMode.UPGRADE);

            BLOB blob = (BLOB) cbs.getCbsDefine();
            ObjToBlob(cbsDto.getCbsRoot(),blob);
            s.flush();
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_002","error while adding CBS define!",ex);
        }
    }
    /**
     * 根据类型查找CBS科目树，若该类型的CBS科目树不存在且为System类型，新增一条记录，否则返回空
     * @param type String
     * @return DtoCbs
     */
    public DtoCbs loadCbsDefine(String type){
        if(type == null)
            throw new BusinessException("CBS_CFG_001","CBS type can not be null!");
        try{
            Session s = this.getDbAccessor().getSession();
            SysSubject cbs = (SysSubject)s.get(SysSubject.class, type);
            if(cbs != null){
                BLOB blob = (BLOB) cbs.getCbsDefine();
                Object obj = blobToObj(blob);
                DtoCbs cbsDto = new DtoCbs();
                cbsDto.setCbsType(type);
                cbsDto.setCbsRoot((DtoTreeNode) obj);
                return cbsDto;
            }else if(DtoCbs.DEFAULT_TYPE.equals(type)){
                //不存在type=system的CBS Define时新增一条
                 DtoCbs cbsDto = new DtoCbs();
                 cbsDto.setCbsType(type);
                 DtoSubject defaultRoot = new DtoSubject(DtoCbs.DEFAULT_ROOT,
                     DtoCbs.DEFAULT_ROOT);
                 defaultRoot.setBudgetCalType(DtoSubject.TYPE_AUTO_CALCULATE);
                 defaultRoot.setCostCalType(DtoSubject.TYPE_AUTO_CALCULATE);
                 defaultRoot.setSubjectAttribute(null);
                 cbsDto.setCbsRoot(new DtoTreeNode(defaultRoot));
                 this.addCbsDefine(cbsDto);
                 return cbsDto;
            }else{
                return null;
            }
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_003","error while loading CBS define!",ex);
        }
    }
    /**
     * 更新CBS科目树
     * @param cbsDto DtoCbs
     */
    public void updateCbsDefine(DtoCbs cbsDto){
        if(cbsDto == null || cbsDto.getCbsType() == null || cbsDto.getCbsRoot() == null){
            throw new BusinessException("CBS_CFG_001","CBS type and subject root can not be null!");
        }
        try {
            Session s = this.getDbAccessor().getSession();
            SysSubject cbs = (SysSubject) s.load(SysSubject.class,
                                                 cbsDto.getCbsType(),
                                                 LockMode.UPGRADE);
            BLOB blob = (BLOB) cbs.getCbsDefine();
            ObjToBlob(cbsDto.getCbsRoot(),blob);
            s.flush();
        } catch (Exception ex) {
            throw new BusinessException("CBS_CFG_004","error while updating CBS define!",ex);
        }
    }
    /**
     * 列出所有的CBS科目
     * @param type String
     * @return List
     */
    public List listAllSubject(String type){
        if(type == null){
            throw new BusinessException("CBS_CFG_001","CBS type can not be null!");
        }
        DtoCbs cbs = loadCbsDefine(type);
        List children = cbs.getCbsRoot().listAllChildrenByPreOrder();
        children.add(0,cbs.getCbsRoot());
        return children;
    }
    public Vector listComboSubject(String type){
        Vector result = new Vector();
        List allSubject = listAllSubject(type);
        for(int i = 0;i < allSubject.size() ;i ++){
            ITreeNode node = (ITreeNode) allSubject.get(i);
            DtoSubject subject = (DtoSubject) node.getDataBean();
            DtoComboItem item = new DtoComboItem(subject.getSubjectCode(),
                                subject.getSubjectCode());
            String attribute = getAttribute(node);
            item.setItemRelation(attribute);
            result.add(item);
        }
        return result;
    }
    private String getAttribute(ITreeNode node){
        if(node == null || node.getDataBean() == null)
            return null;
        DtoSubject subject = (DtoSubject) node.getDataBean();
        String attribute = subject.getSubjectAttribute();
        if(attribute != null && !"".equals(attribute))
            return attribute;
        else
            return getAttribute(node.getParent());
    }
    /**
     * 将Object序列化并写如BLOb
     * @param obj Object
     * @return byte[]
     */
    public static void ObjToBlob(Object obj,BLOB blob ) {
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] data = bos.toByteArray();
            oos.close();
            bos.close();
            BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
            out.write(data);
            out.close();
        }catch(Exception ex){
            throw new BusinessException("CBS_CFG_005","serialize Object error!",ex);
        }
    }
    /**
     * 从Blob从读出对象
     * @param data byte[]
     * @return Object
     */
    public static Object blobToObj(BLOB blob){
        try{
            BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
            ObjectInputStream ois = new ObjectInputStream(in);
            Object obj = ois.readObject();
            in.close();
            ois.close();
            return obj;
        }catch(Exception ex){
            ex.printStackTrace();
            throw new BusinessException("CBS_CFG_006","convert byte[] into Object error!",ex);
        }
    }
    public static void main(String[] args) throws Exception{
        DtoCbs cbsDto = new DtoCbs();
        cbsDto.setCbsType("tp1");
//        DtoTreeNode root = new DtoTreeNode(new DtoSubject("0003","cost3"));
//        DtoTreeNode child = new DtoTreeNode(new DtoSubject("0004","cost4"));
//        root.addChild(child);
//        cbsDto.setCbsRoot(root);
        LgCbs lg = new LgCbs();
        lg.getDbAccessor().followTx();
//        lg.addCbsDefine(cbsDto);
//        lg.updateCbsDefine(cbsDto);
        List l = lg.listComboSubject("SYSTEM");
        lg.getDbAccessor().endTxCommit();

    }
}
