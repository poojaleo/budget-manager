# Track Budget Service Design

## 1. Problem Statement

Track Budget is a service to track all your expenses at one place. The purpose of the website is to create a simple
efficient product to manage your expenses.

User’s can add, update, delete an expense and also create categories to manage the expenses.
This allows users to gain a better understanding of where their money is going. Track Budget also alerts users
when the user surpasses 75% budget threshold.

## 2. Use Cases

U1. Prospective Users can create an account.

U2. As an TrackBudget customer, I want the session to be secured as the information is highly sensitive.

U3. As an TrackBudget customer, I want to set my own total budget for the month.

U3. As an TrackBudget customer, I want to create a new expense with description,
expense date and merchant/vendor used

U5. As an TrackBudget customer, I want to categorize my transactions into a particular category.

U6. As an TrackBudget customer, I want to create multiple categories and
set monthly budget for each category.

U7. As an TrackBudget customer, I should have the ability to delete the expenses.

U8. As an TrackBudget customer, I can delete a category if not in use.

U7. As an TrackBudget customer, I want to be notified when I exceed my budget.


## 3. Project Scope

### 3.1. In Scope

* Creating, updating user account.
* Ability to add monthly income and set monthly budget.
* Creating a new category or updating an existing category and set budget for each category.
* Creating, updating and deleting an expense.
* Pictorial representation of expenses sorted by category.
* Users are notified when user exceeds 75% of budget and then when user exceeds 100% of budget.

### 3.2. Out of Scope

* Ability to update password as there is currently no email verification.
* Users will not be able to upload receipts of the expense.
* Integration with a bank service provider
* The ability to pay a bill directly from the website
* Add multiple income statements. User currently will have the ability to add a fixed average monthly income.
* Unable to export transactions to pdf/excel
* Unable to add recurring transactions automatically.

## 4. Architecture

![Architecture](/src/main/resources/classdiagrams/TrackBudgetArchitecture.png)
*Figure 1: Diagram showing the architecture of Track Budget Application. A user using the Front End React Website makes an API call which connects 
to the AWS load balancer. This forwards the requests to ECS, which connects to a persistent data store for fulfilling the 
request and sends the response back to the client.*
