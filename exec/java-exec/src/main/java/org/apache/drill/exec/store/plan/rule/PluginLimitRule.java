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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.store.plan.rule;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.rel.RelNode;
import org.apache.drill.exec.planner.common.DrillLimitRelBase;
import org.apache.drill.exec.store.plan.PluginImplementor;
import org.apache.drill.exec.store.plan.rel.PluginLimitRel;

public class PluginLimitRule extends PluginConverterRule {

  public PluginLimitRule(RelTrait in, Convention out, PluginImplementor pluginImplementor) {
    super(DrillLimitRelBase.class, in, out, "PluginLimitRule", pluginImplementor);
  }

  @Override
  public RelNode convert(RelNode rel) {
    DrillLimitRelBase sort = (DrillLimitRelBase) rel;
    RelNode input = convert(sort.getInput(), sort.getInput().getTraitSet().replace(getOutConvention()).simplify());
    return new PluginLimitRel(
        rel.getCluster(),
        sort.getTraitSet().replace(getOutConvention()),
        input,
        sort.getOffset(),
        sort.getFetch());
  }
}
