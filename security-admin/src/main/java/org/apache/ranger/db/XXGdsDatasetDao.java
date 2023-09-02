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

package org.apache.ranger.db;

import org.apache.commons.lang.StringUtils;
import org.apache.ranger.common.db.BaseDao;
import org.apache.ranger.entity.XXGdsDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;


@Service
public class XXGdsDatasetDao extends BaseDao<XXGdsDataset> {
	private static final Logger LOG = LoggerFactory.getLogger(XXGdsDatasetDao.class);

	public XXGdsDatasetDao(RangerDaoManagerBase daoManager) {
		super(daoManager);
	}

	public XXGdsDataset findByGuid(String guid) {
		XXGdsDataset ret = null;

		if (StringUtils.isNotBlank(guid)) {
			try {
				ret = getEntityManager().createNamedQuery("XXGdsDataset.findByGuid", tClass)
						                .setParameter("guid", guid).getSingleResult();
			} catch (NoResultException e) {
				LOG.debug("findByGuid({}): ", guid, e);
			}
		}

		return ret;
	}

	public XXGdsDataset findByName(String name) {
		XXGdsDataset ret = null;

		if (StringUtils.isNotBlank(name)) {
			try {
				ret = getEntityManager().createNamedQuery("XXGdsDataset.findByName", tClass)
						                .setParameter("name", name).getSingleResult();
			} catch (NoResultException e) {
				LOG.debug("findByName({}): ", name, e);
			}
		}

		return ret;
	}

	public List<XXGdsDataset> findByDataShareId(Long dataShareId) {
		List<XXGdsDataset> ret = null;

		if (dataShareId != null) {
			try {
				ret = getEntityManager().createNamedQuery("XXGdsDataset.findByDataShareId", tClass)
						                .setParameter("dataShareId", dataShareId).getResultList();
			} catch (NoResultException e) {
				LOG.debug("findByDataShareId({}): ", dataShareId, e);
			}
		}

		return ret != null ? ret : Collections.emptyList();
	}

	public List<XXGdsDataset> findByProjectId(Long projectId) {
		List<XXGdsDataset> ret = null;

		if (projectId != null) {
			try {
				ret = getEntityManager().createNamedQuery("XXGdsDataset.findByProjectId", tClass)
						                .setParameter("projectId", projectId).getResultList();
			} catch (NoResultException e) {
				LOG.debug("findByProjectId({}): ", projectId, e);
			}
		}

		return ret != null ? ret : Collections.emptyList();
	}
}