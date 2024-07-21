# Simplified Scientific Project Management System

This project is a simplified information system for managing scientific projects, developed in Java. The system provides a framework for grant management, project registration, and financial distribution based on predefined rules.

## Overview

The system is structured around several key components:

- **Agency** (`AgencyInterface.java`)
- **Grant** (`GrantInterface.java`)
- **Project** (`ProjectInterface.java`)
- **Organization** (`OrganizationInterface.java`)
- **Person** (`PersonInterface.java`)

## Core Functionality

### Grant Management

- Agencies can publish calls for grants using the `callForProjects` method. Each grant has a budget and a status that changes throughout the grant cycle.
- The grant can be in one of the following states: `STARTED`, `EVALUATING`, or `CLOSED`.

### Project Registration

- Projects must be registered during an active grant call. The project must specify the submitting organization and the project team, all members of which must be employees of the organization.
- Each project is required to start in the same year as the grant call and cannot have an empty team.

### Grant Distribution

- Once the grant call ends and the evaluation phase begins, projects are assessed. The available budget is divided among qualifying projects based on their registration order.
- If there is only one qualifying project, it receives the entire grant budget.
- The distribution is performed evenly among half of the qualifying projects, rounded down. If there are an odd number of qualifying projects, the result is rounded down to the nearest whole number.

### Financial Allocation

- The allocated budget for a project is evenly distributed over the project's duration. Projects may also receive additional funding from their organization, if applicable.
- For universities, projects are financed solely by the grant. For companies, additional funding from their own resources is possible, up to a predefined limit.

### Capacity Check

- Before a project can be evaluated, the system checks whether the project participants exceed the maximum allowed employment capacity (`MAX_EMPLOYMENT_PER_AGENCY`) for the agency.
- The check includes the sum of the participant’s employment across all projects in the agency. Projects with participants exceeding this limit are automatically rejected.

## Rules for Grant Distribution

### Grant Call and Project Registration

- A grant is considered for distribution only after the `evaluateProjects` method is called.
- Projects must be registered within the active grant period and must meet all registration requirements.

### Evaluation Phase

- Projects are evaluated based on their registration order. If the sum of the employment of any participant exceeds the allowed limit, the project is disqualified from receiving funds.
- Qualified projects receive a share of the grant budget. The total budget is split among the top half of the projects, with earlier submissions receiving priority.

### Closing a Grant

- The grant is officially closed by calling the `closeGrant` method. After closure, each project is notified of the assigned budget, which is then distributed annually over the project’s duration.

### Additional Funding

- Companies can augment the grant funding with their own resources. They can allocate additional funds equal to or less than the grant amount, depending on their available budget.

## Usage

The system does not include a graphical user interface or console controls. Instead, functionality is tested through unit tests provided in the template. The project's core logic must be implemented according to the predefined interfaces and constraints.

In the future, it may be possible to integrate a real database to manage actual people and organizations, enhancing the system's functionality and allowing for real-time data interactions.
