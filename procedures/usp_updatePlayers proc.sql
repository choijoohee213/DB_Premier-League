use [premier league];
go
if object_id('usp_updatePlayers') is not null
drop proc usp_updatePlayers

go
create proc usp_updatePlayers
@i1 int,
@c1 varchar(50), 
@i2 int, 
@i3 int ,
@i4 int ,
@c2 varchar(4),
@i5 int,
@i6 int,
@d1 date,
@c3 varchar(50)
as 
begin
declare @i int
select @i = count(player_id) from dbo.Players
where player_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
update dbo.Players
set player_name=@c1, team_id=@i2, goals_scored=@i3, assists=@i4,
position=@c2, height=@i5, weight=@i6, birth=convert(datetime,@d1), player_country=@c3
where player_id=@i1
end


