use [premier league]
go

if(object_id('MatchSchedule')) is not null
drop function MatchSchedule;

go
create function MatchSchedule(@tname as varchar(50))
returns table
return 
select TOP 100 * from(
select m.match_date as Match_date,
s.stadium_name as Place,
m.match_round as Match_round,
t1.team_name as Home_team,
case when m.match_status = 1 then m.home_team_score else null end as Home_score,
case when m.match_status = 1 then m.away_team_score else null end as Away_score,
t2.team_name as Away_team
from dbo.Teams t1 ,dbo.Teams t2, dbo.Matches m, dbo.Stadium s
where m.home_team_id = t1.team_id and m.away_team_id = t2.team_id and m.stadium_id = s.stadium_id and t1.team_name=@tname
UNION ALL
select m.match_date as Match_date,
s.stadium_name as Place,
m.match_round as Match_round,
t1.team_name as Home_team,
case when m.match_status = 1 then m.home_team_score else null end as Home_score,
case when m.match_status = 1 then m.away_team_score else null end as Away_score,
t2.team_name as Away_team
from dbo.Teams t1 ,dbo.Teams t2, dbo.Matches m, dbo.Stadium s
where m.home_team_id = t1.team_id and m.away_team_id = t2.team_id and m.stadium_id = s.stadium_id and t2.team_name=@tname) as game
order by match_date;



--select * from MatchSchedule('Liverpool FC');
