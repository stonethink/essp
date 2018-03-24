
package server.essp.common.code.choose.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoCodeValueChoose;
import db.essp.code.Code;
import db.essp.code.CodeValue;
import server.essp.common.syscode.LgSysParameter;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import com.wits.util.StringUtil;
import itf.seq.AcntSeqFactory;
import itf.seq.IAcntSeqUtil;

public class LgCodeTree extends AbstractBusinessLogic {
    static final String kindAccountType = "ACCOUNT_TYPE";

    /**
     * -------                                       (catalog -> its value is account type)
     *       |
     *       -----------                             (code -> the codes belong to catalog)
     *                 |
     *                 -----------                   (code value -> the code values belong to code)
     *                           |
     *                           ----------          (code value -> the code value belong to code value)
     *
     *本函数构造一个树
     * 1。树的root是假的，不显示
     * 2。树的第一层显示catalog
     * 3. 树的第二层显示code
     * 4. 树的其他层显示code value
     * 注：catalog的取值实际上由account的type而来，从sys_parameter中取到
     * @param type String
     * @return ITreeNode
     */
    public ITreeNode list(String type, String catalog) {
        ITreeNode root = new DtoTreeNode(new DtoCodeValueChoose());

        try {

            if( StringUtil.nvl(catalog).equals("") == false ){
                buildCatalog(root, type, catalog);
            }else{
                List catalogList = getCatalogList();

                for (Iterator iter = catalogList.iterator(); iter.hasNext(); ) {
                    String cata = iter.next().toString();

                    buildCatalog(root, type, cata);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Select code error.", ex);
        }

        /**
         * 特殊处理处理：
         * 如果树的根只有一个儿子，那么将该儿子作为树的根
         */
        if( root.getChildCount() == 1 ){
            root = root.getChildAt(0);
        }
        return root;
    }

    private void buildCatalog(ITreeNode parent,String type, String catalog) {
        DtoCodeValueChoose catalogDto = new DtoCodeValueChoose();
        catalogDto.setCodeName(catalog);
        catalogDto.setIsCodeValue(false);

        ITreeNode catalogNode = new DtoTreeNode(catalogDto);
        parent.addChild(catalogNode, IDto.OP_NOCHANGE);

        buildCode(catalogNode, type, catalog);
    }


    //catalog are account types
    private List getCatalogList(){
        LgSysParameter lgSysParameter = new LgSysParameter();
           Vector accountTypeList = lgSysParameter.listComboSysParas(
               kindAccountType);

        List ret = new ArrayList();
        for (Iterator iter = accountTypeList.iterator(); iter.hasNext(); ) {
            ret.add( iter.next().toString() );
        }

        return ret;
    }

    private void buildCode(ITreeNode catalogNode, String type, String catalog) {

        try {
            List codeList = load(type, catalog);

            for (Iterator iter = codeList.iterator(); iter.hasNext(); ) {
                Code code = (Code) iter.next();

                ITreeNode codeNode = buildCodeValue( code );

                catalogNode.addChild(codeNode, IDto.OP_NOCHANGE);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000001",
                "Error when select code of[type, catalog]-[" + type + "," +
                                        catalog + "]", ex);
        }
    }

    public ITreeNode buildCodeValue(Code code) throws BusinessException {
        ITreeNode root = null;

        try {
            Long codeRid = code.getRid();
            IAcntSeqUtil util = AcntSeqFactory.create();
            Long rootRid = util.getRootRid(codeRid,CodeValue.class);
            if( rootRid == null ){

                //root显示code的内容
                root = createNode( code );
                return root;
            }

            //get root
            CodeValue codeValue = null;
            try {
                getDbAccessor().newTx();
                codeValue = (CodeValue) getDbAccessor().load(CodeValue.class,
                    rootRid);

                if (codeValue != null) {

                    root = buildCodeValue(codeValue);

                    //root显示code的内容
                    root.setDataBean(createNode(code).getDataBean());
                }

                getDbAccessor().endTxCommit();
            } catch (net.sf.hibernate.ObjectNotFoundException ex) {
                //root显示code的内容
                root = createNode( code );
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select codeValue error.",ex);
        }

        return root;
    }

    private ITreeNode buildCodeValue(CodeValue codeValue) {
        boolean status = Boolean.valueOf(codeValue.getStatus()).booleanValue();
        if (codeValue == null || status == true) {
            return null;
        }

        ITreeNode root;
        DtoCodeValueChoose dto = new DtoCodeValueChoose();
        dto.setCodeRid(codeValue.getCode().getRid());
        dto.setValueRid(codeValue.getRid());
        dto.setCodeName(codeValue.getCodeName());
        dto.setCodeValue(codeValue.getValue());
        dto.setCodeValuePath(codeValue.getPath());
        dto.setDescription(codeValue.getDescription());
        dto.setIsCodeValue(true);

        root = new DtoTreeNode(dto);

        //process codeValue - codeValue
        for (Iterator it = codeValue.getChilds().iterator(); it.hasNext(); ) {
            CodeValue child = (CodeValue) it.next();

            ITreeNode childTreeRoot = buildCodeValue(child);
            if (childTreeRoot != null) {
                root.addChild(childTreeRoot, IDto.OP_NOCHANGE);
            }
        }

        return root;
    }

    private ITreeNode createNode(Code code){
        DtoCodeValueChoose dtoCodeValueChoose = new DtoCodeValueChoose();
        dtoCodeValueChoose.setCodeName(code.getName());
        dtoCodeValueChoose.setDescription(code.getDescription());
        dtoCodeValueChoose.setCodeRid(code.getRid());
        dtoCodeValueChoose.setIsCodeValue(false);

        return new DtoTreeNode(dtoCodeValueChoose);
    }

    private List load(String type, String catalog) {
        try {
            String selSql =
                " from Code t where t.type =:type and t.catalog =:catalog and (t.status = 'N' or t.status is null) order by t.seq ";
            List codeList = getDbAccessor().getSession().createQuery(selSql)
                            .setString("type", type)
                            .setString("catalog", catalog)
                            .list();

            return codeList;
        } catch (Exception ex) {
            throw new BusinessException("E000001",
                "Error when select code of[type, catalog]-[" + type + "," +
                                        catalog + "]", ex);
        }
    }


    public static void main(String args[]) {
        try {
            LgCodeTree logic = new LgCodeTree();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            ITreeNode root = logic.list("activity", "project");
            if (root != null) {
                System.out.println(DtoUtil.dumpBean(((DtoTreeNode) root).
                    getDataBean()));
            }
        } catch (BusinessException ex) {
        }
    }


}
