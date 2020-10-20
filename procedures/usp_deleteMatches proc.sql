use [premier league];
go
if object_id('usp_deleteMatches') is not null
drop proc usp_deleteMatches

go
create proc usp_deleteMatches 
@i1 int
as 
begin
declare @i int;
select @i = count(match_id) from dbo.Matches
where match_id=@i1;

if @i=0
raiserror('Record does not exists..',16,1);
else
delete from dbo.Matches
where match_id=@i1;
end
