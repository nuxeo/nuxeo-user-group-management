/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     "Guillaume Renard"
 */

package org.nuxeo.web.usergroupmanagement.providers;

import java.util.ArrayList;
import java.util.List;

import org.nuxeo.ecm.core.api.NuxeoGroup;
import org.nuxeo.ecm.platform.query.api.AbstractPageProvider;
import org.nuxeo.ecm.platform.query.api.PageProvider;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

/**
 * @since 8.2
 */
public abstract class AbstractGroupMemberPageProvider<T> extends AbstractPageProvider<T>
    implements PageProvider<T> {

    protected static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    protected UserManager userManager;

    protected List<T> currentPage;

    @Override
    public List<T> getCurrentPage() {
        if (currentPage == null) {
            currentPage = new ArrayList<T>();
            NuxeoGroup group = (NuxeoGroup) getParameters()[0];
            List<String> members = getMembers(group);
            int limit = safeLongToInt(getCurrentPageOffset() + getPageSize());
            if (limit > members.size()) {
                limit = members.size();
            }

            for (String member : members.subList(safeLongToInt(getCurrentPageOffset()), limit)) {
                currentPage.add(getMember(member));
            }

            setResultsCount(members.size());
        }
        return currentPage;
    }

    protected abstract List<String> getMembers(NuxeoGroup group);

    protected abstract T getMember(String id);


    @Override
    public long getPageLimit() {
        long pageSize = getPageSize();
        if (pageSize == 0) {
            return 0;
        }
        return ((NuxeoGroup) getParameters()[0]).getMemberGroups().size() / pageSize;
    }

    @Override
    public boolean isSortable() {
        return false;
    }

    @Override
    protected void pageChanged() {
        currentPage = null;
        super.pageChanged();
    }

    protected UserManager getUserManager() {
        if (userManager == null) {
            userManager = Framework.getService(UserManager.class);
        }
        return userManager;
    }
}