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

package org.openengsb.domains.report.common;

import java.util.List;

import org.openengsb.domains.report.ReportDomain;
import org.openengsb.domains.report.model.Report;

public abstract class AbstractReportDomain implements ReportDomain {

    private ReportStore store;

    @Override
    public List<Report> getAllReports(String category) {
        return store.getAllReports(category);
    }

    @Override
    public void storeReport(String category, Report report) {
        store.storeReport(category, report);
    }

    @Override
    public void removeReport(String category, Report report) {
        store.removeReport(category, report);
    }

    @Override
    public List<String> getAllCategories() {
        return store.getAllCategories();
    }

    @Override
    public void removeCategory(String category) {
        store.removeCategory(category);
    }

    @Override
    public void createCategory(String category) {
        store.createCategory(category);
    }

    public void setStore(ReportStore store) {
        this.store = store;
    }

    public ReportStore getStore() {
        return store;
    }

}
