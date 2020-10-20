use [premier league];
go
if object_id('usp_deletePlayers') is not null
drop proc usp_deletePlayers

go
create proc usp_deletePlayers 
@i1 int
as 
begin
declare @i int
select @i = count(player_id) from dbo.Players
where player_id=@i1;

if @i=0
raiserror('Record does not exists..',16,1);
else
delete from dbo.Players
where player_id=@i1;
end
