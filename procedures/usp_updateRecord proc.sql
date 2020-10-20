use [premier league];
go
if object_id('usp_updateRecord') is not null
drop proc usp_updateRecord

go
create proc usp_updateRecord
@i1 int,
@i2 int,  
@i3 int,
@i4 int ,
@i5 int,
@i6 int
as 
begin
declare @i int
select @i = count(goal_id) from dbo.Record
where goal_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
update dbo.Record
set goal_player_id=@i2, assisted_player_id=@i3, match_id=@i4, stadium_id=@i5, team_id=@i6
where goal_id=@i1
end

/*
exe usp_updateRecord 44,41,29,26,8,8
select * from dbo.Record;
*/