use [premier league];
go
if object_id('usp_deleteTeams') is not null
drop proc usp_deleteTeams

go
create proc usp_deleteTeams 
@i1 int
as 
begin
declare @i int
select @i = count(team_id) from dbo.Teams
where team_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
delete from dbo.Teams
where team_id=@i1;
end

/*
exec usp_deleteTeams 12
select * from dbo.Teams;
*/