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

package org.openengsb.integrationtest.htmlunit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.integrationtest.util.AbstractExamTestHelper;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@RunWith(JUnit4TestRunner.class)
public class BaseUiInfrastructureIT extends AbstractExamTestHelper {

    @Test
    public void testIfAllMainNavigationLinksWork() throws Exception {
        final WebClient webClient = new WebClient();
        String loginPageEntryUrl =
            "http://localhost:8090/openengsb/?wicket:bookmarkablePage=:org.openengsb.ui.web.LoginPage";
        final HtmlPage page = webClient.getPage(loginPageEntryUrl);
        HtmlForm form = page.getForms().get(0);
        HtmlSubmitInput loginButton = form.getInputByValue("Login");
        form.getInputByName("username").setValueAttribute("user");
        form.getInputByName("password").setValueAttribute("password");
        HtmlPage indexPage = loginButton.click();
        assertTrue(indexPage.asText().contains("This page represents"));
        HtmlPage testClient = indexPage.getAnchorByText("Test Client").click();
        assertTrue(testClient.asText().contains("Current Project"));
        HtmlPage sendEventpage = testClient.getAnchorByText("Send Event Page").click();
        assertTrue(sendEventpage.asText().contains("Current Project"));
        HtmlPage contextPage = testClient.getAnchorByText("Context").click();
        assertTrue(contextPage.asText().contains("Current Project"));
        HtmlPage servicePage = testClient.getAnchorByText("Services").click();
        assertTrue(servicePage.asText().contains("Services with state = Connecting"));
        webClient.closeAllWindows();
    }

}
