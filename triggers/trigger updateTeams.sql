use [premier league]
go
if object_id('trig_updateTeams') is not null
drop trigger trig_updateTeams
go

create trigger trig_updateTeams
on Teams after update 
as begin
declare 
@c1 datetime, @c2 varchar(30), @c3 varchar(30), @c4 varchar(10), 
@c5 varchar(max) 

set @c1=GETDATE();
set @c2=SUSER_NAME();
set @c3='dbo.Teams';
set @c4='update';
select @c5=team_id+','+stadium_id+','+team_name+','+team_manager+
','+team_points+','+team_wins+','+team_draws+','+team_defeats from deleted; 

insert into LogTable values(@c1, @c2, @c3, @c4, @c5);
end


