/**********************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *    IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.wst.server.ui.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.ui.internal.editor.ServerEditorInput;

public abstract class AbstractOpenEditorTestCase extends TestCase {
	private final String SERVER_EDITOR_ID = "org.eclipse.wst.server.ui.editor";
	
	public static Test suite() {
		return new TestSuite(AbstractOpenEditorTestCase.class, "OpenEditorTestCase");
	}

	public void testOpenEditor() throws Exception {
		IServer server = getServer();
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = page.openEditor(new ServerEditorInput(server.getId()), SERVER_EDITOR_ID, true);
		page.closeEditor(editor, false);
		releaseServer(server);
	}

	public abstract IServer getServer() throws Exception;
	
	public abstract void releaseServer(IServer server) throws Exception;
}