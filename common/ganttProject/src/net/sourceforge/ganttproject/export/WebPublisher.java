/*
 * Created on 08.10.2005
 */
package net.sourceforge.ganttproject.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.ganttproject.GanttOptions;
import net.sourceforge.ganttproject.gui.UIFacade;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

class WebPublisher {

    WebPublisher() {
    }

    public void run(final File[] exportFiles, final GanttOptions options, UIFacade uiFacade) {
        IJobManager jobManager = Platform.getJobManager();
        IProgressMonitor monitor = jobManager.createProgressGroup();
        	Job startingJob = new Job("starting") {
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask("Publishing files on FTP", exportFiles.length);
					try{
		            URL baseUrl= new URL("ftp://"+options.getFTPUser()+":"+options.getFTPPwd()+"@"+options.getFTPUrl()+"/"+options.getFTPDirectory()+"/");
		            for (int i=0; i<exportFiles.length; i++) {
		                Job nextJob = createTransferJob(baseUrl, exportFiles[i]);
		                nextJob.setProgressGroup(monitor, 1);
		                nextJob.schedule();
		                nextJob.join();
		            }
		            Job finishingJob = new Job("finishing") {
						protected IStatus run(IProgressMonitor monitor) {
							monitor.done();
							return Status.OK_STATUS;
						}
		            };
		            finishingJob.setProgressGroup(monitor, 0);
		            finishingJob.schedule();
		            finishingJob.join();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        return Status.OK_STATUS;
				}
        	};
        	startingJob.setProgressGroup(monitor, 0);
        	startingJob.schedule();
    }

    private Job createTransferJob(URL baseUrl, final File file) throws IOException {
        final URL outUrl = new URL(baseUrl, file.getName());
        Job result = new Job("transfer file "+file.getName()) {
            protected IStatus run(IProgressMonitor monitor) {
                byte[] buffer = new byte[(int) file.length()];
                FileInputStream inputStream = null;
                OutputStream outStream = null;
                try {
                    inputStream = new FileInputStream(file);
                    inputStream.read(buffer);
                    System.err.println("writing to file="+outUrl);
                    outStream = outUrl.openConnection().getOutputStream();
                    outStream.write(buffer);
                    outStream.flush();
                    monitor.worked(1);
                    return Status.OK_STATUS;
                } catch (IOException e) {
                    e.printStackTrace();
                    return Status.CANCEL_STATUS;
                }
                finally {
                    if (inputStream!=null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outStream!=null) {
                        try {
                            outStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        return result;
    }

}
