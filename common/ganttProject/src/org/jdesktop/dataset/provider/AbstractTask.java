/*
 * $Id: AbstractTask.java,v 1.1 2006/10/10 03:50:55 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset.provider;

/**
 *
 * @author rb156199
 */
public abstract class AbstractTask implements Task {
        private int min = 0;
        private int max = 100;
        private int progress = 0;
        private boolean indeterminate = true;
        private boolean cancellable = false;
        private boolean modal = true;

        protected void setMinimum(int val) {
            min = val < 0 || val > max ? 0 : val;
        }
        
        protected void setMaximum(int val) {
            max = val < 0 || val < min ? min : val;
        }
        
        protected void setProgress(int progress) {
            this.progress = progress < 0 ? 0 : progress;
        }
        
        protected void setIndeterminate(boolean b) {
            indeterminate = b;
        }

        public int getMinimum() {
            return min;
        }

        public int getMaximum() {
            return max;
        }

        public int getProgress() {
            return progress;
        }

        public boolean isIndeterminate() {
            return indeterminate;
        }

        public boolean isModal() {
            return modal;
        }

        public boolean canCancel() {
            return cancellable;
        }
        
        public void setModel(boolean b) {
            modal = b;
        }
        
        public void setCanCancel(boolean b) {
            cancellable = b;
        }
}
