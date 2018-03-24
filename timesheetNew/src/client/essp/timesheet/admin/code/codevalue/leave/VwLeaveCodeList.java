package client.essp.timesheet.admin.code.codevalue.leave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.dto.*;
import c2s.essp.timesheet.code.DtoCodeValue;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJText;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: WistronITS
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class VwLeaveCodeList extends VWTreeTableWorkArea {
	static final String actionIdList = "FTSListLeaveCodeValue";
    static final String actionIdSaveEnable = "FTSSaveCodeValueEnables";
    static final String actionIdDelete = "FTSDeleteCodeValueTree";

    static final String actionIdUpMove = "FTSUpMoveCodeValue";
    static final String actionIdDownMove = "FTSDownMoveCodeValue";
    static final String actionIdLeftMove = "FTSLeftMoveCodeValue";
    static final String actionIdRightMove = "FTSRightMoveCodeValue";
    
    static final String treeColumnName = "name";
    JButton btnAdd;
    JButton btnDel;
    JButton btnLoad;
    JButton btnDown;
    JButton btnUp;
    JButton btnLeft;
    JButton btnRight;
    public VwLeaveCodeList() {
		Object[][] configs = {
				{ "rsid.common.name", "name", VMColumnConfig.EDITABLE, null },
				{ "rsid.common.description", "description",
						VMColumnConfig.UNEDITABLE, new VWJText() },
				{ "rsid.common.enable", "isEnable", VMColumnConfig.UNEDITABLE,
						new VWJCheckBox() } };

		try {
			super.jbInit(configs, treeColumnName, DtoCodeValue.class);

		} catch (Exception e) {
			log.error(e);
		}
		addUICEvent();
		setButtonVisible();
	}
    /**
	 * 设置按钮的可用性
	 */
    private void setButtonVisible() {
        btnAdd.setEnabled(true);
        // 如果当前没有节点，则只能刷新和新增
        ITreeNode cNode = this.getSelectedNode();
        if(cNode == null) {
            btnDel.setEnabled(false);
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
            return;
        }

        // 其它节点可用所有按钮，除了第一位不能上移，最后一位不能下移
        btnDel.setEnabled(true);

        if(getTreeTable().isUpMovable()) {
            btnUp.setEnabled(true);
        } else {
            btnUp.setEnabled(false);
        }

        if(getTreeTable().isDownMovable()) {
            btnDown.setEnabled(true);
        } else {
            btnDown.setEnabled(false);
        }
        
        if(getTreeTable().isLeftMovable()) {
        	btnLeft.setEnabled(true);
        } else {
        	btnLeft.setEnabled(false);
        }
        
        if(getTreeTable().isRightMovable()) {
        	btnRight.setEnabled(true);
        } else {
        	btnRight.setEnabled(false);
        }

        // 新增节点不能移动
        DtoCodeValue dto = (DtoCodeValue) cNode.getDataBean();
        if(dto.isInsert()) {
            btnAdd.setEnabled(false);
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
            return;
        } else {
            btnAdd.setEnabled(true);
        }

        // 获取兄弟节点
        ITreeNode pNode = cNode.getParent();
        if(pNode == null) return;
        int index = pNode.getIndex(cNode);
        int upIndex = index - 1;
        int downIndex = index + 1;
        int maxIdex = pNode.getChildCount() - 1;
        ITreeNode upNode = null;
        ITreeNode downNode = null;
        if(upIndex >= 0 && upIndex <= maxIdex) {
            upNode = pNode.getChildAt(upIndex);
        }
        if(downIndex >= 0 && downIndex <= maxIdex) {
            downNode = pNode.getChildAt(downIndex);
        }
        // 如果上一行为新增的节点，不允许上移
        if(upNode != null ) {
            DtoCodeValue upDto = (DtoCodeValue) upNode.getDataBean();
            if(upDto != null && upDto.isInsert()) {
                btnUp.setEnabled(false);
            }
        }
        // 如果下一行为新增的节点，不允许下移
        if(downNode != null) {
            DtoCodeValue downDto = (DtoCodeValue) downNode.getDataBean();
            if(downDto != null && downDto.isInsert()) {
                btnDown.setEnabled(false);
            }
        }
        
        ITreeNode lastNode = null;
        if (!pNode.isLeaf()) {//如果父不是叶子节点
			lastNode = pNode.getChildAt(maxIdex);
			// 父下有新增节点，不允许左右移动
			if (lastNode != null) {
				DtoCodeValue lastDto = (DtoCodeValue) lastNode.getDataBean();
				if (lastDto != null && lastDto.isInsert()) {
					btnLeft.setEnabled(false);
					btnRight.setEnabled(false);
				}
			}
		}
        
        ITreeNode gNode = pNode.getParent();
        if(gNode != null) {
        	maxIdex = gNode.getChildCount() - 1;
        	lastNode = gNode.getChildAt(maxIdex);
        	//父的父下有新增节点，不允许左移动
        	if(lastNode != null) {
        		DtoCodeValue lastDto = (DtoCodeValue) lastNode.getDataBean();
        		if(lastDto != null && lastDto.isInsert()) {
        			btnLeft.setEnabled(false);
        		}
        	}
        }
        
        if (upNode != null && !upNode.isLeaf()) {
			maxIdex = upNode.getChildCount() - 1;
			lastNode = upNode.getChildAt(maxIdex);
			//上个兄弟节点下有新增节点，不允许右移动
			if (lastNode != null) {
				DtoCodeValue lastDto = (DtoCodeValue) lastNode.getDataBean();
				if (lastDto != null && lastDto.isInsert()) {
					btnRight.setEnabled(false);
				}
			}
		}

    }
    private void addUICEvent() {
        // 捕获事件－－－－
    	this.getTreeTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					setButtonVisible();
				}
			}
    	});

        // Add
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });
        btnAdd.setToolTipText("rsid.common.add");
        // Delete
        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDelete();
            }
        });
        btnDel.setToolTipText("rsid.common.delete");
        // Up
        btnUp = this.getButtonPanel().addButton("up.png");
        btnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp();
            }
        });
        btnUp.setToolTipText("rsid.common.moveUp");

        // Down
        btnDown = this.getButtonPanel().addButton("down.png");
        btnDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown();
            }
        });
        btnDown.setToolTipText("rsid.common.moveDown");
        
        //Left
        btnLeft = this.getButtonPanel().addButton("left.png");
        btnLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				actionPerformedLeft();
			}
        });
        btnLeft.setToolTipText("rsid.common.moveLeft");
        
        //Right
        btnRight = this.getButtonPanel().addButton("right.png");
        btnRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				actionPerformedRight();
			}
        });
        btnRight.setToolTipText("rsid.common.moveRight");
        
        // expand
        JButton expandBtn = this.getButtonPanel().addButton("expand.png");
        expandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getTreeTable().expandsRow();
            }
        });
        expandBtn.setToolTipText("rsid.common.expand");

        // Refresh
        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad();
            }
        });
        btnLoad.setToolTipText("rsid.common.refresh");
    }
    /**
     * 下移
     * @param e ActionEvent
     */
    public void actionPerformedDown() {

        ITreeNode node = this.getSelectedNode();
        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("error.client.VwCodeValueList.saveFirst");
            return;
        }

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdDownMove);
        inputInfo.setInputObj(DtoCodeValue.DTO, dataBean);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            //交换Seq
            ITreeNode parent = node.getParent();
            int index = parent.getIndex(node);
            DtoCodeValue nextDto = (DtoCodeValue)
                                   parent.getChildAt(index + 1).getDataBean();
            Long nextSeq = nextDto.getSeq();
            nextDto.setSeq(dataBean.getSeq());
            dataBean.setSeq(nextSeq);

            //交换位置
            this.getTreeTable().downMove();
            SwingUtilities.invokeLater(new Thread() {
	            public void run() {
	                yield();
	                int selectRow = getTreeTable().getSelectedRow();
	                getTreeTable().getTree().setSelectionRow(selectRow);
	                setButtonVisible();
	            }
	        });
        }
    }

    /**
     * 上移
     * @param e ActionEvent
     */
    public void actionPerformedUp() {

        ITreeNode node = this.getSelectedNode();
        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();

        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("error.client.VwCodeValueList.saveFirst");
            return;
        }

        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpMove);
        inputInfo.setInputObj(DtoCodeValue.DTO, node.getDataBean());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {//交换Seq
            ITreeNode parent = node.getParent();
            int index = parent.getIndex(node);
            DtoCodeValue nextDto = (DtoCodeValue)
                                   parent.getChildAt(index - 1).getDataBean();
            Long nextSeq = nextDto.getSeq();
            nextDto.setSeq(dataBean.getSeq());
            dataBean.setSeq(nextSeq);

            //交换位置
            this.getTreeTable().upMove();
            SwingUtilities.invokeLater(new Thread() {
	            public void run() {
	                yield();
	                int selectRow = getTreeTable().getSelectedRow();
	                getTreeTable().getTree().setSelectionRow(selectRow);
	                setButtonVisible();
	            }
	        });
        }
    }
    /**
     * 左移
     *
     */
    public void actionPerformedLeft() {
    	ITreeNode node = this.getSelectedNode();
        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();
        
        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("error.client.VwCodeValueList.saveFirst");
            return;
        }
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdLeftMove);
        inputInfo.setInputObj(DtoCodeValue.DTO, node.getDataBean());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            ITreeNode parent = node.getParent();
            int index = parent.getIndex(node);//该节点的位置
            int maxIndex = parent.getChildCount();//总个数
            
            DtoCodeValue childDto = null;
            
            for(int i = index + 1; i<maxIndex;i++) {
            	childDto = (DtoCodeValue) parent.getChildAt(i)
												.getDataBean();
            	childDto.setSeq(childDto.getSeq() - 1);
            }
            ITreeNode grad = parent.getParent();
            index = grad.getIndex(parent);
            maxIndex = grad.getChildCount();
            for(int i = index + 1; i<maxIndex;i++) {
            	childDto = (DtoCodeValue) grad.getChildAt(i)
											.getDataBean();
            	childDto.setSeq(childDto.getSeq() + 1);
            }
            
            //左移后该节点的父设置为之前该节点父的父Rid
            //seq设置为该节点之前父的seq+1
            DtoCodeValue parentBean = (DtoCodeValue) parent.getDataBean();
            dataBean.setParentRid(parentBean.getParentRid());
			dataBean.setSeq(parentBean.getSeq() + 1);

            //变换位置
            this.getTreeTable().leftMove();
			SwingUtilities.invokeLater(new Thread() {
	            public void run() {
	                yield();
	                int selectRow = getTreeTable().getSelectedRow();
	                getTreeTable().getTree().setSelectionRow(selectRow);
	                setButtonVisible();
	            }
	        });
        }
    }
    /**
     * 右移
     *
     */
    public void actionPerformedRight() {
    	ITreeNode node = this.getSelectedNode();
        DtoCodeValue dataBean = (DtoCodeValue) node.getDataBean();
        
        if (dataBean.isInsert()) {
            comMSG.dispErrorDialog("error.client.VwCodeValueList.saveFirst");
            return;
        }
        //save this node to db
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdRightMove);
        inputInfo.setInputObj(DtoCodeValue.DTO, node.getDataBean());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            ITreeNode parent = node.getParent();
            int index = parent.getIndex(node);//该节点的位置
            int maxIndex = parent.getChildCount();//总个数
            
            DtoCodeValue childDto = null;
            
            for(int i = index + 1; i<maxIndex;i++) {
            	childDto = (DtoCodeValue) parent.getChildAt(i)
												.getDataBean();
            	childDto.setSeq(childDto.getSeq() - 1);
            }
            ITreeNode upNode = parent.getChildAt(index - 1);
            DtoCodeValue upDto = (DtoCodeValue) upNode.getDataBean();
            maxIndex = upNode.getChildCount();//总个数
            Long newSeq = null;
            if(maxIndex > 0) {
            	DtoCodeValue maxDto = (DtoCodeValue)upNode
                				.getChildAt(maxIndex - 1).getDataBean();
            	newSeq = maxDto.getSeq();
            } else if(maxIndex == 0) {
            	newSeq = new Long(1);
            }
            //右移后该节点的父设置为之前该节点的上一个兄弟节点的Rid
            //seq设置为该节点当前父下最后一个子节点的seq+1
            dataBean.setParentRid(upDto.getRid());
			dataBean.setSeq(newSeq + 1);

            //变换位置
            this.getTreeTable().rightMove();
            SwingUtilities.invokeLater(new Thread() {
	            public void run() {
	                yield();
	                int selectRow = getTreeTable().getSelectedRow();
	                getTreeTable().getTree().setSelectionRow(selectRow);
	                setButtonVisible();
	            }
	        });
        }
    }
    /**
     * UI新增
     * @param e ActionEvent
     */
    public void actionPerformedAdd() {
        DtoCodeValue dtoNew = new DtoCodeValue();
        DtoCodeValue parentDto = (DtoCodeValue) this.getSelectedData();
        if(parentDto != null) {
            dtoNew.setParentRid(parentDto.getRid());
        }
        dtoNew.setIsEnable(true);
        dtoNew.setIsLeaveType(true);
        DtoTreeNode node = new DtoTreeNode(dtoNew);
        this.getTreeTable().addRow(node);
    }

    /**
     * 删除
     * @param e ActionEvent
     */
    public void actionPerformedDelete() {

        int option = comMSG.dispConfirmDialog(
                "error.client.VwCodeValueList.deleteAll");
        if (option == Constant.OK) {
            ITreeNode node = this.getSelectedNode();
            if (node != null && !((DtoCodeValue) node.getDataBean()).isInsert()) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj(DtoCodeValue.DTO_TREE, node);
                inputInfo.setActionId(actionIdDelete);
                //delete from db
                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    if(node.getParent() != null) {
                        this.getTreeTable().deleteRow();
                    } else {
                        this.resetUI();
                    }
                }
            } else {
                this.getTreeTable().deleteRow();
            }
        }
    }
    /**
     * 保存整棵树Enable属性
     */
    void processSaveTree() {
        this.getTreeTable().refreshTree();
        setButtonVisible();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoCodeValue.DTO_TREE, this.getModel().getRoot());
        inputInfo.setActionId(actionIdSaveEnable);
        accessData(inputInfo);
    }

    /**
     * 处理Enabole选中事件
     */
    void processdEnableCheck(boolean isCheck) {
        if(isCheck) {
            checkOnChildren(this.getSelectedNode());
            checkOnParent(this.getSelectedNode());
        } else {
            unCheckOnChildren(this.getSelectedNode());
        }
        this.getTreeTable().refreshTree();
    }

    /**
     * 选中所有子孙节点
     * @param node ITreeNode
     */
    private void checkOnChildren(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(true);
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            checkOnChildren(child);
        }
    }

    /**
     * 取消选中所有子孙节点
     * @param node ITreeNode
     */
    private void unCheckOnChildren(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(false);
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            unCheckOnChildren(child);
        }
    }


    /**
     * 选中所有祖先节点
     * @param node ITreeNode
     */
    private void checkOnParent(ITreeNode node) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        dto.setIsEnable(true);
        ITreeNode parent = node.getParent();
        if(parent != null) {
            checkOnParent(parent);
        }
    }
    /**
     * 执行刷新
     * @param e ActionEvent
     */
    public void actionPerformedLoad() {
        resetUI();
    }
    /**
     * 页面刷新
     */
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdList);
        ReturnInfo returnInfo = accessData(inputInfo);
        ITreeNode root = (ITreeNode) returnInfo.getReturnObj(DtoCodeValue.DTO_TREE);
        this.getTreeTable().setRoot(root);
        setButtonVisible();
    }
}
