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

package org.apache.ranger.plugin.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.ranger.authorization.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RangerPolicy extends RangerBaseModelObject implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public static final int POLICY_TYPE_ACCESS    = 0;
    public static final int POLICY_TYPE_DATAMASK  = 1;
    public static final int POLICY_TYPE_ROWFILTER = 2;
    public static final int POLICY_TYPE_AUDIT     = 3;

    public static final int[] POLICY_TYPES = new int[] {
            POLICY_TYPE_ACCESS,
            POLICY_TYPE_DATAMASK,
            POLICY_TYPE_ROWFILTER
    };

    public static final String MASK_TYPE_NULL   = "MASK_NULL";
    public static final String MASK_TYPE_NONE   = "MASK_NONE";
    public static final String MASK_TYPE_CUSTOM = "CUSTOM";

    public static final int POLICY_PRIORITY_NORMAL   = 0;
    public static final int POLICY_PRIORITY_OVERRIDE = 1;

    public static final String POLICY_PRIORITY_NAME_NORMAL   = "NORMAL";
    public static final String POLICY_PRIORITY_NAME_OVERRIDE = "OVERRIDE";

    public static final Comparator<RangerPolicy> POLICY_ID_COMPARATOR = new PolicyIdComparator();

    private String                                  service;
    private String                                  name;
    private Integer                                 policyType;
    private Integer                                 policyPriority;
    private String                                  description;
    private String                                  resourceSignature;
    private Boolean                                 isAuditEnabled;
    private Map<String, RangerPolicyResource>       resources;
    private List<Map<String, RangerPolicyResource>> additionalResources;
    private List<RangerPolicyItemCondition>         conditions;
    private List<RangerPolicyItem>                  policyItems;
    private List<RangerPolicyItem>                  denyPolicyItems;
    private List<RangerPolicyItem>                  allowExceptions;
    private List<RangerPolicyItem>                  denyExceptions;
    private List<RangerDataMaskPolicyItem>          dataMaskPolicyItems;
    private List<RangerRowFilterPolicyItem>         rowFilterPolicyItems;
    private String                                  serviceType;
    private Map<String, Object>                     options;
    private List<RangerValiditySchedule>            validitySchedules;
    private List<String>                            policyLabels;
    private String                                  zoneName;
    private Boolean                                 isDenyAllElse;

    public RangerPolicy() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    public RangerPolicy(String service, String name, Integer policyType, Integer policyPriority, String description, Map<String, RangerPolicyResource> resources, List<RangerPolicyItem> policyItems, String resourceSignature, Map<String, Object> options, List<RangerValiditySchedule> validitySchedules, List<String> policyLables) {
        this(service, name, policyType, policyPriority, description, resources, policyItems, resourceSignature, options, validitySchedules, policyLables, null);
    }

    public RangerPolicy(String service, String name, Integer policyType, Integer policyPriority, String description, Map<String, RangerPolicyResource> resources, List<RangerPolicyItem> policyItems, String resourceSignature, Map<String, Object> options, List<RangerValiditySchedule> validitySchedules, List<String> policyLables, String zoneName) {
        this(service, name, policyType, policyPriority, description, resources, policyItems, resourceSignature, options, validitySchedules, policyLables, zoneName, null);
    }

    public RangerPolicy(String service, String name, Integer policyType, Integer policyPriority, String description, Map<String, RangerPolicyResource> resources, List<RangerPolicyItem> policyItems, String resourceSignature, Map<String, Object> options, List<RangerValiditySchedule> validitySchedules, List<String> policyLables, String zoneName, List<RangerPolicyItemCondition> conditions) {
        this(service, name, policyType, policyPriority, description, resources, policyItems, resourceSignature, options, validitySchedules, policyLables, zoneName, conditions, null);
    }

    /**
     * @param service
     * @param name
     * @param policyType
     * @param description
     * @param resources
     * @param policyItems
     * @param resourceSignature TODO
     */
    public RangerPolicy(String service, String name, Integer policyType, Integer policyPriority, String description, Map<String, RangerPolicyResource> resources, List<RangerPolicyItem> policyItems, String resourceSignature, Map<String, Object> options, List<RangerValiditySchedule> validitySchedules, List<String> policyLables, String zoneName, List<RangerPolicyItemCondition> conditions, Boolean isDenyAllElse) {
        super();

        setService(service);
        setName(name);
        setPolicyType(policyType);
        setPolicyPriority(policyPriority);
        setDescription(description);
        setResourceSignature(resourceSignature);
        setIsAuditEnabled(null);
        setResources(resources);
        setPolicyItems(policyItems);
        setDenyPolicyItems(null);
        setAllowExceptions(null);
        setDenyExceptions(null);
        setDataMaskPolicyItems(null);
        setRowFilterPolicyItems(null);
        setOptions(options);
        setValiditySchedules(validitySchedules);
        setPolicyLabels(policyLables);
        setZoneName(zoneName);
        setConditions(conditions);
        setIsDenyAllElse(isDenyAllElse);
    }

    /**
     * @param other
     */
    public void updateFrom(RangerPolicy other) {
        super.updateFrom(other);

        setService(other.getService());
        setName(other.getName());
        setPolicyType(other.getPolicyType());
        setPolicyPriority(other.getPolicyPriority());
        setDescription(other.getDescription());
        setResourceSignature(other.getResourceSignature());
        setIsAuditEnabled(other.getIsAuditEnabled());
        setResources(other.getResources());
        setAdditionalResources(other.getAdditionalResources());
        setConditions(other.getConditions());
        setPolicyItems(other.getPolicyItems());
        setDenyPolicyItems(other.getDenyPolicyItems());
        setAllowExceptions(other.getAllowExceptions());
        setDenyExceptions(other.getDenyExceptions());
        setDataMaskPolicyItems(other.getDataMaskPolicyItems());
        setRowFilterPolicyItems(other.getRowFilterPolicyItems());
        setServiceType(other.getServiceType());
        setOptions(other.getOptions());
        setValiditySchedules(other.getValiditySchedules());
        setPolicyLabels(other.getPolicyLabels());
        setZoneName(other.getZoneName());
        setIsDenyAllElse(other.getIsDenyAllElse());
    }

    /**
     * @return the type
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the type to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the policyType
     */
    public Integer getPolicyType() {
        return policyType;
    }

    /**
     * @param policyType the policyType to set
     */
    public void setPolicyType(Integer policyType) {
        this.policyType = policyType;
    }

    /**
     * @return the policyPriority
     */
    public Integer getPolicyPriority() {
        return policyPriority;
    }

    /**
     * @param policyPriority the policyPriority to set
     */
    public void setPolicyPriority(Integer policyPriority) {
        this.policyPriority = policyPriority == null ? RangerPolicy.POLICY_PRIORITY_NORMAL : policyPriority;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the resourceSignature
     */
    public String getResourceSignature() {
        return resourceSignature;
    }

    /**
     * @param resourceSignature the resourceSignature to set
     */
    public void setResourceSignature(String resourceSignature) {
        this.resourceSignature = resourceSignature;
    }

    /**
     * @return the isAuditEnabled
     */
    public Boolean getIsAuditEnabled() {
        return isAuditEnabled;
    }

    /**
     * @param isAuditEnabled the isEnabled to set
     */
    public void setIsAuditEnabled(Boolean isAuditEnabled) {
        this.isAuditEnabled = isAuditEnabled == null ? Boolean.TRUE : isAuditEnabled;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<String> getPolicyLabels() {
        return policyLabels;
    }

    public void setPolicyLabels(List<String> policyLabels) {
        this.policyLabels = nullSafeList(policyLabels);
    }

    public boolean addPolicyLabel(String policyLabel) {
        policyLabels = getUpdatableList(policyLabels);

        return policyLabels.add(policyLabel);
    }

    /**
     * @return the resources
     */
    public Map<String, RangerPolicyResource> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(Map<String, RangerPolicyResource> resources) {
        this.resources = nullSafeMap(resources);
    }

    public RangerPolicyResource setResource(String resourceName, RangerPolicyResource value) {
        resources = getUpdatableMap(resources);

        return resources.put(resourceName, value);
    }

    public List<Map<String, RangerPolicyResource>> getAdditionalResources() {
        return additionalResources;
    }

    public void setAdditionalResources(List<Map<String, RangerPolicyResource>> additionalResources) {
        this.additionalResources = additionalResources;
    }

    public void addResource(Map<String, RangerPolicyResource> resources) {
        if (MapUtils.isNotEmpty(resources)) {
            if (MapUtils.isEmpty(this.resources)) {
                this.resources = nullSafeMap(resources);
            } else {
                this.additionalResources = getUpdatableList(this.additionalResources);

                this.additionalResources.add(resources);
            }
        }
    }

    /**
     * @return the policyItems
     */
    public List<RangerPolicyItem> getPolicyItems() {
        return policyItems;
    }

    /**
     * @param policyItems the policyItems to set
     */
    public void setPolicyItems(List<RangerPolicyItem> policyItems) {
        this.policyItems = nullSafeList(policyItems);
    }

    public boolean addPolicyItem(RangerPolicyItem policyItem) {
        policyItems = getUpdatableList(policyItems);

        return policyItems.add(policyItem);
    }

    /**
     * @return the denyPolicyItems
     */
    public List<RangerPolicyItem> getDenyPolicyItems() {
        return denyPolicyItems;
    }

    /**
     * @param denyPolicyItems the denyPolicyItems to set
     */
    public void setDenyPolicyItems(List<RangerPolicyItem> denyPolicyItems) {
        this.denyPolicyItems = nullSafeList(denyPolicyItems);
    }

    public boolean addDenyPolicyItem(RangerPolicyItem policyItem) {
        denyPolicyItems = getUpdatableList(denyPolicyItems);

        return denyPolicyItems.add(policyItem);
    }

    /**
     * @return the allowExceptions
     */
    public List<RangerPolicyItem> getAllowExceptions() {
        return allowExceptions;
    }

    /**
     * @param allowExceptions the allowExceptions to set
     */
    public void setAllowExceptions(List<RangerPolicyItem> allowExceptions) {
        this.allowExceptions = nullSafeList(allowExceptions);
    }

    public boolean addAllowException(RangerPolicyItem policyItem) {
        allowExceptions = getUpdatableList(allowExceptions);

        return allowExceptions.add(policyItem);
    }

    /**
     * @return the denyExceptions
     */
    public List<RangerPolicyItem> getDenyExceptions() {
        return denyExceptions;
    }

    /**
     * @param denyExceptions the denyExceptions to set
     */
    public void setDenyExceptions(List<RangerPolicyItem> denyExceptions) {
        this.denyExceptions = nullSafeList(denyExceptions);
    }

    public boolean addDenyException(RangerPolicyItem policyItem) {
        denyExceptions = getUpdatableList(denyExceptions);

        return denyExceptions.add(policyItem);
    }

    public List<RangerDataMaskPolicyItem> getDataMaskPolicyItems() {
        return dataMaskPolicyItems;
    }

    public void setDataMaskPolicyItems(List<RangerDataMaskPolicyItem> dataMaskPolicyItems) {
        this.dataMaskPolicyItems = nullSafeList(dataMaskPolicyItems);
    }

    public boolean addDataMaskPolicyItem(RangerDataMaskPolicyItem policyItem) {
        dataMaskPolicyItems = getUpdatableList(dataMaskPolicyItems);

        return dataMaskPolicyItems.add(policyItem);
    }

    public List<RangerRowFilterPolicyItem> getRowFilterPolicyItems() {
        return rowFilterPolicyItems;
    }

    public void setRowFilterPolicyItems(List<RangerRowFilterPolicyItem> rowFilterPolicyItems) {
        this.rowFilterPolicyItems = nullSafeList(rowFilterPolicyItems);
    }

    public boolean addRowFilterPolicyItem(RangerRowFilterPolicyItem policyItem) {
        rowFilterPolicyItems = getUpdatableList(rowFilterPolicyItems);

        return rowFilterPolicyItems.add(policyItem);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = nullSafeMap(options);
    }

    public List<RangerValiditySchedule> getValiditySchedules() {
        return validitySchedules;
    }

    public void setValiditySchedules(List<RangerValiditySchedule> validitySchedules) {
        this.validitySchedules = nullSafeList(validitySchedules);
    }

    public boolean addValiditySchedule(RangerValiditySchedule validitySchedule) {
        validitySchedules = getUpdatableList(validitySchedules);

        return validitySchedules.add(validitySchedule);
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return the conditions
     */
    public List<RangerPolicyItemCondition> getConditions() {
        return conditions;
    }

    /**
     * @param conditions the conditions to set
     */
    public void setConditions(List<RangerPolicyItemCondition> conditions) {
        this.conditions = conditions;
    }

    public boolean addCondition(RangerPolicyItemCondition condition) {
        conditions = getUpdatableList(conditions);

        return conditions.add(condition);
    }

    public Boolean getIsDenyAllElse() {
        return isDenyAllElse;
    }

    public void setIsDenyAllElse(Boolean isDenyAllElse) {
        this.isDenyAllElse = isDenyAllElse == null ? Boolean.FALSE : isDenyAllElse;
    }

    public void dedupStrings(Map<String, String> strTbl) {
        super.dedupStrings(strTbl);

        service      = StringUtil.dedupString(service, strTbl);
        serviceType  = StringUtil.dedupString(serviceType, strTbl);
        zoneName     = StringUtil.dedupString(zoneName, strTbl);
        name         = StringUtil.dedupString(name, strTbl);
        description  = StringUtil.dedupString(description, strTbl);
        policyLabels = StringUtil.dedupStringsList(policyLabels, strTbl);
        resources    = StringUtil.dedupStringsMapOfPolicyResource(resources, strTbl);
        options      = StringUtil.dedupStringsMapOfObject(options, strTbl);

        if (CollectionUtils.isNotEmpty(additionalResources)) {
            List<Map<String, RangerPolicyResource>> updated = new ArrayList<>(additionalResources.size());

            for (Map<String, RangerPolicyResource> additionalResource : additionalResources) {
                updated.add(StringUtil.dedupStringsMapOfPolicyResource(additionalResource, strTbl));
            }

            additionalResources = updated;
        }

        if (CollectionUtils.isNotEmpty(conditions)) {
            for (RangerPolicyItemCondition condition : conditions) {
                condition.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(policyItems)) {
            for (RangerPolicyItem policyItem : policyItems) {
                policyItem.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(denyPolicyItems)) {
            for (RangerPolicyItem policyItem : denyPolicyItems) {
                policyItem.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(allowExceptions)) {
            for (RangerPolicyItem policyItem : allowExceptions) {
                policyItem.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(denyExceptions)) {
            for (RangerPolicyItem policyItem : denyExceptions) {
                policyItem.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(dataMaskPolicyItems)) {
            for (RangerPolicyItem policyItem : dataMaskPolicyItems) {
                policyItem.dedupStrings(strTbl);
            }
        }

        if (CollectionUtils.isNotEmpty(rowFilterPolicyItems)) {
            for (RangerPolicyItem policyItem : rowFilterPolicyItems) {
                policyItem.dedupStrings(strTbl);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        toString(sb);

        return sb.toString();
    }

    public StringBuilder toString(StringBuilder sb) {
        sb.append("RangerPolicy={");

        super.toString(sb);

        sb.append("service={").append(service).append("} ");
        sb.append("name={").append(name).append("} ");
        sb.append("policyType={").append(policyType).append("} ");
        sb.append("policyPriority={").append(policyPriority).append("} ");
        sb.append("description={").append(description).append("} ");
        sb.append("resourceSignature={").append(resourceSignature).append("} ");
        sb.append("isAuditEnabled={").append(isAuditEnabled).append("} ");
        sb.append("serviceType={").append(serviceType).append("} ");

        sb.append("resources={");
        if (resources != null) {
            for (Map.Entry<String, RangerPolicyResource> e : resources.entrySet()) {
                sb.append(e.getKey()).append("={");
                e.getValue().toString(sb);
                sb.append("} ");
            }
        }
        sb.append("} ");
        sb.append("additionalResources={");
        if (additionalResources != null) {
            for (Map<String, RangerPolicyResource> additionalResource : additionalResources) {
                sb.append("{");
                for (Map.Entry<String, RangerPolicyResource> e : additionalResource.entrySet()) {
                    sb.append(e.getKey()).append("={");
                    e.getValue().toString(sb);
                    sb.append("} ");
                }
                sb.append("} ");
            }
        }
        sb.append("} ");
        sb.append("policyLabels={");
        if (policyLabels != null) {
            for (String policyLabel : policyLabels) {
                if (policyLabel != null) {
                    sb.append(policyLabel).append(" ");
                }
            }
        }
        sb.append("} ");

        sb.append("policyConditions={");
        if (conditions != null) {
            for (RangerPolicyItemCondition condition : conditions) {
                if (condition != null) {
                    condition.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("policyItems={");
        if (policyItems != null) {
            for (RangerPolicyItem policyItem : policyItems) {
                if (policyItem != null) {
                    policyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("denyPolicyItems={");
        if (denyPolicyItems != null) {
            for (RangerPolicyItem policyItem : denyPolicyItems) {
                if (policyItem != null) {
                    policyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("allowExceptions={");
        if (allowExceptions != null) {
            for (RangerPolicyItem policyItem : allowExceptions) {
                if (policyItem != null) {
                    policyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("denyExceptions={");
        if (denyExceptions != null) {
            for (RangerPolicyItem policyItem : denyExceptions) {
                if (policyItem != null) {
                    policyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("dataMaskPolicyItems={");
        if (dataMaskPolicyItems != null) {
            for (RangerDataMaskPolicyItem dataMaskPolicyItem : dataMaskPolicyItems) {
                if (dataMaskPolicyItem != null) {
                    dataMaskPolicyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("rowFilterPolicyItems={");
        if (rowFilterPolicyItems != null) {
            for (RangerRowFilterPolicyItem rowFilterPolicyItem : rowFilterPolicyItems) {
                if (rowFilterPolicyItem != null) {
                    rowFilterPolicyItem.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("options={");
        if (options != null) {
            for (Map.Entry<String, Object> e : options.entrySet()) {
                sb.append(e.getKey()).append("={");
                sb.append(e.getValue().toString());
                sb.append("} ");
            }
        }
        sb.append("} ");

        //sb.append("validitySchedules={").append(validitySchedules).append("} ");
        sb.append("validitySchedules={");
        if (validitySchedules != null) {
            for (RangerValiditySchedule schedule : validitySchedules) {
                if (schedule != null) {
                    sb.append("schedule={").append(schedule).append("}");
                }
            }
        }
        sb.append(", zoneName=").append(zoneName);

        sb.append(", isDenyAllElse={").append(isDenyAllElse).append("} ");

        sb.append("}");

        sb.append("}");

        return sb;
    }

    static class PolicyIdComparator implements Comparator<RangerPolicy>, java.io.Serializable {
        @Override
        public int compare(RangerPolicy me, RangerPolicy other) {
            return Long.compare(me.getId(), other.getId());
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyResource implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private List<String> values;
        private Boolean      isExcludes;
        private Boolean      isRecursive;

        public RangerPolicyResource() {
            this((List<String>) null, null, null);
        }

        public RangerPolicyResource(String value) {
            setValue(value);
            setIsExcludes(null);
            setIsRecursive(null);
        }

        public RangerPolicyResource(String value, Boolean isExcludes, Boolean isRecursive) {
            setValue(value);
            setIsExcludes(isExcludes);
            setIsRecursive(isRecursive);
        }

        public RangerPolicyResource(List<String> values, Boolean isExcludes, Boolean isRecursive) {
            setValues(values);
            setIsExcludes(isExcludes);
            setIsRecursive(isRecursive);
        }

        /**
         * @return the values
         */
        public List<String> getValues() {
            return values;
        }

        /**
         * @param values the values to set
         */
        public void setValues(List<String> values) {
            this.values = nullSafeList(values);
        }

        public boolean addValue(String value) {
            this.values = getUpdatableList(this.values);

            return values.add(value);
        }

        public boolean addValues(Collection<String> values) {
            this.values = getUpdatableList(this.values);

            return this.values.addAll(values);
        }

        public boolean addValues(String[] values) {
            this.values = getUpdatableList(this.values);

            return Collections.addAll(this.values, values);
        }

        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            ArrayList<String> values = new ArrayList<>();

            values.add(value);

            this.values = values;
        }

        /**
         * @return the isExcludes
         */
        public Boolean getIsExcludes() {
            return isExcludes;
        }

        /**
         * @param isExcludes the isExcludes to set
         */
        public void setIsExcludes(Boolean isExcludes) {
            this.isExcludes = isExcludes == null ? Boolean.FALSE : isExcludes;
        }

        /**
         * @return the isRecursive
         */
        public Boolean getIsRecursive() {
            return isRecursive;
        }

        /**
         * @param isRecursive the isRecursive to set
         */
        public void setIsRecursive(Boolean isRecursive) {
            this.isRecursive = isRecursive == null ? Boolean.FALSE : isRecursive;
        }

        public void dedupStrings(Map<String, String> strTbl) {
            values = StringUtil.dedupStringsList(values, strTbl);
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyResource={");
            sb.append("values={");
            if (values != null) {
                for (String value : values) {
                    sb.append(value).append(" ");
                }
            }
            sb.append("} ");
            sb.append("isExcludes={").append(isExcludes).append("} ");
            sb.append("isRecursive={").append(isRecursive).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = 1;

            result = prime * result + ((isExcludes == null) ? 0 : isExcludes.hashCode());
            result = prime * result + ((isRecursive == null) ? 0 : isRecursive.hashCode());
            result = prime * result + ((values == null) ? 0 : values.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerPolicyResource other = (RangerPolicyResource) obj;

            if (isExcludes == null) {
                if (other.isExcludes != null) {
                    return false;
                }
            } else if (!isExcludes.equals(other.isExcludes)) {
                return false;
            }

            if (isRecursive == null) {
                if (other.isRecursive != null) {
                    return false;
                }
            } else if (!isRecursive.equals(other.isRecursive)) {
                return false;
            }

            if (values == null) {
                return other.values == null;
            } else {
                return values.equals(other.values);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyItem implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private List<RangerPolicyItemAccess>    accesses;
        private List<String>                    users;
        private List<String>                    groups;
        private List<String>                    roles;
        private List<RangerPolicyItemCondition> conditions;
        private Boolean                         delegateAdmin;

        public RangerPolicyItem() {
            this(null, null, null, null, null, null);
        }

        public RangerPolicyItem(RangerPolicyItem other) {
            this(other.accesses, other.users, other.groups, other.roles, other.conditions, other.delegateAdmin);
        }

        public RangerPolicyItem(List<RangerPolicyItemAccess> accessTypes, List<String> users, List<String> groups, List<String> roles, List<RangerPolicyItemCondition> conditions, Boolean delegateAdmin) {
            setAccesses(accessTypes);
            setUsers(users);
            setGroups(groups);
            setRoles(roles);
            setConditions(conditions);
            setDelegateAdmin(delegateAdmin);
        }

        /**
         * @return the accesses
         */
        public List<RangerPolicyItemAccess> getAccesses() {
            return accesses;
        }

        /**
         * @param accesses the accesses to set
         */
        public void setAccesses(List<RangerPolicyItemAccess> accesses) {
            this.accesses = nullSafeList(accesses);
        }

        public boolean addAccess(RangerPolicyItemAccess access) {
            this.accesses = getUpdatableList(this.accesses);

            return accesses.add(access);
        }

        public boolean addAccesses(Collection<RangerPolicyItemAccess> accesses) {
            this.accesses = getUpdatableList(this.accesses);

            return this.accesses.addAll(accesses);
        }

        /**
         * @return the users
         */
        public List<String> getUsers() {
            return users;
        }

        /**
         * @param users the users to set
         */
        public void setUsers(List<String> users) {
            this.users = nullSafeList(users);
        }

        public boolean addUser(String user) {
            this.users = getUpdatableList(this.users);

            return users.add(user);
        }

        public boolean addUsers(Collection<String> users) {
            this.users = getUpdatableList(this.users);

            return this.users.addAll(users);
        }

        public boolean removeUser(String user) {
            return CollectionUtils.isNotEmpty(users) && users.remove(user);
        }

        /**
         * @return the groups
         */
        public List<String> getGroups() {
            return groups;
        }

        /**
         * @param groups the groups to set
         */
        public void setGroups(List<String> groups) {
            this.groups = nullSafeList(groups);
        }

        public boolean addGroup(String group) {
            this.groups = getUpdatableList(this.groups);

            return groups.add(group);
        }

        public boolean addGroups(Collection<String> groups) {
            this.groups = getUpdatableList(this.groups);

            return this.groups.addAll(groups);
        }

        public boolean removeGroup(String group) {
            return CollectionUtils.isNotEmpty(groups) && groups.remove(group);
        }

        /**
         * @return the roles
         */
        public List<String> getRoles() {
            return roles;
        }

        /**
         * @param roles the roles to set
         */
        public void setRoles(List<String> roles) {
            this.roles = nullSafeList(roles);
        }

        public boolean addRole(String role) {
            this.roles = getUpdatableList(this.roles);

            return roles.add(role);
        }

        public boolean addRoles(Collection<String> roles) {
            this.roles = getUpdatableList(this.roles);

            return this.roles.addAll(roles);
        }

        public boolean removeRole(String role) {
            return CollectionUtils.isNotEmpty(roles) && roles.remove(role);
        }

        /**
         * @return the conditions
         */
        public List<RangerPolicyItemCondition> getConditions() {
            return conditions;
        }

        /**
         * @param conditions the conditions to set
         */
        public void setConditions(List<RangerPolicyItemCondition> conditions) {
            this.conditions = nullSafeList(conditions);
        }

        public boolean addCondition(RangerPolicyItemCondition condition) {
            this.conditions = getUpdatableList(this.conditions);

            return conditions.add(condition);
        }

        public boolean addConditions(Collection<RangerPolicyItemCondition> conditions) {
            this.conditions = getUpdatableList(this.conditions);

            return this.conditions.addAll(conditions);
        }

        /**
         * @return the delegateAdmin
         */
        public Boolean getDelegateAdmin() {
            return delegateAdmin;
        }

        /**
         * @param delegateAdmin the delegateAdmin to set
         */
        public void setDelegateAdmin(Boolean delegateAdmin) {
            this.delegateAdmin = delegateAdmin == null ? Boolean.FALSE : delegateAdmin;
        }

        public void dedupStrings(Map<String, String> strTbl) {
            if (accesses != null) {
                for (RangerPolicyItemAccess access : accesses) {
                    access.dedupStrings(strTbl);
                }
            }

            users  = StringUtil.dedupStringsList(users, strTbl);
            groups = StringUtil.dedupStringsList(groups, strTbl);
            roles  = StringUtil.dedupStringsList(roles, strTbl);

            if (conditions != null) {
                for (RangerPolicyItemCondition condition : conditions) {
                    condition.dedupStrings(strTbl);
                }
            }
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyItem={");

            sb.append("accessTypes={");
            if (accesses != null) {
                for (RangerPolicyItemAccess access : accesses) {
                    if (access != null) {
                        access.toString(sb);
                    }
                }
            }
            sb.append("} ");

            sb.append("users={");
            if (users != null) {
                for (String user : users) {
                    if (user != null) {
                        sb.append(user).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("groups={");
            if (groups != null) {
                for (String group : groups) {
                    if (group != null) {
                        sb.append(group).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("roles={");
            if (roles != null) {
                for (String role : roles) {
                    if (role != null) {
                        sb.append(role).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("conditions={");
            if (conditions != null) {
                for (RangerPolicyItemCondition condition : conditions) {
                    if (condition != null) {
                        condition.toString(sb);
                    }
                }
            }
            sb.append("} ");

            sb.append("delegateAdmin={").append(delegateAdmin).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = 1;

            result = prime * result + ((accesses == null) ? 0 : accesses.hashCode());
            result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
            result = prime * result + ((delegateAdmin == null) ? 0 : delegateAdmin.hashCode());
            result = prime * result + ((roles == null) ? 0 : roles.hashCode());
            result = prime * result + ((groups == null) ? 0 : groups.hashCode());
            result = prime * result + ((users == null) ? 0 : users.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerPolicyItem other = (RangerPolicyItem) obj;

            if (accesses == null) {
                if (other.accesses != null) {
                    return false;
                }
            } else if (!accesses.equals(other.accesses)) {
                return false;
            }

            if (conditions == null) {
                if (other.conditions != null) {
                    return false;
                }
            } else if (!conditions.equals(other.conditions)) {
                return false;
            }

            if (delegateAdmin == null) {
                if (other.delegateAdmin != null) {
                    return false;
                }
            } else if (!delegateAdmin.equals(other.delegateAdmin)) {
                return false;
            }

            if (roles == null) {
                if (other.roles != null) {
                    return false;
                }
            } else if (!roles.equals(other.roles)) {
                return false;
            }

            if (groups == null) {
                if (other.groups != null) {
                    return false;
                }
            } else if (!groups.equals(other.groups)) {
                return false;
            }

            if (users == null) {
                return other.users == null;
            } else {
                return users.equals(other.users);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerDataMaskPolicyItem extends RangerPolicyItem implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private RangerPolicyItemDataMaskInfo dataMaskInfo;

        public RangerDataMaskPolicyItem() {
            this(null, null, null, null, null, null, null);
        }

        public RangerDataMaskPolicyItem(List<RangerPolicyItemAccess> accesses, RangerPolicyItemDataMaskInfo dataMaskDetail, List<String> users, List<String> groups, List<String> roles, List<RangerPolicyItemCondition> conditions, Boolean delegateAdmin) {
            super(accesses, users, groups, roles, conditions, delegateAdmin);

            setDataMaskInfo(dataMaskDetail);
        }

        /**
         * @return the dataMaskInfo
         */
        public RangerPolicyItemDataMaskInfo getDataMaskInfo() {
            return dataMaskInfo;
        }

        /**
         * @param dataMaskInfo the dataMaskInfo to set
         */
        public void setDataMaskInfo(RangerPolicyItemDataMaskInfo dataMaskInfo) {
            this.dataMaskInfo = dataMaskInfo == null ? new RangerPolicyItemDataMaskInfo() : dataMaskInfo;
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerDataMaskPolicyItem={");

            super.toString(sb);

            sb.append("dataMaskInfo={");
            if (dataMaskInfo != null) {
                dataMaskInfo.toString(sb);
            }
            sb.append("} ");

            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = super.hashCode();

            result = prime * result + ((dataMaskInfo == null) ? 0 : dataMaskInfo.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerDataMaskPolicyItem other = (RangerDataMaskPolicyItem) obj;

            if (dataMaskInfo == null) {
                return other.dataMaskInfo == null;
            } else {
                return dataMaskInfo.equals(other.dataMaskInfo);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerRowFilterPolicyItem extends RangerPolicyItem implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private RangerPolicyItemRowFilterInfo rowFilterInfo;

        public RangerRowFilterPolicyItem() {
            this(null, null, null, null, null, null, null);
        }

        public RangerRowFilterPolicyItem(RangerPolicyItemRowFilterInfo rowFilterInfo, List<RangerPolicyItemAccess> accesses, List<String> users, List<String> groups, List<String> roles, List<RangerPolicyItemCondition> conditions, Boolean delegateAdmin) {
            super(accesses, users, groups, roles, conditions, delegateAdmin);

            setRowFilterInfo(rowFilterInfo);
        }

        /**
         * @return the rowFilterInfo
         */
        public RangerPolicyItemRowFilterInfo getRowFilterInfo() {
            return rowFilterInfo;
        }

        /**
         * @param rowFilterInfo the rowFilterInfo to set
         */
        public void setRowFilterInfo(RangerPolicyItemRowFilterInfo rowFilterInfo) {
            this.rowFilterInfo = rowFilterInfo == null ? new RangerPolicyItemRowFilterInfo() : rowFilterInfo;
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerRowFilterPolicyItem={");

            super.toString(sb);

            sb.append("rowFilterInfo={");
            if (rowFilterInfo != null) {
                rowFilterInfo.toString(sb);
            }
            sb.append("} ");

            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = super.hashCode();

            result = prime * result + ((rowFilterInfo == null) ? 0 : rowFilterInfo.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerRowFilterPolicyItem other = (RangerRowFilterPolicyItem) obj;

            if (rowFilterInfo == null) {
                return other.rowFilterInfo == null;
            } else {
                return rowFilterInfo.equals(other.rowFilterInfo);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyItemAccess implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private String  type;
        private Boolean isAllowed;

        public RangerPolicyItemAccess() {
            this(null, null);
        }

        public RangerPolicyItemAccess(String type) {
            this(type, null);
        }

        public RangerPolicyItemAccess(String type, Boolean isAllowed) {
            setType(type);
            setIsAllowed(isAllowed);
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type == null ? "" : type;
        }

        /**
         * @return the isAllowed
         */
        public Boolean getIsAllowed() {
            return isAllowed;
        }

        /**
         * @param isAllowed the isAllowed to set
         */
        public void setIsAllowed(Boolean isAllowed) {
            this.isAllowed = isAllowed == null ? Boolean.TRUE : isAllowed;
        }

        public void dedupStrings(Map<String, String> strTbl) {
            type = StringUtil.dedupString(type, strTbl);
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyItemAccess={");
            sb.append("type={").append(type).append("} ");
            sb.append("isAllowed={").append(isAllowed).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = 1;

            result = prime * result + ((isAllowed == null) ? 0 : isAllowed.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RangerPolicyItemAccess other = (RangerPolicyItemAccess) obj;

            if (isAllowed == null) {
                if (other.isAllowed != null) {
                    return false;
                }
            } else if (!isAllowed.equals(other.isAllowed)) {
                return false;
            }

            if (type == null) {
                return other.type == null;
            } else {
                return type.equals(other.type);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyItemCondition implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private String       type;
        private List<String> values;

        public RangerPolicyItemCondition() {
            this(null, null);
        }

        public RangerPolicyItemCondition(String type, List<String> values) {
            setType(type);
            setValues(values);
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the value
         */
        public List<String> getValues() {
            return values;
        }

        /**
         * @param values the value to set
         */
        public void setValues(List<String> values) {
            this.values = nullSafeList(values);
        }

        public boolean addValue(String value) {
            if (CollectionUtils.isEmpty(values)) {
                values = new ArrayList<>();
            }

            return values.add(value);
        }

        public void dedupStrings(Map<String, String> strTbl) {
            type   = StringUtil.dedupString(type, strTbl);
            values = StringUtil.dedupStringsList(values, strTbl);
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyCondition={");
            sb.append("type={").append(type).append("} ");
            sb.append("values={");
            if (values != null) {
                for (String value : values) {
                    sb.append(value).append(" ");
                }
            }
            sb.append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = 1;

            result = prime * result + ((type == null) ? 0 : type.hashCode());
            result = prime * result + ((values == null) ? 0 : values.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerPolicyItemCondition other = (RangerPolicyItemCondition) obj;

            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else if (!type.equals(other.type)) {
                return false;
            }

            if (values == null) {
                return other.values == null;
            } else {
                return values.equals(other.values);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyItemDataMaskInfo implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private String dataMaskType;
        private String conditionExpr;
        private String valueExpr;

        public RangerPolicyItemDataMaskInfo() {}

        public RangerPolicyItemDataMaskInfo(String dataMaskType, String conditionExpr, String valueExpr) {
            setDataMaskType(dataMaskType);
            setConditionExpr(conditionExpr);
            setValueExpr(valueExpr);
        }

        public RangerPolicyItemDataMaskInfo(RangerPolicyItemDataMaskInfo that) {
            this.dataMaskType  = that.dataMaskType;
            this.conditionExpr = that.conditionExpr;
            this.valueExpr     = that.valueExpr;
        }

        public String getDataMaskType() {
            return dataMaskType;
        }

        public void setDataMaskType(String dataMaskType) {
            this.dataMaskType = dataMaskType;
        }

        public String getConditionExpr() {
            return conditionExpr;
        }

        public void setConditionExpr(String conditionExpr) {
            this.conditionExpr = conditionExpr;
        }

        public String getValueExpr() {
            return valueExpr;
        }

        public void setValueExpr(String valueExpr) {
            this.valueExpr = valueExpr;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = super.hashCode();

            result = prime * result + ((dataMaskType == null) ? 0 : dataMaskType.hashCode());
            result = prime * result + ((conditionExpr == null) ? 0 : conditionExpr.hashCode());
            result = prime * result + ((valueExpr == null) ? 0 : valueExpr.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RangerPolicyItemDataMaskInfo other = (RangerPolicyItemDataMaskInfo) obj;
            if (dataMaskType == null) {
                if (other.dataMaskType != null) {
                    return false;
                }
            } else if (!dataMaskType.equals(other.dataMaskType)) {
                return false;
            }
            if (conditionExpr == null) {
                if (other.conditionExpr != null) {
                    return false;
                }
            } else if (!conditionExpr.equals(other.conditionExpr)) {
                return false;
            }

            if (valueExpr == null) {
                return other.valueExpr == null;
            } else {
                return valueExpr.equals(other.valueExpr);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyItemDataMaskInfo={");

            sb.append("dataMaskType={").append(dataMaskType).append("} ");
            sb.append("conditionExpr={").append(conditionExpr).append("} ");
            sb.append("valueExpr={").append(valueExpr).append("} ");

            sb.append("}");

            return sb;
        }
    }

    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RangerPolicyItemRowFilterInfo implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private String filterExpr;

        public RangerPolicyItemRowFilterInfo() {}

        public RangerPolicyItemRowFilterInfo(String filterExpr) {
            setFilterExpr(filterExpr);
        }

        public RangerPolicyItemRowFilterInfo(RangerPolicyItemRowFilterInfo that) {
            this.filterExpr = that.filterExpr;
        }

        public String getFilterExpr() {
            return filterExpr;
        }

        public void setFilterExpr(String filterExpr) {
            this.filterExpr = filterExpr;
        }

        @Override
        public int hashCode() {
            final int prime  = 31;
            int       result = super.hashCode();

            result = prime * result + ((filterExpr == null) ? 0 : filterExpr.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RangerPolicyItemRowFilterInfo other = (RangerPolicyItemRowFilterInfo) obj;

            if (filterExpr == null) {
                return other.filterExpr == null;
            } else {
                return filterExpr.equals(other.filterExpr);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("RangerPolicyItemRowFilterInfo={");

            sb.append("filterExpr={").append(filterExpr).append("} ");

            sb.append("}");

            return sb;
        }
    }
}
