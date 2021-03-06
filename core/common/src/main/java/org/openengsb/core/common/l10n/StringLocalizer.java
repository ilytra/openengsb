/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.core.common.l10n;

import java.io.Serializable;
import java.util.Locale;

public interface StringLocalizer extends Serializable {

    /**
     * Localize a string identified by the given {@code key} to the given {@code locale}.
     */
    String getString(String key, Locale locale, String... parameters);

    /**
     * Returs a {@code LocalizableString} where the actual localization is done in a later step by calling
     * {@link org.openengsb.core.common.l10n.LocalizableString#getString(Locale) }.
     */
    LocalizableString getString(String key, String... parameters);
}
