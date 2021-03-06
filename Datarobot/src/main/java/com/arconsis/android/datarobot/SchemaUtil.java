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
package com.arconsis.android.datarobot;

import static com.arconsis.android.datarobot.schema.SchemaConstants.ASSOCIATIONS_INTERFACE;
import static com.arconsis.android.datarobot.schema.SchemaConstants.DB;
import static com.arconsis.android.datarobot.schema.SchemaConstants.GENERATED_SUFFIX;
import static com.arconsis.android.datarobot.schema.SchemaConstants.INFO_SUFFIX;
import static com.arconsis.android.datarobot.schema.SchemaConstants.TABLE;
import static com.arconsis.android.datarobot.schema.SchemaConstants.TABLE_NAME;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.arconsis.android.datarobot.schema.EntityInfo;
import com.arconsis.android.datarobot.schema.ToManyAssociation;

/**
 * Utility for different schema access.
 * 
 * @author Falk Appel
 * @author Alexander Frank
 */
class SchemaUtil {
	private static final String ASSOCIATION_TEMPLATE = "%s." + GENERATED_SUFFIX + "." + DB + "$%s" + TABLE + "$" + ASSOCIATIONS_INTERFACE;
	private static final String SCHEMA_TEMPLATE = "%s." + GENERATED_SUFFIX + "." + DB + "$%s" + TABLE;
	private static final String DB_TEMPLATE = "%s." + GENERATED_SUFFIX + "." + DB;

	private static final ConcurrentMap<Class<?>, String> TABLE_NAME_CACHE = new ConcurrentHashMap<Class<?>, String>();
	private static final ConcurrentMap<Class<?>, Class<?>> ASSOCIATION_SCHEMA_CACHE = new ConcurrentHashMap<Class<?>, Class<?>>();
	private static final ConcurrentMap<Class<?>, EntityInfo> ENTITY_INFO_CACHE = new ConcurrentHashMap<Class<?>, EntityInfo>();

	static String getTableName(final Class<?> entityClass, final String packageName) {
		if (TABLE_NAME_CACHE.containsKey(entityClass)) {
			return TABLE_NAME_CACHE.get(entityClass);
		}

		try {
			String className = String.format(SCHEMA_TEMPLATE, packageName, entityClass.getSimpleName());
			Class<?> schemaClass = Class.forName(className);
			String tableName = (String) schemaClass.getField(TABLE_NAME).get(null);
			TABLE_NAME_CACHE.putIfAbsent(entityClass, tableName);
			return tableName;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static Class<?> getAssociationsSchema(final Class<?> entityClass, final String packageName) {
		if (ASSOCIATION_SCHEMA_CACHE.containsKey(entityClass)) {
			ASSOCIATION_SCHEMA_CACHE.get(entityClass);
		}
		try {
			Class<?> associationsSchema = Class.forName(String.format(ASSOCIATION_TEMPLATE, packageName, entityClass.getSimpleName()));
			ASSOCIATION_SCHEMA_CACHE.putIfAbsent(entityClass, associationsSchema);
			return associationsSchema;
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	static EntityInfo getEntityInfo(final Class<?> entityClass, final String packageName) {
		if (ENTITY_INFO_CACHE.containsKey(entityClass)) {
			return ENTITY_INFO_CACHE.get(entityClass);
		}
		try {
			EntityInfo entityInfo = (EntityInfo) Class.forName(String.format(DB_TEMPLATE, packageName)).getField(entityClass.getSimpleName() + INFO_SUFFIX)
					.get(null);
			ENTITY_INFO_CACHE.putIfAbsent(entityClass, entityInfo);
			return entityInfo;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static ToManyAssociation getToManyAsso(final Field associationField, final Class<?> associationsSchema) {
		try {
			Field toManyAssociationInfo = associationsSchema.getDeclaredField(associationField.getName().toUpperCase(Locale.getDefault()));
			return (ToManyAssociation) toManyAssociationInfo.get(null);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
