use [premier league];
go
if object_id('usp_updateStadium') is not null
drop proc usp_updateStadium

go
create proc usp_updateStadium
@i1 int,
@i2 int,  
@i3 int,
@c1 varchar(100),
@c2 varchar(100)
as 
begin
declare @i int
select @i = count(stadium_id) from dbo.Stadium
where stadium_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
update dbo.Stadium
set stadium_team_id=@i2, stadium_capacity=@i3,stadium_hometown=@c1,
stadium_name=@c2
where stadium_id=@i1
end

/*
exec usp_updateStadium 12,12,11400,'Bournemouth','Vitality Stadium'
select * from dbo.Stadium;
*/