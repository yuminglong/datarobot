/*
 * Copyright (C) 2014 The Datarobot Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arconsis.android.datarobot.manifest;

import java.util.Collections;
import java.util.List;

import com.arconsis.android.datarobot.processor.ContentProviderData;

/**
 * Data from the AndroidManifest.xml
 * 
 * @author Alexander Frank
 * @author Falk Appel
 */
public class AndroidManifest {

	private final String packageName;
	private final List<ContentProviderData> contentProviders;

	public AndroidManifest(final String packageName, final List<ContentProviderData> contentProviders) {
		this.packageName = packageName;
		this.contentProviders = contentProviders;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<ContentProviderData> getContentProviders() {
		return Collections.unmodifiableList(contentProviders);
	}
}
