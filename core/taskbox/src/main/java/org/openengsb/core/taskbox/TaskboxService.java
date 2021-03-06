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

package org.openengsb.core.taskbox;

/**
 * The Taskbox is a service which can be used when human interaction is required, e.g. by help desk applications. This
 * core part core is responsible for storing tasks, throwing events and starting workflows. Therefore it provides
 * methods which can be called by workflows e.g. assigning a task to different user-roles (such as case worker or
 * developer) or setting a task status. Another job is to choose the right wicket panel from the UI project to display
 * the right information in a certain situation.
 * 
 * The component uses the persistence compontent to store tasks and the workflow component to take control of specific
 * workflows.
 */
public interface TaskboxService {
    /**
     * Gets the message set by a workflow
     * 
     * @throws TaskboxException when the message is not set
     */
    String getWorkflowMessage() throws TaskboxException;

    /**
     * Starts a test workflow
     * 
     * @throws TaskboxException when the test workflow could not be started
     */
    void startWorkflow() throws TaskboxException;

    /**
     * Used by a workflow to set a message
     */
    void setWorkflowMessage(String message);
}
