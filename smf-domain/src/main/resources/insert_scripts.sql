insert into user_entity values(1,2450,null,'test_name',null,'test_last_name',2,'test','test');
insert into fund values(1,2700,'2015-11-08 04:05:06','test_fund',1);

--insert historic expenses
insert into expense values(1,45.5,'FOOD','2015-11-08 04:05:06','test_expense_food_1',true,1);
insert into expense values(2,545,'CLOTHES','2015-11-08 12:05:06','test_expense_clothes_1',true,1);
insert into expense values(3,150,'GAS','2015-09-08 11:05:06','test_expense_gas_1',true,1);
insert into expense values(4,55,'FOOD','2015-09-08 07:05:06','test_expense_food_2',true,1);
insert into expense values(5,250,'BILLS','2015-09-08 04:05:06','test_expense_bills_1',true,1);
insert into expense values(6,25,'FOOD','2015-10-08 08:05:06','test_expense_food_3',true,1);
insert into expense values(7,35,'FOOD','2015-10-08 17:05:06','test_expense_food_4',true,1);

--today's expense
insert into expense values(8,35,'FOOD','2015-12-30 09:05:06','test_expense_food_1',false,1);
insert into expense values(9,150,'CLOTHES','2015-12-30 08:05:06','test_expense_food_2',false,1);
insert into expense values(10,5,'FOOD','2015-12-30 07:05:06','test_expense_food_3',false,1);



--delete
delete from expense;
delete from fund;
delete from user_entity;
