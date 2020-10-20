use [premier league];
go
if object_id('usp_updateTeams') is not null
drop proc usp_updateTeams

go
create proc usp_updateTeams
@i1 int,
@i2 int,  
@c1 varchar(50),
@c2 varchar(50),
@i3 int,
@i4 int,
@i5 int,
@i6 int
as 
begin
declare @i int
select @i = count(team_id) from dbo.Teams
where team_id=@i1;

select @i4=count(*) from dbo.Matches as m , dbo.Teams t
where (m.win_team_id = t.team_id) and (m.match_status=1) and (t.team_id=@i1);

select @i5 = count(*) from dbo.Matches as m , dbo.Teams t
where ((m.home_team_id = t.team_id) or (m.away_team_id=t.team_id)) and (m.home_team_score=m.away_team_score) and (win_team_id is null) and (m.match_status=1)
and (t.team_id=@i1);

select @i6 = count(*) from dbo.Matches as m , dbo.Teams t
where ((m.home_team_id = t.team_id) or (m.away_team_id=t.team_id)) and (win_team_id!=t.team_id) and (win_team_id is not null)  and (m.match_status=1)
and (t.team_id=@i1);


set @i3 = 3*@i4 + @i5


if @i=0
raiserror('Record does not exists..',16,1);
else
update dbo.Teams
set stadium_id=@i2,team_name=@c1, team_manager=@c2,team_points=@i3,team_wins=@i4, team_draws=@i5,
team_defeats=@i6
where stadium_id=@i1
end

/*
select * from dbo.Teams;
exec usp_updateTeams 1,1,'Liverpool FC','Jurgen Norbert Klopp',0,0,0,0
exec usp_updateTeams 2,2,'Totten Hotspur FC','Mauricio Pochettino',0,0,0,0
exec usp_updateTeams 3,3,'Manchester United FC','Ole Gunnar Solskjaer',0,0,0,0
exec usp_updateTeams 4,4,'Arsenal FC','Unai Emery',0,0,0,0
exec usp_updateTeams 5,5,'Manchester City FC','Jurgen Norbert Klopp',0,0,0,0
exec usp_updateTeams 6,6,'Chelsea FC','Frank Lampard',0,0,0,0
exec usp_updateTeams 7,7,'Leicester City FC','Brendan Rodgers',0,0,0,0
exec usp_updateTeams 8,8,'Everton FC','Marco Silva',0,0,0,0
exec usp_updateTeams 9,9,'Crystal Palace FC','Roy Hodgson',0,0,0,0
exec usp_updateTeams 10,10,'Burnley FC','Sean Dyche',0,0,0,0
exec usp_updateTeams 11,11,'West Ham United  FC','Manuel Pellegrini',0,0,0,0
exec usp_updateTeams 12,12,'AFC Bournemouth','Eddie Howe',0,0,0,0
*/
