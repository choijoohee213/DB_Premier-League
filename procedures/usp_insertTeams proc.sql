use [premier league]
go
if object_id('usp_insertTeams') is not null
drop proc usp_insertTeams

go
create proc usp_insertTeams 
@i1 int,
@i2 int,  
@c1 varchar(50),
@c2 varchar(50),
@i3 int = 0,
@i4 int = 0,
@i5 int = 0,
@i6 int = 0

as 
begin
declare @i int
select @i = count(team_id) from dbo.Teams
where team_id=@i1;

set @i3 = 3*@i4 + @i5

if @i>0
raiserror('Record already exists..',16,1);
else
insert into dbo.Teams
values (@i1,@i2,@c1,@c2,@i3,@i4 ,@i5 ,@i6)
end
