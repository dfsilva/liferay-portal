/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.blogs.trash;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BlogsEntryTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashDuplicate() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashParentAndDeleteParent() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashParentAndRestoreModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndRestore() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String title = getSearchKeywords();
		String description = "Description";
		String content = "Content";
		int displayDateMonth = 1;
		int displayDateDay = 1;
		int displayDateYear = 2012;
		int displayDateHour = 12;
		int displayDateMinute = 0;
		boolean allowPingbacks = true;
		boolean allowTrackbacks = true;
		String[] trackbacks = new String[0];
		boolean smallImage = false;
		String smallImageURL = StringPool.BLANK;
		String smallImageFileName = StringPool.BLANK;
		InputStream smallImageInputStream = null;

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);

		if (approved) {
			BlogsEntryLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}

		return entry;
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return BlogsEntryLocalServiceUtil.getEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected int getBaseModelsNotInTrashCount(BaseModel<?> parentBaseModel)
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), queryDefinition);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

}