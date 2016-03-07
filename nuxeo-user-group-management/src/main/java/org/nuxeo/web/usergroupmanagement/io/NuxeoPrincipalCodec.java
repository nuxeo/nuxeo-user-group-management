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
package org.nuxeo.web.usergroupmanagement.io;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.nuxeo.ecm.automation.io.services.codec.ObjectCodec;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.restapi.jaxrs.io.usermanager.NuxeoPrincipalReader;
import org.nuxeo.ecm.restapi.jaxrs.io.usermanager.NuxeoPrincipalWriter;

import java.io.IOException;

/**
 * @since 8.2
 */
public class NuxeoPrincipalCodec extends ObjectCodec<NuxeoPrincipal> {

    @Override
    public String getType() {
        return "user";
    }

    @Override
    public boolean isBuiltin() {
        return true;
    }

    @Override
    public void write(JsonGenerator jg, NuxeoPrincipal user) throws IOException {
        NuxeoPrincipalWriter npw = new NuxeoPrincipalWriter();
        npw.writeEntity(jg, user);
    }

    @Override
    public NuxeoPrincipal read(JsonParser jp, CoreSession session) throws  IOException {
        return NuxeoPrincipalReader.readJson(jp, null);
    }
}
