/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.th.aten.network.i18n;

import org.jboss.seam.international.status.builder.BundleKey;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
public class AppBundleKey extends BundleKey {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8264513975522482452L;

	public static final String APP_BUNDLE_NAME = "app_messages";

    public AppBundleKey(String key) {
        super(APP_BUNDLE_NAME, key);
    }
    public AppBundleKey(String key,String language) {
        super(APP_BUNDLE_NAME+"_"+language, key);
    }
    
//    public AppBundleKey(String key,String value) {
//        super(EPI_BUNDLE_NAME, key);
//    }
}
