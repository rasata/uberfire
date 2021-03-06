/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.server.cdi;

import static org.jboss.errai.bus.server.api.RpcContext.getMessage;
import static org.jboss.errai.bus.server.api.RpcContext.getQueueSession;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.security.shared.service.AuthenticationService;
import org.uberfire.rpc.SessionInfo;
import org.uberfire.rpc.impl.SessionInfoImpl;

public class UberFireGeneralFactory {

    @Inject
    private Instance<User> user;

    @Produces
    @RequestScoped
    @Default
    public SessionInfo getSessionInfo(AuthenticationService authenticationService) {
        String sessionId = getSessionId();
        User user;
        if ( sessionId == null ) {
            user = getDefaultUser();
            sessionId = user.getIdentifier();
        }
        else {
            user = authenticationService.getUser();
        }
        return new SessionInfoImpl( sessionId, user );
    }

    private User getDefaultUser() {
        if ( user.isAmbiguous() || user.isUnsatisfied() ) {
            throw new IllegalStateException( "Cannot get session info outside of servlet thread when no default user is provided." );
        }
        else {
            return user.get();
        }
    }

    private String getSessionId() {
        return (getMessage() != null && getQueueSession() != null ? getQueueSession().getSessionId() : null );
    }

}
