use [premier league]
go
if object_id('usp_insertMatches') is not null
drop proc usp_insertMatches

go
create proc usp_insertMatches 
@i1 int,
@i2 int, 
@i3 int, 
@i4 int,
@i5 int,
@i6 int,
@d1 date,
@i7 int = 0,
@i8 int = 0,
@b1 int = 1
as 
begin
declare @i int;
select @i = count(match_id) from dbo.Matches
where match_id=@i1;


if @i>0
raiserror('Record already exists..',16,1);
else
insert into dbo.Matches
values (@i1,@i2,@i3,@i4,@i5,@i6, convert(datetime,@d1),@i7,@i8,@b1)
end


/*
select * from dbo.Matches
exec usp_insertMatches 46,2,3,4,5,'dd',6,'2019-10-02',0,0,1
*/