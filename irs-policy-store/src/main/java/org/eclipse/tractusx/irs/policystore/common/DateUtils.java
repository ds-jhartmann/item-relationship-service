/********************************************************************************
 * Copyright (c) 2022,2024 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
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
package org.eclipse.tractusx.irs.policystore.common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Date utilities.
 */
@Slf4j
public final class DateUtils {

    public static final String SIMPLE_DATE_WITHOUT_TIME = "yyyy-MM-dd";

    private DateUtils() {
        // private constructor (utility  class)
    }

    public static boolean isDateBefore(final OffsetDateTime dateTime, final String referenceDateString) {
        if (isDateWithoutTime(referenceDateString)) {
            return dateTime.isBefore(toOffsetDateTimeAtStartOfDay(referenceDateString));
        } else {
            return dateTime.isBefore(OffsetDateTime.parse(referenceDateString));
        }
    }

    public static boolean isDateAfter(final OffsetDateTime dateTime, final String referenceDateString) {
        if (StringUtils.isBlank(referenceDateString)) {
            throw new IllegalArgumentException("Invalid date: must not be blank!");
        }
        if (isDateWithoutTime(referenceDateString)) {
            return dateTime.isAfter(toOffsetDateTimeAtEndOfDay(referenceDateString));
        } else {
            return dateTime.isAfter(OffsetDateTime.parse(referenceDateString));
        }
    }

    public static OffsetDateTime toOffsetDateTimeAtStartOfDay(final String dateString) {
        return parseDate(dateString).atStartOfDay().atOffset(ZoneOffset.UTC);
    }

    public static OffsetDateTime toOffsetDateTimeAtEndOfDay(final String dateString) {
        return parseDate(dateString).atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC);
    }

    private static LocalDate parseDate(final String dateString) {
        if (StringUtils.isBlank(dateString)) {
            throw new IllegalArgumentException("Invalid date format (must not be blank)");
        }
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format (please refer to the documentation)", e);
        }
    }

    @SuppressWarnings("PMD.PreserveStackTrace") // this is intended here as we try to parse with different formats
    public static boolean isDateWithoutTime(final String referenceDateString) {
        try {
            DateTimeFormatter.ofPattern(SIMPLE_DATE_WITHOUT_TIME).parse(referenceDateString);
            return true;
        } catch (DateTimeParseException e) {
            // ignore, trying next format below
            log.trace(e.getMessage(), e);
        }

        try {
            OffsetDateTime.parse(referenceDateString, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (DateTimeParseException e) {
            // ignore, trying next format below
            log.trace(e.getMessage(), e);
        }

        try {
            OffsetDateTime.parse(referenceDateString, DateTimeFormatter.ISO_DATE_TIME);
            return false;
        } catch (DateTimeParseException e) {
            log.trace(e.getMessage(), e);
            throw new IllegalArgumentException("Invalid date format: " + referenceDateString, e);
        }
    }
}
