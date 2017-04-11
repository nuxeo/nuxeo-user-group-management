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

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.ecm.user.invite.UserInvitationService;
import org.nuxeo.ecm.user.invite.UserRegistrationConfiguration;
import org.nuxeo.ecm.user.invite.UserRegistrationInfo;
import org.nuxeo.runtime.api.Framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.nuxeo.ecm.user.invite.UserInvitationService.ValidationMethod;

/**
 * Operation to invite a User.
 *
 * @since 8.2
 */
@Operation(id = UserInvite.ID, category = Constants.CAT_USERS_GROUPS, label = "Invite a user",
    description = "Stores a registration request and returns a unique ID for it.")
public class UserInvite {

    public static final String ID = "User.Invite";

    @Context
    protected UserManager userManager;

    @Context
    protected UserInvitationService invitationService;

    @Param(name = "validationMethod", required = false)
    protected ValidationMethod validationMethod = ValidationMethod.EMAIL;

    @Param(name = "autoAccept", required = false)
    protected boolean autoAccept = true;

    @Param(name = "info", required = false)
    protected Map<String, Serializable> info = new HashMap<>();

    @OperationMethod
    public String run(NuxeoPrincipal user) {
        DocumentModel invitation = invitationService.getUserRegistrationModel(null);
        UserInvitationService userRegistrationService = Framework.getLocalService(UserInvitationService.class);
        UserRegistrationConfiguration conf = userRegistrationService.getConfiguration();
        invitation.setPropertyValue(conf.getUserInfoUsernameField(),  user.getName());
        invitation.setPropertyValue(conf.getUserInfoFirstnameField(), user.getFirstName());
        invitation.setPropertyValue(conf.getUserInfoLastnameField(), user.getLastName());
        invitation.setPropertyValue(conf.getUserInfoEmailField(),  user.getEmail());
        invitation.setPropertyValue(conf.getUserInfoGroupsField(), user.getGroups().toArray());
        invitation.setPropertyValue(conf.getUserInfoCompanyField(), user.getCompany());

        return invitationService.submitRegistrationRequest(invitation, info, validationMethod, autoAccept);
    }
}
