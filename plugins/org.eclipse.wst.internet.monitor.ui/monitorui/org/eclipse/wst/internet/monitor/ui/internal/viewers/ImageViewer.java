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
package org.eclipse.wst.internet.monitor.ui.internal.viewers;

import java.io.ByteArrayInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.internet.monitor.ui.internal.Messages;
import org.eclipse.wst.internet.monitor.ui.internal.MonitorUIPlugin;
import org.eclipse.wst.internet.monitor.ui.internal.provisional.ContentViewer;
/**
 * An image viewer.
 */
public class ImageViewer extends ContentViewer {
	protected static final byte CR = '\r';
	protected static final byte LF = '\n';

	protected Label messageLabel;
	
	protected byte[] content;

	/** (non-Javadoc)
	 * @see ContentViewer#init(Composite)
	 */
	public void init(Composite parent) {
		messageLabel = new Label(parent, SWT.NONE);
		messageLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING));
		messageLabel.setText(Messages.imageViewInvalid);
	}

	/** (non-Javadoc)
	 * @see ContentViewer#setContent(byte[])
	 */
	public void setContent(byte[] b) {
		content = b;
		if (b == null || b.length == 0) {
			messageLabel.setImage(null);
		} else {
			b = MonitorUIPlugin.unzip(b);
			
			int trimFront = 0;
			int trimBack = 0;
			int len = b.length - 1;
			while (trimFront < b.length && b[trimFront] == CR || b[trimFront] == LF)
				trimFront++;
			while (trimBack < b.length && b[len - trimBack] == CR || b[len - trimBack] == LF)
				trimBack++;
			
			if (trimFront + trimBack > 0) {
				if (trimFront + trimBack > b.length) {
					b = new byte[0];
				} else {
					byte[] temp = b;
					b = new byte[temp.length - trimBack - trimFront];
					for (int i = trimFront; i < temp.length - trimBack; i++) {
						b[i - trimFront] = temp[i];
					}
				}
			}
			
			try {
				ImageData imgD = new ImageData(new ByteArrayInputStream(b));
				Image img = new Image(null, imgD);
				messageLabel.setImage(img);
			} catch(Exception e) {
				messageLabel.setImage(null);
			}
		}
		
		Composite parent = messageLabel.getParent();
		messageLabel.setFont(parent.getFont());
		parent.layout(true);
	}

	/**
	 * @see ContentViewer#getContent()
	 */
	public byte[] getContent() {
		return content;
	}

	/** (non-Javadoc)
	 * @see ContentViewer#dispose()
	 */
	public void dispose() {
		messageLabel.dispose();
		messageLabel = null;
		content = null;
	}
}