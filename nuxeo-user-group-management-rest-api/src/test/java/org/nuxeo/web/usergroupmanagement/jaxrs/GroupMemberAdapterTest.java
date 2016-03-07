package org.nuxeo.web.usergroupmanagement.jaxrs;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoGroup;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.core.test.annotations.RepositoryInit;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.ecm.restapi.test.BaseTest;
import org.nuxeo.ecm.restapi.test.RestServerFeature;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(FeaturesRunner.class)
@Features({ RestServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(init = GroupMemberAdapterTest.Init.class, cleanup = Granularity.METHOD)
@Deploy({ "org.nuxeo.web.usergroupmanagement.core", "org.nuxeo.ecm.restapi.server.jaxrs.usergroupmanagement" })
public class GroupMemberAdapterTest extends BaseTest {

    public static class Init implements RepositoryInit {

        @Override
        public void populate(CoreSession session) {

            UserManager um = Framework.getLocalService(UserManager.class);

            // Create a group
            String groupId = "group1";
            NuxeoGroup group = um.getGroup(groupId);
            if (group != null) {
                um.deleteGroup(groupId);
            }

            DocumentModel groupModel = um.getBareGroupModel();
            String schemaName = um.getGroupSchemaName();
            groupModel.setProperty(schemaName, "groupname", groupId);
            groupModel.setProperty(schemaName, "grouplabel", groupId);
            um.createGroup(groupModel);

            // Create some users
            for (int idx = 0; idx < 5; idx++) {
                String userId = "user" + idx;

                NuxeoPrincipal principal = um.getPrincipal(userId);

                if (principal != null) {
                    um.deleteUser(principal.getModel());
                }

                DocumentModel userModel = um.getBareUserModel();
                schemaName = um.getUserSchemaName();
                userModel.setProperty(schemaName, "username", userId);
                userModel.setProperty(schemaName, "password", userId);
                userModel.setProperty(schemaName, "groups", new String[] { groupId });
                um.createUser(userModel);
            }
        }
    }

    @Test
    public void itCanPaginateGroupMembers() throws Exception {

        int[] expectedPageSizes = new int[] {3, 2};

        for (int i = 0; i < expectedPageSizes.length; i++) {
            JsonNode node = getResponseAsJson(BaseTest.RequestType.GET, "/group/group1/@members", getQueryParamsForPage(i));
            assertPaging(i, 3, 2, 5, expectedPageSizes[i], node);
        }

    }

    private void assertPaging(int currentPageIndex, int pageSize, int numberOfPage, int resultsCount,
        int currentPageSize, JsonNode node) {
        assertTrue(node.get("isPaginable").getBooleanValue());
        assertEquals(currentPageIndex, node.get("currentPageIndex").getIntValue());
        assertEquals(pageSize, node.get("pageSize").getIntValue());
        assertEquals(numberOfPage, node.get("numberOfPages").getIntValue());
        assertEquals(resultsCount, node.get("resultsCount").getIntValue());
        assertEquals(currentPageSize, node.get("currentPageSize").getIntValue());
    }

    private MultivaluedMap<String, String> getQueryParamsForPage(int pageIndex) {
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.putSingle("q", "*");
        queryParams.putSingle("currentPageIndex", Integer.toString(pageIndex));
        queryParams.putSingle("pageSize", "3");
        return queryParams;
    }
}