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
package org.eclipse.jst.server.core;

import org.eclipse.core.runtime.IPath;
/**
 * A J2EE connector module.
 * 
 * @since 1.0
 */
public interface IConnectorModule extends IJ2EEModule {
	/**
	 * Returns the classpath as an array of absolute IPaths.
	 * 
	 * @return an array of classpath entries
	 */
	public IPath[] getClasspath();
}