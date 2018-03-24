/*
 * Created on 12.03.2005
 */
package net.sourceforge.ganttproject.document;

import java.io.File;

import net.sourceforge.ganttproject.gui.options.model.GPOptionGroup;

/**
 * @author bard
 */
public interface DocumentManager {
    Document getDocument(String path);

    void addToRecentDocuments(Document document);

    Document getDocument(String path, String userName, String password);

	void changeWorkingDirectory(File parentFile);

	String getWorkingDirectory();

	GPOptionGroup getOptionGroup();
    GPOptionGroup[] getNetworkOptionGroups();
}
