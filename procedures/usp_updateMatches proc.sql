use [premier league];
go
if object_id('usp_updateMatches') is not null
drop proc usp_updateMatches

go
create proc usp_updateMatches 
@i1 int,
@i2 int, 
@i3 int, 
@i4 int,
@i5 int,
@i6 int,
@d1 date,
@i7 int,
@i8 int ,
@b1 int = 1
as 
begin
declare @i int;
select @i = count(match_id) from dbo.Matches
where match_id=@i1;


if @i=0
raiserror('Record does not exists..',16,1);
else
update dbo.Matches
set stadium_id=@i2, home_team_id=@i3, away_team_id=@i4, win_team_id=@i5,
match_round=@i6, match_date=convert(datetime,@d1), home_team_score=@i7, away_team_score=@i8, match_status=@b1
where match_id=@i1
end


--exec usp_updateMatches 29,3,3,1,3,9,'2019-10-21 00:30',2,1,1
--select * from dbo.Matches


