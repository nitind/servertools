/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.server.ui.internal.wizard.page;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.wst.server.core.*;
import org.eclipse.wst.server.ui.internal.ContextIds;
import org.eclipse.wst.server.ui.internal.ImageResource;
import org.eclipse.wst.server.ui.internal.Messages;
import org.eclipse.wst.server.ui.internal.SWTUtil;
import org.eclipse.wst.server.ui.internal.viewers.RuntimeTypeComposite;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
/**
 * 
 */
public class NewRuntimeComposite extends Composite {
	protected Tree tree;
	protected TreeViewer treeViewer;

	protected IRuntimeWorkingCopy runtime;
	protected Map runtimeMap = new HashMap();
	
	protected TaskModel taskModel;
	protected IWizardHandle wizard;
	
	protected String type;
	protected String version;
	protected String runtimeTypeId;

	public NewRuntimeComposite(Composite parent, IWizardHandle wizard, TaskModel tm, String type, String version, String runtimeTypeId) {
		super(parent, SWT.NONE);
		
		this.wizard = wizard;
		this.taskModel = tm;
		this.type = type;
		this.version = version;
		this.runtimeTypeId = runtimeTypeId;
		
		createControl();
		
		wizard.setTitle(Messages.wizNewRuntimeTitle);
		wizard.setDescription(Messages.wizNewRuntimeDescription);
		wizard.setImageDescriptor(ImageResource.getImageDescriptor(ImageResource.IMG_WIZBAN_NEW_RUNTIME));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl() {
		//initializeDialogUnits(parent);
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = SWTUtil.convertHorizontalDLUsToPixels(this, 4);
		layout.verticalSpacing = SWTUtil.convertVerticalDLUsToPixels(this, 4);
		setLayout(layout);
		IWorkbenchHelpSystem whs = PlatformUI.getWorkbench().getHelpSystem();
		whs.setHelp(this, ContextIds.NEW_RUNTIME_WIZARD);
		
		final RuntimeTypeComposite comp = new RuntimeTypeComposite(this, SWT.NONE, true, new RuntimeTypeComposite.RuntimeTypeSelectionListener() {
			public void runtimeTypeSelected(IRuntimeType runtimeType) {
				handleSelection(runtimeType);
			}
		}, type, version, runtimeTypeId);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 300;
		comp.setLayoutData(data);
	}

	protected void handleSelection(IRuntimeType runtimeType) {
		if (runtimeType == null)
			runtime = null;
		else {
			try {
				runtime = null;
				runtime = (IRuntimeWorkingCopy) runtimeMap.get(runtimeType);
			} catch (Exception e) {
				// ignore
			}
			if (runtime == null) {
				try {
					runtime = runtimeType.createRuntime(null, null);
					ServerUtil.setRuntimeDefaultName(runtime);
					if (runtime != null)
						runtimeMap.put(runtimeType, runtime);
				} catch (Exception e) {
					// ignore
				}
			}
		}

		taskModel.putObject(TaskModel.TASK_RUNTIME, runtime);
		wizard.update();
	}

	public IRuntimeWorkingCopy getRuntime() {
		return runtime;
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		Control[] c = getChildren();
		if (c != null) {
			int size = c.length;
			for (int i = 0; i < size; i++)
				if (c[i] != null)
					c[i].setVisible(visible);
		}
	}
}