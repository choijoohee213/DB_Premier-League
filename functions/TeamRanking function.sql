use [premier league]
go

if(object_id('TeamRanking')) is not null
drop function TeamRanking;

go
create function TeamRanking()
returns table
return
select RANK() OVER (ORDER BY sum(team_points) desc) as Team_Rank,
team_name as Team_name,
sum(team_wins+team_draws+team_defeats) as Games,
team_points as Team_point,
team_wins as win,
team_draws as draw,
team_defeats as defeat
from dbo.Teams
group by team_name, team_points, team_wins, team_draws, team_defeats;


--select * from TeamRanking();
