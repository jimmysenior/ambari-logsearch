/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ambari.logsearch.web.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.ambari.logsearch.common.PropertiesHelper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

abstract class LogsearchAbstractAuthenticationProvider implements AuthenticationProvider {

  private static final String AUTH_METHOD_PROPERTY_PREFIX = "logsearch.auth.";

  protected enum AuthMethod {
    LDAP, FILE, EXTERNAL_AUTH, SIMPLE
  };

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  /**
   * GET Default GrantedAuthority
   */
  protected List<GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuths = new ArrayList<>();
    grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
    return grantedAuths;
  }

  /**
   * Check authentication provider is enable or disable for specified method
   */
  public boolean isEnable(AuthMethod method) {
    String methodName = method.name().toLowerCase();
    String property = AUTH_METHOD_PROPERTY_PREFIX + methodName + ".enable";
    boolean isEnable = PropertiesHelper.getBooleanProperty(property, false);
    return isEnable;
  }

}
