use [premier league]
go
if object_id('usp_insertRecord') is not null
drop proc usp_insertRecord

go
create proc usp_insertRecord 
@i1 int,
@i2 int,  
@i3 int,
@i4 int ,
@i5 int,
@i6 int
as 
begin
declare @i int
select @i = count(goal_id) from dbo.Record
where goal_id=@i1;

if @i>0
raiserror('Record already exists..',16,1);
else
insert into dbo.Record
values (@i1,@i2,@i3,@i4,@i5,@i6)
end

/*
exec usp_insertRecord 45,41,40,26,8,8
select * from Record;
*/