package server.framework.taglib.html;

import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import server.framework.taglib.util.Constants;
import server.framework.taglib.util.TagUtils;
import server.framework.taglib.util.ISelectOption;
import org.apache.struts.util.*;

public class OptionsCollectionTag extends AbstractBaseInputTag {
    private String name;
    private SelectTag selectTag;

    public OptionsCollectionTag() {
        name = "org.apache.struts.taglib.html.BEAN";
        selectTag = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String string) {
        name = string;
    }

    public void doCheck() throws JspException {
        /*
        if (name == null || name.trim().equals("")) {
            MessageResources mr = MessageResources.getMessageResources(
                    Constants.MESSAGE_CONFIG);
            String message = mr.getMessage("fieldtype.need", new String[] {
                                           "optionCollectionTag",
                                           "\"name\""
            });
            throw new JspException(message);
        }
        */
        if (selectTag == null) {
            MessageResources mr = MessageResources.getMessageResources(
                    Constants.MESSAGE_CONFIG);
            String message = mr.getMessage("taglib.nest", new String[] {
                                           "optionsCollection", "select"
            });
            throw new JspException(message);
        } else {
            return;
        }
    }

    public int doStartTag() throws JspException {
        //log.debug("doStartTag:START");
        try {
            selectTag = (SelectTag) pageContext.getAttribute(Constants.
                    SELECT_KEY);
            if (selectTag.getReadonly()) {
                doCheck();
                Object collection = null;
                if (property == null || property.trim().equals("")) {
                    collection = TagUtils.lookup(pageContext, name, null);
                } else {
                    collection = TagUtils.lookup(pageContext, name,
                            property, null);
                }
//                if (collection == null) {
//                    MessageResources mr = MessageResources.getMessageResources(
//                            Constants.MESSAGE_CONFIG);
//                    String message = mr.getMessage("lookup.error", new String[] {
//                            name, property
//                    });
//                    throw new JspException(message);
//                }
                if (!(collection instanceof List)) {
                    MessageResources mr = MessageResources.getMessageResources(
                            Constants.MESSAGE_CONFIG);
                    String message = mr.getMessage("implementation.error",
                            new String[] {
                            property, "java.util.List"
                    });
                    throw new JspException(message);
                }
                List list = (List) collection;
                StringBuffer buf = new StringBuffer();
                Iterator iter = list.iterator();
                String originalStyleClass = getStyleClass();
                for (; iter.hasNext(); setStyleClass(originalStyleClass)) {
                    Object element = iter.next();
                    if (!(element instanceof ISelectOption)) {
                        MessageResources mr = MessageResources.
                                              getMessageResources(
                                Constants.MESSAGE_CONFIG);
                        String message = mr.getMessage("implementation.error",
                                new String[] {
                                "element of " + property,
                                "server.framework.taglib.util.ISelectOption"
                        });
                        throw new JspException(message);
                    }
                    ISelectOption option = (ISelectOption) element;
                    if (selectTag.isMatched(option.getValue())) {
                        selectTag.setDefaultTitle(option.getTitle());
                        selectTag.setMatchedLabel(option.getLabel());
                    }
                }

                release();
                return 0;
            } else {
                doCheck();
                Object collection = null;
                if (property == null || property.trim().equals("")) {
                    collection = TagUtils.lookup(pageContext, name, null);
                } else {
                    collection = TagUtils.lookup(pageContext, name,
                            property, null);
                }
//                if (collection == null) {
//                    MessageResources mr = MessageResources.getMessageResources(
//                            Constants.MESSAGE_CONFIG);
//                    String message = mr.getMessage("lookup.error", new String[] {
//                            name, property
//                    });
//                    throw new JspException(message);
//                }
                if (!(collection instanceof List)) {
                    MessageResources mr = MessageResources.getMessageResources(
                            Constants.MESSAGE_CONFIG);
                    String message = mr.getMessage("implementation.error",
                            new String[] {
                            property, "java.util.List"
                    });
                    throw new JspException(message);
                }
                List list = (List) collection;
                StringBuffer buf = new StringBuffer();
                Iterator iter = list.iterator();
                String originalStyleClass = getStyleClass();

                //check : Is selected item exist ???
                boolean isSelected = false;
                for (; iter.hasNext(); ) {
                    Object element = iter.next();
                    if (!(element instanceof ISelectOption)) {
                        MessageResources mr = MessageResources.
                                              getMessageResources(
                                Constants.MESSAGE_CONFIG);
                        String message = mr.getMessage("implementation.error",
                                new String[] {
                                "element of " + property,
                                "server.framework.taglib.util.ISelectOption"
                        });
                        throw new JspException(message);
                    }
                    ISelectOption option = (ISelectOption) element;
                    if (selectTag.isMatched(option.getValue())) {
                        isSelected = true;
                        break;
                    }
                }

                if (!isSelected) {
                    if (selectTag.getDefaultIndex() == null &&
                        selectTag.getDefaultValue() == null) {
                        selectTag.setDefaultIndex("0");
                    }
                }

                iter = list.iterator();
                int iCurrentIndex = 0;
                for (; iter.hasNext(); setStyleClass(originalStyleClass)) {
                    Object element = iter.next();
                    ISelectOption option = (ISelectOption) element;
                    buf.append("<option");

                    if(option.getValue()!=null) {
                        buf.append(" value=\"" + option.getValue()+"\"");
                    } else {
                        buf.append(" value=\"\"");
                    }
                    // start wenjun.yang
                    if(option.getTitle()!=null){
                        buf.append(" title=\""+option.getTitle()+"\"");
                    } else{
                        buf.append(" title=\"\"");
                    }
                    // end wenjun.yang
                    if (isSelected) {
                        if (selectTag.isMatched(option.getValue())) {
                            if (selectTag.isMsg()) {
                                if (originalStyleClass != null) {
                                    setStyleClass(originalStyleClass + " Err");
                                } else {
                                    setStyleClass("Err");
                                }
                            }
                            TagUtils.appendAttribute(buf, "selected",
                                    "selected");
                        }
                    } else {
                        if (selectTag.getDefaultIndex() != null) {
                            try {
                                int defaultSelectedIndex = Integer.parseInt(
                                        selectTag.getDefaultIndex());
                                if (iCurrentIndex == defaultSelectedIndex) {
                                    if (selectTag.isMsg()) {
                                        if (originalStyleClass != null) {
                                            setStyleClass(originalStyleClass +
                                                    " Err");
                                        } else {
                                            setStyleClass("Err");
                                        }
                                    }
                                    TagUtils.appendAttribute(buf, "selected",
                                            "selected");
                                }
                            } catch (Exception e) {
                            }
                        } else if (selectTag.getDefaultValue() != null) {
                            try {
                                if (selectTag.getDefaultValue().equals(option.
                                        getValue())) {
                                    if (selectTag.isMsg()) {
                                        if (originalStyleClass != null) {
                                            setStyleClass(originalStyleClass +
                                                    " Err");
                                        } else {
                                            setStyleClass("Err");
                                        }
                                    }
                                    TagUtils.appendAttribute(buf, "selected",
                                            "selected");
                                }
                            } catch (Exception e) {
                            }

                        }
                    }
                    buf.append(super.prepareStyles());
                    buf.append(">");
                    buf.append(ResponseUtils.filter(option.getLabel()));
                    buf.append("</option>");
                    buf.append(Constants.LINE_END);
                    iCurrentIndex++;
                }

                ResponseUtils.write(pageContext, buf.toString());
                release();
                //log.debug("doStartTag:END:" + buf.toString());
                return 0;
            }
        } catch (Exception ex) {
            throw new JspException(ex);
        }
    }

    public void release() {
        super.release();
        name = "org.apache.struts.taglib.html.BEAN";
        selectTag = null;
    }
}
