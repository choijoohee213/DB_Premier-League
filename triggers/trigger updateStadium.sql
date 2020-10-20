use [premier league]
go
if object_id('trig_updateStadium') is not null
drop trigger trig_updateStadium
go

create trigger trig_updateStadium
on Stadium after update 
as begin
declare 
@c1 datetime, @c2 varchar(30), @c3 varchar(30), @c4 varchar(10), 
@c5 varchar(max) 

set @c1=GETDATE();
set @c2=SUSER_NAME();
set @c3='dbo.Stadium';
set @c4='update';
select @c5=stadium_id+','+stadium_team_id+','+stadium_capacity+','+
stadium_hometown+','+stadium_name from deleted;

insert into LogTable values(@c1, @c2, @c3, @c4, @c5);
end


