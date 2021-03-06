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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openengsb.domains.report.model.Report;
import org.openengsb.domains.report.model.ReportPart;
import org.openengsb.domains.report.model.SimpleReportPart;

public abstract class ReportStoreTest {

    private ReportStore reportStore;

    public abstract ReportStore getReportStore();

    public abstract void clearStore() throws Exception;

    @Before
    public void setUp() {
        this.reportStore = getReportStore();
        reportStore.createCategory("42");
        reportStore.storeReport("42", new Report("test"));
        reportStore.storeReport("42", new Report("test1"));
        reportStore.storeReport("42", new Report("test2"));
    }

    @After
    public void tearDown() throws Exception {
        clearStore();
    }

    @Test
    public void createCategory_shouldWork() {
        assertThat(reportStore.getAllCategories().contains("testCategory"), is(false));
        reportStore.createCategory("testCategory");
        assertThat(reportStore.getAllCategories().contains("testCategory"), is(true));
    }

    @Test
    public void createCategoryTwice_shouldPerformNoOpOnSecondCreation() {
        assertThat(reportStore.getAllCategories().contains("testCategory"), is(false));
        reportStore.createCategory("testCategory");
        List<String> categories = reportStore.getAllCategories();
        reportStore.createCategory("testCategory");
        assertThat(reportStore.getAllCategories(), is(categories));
    }

    @Test
    public void removeCategory_shouldWork() {
        assertThat(reportStore.getAllCategories().contains("42"), is(true));
        this.reportStore.removeCategory("42");
        assertThat(reportStore.getAllCategories().contains("42"), is(false));
    }

    @Test
    public void removeCategoryTwice_shouldPerformNoOpOnSecondRemoval() {
        this.reportStore.removeCategory("42");
        assertThat(reportStore.getAllCategories().contains("42"), is(false));
        this.reportStore.removeCategory("42");
    }

    @Test
    public void getAllCategoriesWithoutCategories_shouldReturnEmptyList() {
        reportStore.removeCategory("42");
        assertThat(reportStore.getAllCategories().isEmpty(), is(true));
    }

    @Test
    public void getAllCategoriesWithCategories_shouldReturnAllCategories() {
        this.reportStore.createCategory("testCategory");
        this.reportStore.createCategory("testCategory2");
        this.reportStore.createCategory("testCategory3");
        List<String> categories = reportStore.getAllCategories();
        assertThat(categories.size(), is(4));
        assertThat(categories.contains("42"), is(true));
        assertThat(categories.contains("testCategory"), is(true));
        assertThat(categories.contains("testCategory2"), is(true));
        assertThat(categories.contains("testCategory3"), is(true));
    }

    @Test
    public void storeReportNewCategory_shouldCreateCategoryAndStoreReport() {
        Report report = new Report("testReport");
        this.reportStore.storeReport("testCategory", report);
        assertThat(reportStore.getAllReports("testCategory").get(0).getName(), is("testReport"));
    }

    @Test
    public void storeReportExistingCategory_shouldStoreReport() {
        this.reportStore.createCategory("testCategory");
        Report report = new Report("testReport");
        this.reportStore.storeReport("testCategory", report);
        assertThat(reportStore.getAllReports("testCategory").get(0).getName(), is("testReport"));
    }

    @Test
    public void storeReportTwice_shouldOverwriteReport() {
        Report report = new Report("testReport");
        this.reportStore.storeReport("testCategory", report);
        report = new Report("testReport");
        report.addPart(new SimpleReportPart("somePart", "text/plain", "foo".getBytes()));
        this.reportStore.storeReport("testCategory", report);
        List<ReportPart> parts = reportStore.getAllReports("testCategory").get(0).getParts();
        assertThat(parts.size(), is(1));
        assertThat(parts.get(0).getPartName(), is("somePart"));
        assertThat(parts.get(0).getContentType(), is("text/plain"));
        assertThat(parts.get(0).getContent(), is("foo".getBytes()));
    }

    @Test
    public void removeReport_shouldWork() {
        Report report = new Report("testReport");
        this.reportStore.storeReport("testCategory", report);
        this.reportStore.removeReport("testCategory", report);
        assertThat(reportStore.getAllReports("testCategory").isEmpty(), is(true));

    }

    @Test
    public void removeReportTwice_shouldPerformNoOPOnSecondRemoval() {
        Report report = new Report("testReport");
        this.reportStore.storeReport("testCategory", report);
        this.reportStore.removeReport("testCategory", report);
        assertThat(reportStore.getAllReports("testCategory").isEmpty(), is(true));
        this.reportStore.removeReport("testCategory", report);
    }

    @Test
    public void getAllReportsCategoryDoesNotExist_shouldReturnEmptyList() {
        List<Report> allReports = this.reportStore.getAllReports("foo");
        assertThat(allReports.isEmpty(), is(true));
    }

    @Test
    public void getAllReportsEmptyCategory_shouldReturnEmptyList() {
        reportStore.createCategory("foo");
        List<Report> allReports = reportStore.getAllReports("foo");
        assertThat(allReports.isEmpty(), is(true));
    }

    @Test
    public void getAllReports_shouldReturnAllReports() {
        this.reportStore.storeReport("testCategory", new Report("testReport"));
        this.reportStore.storeReport("testCategory", new Report("testReport1"));
        this.reportStore.storeReport("testCategory", new Report("testReport2"));
        List<Report> allReports = reportStore.getAllReports("testCategory");
        assertThat(allReports.size(), is(3));
    }

}
