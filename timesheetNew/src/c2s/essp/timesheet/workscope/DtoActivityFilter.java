package c2s.essp.timesheet.workscope;

import c2s.dto.DtoBase;

public class DtoActivityFilter extends DtoBase {
	
	public boolean notStart = true;
	public boolean inProgress = true;
	public boolean completed = false;
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public boolean isInProgress() {
		return inProgress;
	}
	
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	
	public boolean isNotStart() {
		return notStart;
	}
	
	public void setNotStart(boolean notStart) {
		this.notStart = notStart;
	}

}
