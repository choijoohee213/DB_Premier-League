use [premier league]
go
if object_id('trig_updatePlayers') is not null
drop trigger trig_updatePlayers
go

create trigger trig_updatePlayers
on Players after update 
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
set @c3='dbo.Players';
set @c4='update';
select @c5=player_id+','+player_name+','+team_id+','+goals_scored+','+assists+','+position+','+height+','+weight+','+convert(datetime,birth)+','
+player_country from deleted; 

insert into LogTable values(@c1, @c2, @c3, @c4, @c5);

end
