/********************************************************************************
 * Copyright (c) 2022,2024
 *       2022: ZF Friedrichshafen AG
 *       2022: ISTOS GmbH
 *       2022,2024: Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *       2022,2023: BOSCH AG
 * Copyright (c) 2021,2024 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
package org.eclipse.tractusx.irs.semanticshub;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * @param models list of semantic models
 * @param lastUpdated timestamp of latest update
 */
@Builder
@Schema(example = AspectModels.EXAMPLE)
public record AspectModels(List<AspectModel> models, String lastUpdated) {
    public static final String EXAMPLE = "{\"lastUpdated\"=\"2023-02-13T08:18:11.990659500Z\", \"models\"=[{\"name\"=\"SingleLevelBomAsBuilt\", \"status\"=\"RELEASED\", \"type\"=\"BAMM\", \"urn\"=\"urn:bamm:io.catenax.single_level_bom_as_built:1.0.0#SingleLevelBomAsBuilt\", \"version\"=\"1.0.0\"},\n"
            + "    {\"name\"=\"SerialPart\", \"status\"=\"RELEASED\", \"type\"=\"BAMM\", \"urn\"=\"urn:bamm:io.catenax.serial_part:1.0.0#SerialPart\", \"version\"=\"1.0.0\"}]}";
}
