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
 *     Nelson Silva <nsilva@nuxeo.com>
 */
package org.nuxeo.web.usergroupmanagement.operations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.ecm.platform.usermanager.NuxeoPrincipalImpl;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.ecm.user.invite.UserRegistrationInfo;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import javax.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features(PlatformFeature.class)
@Deploy({ "org.nuxeo.ecm.automation.core", "org.nuxeo.ecm.user.invite", "org.nuxeo.web.usergroupmanagement.core" })
@RepositoryConfig(init = DefaultRepositoryInit.class, user = "Administrator", cleanup = Granularity.METHOD)
@LocalDeploy({ "org.nuxeo.ecm.user.invite:test-invite-contrib.xml" })
public class UserInviteTest {

    @Inject
    AutomationService service;

    @Inject
    CoreSession session;

    @Inject
    UserManager userManager;

    @Before
    public void init() {
        DocumentModel container = session.createDocumentModel("Workspace");
        container.setPathInfo("/", "requests");
        session.createDocument(container);
        session.save();
    }

    @Test
    public void testInviteUser() throws Exception {
        OperationContext ctx = new OperationContext(session);
        NuxeoPrincipal user = new NuxeoPrincipalImpl("user");
        ctx.setInput(user);

        String invitationId = (String) service.run(ctx, UserInvite.ID);

        DocumentModel doc = session.getDocument(new IdRef(invitationId));
        Assert.assertEquals(user.getName(), doc.getPropertyValue(UserRegistrationInfo.USERNAME_FIELD));
    }
}