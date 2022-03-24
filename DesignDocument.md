# Expense Tracker Service Design

## 1. Problem Statement

Expense Tracker is a service to track all your expenses at one place. The purpose of the website is to manage the 
daily / monthly spending efficiently.

It gives visibility into spending habits over various time periods. User’s can
add an expense, create categories, and add a category to any given
expense. This allows them to gain a better understanding of where their
money is going.

## 2. Use Cases

U1. Prospective Users can create an account.

U2. Existing ExpenseTracker customer can update the account except username. 

U3. As an ExpenseTracker customer, I want to create a new expense with description, 
expense date and merchant/vendor used

U4. As an ExpenseTracker customer, I want to track and view my expenses at daily, 
weekly, monthly and yearly level.

U5. As an ExpenseTracker customer, I want to categorize my transactions into a particular category.

U6. As an ExpenseTracker customer, I want to create multiple categories and 
set monthly budget for each category.

U7. As an ExpenseTracker customer, I can delete an expense.

U8. As an ExpenseTracker customer, I can delete a category if not in use. 

U7. As an ExpenseTracker customer, I want to be notified when I exceed my budget. 

U8. As an ExpenseTracker customer, I want to filter my expenses based on either category, time period or 
expense amount.

## 3. Project Scope

### 3.1. In Scope

* Creating, updating user account.
* Ability to add monthly income and set monthly budget. 
* Creating a new category or updating an existing category and set budget for each category.
* Creating, updating and deleting an expense. 
* Pictorial representation of expenses filtered by daily, monthly or yearly. 
* Graph visualization of spending month over month. 
* To get notification, when user exceeds the budget.

### 3.2. Out of Scope

* Users will not be able to upload receipts of the expense.
* Integration with a bank service provider
* The ability to pay a bill directly from the website
* Add multiple income statements. User currently will have the ability to add a fixed average monthly income.
* Unable to export transactions to pdf/excel
* Unable to add recurring transactions automatically.