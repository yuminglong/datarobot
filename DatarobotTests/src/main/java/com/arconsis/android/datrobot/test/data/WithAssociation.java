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
package com.arconsis.android.datrobot.test.data;

import com.arconsis.android.datarobot.entity.AutoIncrement;
import com.arconsis.android.datarobot.entity.Column;
import com.arconsis.android.datarobot.entity.Entity;
import com.arconsis.android.datarobot.entity.PrimaryKey;
import com.arconsis.android.datarobot.entity.Relationship;

/**
 * @author Falk Appel
 * @author Alexander Frank
 */
@Entity
 public class WithAssociation {

	 @Column
	 @PrimaryKey
	 @AutoIncrement
	 private Integer _id;
	 @Column
	 private String name;
	 @Relationship
	 private Simple entity;

	 public WithAssociation() {
	 }
	 public WithAssociation(final Integer id, final String name, final Simple entity) {
		 this._id = id;
		 this.name = name;
		 this.entity = entity;
	 }

	 public Integer getId() {
		 return _id;
	 }

	 public void setId(final Integer id) {
		 this._id = id;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(final String name) {
		 this.name = name;
	 }

	 public Simple getEntity() {
		 return entity;
	 }

	 public void setEntity(final Simple entity) {
		 this.entity = entity;
	 }

 }
