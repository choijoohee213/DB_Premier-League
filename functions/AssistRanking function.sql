use [premier league]
go

if(object_id('AssistRanking')) is not null
drop function AssistRanking;

go
create function AssistRanking()
returns table
return
select RANK() OVER (ORDER BY p.assists desc) as Assist_Rank,
p.player_name as Player_name,
t.team_name as Team_name,
sum(t.team_wins+t.team_draws+t.team_defeats) as Games,
p.assists as Assist
from dbo.Teams t , dbo.Players p
where t.team_id = p.team_id
group by p.player_name, t.team_name, p.assists;

--select * from AssistRanking();

