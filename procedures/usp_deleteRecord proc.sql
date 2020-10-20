use [premier league];
go
if object_id('usp_deleteRecord') is not null
drop proc usp_deleteRecord

go
create proc usp_deleteRecord 
@i1 int
as 
begin
declare @i int
select @i = count(goal_id) from dbo.Record
where goal_id=@i1;

if @i=0
raiserror('Record does not exists..',16,1);
else
delete from dbo.Record
where goal_id=@i1;
end
