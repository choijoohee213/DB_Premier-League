use [premier league];
go
if object_id('usp_deleteStadium') is not null
drop proc usp_deleteStadium

go
create proc usp_deleteStadium 
@i1 int
as 
begin
declare @i int
select @i = count(stadium_id) from dbo.Stadium
where stadium_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
delete from dbo.Stadium
where stadium_id=@i1;
end
