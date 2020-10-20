use [premier league]
go

if(object_id('RelativeRecord')) is not null
drop function RelativeRecord;

go
create function RelativeRecord(@tname1 as varchar(40), @tname2 as varchar(40))
returns table
return 
select TOP 100 * from(
select m.match_date as Match_date,
s.stadium_name as Place,
m.match_round as Match_round,
t1.team_name as Home_team,
m.home_team_score as Home_score,
m.away_team_score as Away_score,
t2.team_name as Away_team
from dbo.Teams t1 ,dbo.Teams t2, dbo.Matches m, dbo.Stadium s
where m.home_team_id = t1.team_id and m.away_team_id = t2.team_id and m.stadium_id = s.stadium_id 
and t1.team_name=@tname1 and t2.team_name=@tname2 and m.match_status=1
UNION ALL
select m.match_date as Match_date,
s.stadium_name as Place,
m.match_round as Match_round,
t1.team_name as Home_team,
m.home_team_score as Home_score,
m.away_team_score as Away_score,
t2.team_name as Away_team
from dbo.Teams t1 ,dbo.Teams t2, dbo.Matches m, dbo.Stadium s
where m.home_team_id = t1.team_id and m.away_team_id = t2.team_id and m.stadium_id = s.stadium_id 
and t2.team_name=@tname2 and t2.team_name=@tname1 and m.match_status=1) as game
order by match_date;



--select * from RelativeRecord('Liverpool FC', 'Arsenal FC');

