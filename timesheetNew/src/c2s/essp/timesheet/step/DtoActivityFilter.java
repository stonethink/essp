package c2s.essp.timesheet.step;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoActivityFilter extends DtoBase {

	public static final String DTO_KEY = "DtoActivityFilter_key";

	public static final String DTO_ACTIVITY_LIST = "DtoActivityFilter_Activity_List";
	
	public static final String TREE_NODE_KEY = "DtoActivityFilter_Tree_Node";

	private String wbs;

	private boolean notStart = true;

	private Date start;

	private boolean inProgress = true;

	private boolean completed = false;

	private Date finish;

	private boolean checkManager;

	private String manager;

	private boolean checkResource;

	private String resource;

	private boolean checkOthers;

	private String name;

	/**
	 * @return Returns the checkManager.
	 */
	public boolean isCheckManager() {
		return checkManager;
	}

	/**
	 * @param checkManager
	 *            The checkManager to set.
	 */
	public void setCheckManager(boolean checkManager) {
		this.checkManager = checkManager;
	}

	/**
	 * @return Returns the checkOthers.
	 */
	public boolean isCheckOthers() {
		return checkOthers;
	}

	/**
	 * @param checkOthers
	 *            The checkOthers to set.
	 */
	public void setCheckOthers(boolean checkOthers) {
		this.checkOthers = checkOthers;
	}

	/**
	 * @return Returns the checkResource.
	 */
	public boolean isCheckResource() {
		return checkResource;
	}

	/**
	 * @param checkResource
	 *            The checkResource to set.
	 */
	public void setCheckResource(boolean checkResource) {
		this.checkResource = checkResource;
	}

	/**
	 * @return Returns the completed.
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed
	 *            The completed to set.
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return Returns the finish.
	 */
	public Date getFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            The finish to set.
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
	}

	/**
	 * @return Returns the inProgress.
	 */
	public boolean isInProgress() {
		return inProgress;
	}

	/**
	 * @param inProgress
	 *            The inProgress to set.
	 */
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	/**
	 * @return Returns the manager.
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            The manager to set.
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the notStart.
	 */
	public boolean isNotStart() {
		return notStart;
	}

	/**
	 * @param notStart
	 *            The notStart to set.
	 */
	public void setNotStart(boolean notStart) {
		this.notStart = notStart;
	}

	/**
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource
	 *            The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * @return Returns the start.
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start
	 *            The start to set.
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return Returns the wbs.
	 */
	public String getWbs() {
		return wbs;
	}

	/**
	 * @param wbs
	 *            The wbs to set.
	 */
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
}
