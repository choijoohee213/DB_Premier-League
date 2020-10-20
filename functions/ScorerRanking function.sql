use [premier league]
go

if(object_id('ScorerRanking')) is not null
drop function ScorerRanking;

go
create function ScorerRanking()
returns table
return
select RANK() OVER (ORDER BY p.goals_scored desc) as Score_Rank,
p.player_name as Player_name,
t.team_name as Team_name,
sum(t.team_wins+t.team_draws+t.team_defeats) as Games,
p.goals_scored as Score
from dbo.Teams t , dbo.Players p
where t.team_id = p.team_id
group by p.player_name, t.team_name, p.goals_scored;


--select * from ScorerRanking();