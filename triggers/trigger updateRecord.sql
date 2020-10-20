use [premier league]
go
if object_id('trig_updateRecord') is not null
drop trigger trig_updateRecord
go

create trigger trig_updateRecord
on Record after update 
as begin
declare 
@c1 datetime, @c2 varchar(30), @c3 varchar(30), @c4 varchar(10), 
@c5 varchar(max) 

set @c1=GETDATE();
set @c2=SUSER_NAME();
set @c3='dbo.Record';
set @c4='update';
select @c5=goal_id+','+goal_player_id+','+assisted_player_id+','+match_id+','+stadium_id+','+team_id from deleted;

insert into LogTable values(@c1, @c2, @c3, @c4, @c5);
end





