insert into category(id, category_name) values (1,'Travel');
insert into category(id, category_name) values (2,'Auto Loan');
insert into category(id, category_name)  values (3,'Grocery');

insert into user(id, email_address, username) values (1,'Codeengine11@gmail.com', 'Siamak');
insert into user(id, email_address, username) values (2, 'John@john.com','John');
insert into user(id, email_address, username) values (3, 'adam@adam.com','Adam');

insert into expense(id, amount, description, expense_date,  category_id, user_id ) values (100, 155.21, 'New York Business Trip','2019-06-16T17:00:00.000Z', 1,1);
insert into expense values (101, 450.25, 'Ford Mustang Payment','2019-06-15T15:00:00.000Z', 2,2);
insert into expense values(102, 1400, 'Grand Canyon Trip With Family','2019-06-15T16:00:00.000Z', 3,1);



