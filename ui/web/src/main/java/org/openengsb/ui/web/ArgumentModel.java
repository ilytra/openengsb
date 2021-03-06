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

package org.openengsb.ui.web;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class ArgumentModel implements Serializable {
    private int index;
    private Class<?> type;
    private Object value;
    private boolean isBean;

    public ArgumentModel() {
    }

    public ArgumentModel(int index, Class<?> type, String value) {
        this.index = index;
        this.value = value;
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getType() {
        return this.type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return index + ": " + value;
    }

    @SuppressWarnings("unchecked")
    public Object toValue() {
        if (!isBean) {
            return getValue();
        } else {
            return MethodUtil.buildBean(type, (Map<String, String>) value);
        }
    }

    public void setBean(boolean isBean) {
        this.isBean = isBean;
    }

    public boolean isBean() {
        return this.isBean;
    }
}
