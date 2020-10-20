use [premier league]
go
if object_id('trig_updateMatches') is not null
drop trigger trig_updateMatches
go

create trigger trig_updateMatches
on Matches after update 
as
begin
declare 
@c1 datetime, 
@c2 varchar(30), 
@c3 varchar(30), 
@c4 varchar(10), 
@c5 varchar(max) 

set @c1=GETDATE();
set @c2=SUSER_NAME();
set @c3='dbo.Matches';
set @c4='update';
select @c5=match_id+','+stadium_id+','+home_team_id+','+away_team_id+','+win_team_id+','+match_round+','+
convert(datetime,match_date)+','+home_team_score+','+away_team_score+','+match_status from deleted; 

insert into LogTable values(@c1, @c2, @c3, @c4, @c5);
end
